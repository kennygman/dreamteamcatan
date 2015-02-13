package model;

import java.util.List;
import shared.definitions.PieceType;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import client.data.RobPlayerInfo;
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
	
	//--------------------------------------------------------------------------------
	public Game getGame()
	{
		return game;
	}

	//--------------------------------------------------------------------------------
	public void setGame(Game game)
	{
		this.game=game;
	}

	//--------------------------------------------------------------------------------
	public boolean isPlayerTurn(Player player)
	{
		return game.getTurnTracker().getCurrentTurn() == player.getPlayerIndex();
	}

	//--------------------------------------------------------------------------------
	@Override
	public void placeRoad(EdgeLocation edgeLoc)
	{
		game.getBoard().setRoad(new Road(game.getTurnTracker().getCurrentTurn(), edgeLoc));
	}

	//--------------------------------------------------------------------------------
	@Override
	public void placeSettlement(VertexLocation vertLoc)
	{
		game.getBoard().setSettlement(new Settlement(game.getTurnTracker().getCurrentTurn(), vertLoc));
	}

	//--------------------------------------------------------------------------------
	@Override
	public void placeCity(VertexLocation vertLoc)
	{
		game.getBoard().setCity(new City(game.getTurnTracker().getCurrentTurn(), vertLoc));
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean canPlaceRobber(HexLocation hexLoc)
	{
		return !game.getBoard().getRobber().equals(hexLoc);
	}

	//--------------------------------------------------------------------------------
	@Override
	public void placeRobber(HexLocation hexLoc)
	{
		if (this.canPlaceRobber(hexLoc))
		{
			game.getBoard().setRobber(hexLoc);
		}
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
	public void robPlayer(RobPlayerInfo victim)
	{
		
	}
	//--------------------------------------------------------------------------------

	
	public void sendChat()
	{
		proxy.sendChat(new SendChatParam(
				game.getTurnTracker().getCurrentTurn(),
				game.getChat().toString()));
	}

	//--------------------------------------------------------------------------------
	public boolean canAcceptTrade(Player player)
	{
		return true;
	}
	
	//--------------------------------------------------------------------------------
	public void acceptTrade(Player player, boolean response)
	{
		if (!canAcceptTrade(player)) return;
		proxy.acceptTrade(new AcceptTradeParam(player.getPlayerIndex(), response));
	}
	
	//--------------------------------------------------------------------------------
	public void createGame(String name, boolean randTiles, boolean randNumbers, boolean randPorts)
	{
		proxy.createGame(new CreateGameParam(name, randTiles, randNumbers, randPorts));
	}
	
	//--------------------------------------------------------------------------------
	@Override
	public boolean CanDiscardCards(Player player)
	{
		if (!isPlayerTurn(player)) return false;
		return player.getResources().size() > 7;
	}

	//--------------------------------------------------------------------------------
	public void discardCards(Player player, Resources resources)
	{
		
		proxy.discardCards(new DiscardCardsParam(player.getPlayerIndex(), resources));
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanRollNumber(int n)
	{
		return n > 1 && n < 13;
	}

	//--------------------------------------------------------------------------------
	public void rollNumber(int d1, int d2)
	{
		proxy.rollNumber(new RollNumParam(d1, d2));
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean canPlaceRoad(EdgeLocation edgeLoc)
	{
		EdgeLocation edge =edgeLoc.getNormalizedLocation();
		boolean valid = false;
		
		// Check if edge is empty
		if (game.getBoard().contains(edge)) return false;
		
		
		// Check if Edge is connected to pieces owned by the current player
		List<VertexLocation> vertLoc = game.getBoard().getVertices(edge);
		for (VertexLocation v : vertLoc)
		{
			// Cities or Settlements on Vertex
			Object structure = game.getBoard().getStructure(v);
			if (structure != null)
			{
				// Check if structure is owned by the current player
				if (structure instanceof City)
				{
					if (((City)(structure)).getOwner() == game.getTurnTracker().getCurrentTurn()) valid = true;
				}
				else if (structure instanceof Settlement)
				{
					if (((Settlement) structure).getOwner() == game.getTurnTracker().getCurrentTurn()) valid = true;
				}
			}
			// Check if Road is connected to vertex
			else
			{
				List<EdgeLocation> edges = game.getBoard().getEdges(v);
				for (EdgeLocation e : edges)
				{
					// Check if road is owned by the current player
					Road road = game.getBoard().getRoad(e);
					if (road != null)
					{
						if (road.getOwner() == game.getTurnTracker().getCurrentTurn()) valid = true;
					}
				}
			}
			
		}
		
		return valid;
	}	

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanBuildRoad(Player player)
	{
		if (!isPlayerTurn(player)) return false;
		Resources hand = player.getResources();
		return hand.getResourceAmount(ResourceType.WOOD) > 0 &&
				hand.getResourceAmount(ResourceType.BRICK) > 0;
	}

	//--------------------------------------------------------------------------------
	public void buildRoad(Player player, EdgeLocation edge, boolean free)
	{
		if (this.CanBuildRoad(player) && this.canPlaceRoad(edge)) 
		{
			proxy.buildRoad(new BuildRoadParam(player.getPlayerIndex(), edge, free));
		}
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanBuildSettlement(Player player)
	{
		if (!isPlayerTurn(player)) return false;
		Resources hand = player.getResources();
		return hand.getResourceAmount(ResourceType.WOOD) > 0 &&
				hand.getResourceAmount(ResourceType.SHEEP) > 0 &&
				hand.getResourceAmount(ResourceType.WHEAT) > 0 &&
				hand.getResourceAmount(ResourceType.BRICK) > 0;
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean canPlaceSettlement(VertexLocation vertLoc)
	{
		VertexLocation vert = vertLoc.getNormalizedLocation();
		Object structure = game.getBoard().getStructure(vert);
		boolean valid = false;
		
		// Check if vertex is empty
		if (structure != null)
		{
			return false;
		}

		// Check if neighboring vertices are empty
		else
		{
			if (vert.getDir().equals(VertexDirection.NorthWest))
			{
				if (	game.getBoard().getStructure(new VertexLocation(vert.getHexLoc(),
								VertexDirection.NorthEast)) == null ||
						game.getBoard().getStructure(new VertexLocation(vert.getHexLoc().getNeighborLoc(EdgeDirection.NorthWest),
								VertexDirection.NorthEast)) == null ||
						game.getBoard().getStructure(new VertexLocation(vert.getHexLoc().getNeighborLoc(EdgeDirection.SouthWest),
								VertexDirection.NorthEast)) == null
					) valid = true;
			}
			else
			{
				if (	game.getBoard().getStructure(new VertexLocation(vert.getHexLoc(),
								VertexDirection.NorthWest)) == null ||
						game.getBoard().getStructure(new VertexLocation(vert.getHexLoc().getNeighborLoc(EdgeDirection.NorthEast),
								VertexDirection.NorthWest)) == null ||
						game.getBoard().getStructure(new VertexLocation(vert.getHexLoc().getNeighborLoc(EdgeDirection.SouthEast),
								VertexDirection.NorthWest)) == null
					) valid = true;
			}
		}
		
		return valid;
	}

	//--------------------------------------------------------------------------------
	public void buildSettlement(Player player, VertexLocation vert, boolean free)
	{
		if (this.CanBuildSettlement(player) && this.canPlaceSettlement(vert))
		{
			proxy.buildSettlement(new BuildSettlementParam(player.getPlayerIndex(), vert, free));
		}
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanBuildCity(Player player)
	{
		if (!isPlayerTurn(player)) return false;
		Resources hand = player.getResources();
		return hand.getResourceAmount(ResourceType.WHEAT) > 1 &&
				hand.getResourceAmount(ResourceType.ORE) > 2;
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean canPlaceCity(VertexLocation vertLoc)
	{
		Object obj = game.getBoard().getStructure(vertLoc);
		if (obj == null) return false;
		if (obj instanceof City) return false;

		// Check if current player owns a settlement at the Vertex Location
		if (((Settlement)(obj)).getOwner() == game.getTurnTracker().getCurrentTurn()) return true;

		return false;
	}

	//--------------------------------------------------------------------------------
	public void buildCity(Player player, VertexLocation vert)
	{
		if (this.CanBuildCity(player) && this.canPlaceCity(vert))
		{
			proxy.buildCity(new BuildCityParam(player.getPlayerIndex(), vert));
		}
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanOfferTrade(Player player)
	{
		return isPlayerTurn(player);
	}

	//--------------------------------------------------------------------------------
	public void offerTrade(Player sender, Player receiver, Resources resources)
	{
		proxy.offerTrade(new OfferTradeParam(sender.getPlayerIndex(), receiver.getPlayerIndex(), resources));
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanMaritimeTrade(Player player)
	{
		return isPlayerTurn(player);
	}

	//--------------------------------------------------------------------------------
	public void maritimeTrade(Player player, int ratio, String inputResource, String outResource)
	{
		if (this.CanMaritimeTrade(player))
		{
			proxy.maritimeTrade(new MaritimeTradeParam(player.getPlayerIndex(), ratio, inputResource, outResource));
		}
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanFinishTurn(Player player)
	{
		return isPlayerTurn(player);
	}

	//--------------------------------------------------------------------------------
	public void finishTurn(Player player)
	{
		if (this.CanFinishTurn(player))
		{
			proxy.finishTurn(new FinishTurnParam(player.getPlayerIndex()));
		}
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanBuyDevCard(Player player)
	{
		Resources hand = player.getResources();
		if (!isPlayerTurn(player)) return false;
		return hand.getResourceAmount(ResourceType.WHEAT) > 0 &&
				hand.getResourceAmount(ResourceType.SHEEP) > 0 &&
				hand.getResourceAmount(ResourceType.ORE) > 0;
	}

	//--------------------------------------------------------------------------------
	public void buyDevCard(Player player)
	{
		if (this.CanBuyDevCard(player))
		{
			proxy.buyDevCard(new BuyDevCardParam(player.getPlayerIndex()));
		}
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanUseYearOfPlenty(Player player)
	{
		if (!isPlayerTurn(player)) return false;
		return player.getNewDevCards().getYearOfPlenty() > 0;
	}

	//--------------------------------------------------------------------------------
	public void yearOfPlenty(Player player, String resource1, String resource2)
	{
		if (this.CanUseYearOfPlenty(player))
		{
			proxy.playYearOfPlenty(new PlayYearOfPlentyParam(player.getPlayerIndex(), resource1, resource2));
		}
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanUseRoadBuilder(Player player)
	{
		if (!isPlayerTurn(player)) return false;
		return player.getNewDevCards().getRoadBuilding() > 0;
	}

	//--------------------------------------------------------------------------------
	public void roadCard(Player player, EdgeLocation spot1, EdgeLocation spot2)
	{
		if (this.CanUseRoadBuilder(player))
		{
			proxy.playRoadBuilding(new PlayRoadBuildingParam(player.getPlayerIndex(), spot1, spot2));
		}
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanUseSoldier(Player player)
	{
		if (!isPlayerTurn(player)) return false;
		return player.getNewDevCards().getSolider() > 0;
	}

	//--------------------------------------------------------------------------------
	public void soldier(Player player, int victimIndex, HexLocation location)
	{
		if (this.CanUseSoldier(player))
		{
			proxy.playSoldier(new PlaySoldierParam(player.getPlayerIndex(), victimIndex, location));
		}
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanUseMonopoly(Player player)
	{
		if (!isPlayerTurn(player)) return false;
		return player.getNewDevCards().getMonopoly() > 0;
	}

	//--------------------------------------------------------------------------------
	public void monopoly(Player player, String resource)
	{
		if (this.CanUseMonopoly(player))
		{
			proxy.playMonopoly(new PlayMonopolyParam(player.getPlayerIndex(), resource));
		}
	}

	//--------------------------------------------------------------------------------
	@Override
	public boolean CanUseMonument(Player player)
	{
		if (!isPlayerTurn(player)) return false;
		return player.getNewDevCards().getMonument() > 0;
	}

	//--------------------------------------------------------------------------------
	public void monument(Player player)
	{
		if (this.CanUseMonument(player))
		{
			proxy.playMonument(new PlayMonumentParam(player.getPlayerIndex()));
		}
	}

	@Override
	public void playSoldierCard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playRoadBuildingCard() {
		// TODO Auto-generated method stub
		
	}

	
	//--------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------
}