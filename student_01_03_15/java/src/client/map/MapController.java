package client.map;

import java.util.*;

import shared.definitions.*;
import shared.locations.*;
import client.base.*;
import client.data.*;
import model.Game;
import model.ModelFacade;
import model.TurnTracker;
import model.board.*;
import model.player.Player;

/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController,
		Observer
{

	private IRobView robView;
	private boolean isRoadBuilding = false;
	private boolean isFirst = false;
	private boolean isOpen = false;
	private boolean isSoldier = false;

	private HexLocation robberLocation;
	private String state;

	public MapController(IMapView view, IRobView robView)
	{
		super(view);
		ModelFacade.getInstance().addObserver(this);
		setRobView(robView);
	}

	public IMapView getView()
	{
		return (IMapView) super.getView();
	}

	private IRobView getRobView()
	{
		return robView;
	}

	private void setRobView(IRobView robView)
	{
		this.robView = robView;
	}

	/**
	 * Check if the map overlay modal is NOT open--the player is NOT currently
	 * placing a piece, The turn belongs to the client, The game is full
	 * 
	 * @return true if all valid, false otherwise
	 */
	private boolean canPlacePiece()
	{
		if (!getRobView().isModalShowing() && !isOpen
				&& ModelFacade.getInstance().isPlayerTurn()
				&& ModelFacade.getInstance().isGameFull())
			return true;
		return false;
	}

	private boolean isSetupRound()
	{
		return state.equals(TurnTracker.FIRSTROUND)
				|| state.equals(TurnTracker.SECONDROUND);
	}

	private void placePieces()
	{
		if (canPlacePiece())
		{
			switch (state)
			{
			case "Robbing":
			{
				if (state.equals(TurnTracker.ROBBING))
				{
					startMove(PieceType.ROBBER, true, false);
				}
				break;
			}
			case "FirstRound":
			case "SecondRound":
			{
				Player player = ModelFacade.getInstance().getPlayer();
				int roads = player.getRoads();
				int settlements = player.getSettlements();

				if (state.equals(TurnTracker.FIRSTROUND))
				{
					if (roads == 15 && settlements == 5)
					{
						startMove(PieceType.ROAD, true, false);
					} else if (roads == 14 && settlements == 5)
					{
						startMove(PieceType.SETTLEMENT, true, false);
					}
				} else if (state.equals(TurnTracker.SECONDROUND))
				{
					if (roads == 14 && settlements == 4)
					{
						startMove(PieceType.ROAD, true, false);
					} else if (roads == 13 && settlements == 4)
					{
						startMove(PieceType.SETTLEMENT, true, false);
					}
				}
				break;
			}
			default:
				break;
			}
		}
	}

	private void drawPieces()
	{
		Game game = ModelFacade.getInstance().getGame();
		Board board = game.getBoard();

		drawHexes(game, board);
		drawWaterHexes(board);
		getView().placeRobber(board.getRobber());
	}

	private void drawHexes(Game game, Board board)
	{
		for (Hex hex : board.getHexes())
		{
			getView().addHex(hex.getLocation(),
					HexType.fromString(hex.getResource()));
			if (hex.getNumber() > 1)
			{
				getView().addNumber(hex.getLocation(), hex.getNumber());
			}
		}
		Player[] players = game.getPlayers();
		for (Road road : board.getRoads())
		{
			CatanColor cc = players[road.getOwner()].getColor();
			getView().placeRoad(road.getEdgeLocation(), cc);
		}
		for (Settlement settlement : board.getSettlements())
		{
			CatanColor cc = players[settlement.getOwner()].getColor();
			getView().placeSettlement(settlement.getLocation(), cc);
		}
		for (City city : board.getCities())
		{
			CatanColor cc = players[city.getOwner()].getColor();
			getView().placeCity(city.getLocation(), cc);
		}
	}

	private void drawWaterHexes(Board board)
	{
		getView().addHex(new HexLocation(-3, 0), HexType.WATER);
		getView().addHex(new HexLocation(-3, 1), HexType.WATER);
		getView().addHex(new HexLocation(-3, 2), HexType.WATER);
		getView().addHex(new HexLocation(-3, 3), HexType.WATER);
		getView().addHex(new HexLocation(-2, -1), HexType.WATER);
		getView().addHex(new HexLocation(-2, 3), HexType.WATER);
		getView().addHex(new HexLocation(-1, -2), HexType.WATER);
		getView().addHex(new HexLocation(-1, 3), HexType.WATER);
		getView().addHex(new HexLocation(0, -3), HexType.WATER);
		getView().addHex(new HexLocation(0, 3), HexType.WATER);
		getView().addHex(new HexLocation(1, -3), HexType.WATER);
		getView().addHex(new HexLocation(1, 2), HexType.WATER);
		getView().addHex(new HexLocation(2, 1), HexType.WATER);
		getView().addHex(new HexLocation(2, -3), HexType.WATER);
		getView().addHex(new HexLocation(3, -3), HexType.WATER);
		getView().addHex(new HexLocation(3, -2), HexType.WATER);
		getView().addHex(new HexLocation(3, -1), HexType.WATER);
		getView().addHex(new HexLocation(3, 0), HexType.WATER);
		for (Port port : board.getPorts())
		{
			getView().addPort(port.getEdgeLocation(),
					PortType.fromString(port.getResource()));
		}
	}

	public boolean canPlaceRoad(EdgeLocation edgeLoc)
	{
		if (isSetupRound() || isRoadBuilding)
		{
			return ModelFacade.getInstance().canPlaceRoad(edgeLoc, true);
		} else
		{
			return ModelFacade.getInstance().canPlaceRoad(edgeLoc, false);
		}
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc)
	{
		if (isSetupRound())
		{
			return ModelFacade.getInstance().canPlaceSettlement(vertLoc, true);
		} else
		{
			return ModelFacade.getInstance().canPlaceSettlement(vertLoc, false);
		}
	}

	public boolean canPlaceCity(VertexLocation vertLoc)
	{
		return ModelFacade.getInstance().canPlaceCity(vertLoc);
	}

	public boolean canPlaceRobber(HexLocation hexLoc)
	{
		return ModelFacade.getInstance().canPlaceRobber(hexLoc);
	}

	public void placeRoad(EdgeLocation edgeLoc)
	{
		boolean isFree = false;
		if (isSetupRound() || isRoadBuilding)
		{
			isFree = true;
		}

		PlayerInfo player = ModelFacade.getInstance().getPlayerInfo();
		if (isRoadBuilding && isFirst)
		{
			getView().placeRoad(edgeLoc, player.getColor());
			ModelFacade.getInstance().setFirstRoadBuidingRoad(edgeLoc);
			isFirst = false;
			startMove(PieceType.ROAD, true, false);
		} else if (isRoadBuilding)
		{
			isRoadBuilding = false;
			ModelFacade.getInstance().playRoadCard(null, edgeLoc);
		} else
		{
			ModelFacade.getInstance().buildRoad(edgeLoc, isFree);
			getView().placeRoad(edgeLoc, player.getColor());
		}
		isOpen = false;

	}

	public void placeSettlement(VertexLocation vertLoc)
	{
		boolean isFree = false;
		boolean isEnd = false;
		isOpen = false;
		if (isSetupRound())
		{
			isFree = true;
			isEnd = true;
		}

		ModelFacade.getInstance().buildSettlement(vertLoc, isFree);
		PlayerInfo player = ModelFacade.getInstance().getPlayerInfo();

		getView().placeSettlement(vertLoc, player.getColor());
		if (isEnd)
		{
			ModelFacade.getInstance().finishTurn();
		}
	}

	public void placeCity(VertexLocation vertLoc)
	{
		ModelFacade.getInstance().buildCity(vertLoc);
		PlayerInfo player = ModelFacade.getInstance().getPlayerInfo();
		getView().placeCity(vertLoc, player.getColor());
		isOpen = false;
	}

	public void placeRobber(HexLocation hexLoc)
	{
		RobPlayerInfo[] players = ModelFacade.getInstance()
				.getRobPlayerInfoList(hexLoc);
		getRobView().setPlayers(players);
		robberLocation = hexLoc;
		getRobView().showModal();
		isOpen = false;
	}

	public void startMove(PieceType pieceType, boolean isFree,
			boolean allowCancel)
	{
		isOpen = true;
		CatanColor color = ModelFacade.getInstance().getPlayerInfo().getColor();
		getView().startDrop(pieceType, color, allowCancel);
	}

	public void cancelMove()
	{
		System.out.println("cancel");
	}

	public void playSoldierCard()
	{
		isSoldier = true;
		startMove(PieceType.ROBBER, true, true);
	}

	public void playRoadBuildingCard()
	{
		isRoadBuilding = true;
		isFirst = true;
		startMove(PieceType.ROAD, true, true);
	}

	public void robPlayer(RobPlayerInfo victim)
	{
		if (victim == null)
		{
			ModelFacade.getInstance().robPlayer(robberLocation, -1);
		} else if (isSoldier)
		{
			ModelFacade.getInstance().playSoldierCard(victim.getPlayerIndex(),
					victim.getLocation());
		} else
		{
			ModelFacade.getInstance().robPlayer(victim.getLocation(),
					victim.getPlayerIndex());
		}
	}

	protected void initFromModel()
	{
		drawPieces();
		placePieces();
	}

	@Override
	public void update(Observable o, Object o1)
	{
		state = ModelFacade.getInstance().getState();
		//System.out.println("MapController(state): " + state);
		initFromModel();
	}
}
