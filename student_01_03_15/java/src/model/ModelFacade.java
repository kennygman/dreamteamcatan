package model;

import java.util.Observable;

import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import client.data.GameInfo;
import client.data.PlayerInfo;
import client.proxy.IProxy;
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
            if (game == null) 
            {
                GameModelResponse response = proxy.getGameModel();
                if(response.isValid())
                {
                    game = response.getGame();
                }
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
		return (this.isPlayerTurn() && game.getTurnTracker().getStatus().equals("Rolling"));
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean canPlaceRoad(EdgeLocation edgeLoc, boolean free)
	{
            if (!canPlay()) return false;
            EdgeLocation edge = edgeLoc.getNormalizedLocation();
            Board board = game.getBoard();

            if (board.containsRoad(edge)) return false;
            else if (!free && !CanBuyRoad()) return false;
            else if (board.hasWaterEdge(edge.getHexLoc(), edge.getDir())) return false;
            else if (board.hasNeighborRoad(edge, player.getPlayerIndex(), free))  return true;
			
             return false;
	}	
 		
	//--------------------------------------------------------------------------------
	@Override
	public boolean canPlaceSettlement(VertexLocation vertLoc, boolean free)
	{
		if (!canPlay()) return false;
		VertexLocation vertex = vertLoc.getNormalizedLocation();
                Board board =  game.getBoard();
		
		if ((!free && !CanBuySettlement())) return false;
                else if (board.containsStructure(vertex)) return false;
                else if (board.hasWaterVertex(vertex.getHexLoc(), vertex.getDir())) return false;
                else if (!board.hasNeighborRoad(vertex, player.getPlayerIndex())) return false;
                else if (board.hasNeighborStructure(vertex)) return false;
		
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
		if (!canPlay()) return false;
		if (game.getPlayer().getResources().getResourceAmount(outResource) < ratio) return false;
		if (ratio == 4)return true;
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
		return !canPlay();
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanBuyDevCard()
	{
		if (!canPlay()) return false;
		if (game.getBank().size() < 1) return false;
		Resources hand = game.getPlayer().getResources();
		return hand.getResourceAmount(ResourceType.WHEAT) > 0 &&
				hand.getResourceAmount(ResourceType.SHEEP) > 0 &&
				hand.getResourceAmount(ResourceType.ORE) > 0;
	}
	
	// DevCard Preconditions ================================================================================
	private boolean canPlayDevCard(DevCardType devCard)
	{
		if (!isPlayerTurn() ||
			!game.getTurnTracker().getStatus().equals("Playing")
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
		if (!canPlayDevCard(DevCardType.ROAD_BUILD)
				|| !canPlaceRoad(spot1, true)
				|| game.getPlayer().getRoads() < 2
				|| game.getBoard().containsRoad(spot2)
				|| spot1.equals(spot2)
			) 
			{
				return false;
			}
		
		if (game.getBoard().hasNeighborRoad(spot2,
				game.getPlayer().getPlayerIndex(), false) /*||
			game.getBoard().areNeighbors(spot1, spot2)*/
			) 
		{
			
			return true;
		}
			
		
		
		return false;
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
                    this.update(newGame);
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
                    Game newGame = response.getGame();
                    p.setResources(newGame.getPlayers()[p.getPlayerIndex()].getResources());
                    game.getTurnTracker().setStatus(newGame.getTurnTracker().getStatus());
                }
	}

	//--------------------------------------------------------------------------------
	@Override
	public void rollNumber(int d1, int d2)
	{
		proxy.rollNumber(new RollNumParam(d1, d2));
	}

	//--------------------------------------------------------------------------------
	@Override
	public void buildRoad(EdgeLocation edge, boolean free)
	{
                GameModelResponse response = proxy.buildRoad(new BuildRoadParam
                        (game.getTurnTracker().getCurrentTurn(), edge, free));
                if(response.isValid())
                {
                    Game newGame = response.getGame();
                    update(newGame);
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
                    Game newGame = response.getGame();
                    update(newGame);
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
                    Game newGame = response.getGame();
                    update(newGame);
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
                    Game newGame = response.getGame();
                    game.setTradeOffer(newGame.getTradeOffer());
                    update(newGame);
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
                }
	}

	//--------------------------------------------------------------------------------
	@Override
	public void finishTurn()
	{
		Player p = game.getPlayer();
                GameModelResponse response = proxy.finishTurn(
				new FinishTurnParam(p.getPlayerIndex()));
                if(response.isValid())
                {
                    Game newGame = response.getGame();
                    p.setNewDevCards(newGame.getPlayers()[p.getPlayerIndex()].getNewDevCards());
                    p.setOldDevCards(newGame.getPlayers()[p.getPlayerIndex()].getOldDevCards());
                    game.getTurnTracker().setCurrentTurn(newGame.getTurnTracker().getCurrentTurn());
                }
	}

	//--------------------------------------------------------------------------------
	@Override
	public void buyDevCard()
	{
		Player p = game.getPlayer();
                GameModelResponse response = proxy.buyDevCard(
			new BuyDevCardParam(game.getPlayer().getPlayerIndex()));
                if(response.isValid())
                {
                    Game newGame = response.getGame();
                    p.setNewDevCards(newGame.getPlayers()[p.getPlayerIndex()].getNewDevCards());
                    p.setOldDevCards(newGame.getPlayers()[p.getPlayerIndex()].getOldDevCards());
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
                    Game newGame = response.getGame();
                    this.update(newGame);
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
                }
	}

	public void update(Game newGame)
	{
		Player p = newGame.getPlayer();
		p.update(newGame.getPlayers()[p.getPlayerIndex()]);
                game.update(newGame);
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
                }
		return response;
	}

	//--------------------------------------------------------------------------------
	public CreateGameResponse createGame(CreateGameParam params)
	{
		return proxy.createGame(params);
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
	public void getGameModel()
	{
		this.setGame(getGame());
		this.modelChanged();
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
		
		PlayerInfo[] players = new PlayerInfo[numPlayers];
		for(int k = 0; k < numPlayers; k++)
		{
			players[k] = games[gameindex].getPlayers().get(k);
		}
		
		
		return players;
		
	}

	
}
	//================================================================================
	// END
	//================================================================================