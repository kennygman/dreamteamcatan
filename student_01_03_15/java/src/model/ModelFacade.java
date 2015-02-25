package model;

import java.util.Observable;

import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import client.data.PlayerInfo;
import client.proxy.IProxy;
import model.board.City;
import model.board.Settlement;
import model.player.Player;
import model.player.Resources;
import shared.parameters.*;
import shared.response.CreateGameResponse;
import shared.response.ListGamesResponse;
import shared.response.LoginResponse;
import shared.response.StandardResponse;

public class ModelFacade extends Observable implements IModelFacade
{
	private IProxy proxy;
	private Game game;
	private PlayerInfo player;
	
	public ModelFacade(IProxy proxy)
	{
		this.proxy = proxy;
//		getGame();
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
	public PlayerInfo getPlayerInfo()
	{
		return player;
	}
	
	public Game getGame()
	{
		if (game == null) 
		{
			game = proxy.getGameModel().getGame();
		}
		return game;
	}
	public void setGame(Game game)
	{
		this.game=game;
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
	public boolean CanRollNumber(int n)
	{
		return (this.isPlayerTurn() && game.getTurnTracker().getStatus().equals("Rolling"));
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean canPlaceRoad(EdgeLocation edgeLoc, boolean free)
	{
		if (!canPlay()) return false;
		EdgeLocation edge = edgeLoc.getNormalizedLocation();
		if (game.getBoard().contains(edge) ||
			!free && !CanBuyRoad() ||
			game.getBoard().hasNeighborWater(edge.getHexLoc())) return false;
		
		if (game.getTurnTracker().getStatus().equals("FirstRound") ||
			game.getTurnTracker().getStatus().equals("SecondRound"))
		{
			if (game.getBoard().hasNeighborSettlement(edge, game.getPlayer().getPlayerIndex()) &&
				!game.getBoard().hasNeighborRoad(edge, game.getPlayer().getPlayerIndex(), true))
				return true;
		}
		else
		{
			if (game.getBoard().hasNeighborRoad(edge, game.getPlayer().getPlayerIndex(), false))
				return true;
		}
			
		return false;
	}	
 		
	//--------------------------------------------------------------------------------
	@Override
	public boolean canPlaceSettlement(VertexLocation vertLoc, boolean free)
	{
		if (!canPlay()) return false;
		VertexLocation vert = vertLoc.getNormalizedLocation();
		Object structure = game.getBoard().getStructure(vert);
		boolean setup = false;
		
		if (!CanBuySettlement() ||
			structure != null ||
			game.getBoard().hasNeighborWater(vert.getHexLoc()) ||
			game.getBoard().hasNeighborStructure(vert)) return false;

		if (game.getTurnTracker().getStatus().equals("FirstRound") ||
				game.getTurnTracker().getStatus().equals("SecondRound")) setup = true;
		
		boolean neighbor = game.getBoard().hasNeighborRoad(
				vert, game.getPlayer().getPlayerIndex(), setup);
		
		if (setup)
		{
			if (!neighbor) return true;
		}
		else
		{
			if (neighbor) return true;
		}
		
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
		if (obj instanceof City) return false;
		if (((Settlement)(obj)).getOwner() == game.getTurnTracker().getCurrentTurn()) return true;

		return false;
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanBuyRoad()
	{
		if (game.getPlayer().getRoads() < 1) return false;
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
				|| game.getBoard().contains(spot2)
				|| spot1.equals(spot2)
			) 
			{
				return false;
			}
		
		if (game.getBoard().hasNeighborRoad(spot2,
				game.getPlayer().getPlayerIndex(), false) ||
			game.getBoard().areNeighbors(spot1, spot2)
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
		game.getChat().setLines(newGame.getChat().getLines());
	}

	//--------------------------------------------------------------------------------
	@Override
	public void acceptTrade(boolean accept)
	{
		Player reciever = game.getPlayers()[game.getTradeOffer().getReciever()];
		Game newGame = proxy.acceptTrade(
				new AcceptTradeParam(reciever.getPlayerIndex(), accept))
				.getGame();
		if (!accept) return;
		game.setTradeOffer(newGame.getTradeOffer());
		this.update(newGame);
	}
	
	//--------------------------------------------------------------------------------
	@Override
	public void discardCards(Resources resources)
	{
		Player p = game.getPlayer();
		Game newGame = proxy.discardCards(new DiscardCardsParam(0, resources)).getGame();
		p.setResources(newGame.getPlayers()[p.getPlayerIndex()].getResources());
		game.getTurnTracker().setStatus(newGame.getTurnTracker().getStatus());
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
		Game newGame = proxy.buildRoad(new BuildRoadParam(
				game.getTurnTracker().getCurrentTurn(), edge, free)).getGame();
		update(newGame);
	}

	//--------------------------------------------------------------------------------
	@Override
	public void buildSettlement(VertexLocation vert, boolean free)
	{
		Game newGame = proxy.buildSettlement(new BuildSettlementParam(
				game.getPlayer().getPlayerIndex(), vert, free)).getGame();
		update(newGame);
	}

	//--------------------------------------------------------------------------------
	@Override
	public void buildCity(VertexLocation vert)
	{
		Game newGame = proxy.buildCity(
				new BuildCityParam(game.getPlayer().getPlayerIndex(), vert)).getGame();
		update(newGame);
	}

	//--------------------------------------------------------------------------------
	@Override
	public void offerTrade(int receiver, Resources resources)
	{
		Game newGame =	proxy.offerTrade(new OfferTradeParam(
				game.getPlayer().getPlayerIndex(), receiver, resources))
			.getGame();
		game.setTradeOffer(newGame.getTradeOffer());
		update(newGame);
	}

	//--------------------------------------------------------------------------------
	@Override
	public void maritimeTrade(int ratio, String inputResource, String outResource)
	{
		Player p = game.getPlayer();
		Game newGame =  proxy.maritimeTrade(
				new MaritimeTradeParam(game.getPlayer().getPlayerIndex(),
				ratio, inputResource, outResource)).getGame();
		game.setBank(newGame.getBank());
		p.setResources(newGame.getPlayers()[p.getPlayerIndex()].getResources());
	}

	//--------------------------------------------------------------------------------
	@Override
	public void robPlayer(HexLocation location, int victimIndex)
	{
		Player p = game.getPlayer();
		Game newGame =  proxy.robPlayer(new RobPlayerParam(
				game.getPlayer().getPlayerIndex(), victimIndex, location)).getGame();
		game.getBoard().setRobber(newGame.getBoard().getRobber());
		p.setResources(newGame.getPlayers()[p.getPlayerIndex()].getResources());
	}

	//--------------------------------------------------------------------------------
	@Override
	public void finishTurn()
	{
		Player p = game.getPlayer();
		Game newGame = proxy.finishTurn(
				new FinishTurnParam(p.getPlayerIndex())).getGame();
		p.setNewDevCards(newGame.getPlayers()[p.getPlayerIndex()].getNewDevCards());
		p.setOldDevCards(newGame.getPlayers()[p.getPlayerIndex()].getOldDevCards());
		game.getTurnTracker().setCurrentTurn(newGame.getTurnTracker().getCurrentTurn());
	}

	//--------------------------------------------------------------------------------
	@Override
	public void buyDevCard()
	{
		Player p = game.getPlayer();
		Game newGame = 	proxy.buyDevCard(
				new BuyDevCardParam(game.getPlayer().getPlayerIndex())).getGame();
		p.setNewDevCards(newGame.getPlayers()[p.getPlayerIndex()].getNewDevCards());
		p.setOldDevCards(newGame.getPlayers()[p.getPlayerIndex()].getOldDevCards());
	}

	//--------------------------------------------------------------------------------
	@Override
	public void playSoldierCard(int victimIndex, HexLocation location)
	{
		Game newGame = proxy.playSoldier(new PlaySoldierParam(
				game.getPlayer().getPlayerIndex(), victimIndex, location)).getGame();
		this.update(newGame);
	}

	//--------------------------------------------------------------------------------
	@Override
	public void playYearOfPlentyCard(String resource1, String resource2)
	{
		Player p = game.getPlayer();
		Game newGame = 	proxy.playYearOfPlenty(new PlayYearOfPlentyParam(
				game.getPlayer().getPlayerIndex(), resource1, resource2)).getGame();
		p.setResources(newGame.getPlayers()[p.getPlayerIndex()].getResources());
	}

	//--------------------------------------------------------------------------------
	@Override
	public void playRoadCard(EdgeLocation spot1, EdgeLocation spot2)
	{
		Player p = game.getPlayer();
		Game newGame =	proxy.playRoadBuilding(new PlayRoadBuildingParam(
				game.getPlayer().getPlayerIndex(), spot1, spot2)).getGame();
		p.setRoads(newGame.getPlayers()[p.getPlayerIndex()].getRoads());
		game.getBoard().setRoads(newGame.getBoard().getRoads());
		game.getBoard().sort();
		game.getTurnTracker().setLongestRoad(newGame.getTurnTracker().getLongestRoad());
	}

	//--------------------------------------------------------------------------------
	@Override
	public void playMonopolyCard(String resource)
	{
		Player p = game.getPlayer();
		Game newGame =	proxy.playMonopoly(new PlayMonopolyParam(
				game.getPlayer().getPlayerIndex(), resource)).getGame();
		p.setResources(newGame.getPlayers()[p.getPlayerIndex()].getResources());
	}

	//--------------------------------------------------------------------------------
	@Override
	public void playMonumentCard()
	{
		Player p = game.getPlayer();
		Game newGame =	proxy.playMonument(new PlayMonumentParam(
				game.getPlayer().getPlayerIndex())).getGame();
		p.setVictoryPoints(newGame.getPlayers()[p.getPlayerIndex()].getVictoryPoints());
	}

	public void update(Game newGame)
	{
		Player p = newGame.getPlayer();
		p.update(newGame.getPlayers()[p.getPlayerIndex()]);
		game.getBoard().update(newGame.getBoard());
		game.getTurnTracker().update(newGame.getTurnTracker());
	}
	
	//================================================================================
	// MISC PROXY FUNCTIONS
	//================================================================================
	
	//--------------------------------------------------------------------------------
	public LoginResponse login(CredentialsParam params)
	{
		return proxy.login(params);
	}
	
	//--------------------------------------------------------------------------------
	public LoginResponse register(CredentialsParam params)
	{
		return proxy.register(params);
	}
	
	//--------------------------------------------------------------------------------
	public StandardResponse joinGame(JoinGameParam params)
	{
		return proxy.joinGame(params);
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

	
}
	//================================================================================
	// END
	//================================================================================