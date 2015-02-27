package client.map;

import java.util.*;

import shared.definitions.*;
import shared.locations.*;
import client.base.*;
import client.data.*;
import model.Game;
import model.ModelFacade;
import model.board.Board;
import model.board.City;
import model.board.Hex;
import model.board.Port;
import model.board.Road;
import model.board.Settlement;
import model.player.Player;


/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController , Observer{
	
	private IRobView robView;
	
	public MapController(IMapView view, IRobView robView) {
		
		super(view);
		
		setRobView(robView);
	}
	
	public IMapView getView() {
		
		return (IMapView)super.getView();
	}
	
	private IRobView getRobView() {
		return robView;
	}
	private void setRobView(IRobView robView) {
		this.robView = robView;
	}
	
	protected void initFromModel() {
            Game game = ModelFacade.getInstance().getGame();            
            Board board = game.getBoard();
            
            drawHexes(game, board);
            drawWaterHexes(board);
            getView().placeRobber(board.getRobber());
            
            
            
            
            
            
//		for (int x = 0; x <= 3; ++x) {
//			
//			int maxY = 3 - x;			
//			for (int y = -3; y <= maxY; ++y) {				
//				int r = rand.nextInt(HexType.values().length);
//				HexType hexType = HexType.values()[r];
//				HexLocation hexLoc = new HexLocation(x, y);
//				getView().addHex(hexLoc, hexType);
//				getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.NorthWest),
//						CatanColor.RED);
//				getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.SouthWest),
//						CatanColor.BLUE);
//				getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.South),
//						CatanColor.WHITE);
//				getView().placeSettlement(new VertexLocation(hexLoc,  VertexDirection.NorthWest), CatanColor.GREEN);
//				getView().placeCity(new VertexLocation(hexLoc,  VertexDirection.NorthEast), CatanColor.PURPLE);
//			}
//			
//			if (x != 0) {
//				int minY = x - 3;
//				for (int y = minY; y <= 3; ++y) {
//					int r = rand.nextInt(HexType.values().length);
//					HexType hexType = HexType.values()[r];
//					HexLocation hexLoc = new HexLocation(-x, y);
//					getView().addHex(hexLoc, hexType);
//					getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.NorthWest),
//							CatanColor.RED);
//					getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.SouthWest),
//							CatanColor.BLUE);
//					getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.South),
//							CatanColor.ORANGE);
//					getView().placeSettlement(new VertexLocation(hexLoc,  VertexDirection.NorthWest), CatanColor.GREEN);
//					getView().placeCity(new VertexLocation(hexLoc,  VertexDirection.NorthEast), CatanColor.PURPLE);
//				}
//			}
//		}
//		
//		
		
		//</temp>
	}
        
        private void drawHexes(Game game, Board board)
        {
            for (Hex hex : board.getHexes())
            {
                getView().addHex(hex.getLocation(), HexType.fromString(hex.getResource()));
                if(hex.getNumber() != 0)
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
            getView().addHex(new HexLocation(-3,0),HexType.WATER);
            getView().addHex(new HexLocation(-3,1),HexType.WATER);
            getView().addHex(new HexLocation(-3,2),HexType.WATER);
            getView().addHex(new HexLocation(-3,3),HexType.WATER);
            getView().addHex(new HexLocation(-2,-1),HexType.WATER);
            getView().addHex(new HexLocation(-2,3),HexType.WATER);
            getView().addHex(new HexLocation(-1,-2),HexType.WATER);
            getView().addHex(new HexLocation(-1,3),HexType.WATER);
            getView().addHex(new HexLocation(0,-3),HexType.WATER);
            getView().addHex(new HexLocation(0,3),HexType.WATER);
            getView().addHex(new HexLocation(1,-3),HexType.WATER);
            getView().addHex(new HexLocation(1,2),HexType.WATER);
            getView().addHex(new HexLocation(2,1),HexType.WATER);
            getView().addHex(new HexLocation(2,-3),HexType.WATER);
            getView().addHex(new HexLocation(3,-3),HexType.WATER);
            getView().addHex(new HexLocation(3,-2),HexType.WATER);
            getView().addHex(new HexLocation(3,-1),HexType.WATER);
            getView().addHex(new HexLocation(3,0),HexType.WATER);
            for (Port port : board.getPorts())
            {
                getView().addPort(port.getEdgeLocation(), PortType.fromString(port.getResource()));
            }
        }

	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		
		return true;
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		
		return true;
	}

	public boolean canPlaceCity(VertexLocation vertLoc) {
		
		return true;
	}

	public boolean canPlaceRobber(HexLocation hexLoc) {
		
		return true;
	}

	public void placeRoad(EdgeLocation edgeLoc) {
		
            //getView().placeRoad(edgeLoc, CatanColor.ORANGE);
	}

	public void placeSettlement(VertexLocation vertLoc) {
		
            //getView().placeSettlement(vertLoc, CatanColor.ORANGE);
	}

	public void placeCity(VertexLocation vertLoc) {
		
            //getView().placeCity(vertLoc, CatanColor.ORANGE);
	}

	public void placeRobber(HexLocation hexLoc) {
		
            //getView().placeRobber(hexLoc);
		
            //getRobView().showModal();
	}
	
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {	
		
            //getView().startDrop(pieceType, CatanColor.ORANGE, true);
	}
	
	public void cancelMove() {
		
	}
	
	public void playSoldierCard() {	
		
	}
	
	public void playRoadBuildingCard() {	
		
	}
	
	public void robPlayer(RobPlayerInfo victim) {	
		
	}
        
        @Override
        public void update(Observable o, Object o1) 
        {
            initFromModel();
        }
}

