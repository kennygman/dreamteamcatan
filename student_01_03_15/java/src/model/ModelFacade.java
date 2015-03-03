package model;

import java.util.Observable;

import shared.definitions.DevCardType;
import shared.definitions.PortType;
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
import model.board.Port;
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
    private boolean startRoad = true;
    private boolean startSettlement = false;
    private boolean hasPlayedDevCard;
    private boolean hasRolled;
    
    private PlayerInfo[] players;
    private boolean fullGame = false;

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
	
	public List<Port> getPorts()
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
	// ===============================================================================
	// CAN-DO METHODS & PRECONDITIONS
	// ===============================================================================

	//--------------------------------------------------------------------------------
	@Override
	public boolean isPlayerTurn()
	{
            return game.getTurnTracker().getCurrentTurn() == game.getPlayer().getPlayerIndex();
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean canAcceptTrade()
	{
		TradeOffer offer = game.getTradeOffer();
		if (offer==null) return false;
		Player recipient = game.getPlayers()[offer.getReciever()];
		
		if (recipient.getPlayerIndex() != game.getPlayer().getPlayerIndex()) return false;
		return (recipient.getResources().contains(offer.getOffer()));
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanDiscardCards(Resources resources)
	{
		if (!game.getTurnTracker().getStatus().equals("Discarding")) 
			{
				return false;
			}
		if (game.getPlayer().getResources().size() < 8) 
			{
				return false;
			}
		resources.invert();
		boolean valid = game.getPlayer().getResources().contains(resources);
		resources.invert();
		return valid;
	}

	// Playing Preconditions ================================================================================
	private boolean canPlay()
	{
		boolean valid = true;
		if (!isPlayerTurn() ) valid = false;
		if (!game.getTurnTracker().getStatus().equals("Playing")) valid = false;
		return valid;
	}
	//--------------------------------------------------------------------------------
	@Override
	public boolean CanRollNumber()
	{
		boolean result = (this.isPlayerTurn() && game.getTurnTracker().getStatus().equals("Rolling") && !hasRolled);
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
            else if (!free && !CanBuyRoad()) {return false;}
            else if (board.hasWaterEdge(edge.getHexLoc(), edge.getDir())) {return false;}
            else if (board.hasNeighborRoad(edge, player.getPlayerIndex(), getState()))  {return true;}
			
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
		if (game.getPlayer().getRoads() <= 0) return false;
		Resources hand = game.getPlayer().getResources();
		return hand.getResourceAmount(ResourceType.WOOD) > 0 &&
				hand.getResourceAmount(ResourceType.BRICK) > 0;
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanBuySettlement()
	{
		
		if (game.getPlayer().getSettlements() < 1) return false;
		Resources hand = game.getPlayer().getResources();
		return hand.getResourceAmount(ResourceType.WOOD) > 0 &&
				hand.getResourceAmount(ResourceType.SHEEP) > 0 &&
				hand.getResourceAmount(ResourceType.WHEAT) > 0 &&
				hand.getResourceAmount(ResourceType.BRICK) > 0;
				
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanBuyCity()
	{
		if (game.getPlayer().getCities() < 1) return false;
		Resources hand = game.getPlayer().getResources();
		return hand.getResourceAmount(ResourceType.WHEAT) > 1 &&
				hand.getResourceAmount(ResourceType.ORE) > 2;
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanOfferTrade(Resources offer)
	{
		if (!canPlay()) return false;
		offer.invert();
		boolean valid = game.getPlayer().getResources().contains(offer);
		offer.invert();
		return valid;
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanMaritimeTrade(int ratio, String inputResource, String outResource)
	{
		Resources resources = this.getGame().getPlayer().getResources();
		if (!canPlay()) return false;
		if (game.getPlayer().getResources().getResourceAmount(outResource) < ratio) return false;
		if (ratio == 4 && resources.getResourceAmount(inputResource) >= 4)return true;
		if (game.getBoard().hasPort(game.getPlayer().getPlayerIndex(), outResource))return true;

		return false;
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean canPlaceRobber(HexLocation hexLoc)
	{
		return !game.getBoard().getRobber().equals(hexLoc);
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
		Resources hand = game.getPlayer().getResources();
		return hand.getResourceAmount(ResourceType.WHEAT) > 0 &&
				hand.getResourceAmount(ResourceType.SHEEP) > 0 &&
				hand.getResourceAmount(ResourceType.ORE) > 0;
	}
	
	// DevCard Preconditions ================================================================================
	public boolean canPlayDevCard(DevCardType devCard)
	{
		if (!isPlayerTurn() || hasPlayedDevCard ||
			!getState().equals("Playing")
			|| !game.getPlayer().getOldDevCards().hasDevCard(devCard)
			|| game.getPlayer().isPlayedDevCard()
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
                else if (game.getPlayer().getRoads() < 2) {System.out.println("Doesn't have 2 roads left"); return false;}
                else if (game.getBoard().containsRoad(spot2)) {System.out.println("Can't place spot2"); return false;}
                else if (spot1.equals(spot2)) {System.out.println("Spot1 and 2 are the same"); return false;}
		
		if (game.getBoard().hasNeighborRoad(spot2, game.getPlayer().getPlayerIndex(), getState()))
		{
			
			return true;
		}
			
		
//		System.out.println("The spot2 doesn't have a neighbor");
		return false;
	}
        
        public boolean canUseRoadBuilderOfficial()
        {
            if (!canPlayDevCard(DevCardType.ROAD_BUILD)) {return false;}
                else if (game.getPlayer().getRoads() < 2) {return false;}	
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
		if (!canPlayDevCard(DevCardType.MONUMENT)) return false;
		if ( game.getPlayer().getVictoryPoints() +
				game.getPlayer().getNewDevCards().getMonument() +
				game.getPlayer().getOldDevCards().getMonument() >= 10) return true;
		return false;
	}

	
	// ===============================================================================
	// PROXY METHODS & POST CONDITIONS
	// ===============================================================================

	//--------------------------------------------------------------------------------
	@Override
	public void sendChat(SendChatParam param)
	{
		Game newGame = proxy.sendChat(param).getGame();
		game = newGame;
		this.modelChanged();
	}

	//--------------------------------------------------------------------------------
	@Override
	public void acceptTrade(boolean accept)
	{
		Player reciever = game.getPlayers()[game.getTradeOffer().getReciever()];
                GameModelResponse response = proxy.acceptTrade(
				new AcceptTradeParam(reciever.getPlayerIndex(), accept));
                if(response.isValid())
                {
                    Game newGame = response.getGame();
                    if (!accept) return;
                    game.setTradeOffer(newGame.getTradeOffer());
                    updateGameModel();
                }
	}
	
	//--------------------------------------------------------------------------------
	@Override
	public void discardCards(Resources resources)
	{
		Player p = game.getPlayer();
                GameModelResponse response = proxy.discardCards(new DiscardCardsParam(0, resources));
                if(response.isValid())
                {
                    game = response.getGame();
                    updateGameModel();
                }
	}

	//--------------------------------------------------------------------------------
	@Override
	public void rollNumber(int total, int dontUseThis)
	{

		int playerIndex = this.getPlayerInfo().getPlayerIndex();
		GameModelResponse response = proxy.rollNumber(new RollNumParam(playerIndex, total));
        if(response.isValid())
        {
            updateGameModel();
            hasPlayedDevCard = false;
 //           System.out.println("inRoll");
            this.hasRolled = true;
        }
                
	}
        

	//--------------------------------------------------------------------------------
	@Override
	public void buildRoad(EdgeLocation edge, boolean free)
	{
                GameModelResponse response = proxy.buildRoad(new BuildRoadParam
                        (game.getTurnTracker().getCurrentTurn(), edge, free));
                if(response.isValid())
                {
                    game = response.getGame();
                    updateGameModel();
                }
	}

	//--------------------------------------------------------------------------------
	@Override
	public void buildSettlement(VertexLocation vert, boolean free)
	{
            
            
                GameModelResponse response = proxy.buildSettlement(new BuildSettlementParam(
				game.getPlayer().getPlayerIndex(), vert, free));
                if(response.isValid())
                {
                    game = response.getGame();
                    updateGameModel();
                }
	}

	//--------------------------------------------------------------------------------
	@Override
	public void buildCity(VertexLocation vert)
	{
                GameModelResponse response = proxy.buildCity(
				new BuildCityParam(game.getPlayer().getPlayerIndex(), vert));
                if(response.isValid())
                {
                    game = response.getGame();
                    updateGameModel();
                }
	}

	//--------------------------------------------------------------------------------
	@Override
	public void offerTrade(int receiver, Resources resources)
	{
                GameModelResponse response = proxy.offerTrade(new OfferTradeParam(
			game.getPlayer().getPlayerIndex(), receiver, resources));
                if(response.isValid())
                {
                    game = response.getGame();
                    updateGameModel();
                }
	}

	//--------------------------------------------------------------------------------
	@Override
	public void maritimeTrade(int ratio, String inputResource, String outResource)
	{
		Player p = game.getPlayer();
                GameModelResponse response = proxy.maritimeTrade(
				new MaritimeTradeParam(game.getPlayer().getPlayerIndex(),
				ratio, inputResource, outResource));
                if(response.isValid())
                {
                    Game newGame =  response.getGame();
                    game.setBank(newGame.getBank());
                    p.setResources(newGame.getPlayers()[p.getPlayerIndex()].getResources());
                }
	}

	//--------------------------------------------------------------------------------
	@Override
	public void robPlayer(HexLocation location, int victimIndex)
	{
		Player p = game.getPlayer();
                GameModelResponse response = proxy.robPlayer(new RobPlayerParam(
			game.getPlayer().getPlayerIndex(), victimIndex, location));
                if(response.isValid())
                {
                    Game newGame =  response.getGame();
                    game.getBoard().setRobber(newGame.getBoard().getRobber());
                    p.setResources(newGame.getPlayers()[p.getPlayerIndex()].getResources());
                    updateGameModel();
                }
	}

	//--------------------------------------------------------------------------------
	@Override
	public void finishTurn()
	{
		Player p = game.getPlayer();
        GameModelResponse response = proxy.finishTurn(new FinishTurnParam(p.getPlayerIndex()));
        if(response.isValid())
        {
        	game = response.getGame();
            updateGameModel();
            hasRolled = false;
        }
	}

	//--------------------------------------------------------------------------------
	@Override
	public void buyDevCard()
	{
		Player p = game.getPlayer();
		 
                GameModelResponse response = proxy.buyDevCard(
			new BuyDevCardParam(p.getPlayerIndex()));
                if(response.isValid())
                {
                    p = response.getGame().getPlayer();
/*                	System.out.println("player's monopoly" + p.getNewDevCards().getMonopoly());
                	System.out.println("player's monument" + p.getNewDevCards().getMonument());
                	System.out.println("player's road building" + p.getNewDevCards().getRoadBuilding());
                	System.out.println("player's soldier" + p.getNewDevCards().getSoldier());
                	System.out.println("player's year of p" + p.getNewDevCards().getYearOfPlenty());
*/	                updateGameModel();
                }
	}

	//--------------------------------------------------------------------------------
	@Override
	public void playSoldierCard(int victimIndex, HexLocation location)
	{
                GameModelResponse response = proxy.playSoldier(new PlaySoldierParam(
                game.getPlayer().getPlayerIndex(), victimIndex, location));
                if(response.isValid())
                {
                    game = response.getGame();
                    hasPlayedDevCard = true;
//                    System.out.println("inSoldier");
                    updateGameModel();
                }
	}

	//--------------------------------------------------------------------------------
	@Override
	public void playYearOfPlentyCard(String resource1, String resource2)
	{
		Player p = game.getPlayer();
        GameModelResponse response = proxy.playYearOfPlenty(new PlayYearOfPlentyParam(
		game.getPlayer().getPlayerIndex(), resource1, resource2));
        if(response.isValid())
        {
            Game newGame = response.getGame();
            p.setResources(newGame.getPlayers()[p.getPlayerIndex()].getResources());
            hasPlayedDevCard = true;
//            System.out.println("inYOP");
            updateGameModel();
           
        }
	}

	//--------------------------------------------------------------------------------
	@Override
	public void playRoadCard(EdgeLocation spot1, EdgeLocation spot2)
	{
		Player p = game.getPlayer();
        GameModelResponse response = proxy.playRoadBuilding(new PlayRoadBuildingParam(
		game.getPlayer().getPlayerIndex(), spot1, spot2));
        if(response.isValid())
        {
            Game newGame = response.getGame();
            p.setRoads(newGame.getPlayers()[p.getPlayerIndex()].getRoads());
            game.getBoard().setRoads(newGame.getBoard().getRoads());
            game.getBoard().sort();
            game.getTurnTracker().setLongestRoad(newGame.getTurnTracker().getLongestRoad());
            hasPlayedDevCard = true;
//            System.out.println("inRoad");
            updateGameModel();
        }
	}

	//--------------------------------------------------------------------------------
	@Override
	public void playMonopolyCard(String resource)
	{
		Player p = game.getPlayer();
        GameModelResponse response = proxy.playMonopoly(new PlayMonopolyParam(
		game.getPlayer().getPlayerIndex(), resource));
        if(response.isValid())
        {
            Game newGame = response.getGame();
            p.setResources(newGame.getPlayers()[p.getPlayerIndex()].getResources());
            hasPlayedDevCard = true;
//            System.out.println("inMonopoly");
            updateGameModel();  
        }
	}

	//--------------------------------------------------------------------------------
	@Override
	public void playMonumentCard()
	{
		Player p = game.getPlayer();
        GameModelResponse response = proxy.playMonument(new PlayMonumentParam(
		game.getPlayer().getPlayerIndex()));
        if(response.isValid())
        {
            Game newGame = response.getGame();
            p.setVictoryPoints(newGame.getPlayers()[p.getPlayerIndex()].getVictoryPoints());
            updateGameModel();
        }
	}
        
        //--------------------------------------------------------------------------------        
        public boolean isSetUpRoad()
        {
            return startRoad;
        }
        
        public boolean isSetUpSettlement()
        {
            return startSettlement;
        }
        
        public void setSetUpRoad(boolean s)
        {
            startRoad = s;
        }
        
        public void setSetUpSettlement(boolean s)
        {
            startSettlement = s;
        }
        
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
//            GameModelResponse gameResponse = proxy.getGameModel();
//	        if(response.isValid())
//	        {
//	            game = gameResponse.getGame();
//	        }
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
	    	StandardResponse joinResponse = proxy.joinGame(new JoinGameParam(response.getGameId(), "white"));
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
		proxy.resetGame().getGame();
		updateGameModel();
	}
	//---------------------------------------------------------------------------------
	public void updateGameModel()
	{
//		if(game != null)
//		{
	        GameModelResponse response = proxy.getGameModel();
	        if(response.isValid())
	        {
	            game = response.getGame();
	            this.modelChanged();
	        }
//		}
	}

	//---------------------------------------------------------------------------------
        public RobPlayerInfo[] getRobPlayerInfoList(HexLocation hexLoc)
	{
            Object[] objects = game.getBoard().getStructure(hexLoc);
            List<RobPlayerInfo> players = new ArrayList<RobPlayerInfo>();
            PlayerInfo[] playerInfo = getPlayerInfoList();
            
            int index = 0;
            for(Object obj : objects)
            {
                if(obj != null)
                {
                    if(obj instanceof Settlement)
                    {
                        Settlement s = (Settlement) obj;
                        if(s.getOwner() != game.getPlayer().getPlayerID())
                        {
                            players.add(new RobPlayerInfo(playerInfo[s.getOwner()]));
                            Player[] p = game.getPlayers();
                            players.get(index).setNumCards(p[s.getOwner()].getNumCards());
                            players.get(index).setLocation(hexLoc);
                        }
                    }
                    else //if(obj instanceof City)
                    {
                        City c = (City) obj;
                        if(c.getOwner() != game.getPlayer().getPlayerID())
                        {
                            players.add(new RobPlayerInfo(playerInfo[c.getOwner()]));
                            Player[] p = game.getPlayers();
                            players.get(index).setNumCards(p[c.getOwner()].getNumCards());
                            players.get(index).setLocation(hexLoc);
                        }
                    }
                    
                    index++;
                }
            }
            RobPlayerInfo[] arr = new RobPlayerInfo[players.size()];
            for(int i = 0; i < players.size(); i++)
            {
                arr[i] = players.get(i);
            }
/*            System.out.println("size of infoList: " + players.size());
            System.err.println(players.toString());
*/            return arr;
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
                if(!response.isValid())
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
		
		
		return players;
		
	}

	//---------------------------------------------------------------------------------
	public boolean isGameFull()
	{
		getPlayerInfoList();
		if (players == null) return false;
		return players.length >= 4;
	}
	
}

	//================================================================================
	// END
	//================================================================================