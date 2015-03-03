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
        private boolean isRoadBuilding;
        private boolean isFirst;
	
	public MapController(IMapView view, IRobView robView) 
        {
		super(view);
		ModelFacade.getInstance().addObserver(this);
		setRobView(robView);
	}
	
	public IMapView getView() 
        {
		return (IMapView)super.getView();
	}
	
	private IRobView getRobView() 
        {
		return robView;
	}
	private void setRobView(IRobView robView) 
        {
		this.robView = robView;
	}
	
	protected void initFromModel()
        {
            Game game = ModelFacade.getInstance().getGame();            
            Board board = game.getBoard();
            String status = ModelFacade.getInstance().getState();
            
            
            drawHexes(game, board);
            drawWaterHexes(board);
            getView().placeRobber(board.getRobber());
            
            if(ModelFacade.getInstance().isPlayerTurn())
            {
                switch(status)
                {
                    case "Robbing": 
                    {
                        startMove(PieceType.ROBBER, true, false);
                        break;
                    }  
                    case "FirstRound": 
                    case "SecondRound":
                    {
                        if(ModelFacade.getInstance().checkGameFull())
                        {
                            if(ModelFacade.getInstance().isSetUpRoad() && !ModelFacade.getInstance().isSetUpSettlement())
                            {
                                startMove(PieceType.ROAD, true, false);
                                ModelFacade.getInstance().setSetUpRoad(false);
                            }
                            else if(!ModelFacade.getInstance().isSetUpRoad() && ModelFacade.getInstance().isSetUpSettlement())
                            {
                                startMove(PieceType.SETTLEMENT, true, false);
                                ModelFacade.getInstance().setSetUpSettlement(false);
                            }
                            else if(ModelFacade.getInstance().isSetUpRoad() && ModelFacade.getInstance().isSetUpSettlement())
                            {
                                ModelFacade.getInstance().setSetUpRoad(true);
                                ModelFacade.getInstance().setSetUpSettlement(false);
                                ModelFacade.getInstance().finishTurn();
                            }
                        }
                        break;
                    }
                    case "Playing": break;
                    case "Discarding": break;
                    case "Rolling": break;
                }
            }
            
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

	public boolean canPlaceRoad(EdgeLocation edgeLoc) 
        {	
            String status = ModelFacade.getInstance().getState();
            if(status.equals("FirstRound") || status.equals("SecondRound") || isRoadBuilding)
            {
                return ModelFacade.getInstance().canPlaceRoad(edgeLoc, true);
            }
            else
            {
                return ModelFacade.getInstance().canPlaceRoad(edgeLoc, false);
            }
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc) 
        {	
            String status = ModelFacade.getInstance().getState();
            if(status.equals("FirstRound") || status.equals("SecondRound"))
            {
		return ModelFacade.getInstance().canPlaceSettlement(vertLoc, true);
            }
            else
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
            String status = ModelFacade.getInstance().getState();
            if(status.equals("FirstRound") || status.equals("SecondRound") || isRoadBuilding)
            {
                isFree = true;
            }
            ModelFacade.getInstance().setSetUpSettlement(true);
            ModelFacade.getInstance().buildRoad(edgeLoc, isFree);
            PlayerInfo player = ModelFacade.getInstance().getPlayerInfo();
            getView().placeRoad(edgeLoc, player.getColor());
            if(isRoadBuilding && isFirst)
            {
                ModelFacade.getInstance().updateGameModel();
                isFirst = false;
                startMove(PieceType.ROAD, true, true);
            }
            else if(isRoadBuilding)
            {
                isRoadBuilding = false;
            }
	}

	public void placeSettlement(VertexLocation vertLoc) 
        {
            boolean isFree = false;
            String status = ModelFacade.getInstance().getState();
            if(status.equals("FirstRound") || status.equals("SecondRound"))
            {
                isFree = true;
            }
            ModelFacade.getInstance().setSetUpSettlement(true);
            ModelFacade.getInstance().setSetUpRoad(true);
            ModelFacade.getInstance().buildSettlement(vertLoc, isFree);
            PlayerInfo player = ModelFacade.getInstance().getPlayerInfo();
            getView().placeSettlement(vertLoc, player.getColor());
	}

	public void placeCity(VertexLocation vertLoc) 
        {
            ModelFacade.getInstance().buildCity(vertLoc);
            PlayerInfo player = ModelFacade.getInstance().getPlayerInfo();
            getView().placeCity(vertLoc, player.getColor());
	}

	public void placeRobber(HexLocation hexLoc) 
        {
            RobPlayerInfo[] players = ModelFacade.getInstance().getRobPlayerInfoList(hexLoc);
            getRobView().setPlayers(players);
            getRobView().showModal();
	}
	
	public void startMove(PieceType pieceType, boolean isFree, boolean allowCancel) 
        {
            CatanColor color = ModelFacade.getInstance().getPlayerInfo().getColor();
            getView().startDrop(pieceType, color, allowCancel);
	}
	
	public void cancelMove() 
        {
            
	}
	
	public void playSoldierCard() 
        {	
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
            ModelFacade.getInstance().robPlayer(victim.getLocation(), victim.getPlayerIndex());
	}
        
        @Override
        public void update(Observable o, Object o1) 
        {
            initFromModel();
        }
}

