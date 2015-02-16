package model;

import shared.definitions.DevCardType;
import shared.definitions.PieceType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import client.proxy.IProxy;
import model.board.City;
import model.board.Road;
import model.board.Settlement;
import model.player.Player;
import model.player.Resources;
import shared.parameters.*;

public class ModelFacade implements IModelFacade
{
	private IProxy proxy;
	private Game game;
	
	//--------------------------------------------------------------------------------
	public ModelFacade(IProxy proxy)
	{
		this.proxy=proxy;
		this.game=proxy.getGameModel().getGame();
	}
	
	// ===============================================================================
	// GETTERS AND SETTERS
	// ===============================================================================
	public Game getGame()
	{
		return game;
	}
	public void setGame(Game game)
	{
		this.game=game;
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
		Player recipient = game.getPlayers()[offer.getReciever()];
		if (recipient.getPlayerIndex() != game.getPlayer().getPlayerIndex()) return false;
		return (recipient.getResources().contains(offer.getOffer()));
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanDiscardCards(Resources resources)
	{
		if (!game.getTurnTracker().getStatus().equals("Discarding")) return false;
		if (game.getPlayer().getResources().size() < 8) return false;
		resources.invert();
		boolean valid = game.getPlayer().getResources().contains(resources);
		resources.invert();
		return valid;
	}

	// Playing Preconditions ================================================================================
	private boolean canPlay()
	{
		return this.isPlayerTurn() && game.getTurnTracker().getStatus().equals("Playing");
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
			!game.getBoard().hasNeighborWater(edge.getHexLoc())) return false;
		
		if (game.getTurnTracker().getStatus().equals("FirstRound") ||
			game.getTurnTracker().getStatus().equals("SecondRound"))
		{
			if (game.getBoard().hasNeighborSettlement(edge, game.getPlayer().getPlayerIndex()) &&
				!game.getBoard().hasNeighborRoad(edge, game.getPlayer().getPlayerIndex(), true)) return true;
		}
		else
		{
			if (game.getBoard().hasNeighborRoad(edge, game.getPlayer().getPlayerIndex(), false)) return true;
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
		
		if (!CanBuySettlement() || structure != null ||
			game.getBoard().hasNeighborWater(vert.getHexLoc()) ||
			game.getBoard().hasNeighborStructure(vert)) return false;

		if (game.getTurnTracker().getStatus().equals("FirstRound") ||
				game.getTurnTracker().getStatus().equals("SecondRound")) setup = true;
		
		boolean neighbor = game.getBoard().hasNeighborRoad(vert, game.getPlayer().getPlayerIndex(), setup);
		if (setup)
		{
			if (!neighbor) return true;
		}
		else
		{
			if (neighbor) return true;
		}
		
		return false;
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
	public boolean CanOfferTrade()
	{
		if (!canPlay()) return false;
		Resources offer = game.getTradeOffer().getOffer();
		if (game.getTradeOffer().getSender() != game.getPlayer().getPlayerIndex()) return false;
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
		if (game.getBoard().hasPort(game.getPlayer().getPlayerIndex(), outResource)) return true;
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
	private boolean canPlayDevCard(DevCardType devCard, boolean monument)
	{
		if (!isPlayerTurn() ||
			!game.getTurnTracker().getStatus().equals("Playing") ||
			!monument && !game.getPlayer().getOldDevCards().hasDevCard(devCard) ||  
			game.getPlayer().isPlayedDevCard()
			) return false;
		return true;
	}
	
	//--------------------------------------------------------------------------------
	@Override
	public boolean CanUseSoldier(int victimIndex, HexLocation location)
	{
		if (!canPlayDevCard(DevCardType.SOLDIER, false) ||
			!canRobPlayer(location, victimIndex)
			) return false;
		return true;
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanUseYearOfPlenty(String resource1, String resource2)
	{
		if (!canPlayDevCard(DevCardType.YEAR_OF_PLENTY, false)) return false;
		if (game.getBank().getResourceAmount(resource1) > 0 &&
			game.getBank().getResourceAmount(resource2) > 0
			) return true;
		return false;
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanUseRoadBuilder(EdgeLocation spot1, EdgeLocation spot2)
	{
		if (!canPlayDevCard(DevCardType.ROAD_BUILD, false) ||
			!canPlaceRoad(spot1, true) ||
			game.getPlayer().getRoads() < 2 ||
			game.getBoard().contains(spot2) ||
			spot1.equals(spot2)
			) return false;
		
		if (game.getBoard().hasNeighborRoad(spot2, game.getPlayer().getPlayerIndex(), false) ||
			game.getBoard().areNeighbors(spot1, spot2)
			) return true;
		return false;
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanUseMonopoly(String resource)
	{
		return canPlayDevCard(DevCardType.MONOPOLY, false);
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanUseMonument()
	{
		if (!canPlayDevCard(DevCardType.MONUMENT, true)) return false;
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
	public void sendChat()
	{
		Game newGame = proxy.sendChat(new SendChatParam(
			game.getTurnTracker().getCurrentTurn(),
			game.getChat().toString())).getGame();

		game.getChat().setLines(newGame.getChat().getLines());
	}

	//--------------------------------------------------------------------------------
	@Override
	public void acceptTrade(boolean accept)
	{
		Game newGame = proxy.acceptTrade(new AcceptTradeParam(
				game.getTradeOffer().getReciever(), accept)).getGame();
	}
	
	//--------------------------------------------------------------------------------
	@Override
	public void startMove(PieceType pieceType, boolean isFree,
			boolean allowDisconnected)
	{
		
	}

	//--------------------------------------------------------------------------------
	@Override
	public void cancelMove()
	{
		
	}

	//--------------------------------------------------------------------------------
	@Override
	public void createGame(String name, boolean randTiles, boolean randNumbers, boolean randPorts)
	{
		proxy.createGame(new CreateGameParam(name, randTiles, randNumbers, randPorts));
	}
	
	//--------------------------------------------------------------------------------
	@Override
	public void discardCards(Resources resources)
	{
		proxy.discardCards(new DiscardCardsParam(0, resources));
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
		proxy.buildRoad(new BuildRoadParam(game.getTurnTracker().getCurrentTurn(), edge, free));
	}

	//--------------------------------------------------------------------------------
	@Override
	public void buildSettlement(VertexLocation vert, boolean free)
	{
		proxy.buildSettlement(new BuildSettlementParam(game.getPlayer().getPlayerIndex(), vert, free));
	}

	//--------------------------------------------------------------------------------
	@Override
	public void buildCity(VertexLocation vert)
	{
		proxy.buildCity(new BuildCityParam(game.getPlayer().getPlayerIndex(), vert));
	}

	//--------------------------------------------------------------------------------
	@Override
	public void offerTrade(Player sender, Player receiver, Resources resources)
	{
		proxy.offerTrade(new OfferTradeParam(sender.getPlayerIndex(), receiver.getPlayerIndex(), resources));
	}

	//--------------------------------------------------------------------------------
	@Override
	public void maritimeTrade(int ratio, String inputResource, String outResource)
	{
		proxy.maritimeTrade(new MaritimeTradeParam(game.getPlayer().getPlayerIndex(), ratio, inputResource, outResource));
	}

	//--------------------------------------------------------------------------------
	@Override
	public void finishTurn()
	{
		if (this.CanFinishTurn())
		{
			proxy.finishTurn(new FinishTurnParam(game.getPlayer().getPlayerIndex()));
		}
	}

	//--------------------------------------------------------------------------------
	@Override
	public void buyDevCard()
	{
		proxy.buyDevCard(new BuyDevCardParam(game.getPlayer().getPlayerIndex()));
	}

	//--------------------------------------------------------------------------------
	@Override
	public void playYearOfPlentyCard(String resource1, String resource2)
	{
		proxy.playYearOfPlenty(new PlayYearOfPlentyParam(game.getPlayer().getPlayerIndex(), resource1, resource2));
	}

	//--------------------------------------------------------------------------------
	@Override
	public void playRoadCard(EdgeLocation spot1, EdgeLocation spot2)
	{
		proxy.playRoadBuilding(new PlayRoadBuildingParam(game.getPlayer().getPlayerIndex(), spot1, spot2));
	}

	//--------------------------------------------------------------------------------
	@Override
	public void playSoldierCard(int victimIndex, HexLocation location)
	{
		proxy.playSoldier(new PlaySoldierParam(game.getPlayer().getPlayerIndex(), victimIndex, location));
	}

	@Override
	public void playMonumentCard()
	{
		proxy.playMonument(new PlayMonumentParam(game.getPlayer().getPlayerIndex()));
	}

	//--------------------------------------------------------------------------------
	@Override
	public void playMonopolyCard(String resource)
	{
		proxy.playMonopoly(new PlayMonopolyParam(game.getPlayer().getPlayerIndex(), resource));
	}

	//--------------------------------------------------------------------------------
	@Override
	public void robPlayer(HexLocation location, int victimIndex)
	{
		proxy.robPlayer(new RobPlayerParam(game.getPlayer().getPlayerIndex(), victimIndex, location));
	}
}
	//================================================================================
	// END
	//================================================================================