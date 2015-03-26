package model;

import java.util.Observable;

import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import client.data.GameInfo;
import client.data.PlayerInfo;
import client.data.RobPlayerInfo;
import client.poller.Poller;
import client.proxy.IProxy;

import java.util.ArrayList;
import java.util.List;

import model.board.Board;
import model.board.City;
import model.board.Settlement;
import model.player.Player;
import model.player.Resources;
import shared.definitions.CatanColor;
import shared.parameters.*;
import shared.response.CreateGameResponse;
import shared.response.GameModelResponse;
import shared.response.ListAIResponse;
import shared.response.ListGamesResponse;
import shared.response.LoginResponse;
import shared.response.StandardResponse;

public class ModelFacade extends Observable implements IModelFacade
{
    private IProxy proxy;
    private Game game;
    private PlayerInfo player;
    private GameInfo gameInfo;
    private Poller poller;
    private boolean hasJoined = false;
    private boolean hasPlayedDevCard;
    private boolean isRoadBuilding;
    private EdgeLocation firstRoadInRoadBuilding;
    private boolean hasRolled;
    
    private PlayerInfo[] players;

	public ModelFacade(IProxy proxy)
	{
		this.proxy = proxy;
		player = new PlayerInfo();
		player.setPlayerIndex(0);
	}

	/**
	 * Singleton implementation
	 */
	private static ModelFacade instance;
	
	public static ModelFacade getInstance()
	{
		if (instance == null) {
			throw new IllegalStateException("Tried to get instance of ModelFacade without initializing it first!");
		}
		return instance;
	}
	public static void createInstance(IProxy proxy)
	{
		instance = new ModelFacade(proxy);
	}

	/**
	 * Observable methods
	 */
	public void modelChanged()
	{
		if (this.getGame() == null) return;
		this.setChanged();
		this.notifyObservers();
	}
	
	// ===============================================================================
	// GETTERS AND SETTERS
	// ===============================================================================
	
	public Resources getPorts()
	{
		return this.getGame().getBoard().getPorts(player.getPlayerIndex());
	}
	public String getState()
	{
		return this.getGame().getTurnTracker().getStatus();
	}
	public void setPlayerInfo(PlayerInfo player)
	{
		this.player=player;
	}
	public GameInfo getGameInfo()
	{
		return gameInfo;
	}
	public void setGameInfo(GameInfo gameInfo)
	{
		this.gameInfo = gameInfo;
	}
	public PlayerInfo getPlayerInfo()
	{
		return player;
	}
	public Player getPlayer()
	{
		return game.getPlayer(player.getPlayerIndex());
	}
	public Game getGame()
	{
            if(game == null)
            {
                updateGameModel();
            }
            return game;
	}
	public void setGame(Game game)
	{
            if(game != null)
            {
		this.game=game;
            }
	}
	public IProxy getProxy() throws Exception
	{
            if (proxy == null) {
                    throw new Exception("Proxy not initialized!");
            }
            return proxy;
	}
	
	public void setPoller(Poller p)
	{
		poller = p;
	}

	public Poller getPoller()
	{
		return poller;
	}
        
        public void setFirstRoadBuidingRoad(EdgeLocation edge)
        {
            this.firstRoadInRoadBuilding = edge;
            isRoadBuilding = true;
        }
	// ===============================================================================
	// CAN-DO METHODS & PRECONDITIONS
	// ===============================================================================

