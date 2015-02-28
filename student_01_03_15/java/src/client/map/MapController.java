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
		ModelFacade.getInstance().addObserver(this);
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
		
		return ModelFacade.getInstance().canPlaceRoad(edgeLoc, false);
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		
		return ModelFacade.getInstance().canPlaceSettlement(vertLoc, false);
	}

	public boolean canPlaceCity(VertexLocation vertLoc) {
		
		return ModelFacade.getInstance().canPlaceCity(vertLoc);
	}

	public boolean canPlaceRobber(HexLocation hexLoc) {
		
		return ModelFacade.getInstance().canPlaceRobber(hexLoc);
	}

	public void placeRoad(EdgeLocation edgeLoc) {
            
            if(canPlaceRoad(edgeLoc))
            {
                ModelFacade.getInstance().buildRoad(edgeLoc, false);
                PlayerInfo player = ModelFacade.getInstance().getPlayerInfo();
                getView().placeRoad(edgeLoc, player.getColor());
            }
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
	
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) 
        {
            getView().startDrop(pieceType, CatanColor.ORANGE, true);
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