	//--------------------------------------------------------------------------------
	@Override
	public boolean isPlayerTurn()
	{
		if (game == null || game.getTurnTracker() == null || player == null) return false;
		return game.getTurnTracker().getCurrentTurn() == player.getPlayerIndex();
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean canAcceptTrade()
	{
		TradeOffer offer = game.getTradeOffer();
		if (offer==null) return false;
		Player recipient = game.getPlayers()[offer.getReceiver()];
		if (recipient.getPlayerIndex() != player.getPlayerIndex()) return false;

		return (recipient.getResources().contains(offer.getOffer().invert()));
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanDiscardCards(Resources resources)
	{
		if (!game.getTurnTracker().getStatus().equals(TurnTracker.DISCARDING)) 
			{
				return false;
			}
		if (getPlayer().getResources().size() < 8) 
			{
				return false;
			}
		resources.invert();
		boolean valid = getPlayer().getResources().contains(resources);
		resources.invert();
		return valid;
	}

	// Playing Preconditions ================================================================================
	private boolean canPlay()
	{
		if (!isPlayerTurn()) return false;
		if (getState().equals(TurnTracker.FIRSTROUND)) return true;
		if (getState().equals(TurnTracker.SECONDROUND)) return true;
		if (!game.getTurnTracker().getStatus().equals(TurnTracker.PLAYING)) return false;
		return false;
	}
	//--------------------------------------------------------------------------------
	@Override
	public boolean CanRollNumber()
	{
		boolean result = (this.isPlayerTurn() && game.getTurnTracker().getStatus().equals(TurnTracker.ROLLING) && !hasRolled);
		if(result)
		{
			hasRolled = true;
		}
		return result;
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean canPlaceRoad(EdgeLocation edgeLoc, boolean free)
	{
            if (!isPlayerTurn()) {return false;}
            EdgeLocation edge = edgeLoc.getNormalizedLocation();
            Board board = game.getBoard();

            if (board.containsRoad(edge)) {return false;}
            else if (isRoadBuilding && firstRoadInRoadBuilding != null && firstRoadInRoadBuilding.equals(edgeLoc)) {return false;}
            else if (!free && !CanBuyRoad()) {return false;}
            else if (board.hasWaterEdge(edge.getHexLoc(), edge.getDir())) {return false;}
            else if (board.hasNeighborRoad(edge, player.getPlayerIndex(), getState()))  {return true;}
            else if (isRoadBuilding && firstRoadInRoadBuilding.isNeighbor(edgeLoc)) {return true;}
			
            return false;
	}	
 		
	//--------------------------------------------------------------------------------
	@Override
	public boolean canPlaceSettlement(VertexLocation vertLoc, boolean isSetup)
	{
		if (!isPlayerTurn()) {return false;}
		VertexLocation vertex = vertLoc.getNormalizedLocation();
                Board board =  game.getBoard();
		
		if ((!isSetup && !CanBuySettlement())){return false;}
                else if (board.containsStructure(vertex)){return false;}
                else if (board.hasWaterVertex(vertex.getHexLoc(), vertex.getDir())) {return false;}
                else if (!board.hasNeighborRoad(vertex, player.getPlayerIndex())) {return false;}
                else if (board.hasNeighborStructure(vertex)) {return false;}
		
		return true;
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean canPlaceCity(VertexLocation vertLoc)
	{
		if (!canPlay()) return false;
		if (!CanBuyCity()) return false;

		Object obj = game.getBoard().getStructure(vertLoc);
		if (obj == null) return false;
                else if (obj instanceof City) return false;
                else if (((Settlement)(obj)).getOwner() == player.getPlayerIndex()) return true;

		return false;
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanBuyRoad()
	{
		if (getPlayer().getRoads() <= 0) return false;
		Resources hand = getPlayer().getResources();
		return hand.getResourceAmount(ResourceType.WOOD) > 0 &&
				hand.getResourceAmount(ResourceType.BRICK) > 0;
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanBuySettlement()
	{
		
		if (getPlayer().getSettlements() < 1) return false;
		Resources hand = getPlayer().getResources();
		return hand.getResourceAmount(ResourceType.WOOD) > 0 &&
				hand.getResourceAmount(ResourceType.SHEEP) > 0 &&
				hand.getResourceAmount(ResourceType.WHEAT) > 0 &&
				hand.getResourceAmount(ResourceType.BRICK) > 0;
				
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanBuyCity()
	{
		if (getPlayer().getCities() < 1) return false;
		Resources hand = getPlayer().getResources();
		return hand.getResourceAmount(ResourceType.WHEAT) > 1 &&
				hand.getResourceAmount(ResourceType.ORE) > 2;
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanOfferTrade(Resources offer)
	{
		if (!canPlay()) return false;
		return getPlayer().getResources().contains(offer);
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanMaritimeTrade(int ratio, String inputResource, String outResource)
	{
		if (!canPlay()) return false;
		if (getPlayer().getResources().getResourceAmount(inputResource) < ratio) return false;
		if (game.getBoard().hasPort(player.getPlayerIndex(), inputResource, ratio)) return true;

		return false;
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean canPlaceRobber(HexLocation hexLoc)
	{
                Board board = game.getBoard();
                if(board.getRobber().equals(hexLoc)) return false;
                else if(board.isWaterHex(hexLoc)) return false;
                return true;
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean canRobPlayer(HexLocation location, int victimIndex)
	{
		if (!canPlay()) return false;
		if (!canPlaceRobber(location) ||
		game.getPlayers()[victimIndex].getResources().size()<1) return false;
		return true; 
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanFinishTurn()
	{
		return canPlay();
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanBuyDevCard()
	{
		if (!canPlay()) return false;
		if (game.getDeck().size() < 1) return false;
		Resources hand = getPlayer().getResources();
		return hand.getResourceAmount(ResourceType.WHEAT) > 0 &&
				hand.getResourceAmount(ResourceType.SHEEP) > 0 &&
				hand.getResourceAmount(ResourceType.ORE) > 0;
	}
	
	// DevCard Preconditions ================================================================================
	public boolean canPlayDevCard(DevCardType devCard)
	{
		if (!isPlayerTurn() || hasPlayedDevCard ||
			!getState().equals(TurnTracker.PLAYING)
			|| !getPlayer().getOldDevCards().hasDevCard(devCard)
			//|| getPlayer().isPlayedDevCard()
			) return false;
		return true;
	}
	
	//--------------------------------------------------------------------------------
	@Override
	public boolean CanUseSoldier(int victimIndex, HexLocation location)
	{
		if (!canPlayDevCard(DevCardType.SOLDIER) ||
			!canRobPlayer(location, victimIndex)
			) return false;
		return true;
	}
        
        public boolean canUseSoldierOfficial()
        {
            return canPlayDevCard(DevCardType.SOLDIER);
        }

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanUseYearOfPlenty(String resource1, String resource2)
	{
		if (!canPlayDevCard(DevCardType.YEAR_OF_PLENTY)) return false;
		if (game.getBank().getResourceAmount(resource1) > 0 &&
			game.getBank().getResourceAmount(resource2) > 0
			) return true;
		return false;
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanUseRoadBuilder(EdgeLocation spot1, EdgeLocation spot2)
	{
            if (!canPlayDevCard(DevCardType.ROAD_BUILD)) {System.out.println("Can't play card"); return false;}
            else if(!canPlaceRoad(spot1, true)) {System.out.println("Can't place spot1"); return false;}
            else if (getPlayer().getRoads() < 2) {System.out.println("Doesn't have 2 roads left"); return false;}
            else if (game.getBoard().containsRoad(spot2)) {System.out.println("Can't place spot2"); return false;}
            else if (spot1.equals(spot2)) {System.out.println("Spot1 and 2 are the same"); return false;}

            if (game.getBoard().hasNeighborRoad(spot2, getPlayer().getPlayerIndex(), getState()))
            {
                    return true;
            }

            return false;
	}
        
        public boolean canUseRoadBuilderOfficial()
        {
            if (!canPlayDevCard(DevCardType.ROAD_BUILD)) {return false;}
                else if (getPlayer().getRoads() < 2) {return false;}	
            return true;
        }

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanUseMonopoly(String resource)
	{
		return canPlayDevCard(DevCardType.MONOPOLY);
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanUseMonument()
	{
            if (!isPlayerTurn()) return false;
            else if (!getState().equals(TurnTracker.PLAYING)) return false;
            else if (!getPlayer().getOldDevCards().hasDevCard(DevCardType.MONUMENT)) return false;
            else if (getPlayer().isPlayedDevCard()) return false;
            return true;
	}

	
	// ===============================================================================
	// PROXY METHODS & POST CONDITIONS
	// ===============================================================================

	//--------------------------------------------------------------------------------
	@Override
	public void sendChat(SendChatParam param)
	{
            GameModelResponse response = proxy.sendChat(param);
            if(response.isValid())
            {
                game = response.getGame();
                update();
                //updateGameModel();
            }
	}

	//--------------------------------------------------------------------------------
	@Override
	public void acceptTrade(boolean accept)
	{
		Player receiver = game.getPlayers()[game.getTradeOffer().getReceiver()];
		GameModelResponse response = proxy.acceptTrade(new AcceptTradeParam(receiver.getPlayerIndex(), accept));
		if (response.isValid())
                {
                    game = response.getGame();
                    update();
                    //updateGameModel();
                }
	}
	
	//--------------------------------------------------------------------------------
	@Override
	public void discardCards(Resources resources)
	{
		GameModelResponse response = proxy.discardCards(new DiscardCardsParam(0, resources));
		if (response.isValid())
                {
                    game = response.getGame();
                    update();
                    //updateGameModel();
                }
	}

	//--------------------------------------------------------------------------------
	@Override
	public void rollNumber(int total, int dontUseThis)
	{
		int playerIndex = this.getPlayerInfo().getPlayerIndex();
		GameModelResponse response = proxy.rollNumber(new RollNumParam(playerIndex, total));
		if (response.isValid())
                {
                    game = response.getGame();
                    update();
                    hasPlayedDevCard = false;
                    this.hasRolled = true;
                    this.getPoller().stop();
                    //updateGameModel();
                }     
	}
        

	//--------------------------------------------------------------------------------
	@Override
	public void buildRoad(EdgeLocation edge, boolean free)
	{
		GameModelResponse response = proxy.buildRoad(new BuildRoadParam
		        (game.getTurnTracker().getCurrentTurn(), edge, free));
		if (response.isValid())
                {
                    game = response.getGame();
                    update();
                    //updateGameModel();
                }
	}

	//--------------------------------------------------------------------------------
	@Override
	public void buildSettlement(VertexLocation vert, boolean free)
	{
		GameModelResponse response = proxy.buildSettlement(new BuildSettlementParam(
				player.getPlayerIndex(), vert, free));
		if (response.isValid())
                {
                    game = response.getGame();
                    update();
                    //updateGameModel();
                }
	}

	//--------------------------------------------------------------------------------
	@Override
	public void buildCity(VertexLocation vert)
	{
		GameModelResponse response = proxy.buildCity(
				new BuildCityParam(player.getPlayerIndex(), vert));
		if (response.isValid())
                {
                    game = response.getGame();
                    update();
                    //updateGameModel();
                }
	}

	//--------------------------------------------------------------------------------
	@Override
	public void offerTrade(int receiver, Resources resources)
	{
	    GameModelResponse response = proxy.offerTrade(new OfferTradeParam(
	    		player.getPlayerIndex(), receiver, resources));
		if (response.isValid())
		{
			game = response.getGame();
			update();
			//updateGameModel();
		}
	}

	//--------------------------------------------------------------------------------
	@Override
	public void maritimeTrade(int ratio, String inputResource, String outResource)
	{
        GameModelResponse response = proxy.maritimeTrade(
		new MaritimeTradeParam(player.getPlayerIndex(),
		ratio, inputResource, outResource));
		if (response.isValid())
                {
                    game = response.getGame();
                    update();
                    //updateGameModel();
                }
	}

	//--------------------------------------------------------------------------------
	@Override
	public void robPlayer(HexLocation location, int victimIndex)
	{
		GameModelResponse response = proxy.robPlayer(new RobPlayerParam(
				player.getPlayerIndex(), victimIndex, location));
		if (response.isValid())
                {
                    game = response.getGame();
                    update();
                    //updateGameModel();
                }
	}

	//--------------------------------------------------------------------------------
	@Override
	public void finishTurn()
	{
		
        GameModelResponse response = proxy.finishTurn(
        		new FinishTurnParam(player.getPlayerIndex()));
		if (response.isValid())
                {
					poller.pollerStart();
                    game = response.getGame();
                    update();
                    //updateGameModel();
                    hasRolled = false;
                }
	}

	//--------------------------------------------------------------------------------
	@Override
	public void buyDevCard()
	{
		GameModelResponse response = proxy.buyDevCard(
				new BuyDevCardParam(player.getPlayerIndex()));
		if (response.isValid())
                {
                    game = response.getGame();
                    update();
                    //updateGameModel();
                }
	}

	//--------------------------------------------------------------------------------
	@Override
	public void playSoldierCard(int victimIndex, HexLocation location)
	{
		GameModelResponse response = proxy.playSoldier(new PlaySoldierParam(
				player.getPlayerIndex(), victimIndex, location));
		if (response.isValid()) 
                {
                    game = response.getGame();
                    update();
                    hasPlayedDevCard = true;
                    //updateGameModel();
                }
	}

	//--------------------------------------------------------------------------------
	@Override
	public void playYearOfPlentyCard(String resource1, String resource2)
	{
            GameModelResponse response = proxy.playYearOfPlenty(new PlayYearOfPlentyParam(
        		player.getPlayerIndex(), resource1, resource2));
            if (response.isValid())
            {
                game = response.getGame();
                update();
                hasPlayedDevCard = true;
                //updateGameModel();
            }
	}

	//--------------------------------------------------------------------------------
	@Override
	public void playRoadCard(EdgeLocation spot1, EdgeLocation spot2)
	{
		GameModelResponse response = proxy.playRoadBuilding(new PlayRoadBuildingParam(
				player.getPlayerIndex(), firstRoadInRoadBuilding, spot2));
		if (response.isValid())
                {
                    game = response.getGame();
                    update();
                    hasPlayedDevCard = true;
                    isRoadBuilding = false;
                    firstRoadInRoadBuilding = null;
                    //updateGameModel();
                }
	}

	//--------------------------------------------------------------------------------
	@Override
	public void playMonopolyCard(String resource)
	{
            GameModelResponse response = proxy.playMonopoly(new PlayMonopolyParam(
                            player.getPlayerIndex(), resource));
            if (response.isValid())
            {
                game = response.getGame();
                update();
                hasPlayedDevCard = true;
		//updateGameModel();  
            }
	}

	//--------------------------------------------------------------------------------
	@Override
	public void playMonumentCard()
	{
            GameModelResponse response = proxy.playMonument(new PlayMonumentParam(
                            player.getPlayerIndex()));
            if (response.isValid())
            {
                game = response.getGame();
                update();
                //updateGameModel();
            }
	}
        
	//--------------------------------------------------------------------------------        
	
	//================================================================================
	// MISC PROXY FUNCTIONS
	//================================================================================
	
	//--------------------------------------------------------------------------------
	public LoginResponse login(CredentialsParam params)
	{
		LoginResponse response = proxy.login(params);
		if(response.isValid())
		{
			player = response.getPlayerInfo();
			player.setName(params.getUser());
		}
		
		return response;
	}
	
	//--------------------------------------------------------------------------------
	public LoginResponse register(CredentialsParam params)
	{
		LoginResponse response = proxy.register(params);
		if(response.isValid())
		{
			player = response.getPlayerInfo();
			player.setName(params.getUser());
		}
		
		return response;
	}
	
	//--------------------------------------------------------------------------------
	public StandardResponse joinGame(JoinGameParam params)
	{
            StandardResponse response = proxy.joinGame(params);
            if(response.isValid())
            {
                this.player.setColor(CatanColor.stringToColor(params.getColor()));
                this.hasJoined = true;
            }
                    return response;
	}

	public boolean isHasJoined()
	{
		return hasJoined;
	}
	public void setHasJoined(boolean hasJoined)
	{
		this.hasJoined = hasJoined;
	}
	//--------------------------------------------------------------------------------
	public CreateGameResponse createGame(CreateGameParam params)
	{
	    CreateGameResponse response = proxy.createGame(params);
	    if(response.isValid())
	    {
	    	proxy.joinGame(new JoinGameParam(response.getGameId(), "white"));
	    }
		return response;
	}

	//--------------------------------------------------------------------------------
	public ListGamesResponse listGames()
	{
		return proxy.listGames();
	}

	//--------------------------------------------------------------------------------
	public StandardResponse addAi(AddAiParam input)
	{
		return proxy.addAi(input);
	}
	//---------------------------------------------------------------------------------
	public ListAIResponse listAi()
	{
		return proxy.listAi();
	}
	//---------------------------------------------------------------------------------
	public void resetGame()
	{
            GameModelResponse response = proxy.resetGame();
            if(response.isValid())
            {
                game = response.getGame();
                update();
                //updateGameModel();
            }
	}
	//---------------------------------------------------------------------------------
	public void updateGameModel()
	{
            GameModelResponse response = proxy.getGameModel();
            if(response.isValid())
            {
                game = response.getGame();
                game.getBoard().sort();
                this.modelChanged();
            }
	}
        
        public void update()
        {
            game.getBoard().sort();
            this.modelChanged();
        }
	//---------------------------------------------------------------------------------
        public RobPlayerInfo[] getRobPlayerInfoList(HexLocation hexLoc)
	{
            Object[] objects = game.getBoard().getStructure(hexLoc);
            List<RobPlayerInfo> playersToRob = new ArrayList<RobPlayerInfo>();
            PlayerInfo[] playerInfo = getPlayerInfoList();
            Player[] players = game.getPlayers();
            
            for(Player p : players)
            {
                if(p.getPlayerIndex() != player.getPlayerIndex())
                {
                    for(Object obj : objects)
                    {
                        if(obj != null)
                        {
                            if(obj instanceof Settlement)
                            {
                                Settlement s = (Settlement) obj;
                                if(s.getOwner() == p.getPlayerIndex())
                                {
                                    playersToRob.add(new RobPlayerInfo(playerInfo[s.getOwner()]));
                                    int index = playersToRob.size() - 1;
                                    playersToRob.get(index).setNumCards(p.getNumCards());
                                    playersToRob.get(index).setLocation(hexLoc);
                                    break;
                                }
                            } 
                            else //if city
                            {
                                City c = (City) obj;
                                if(c.getOwner() == p.getPlayerIndex())
                                {
                                    playersToRob.add(new RobPlayerInfo(playerInfo[c.getOwner()]));
                                    int index = playersToRob.size() - 1;
                                    playersToRob.get(index).setNumCards(p.getNumCards());
                                    playersToRob.get(index).setLocation(hexLoc);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            
            /*int index = 0;
            for(Object obj : objects)
            {
                if(obj != null)
                {
                    if(obj instanceof Settlement)
                    {
                        Settlement s = (Settlement) obj;
                        if(s.getOwner() != game.getPlayer().getPlayerID())
                        {
                            playersToRob.add(new RobPlayerInfo(playerInfo[s.getOwner()]));
                            Player[] p = game.getPlayers();
                            playersToRob.get(index).setNumCards(p[s.getOwner()].getNumCards());
                            playersToRob.get(index).setLocation(hexLoc);
                        }
                    }
                    else //if(obj instanceof City)
                    {
                        City c = (City) obj;
                        if(c.getOwner() != game.getPlayer().getPlayerID())
                        {
                            playersToRob.add(new RobPlayerInfo(playerInfo[c.getOwner()]));
                            Player[] p = game.getPlayers();
                            playersToRob.get(index).setNumCards(p[c.getOwner()].getNumCards());
                            playersToRob.get(index).setLocation(hexLoc);
                        }
                    }
                    
                    index++;
                }
            }*/
            RobPlayerInfo[] arr;
            if(playersToRob.isEmpty())
            {
                arr = null;
            }
            else
            {
                arr = new RobPlayerInfo[playersToRob.size()];
                for(int i = 0; i < playersToRob.size(); i++)
                {
                    arr[i] = playersToRob.get(i);
                }
            }
            return arr;
        }
	//---------------------------------------------------------------------------------
	public boolean checkGameFull()
	{
		ListGamesResponse response = proxy.listGames();
		int gameId = proxy.getGameId();
		GameInfo[] games = response.getGameListObject();
		//check the games in the game list for matching desired id and for capacity less than 
		for(int i = 0; i < games.length; i++)
		{
			if(gameId == games[i].getId())
			{
				for(int j = 0; j <games[i].getPlayers().size(); j++ )
				{
					
					if(games[i].getPlayers().size() < 4 || games[i].getPlayers().get(j).getId() == -1)
					{
						return false;
					}
				}
				return true;
			}
		}
		return true;
	}
	
	//---------------------------------------------------------------------------------
	public int getGameId()
	{
		return proxy.getGameId();
	}

	//---------------------------------------------------------------------------------
	
	/***
	 * Will call the list games and pull the players from there as it is in the desired format for parts of the view controllers.
	 * Different from the players stored in the game object.
	 * @return
	 */
	public PlayerInfo[] getPlayerInfoList()
	{
		ListGamesResponse response = proxy.listGames();
		if (!response.isValid()) return null;
		setGameInfo(response.getGameListObject(gameInfo.getId()));
		players = gameInfo.getPlayers().toArray(new PlayerInfo[0]);
		return players;

		
// The following code repeats what response.getGameListObject() already does:
		
/*                if(!response.isValid())
                {
                    return null;
                }
		int gameId = proxy.getGameId();
		GameInfo[] games = response.getGameListObject();
		
		int gameindex = -1;
		int numPlayers = 0;
		for(int i = 0; i < games.length; i++)
		{
			if(gameId == games[i].getId())
			{
				gameindex = i;
				for(int j = 0; j <games[i].getPlayers().size(); j++ )
				{
					
					if(games[i].getPlayers().get(j).getId() != -1)
					{
						numPlayers ++;
					
					}
					
				}
				break;
			}
		}
		
		players = new PlayerInfo[numPlayers];
		for(int k = 0; k < numPlayers; k++)
		{
			players[k] = games[gameindex].getPlayers().get(k);
		}
*/		
		
	}

	//---------------------------------------------------------------------------------
	public boolean isGameFull()
	{
		ListGamesResponse response = proxy.listGames();
		if (!response.isValid()) return false;
		setGameInfo(response.getGameListObject(gameInfo.getId()));
		return gameInfo.getPlayers().size()==4;
	}
	//---------------------------------------------------------------------------------
	public Player[] getPlayers()
	{
		return game.getPlayers();
	}
	
}

	//================================================================================
	// END
	//================================================================================