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
        private boolean isRoadBuilding = false;
        private boolean isFirst = false;
        private boolean isOpen = false;
        private boolean isSoldier = false;
        private EdgeLocation first;
	
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
                    if(!isOpen)
                    {
                        isOpen = true;
                        ModelFacade.getInstance().getPoller().stop();
                        startMove(PieceType.ROBBER, true, false);
                    }
                    break;
                }  
                case "FirstRound":   
                case "SecondRound":
                {  
                 
                 
                    ModelFacade.getInstance().getPoller().stop();
                    if(ModelFacade.getInstance().checkGameFull())
                    {
                        int roads = ModelFacade.getInstance().getGame().getPlayer().getRoads();
                        int settlements = ModelFacade.getInstance().getGame().getPlayer().getSettlements();
                        if((roads == 15 || (roads == 14 && settlements == 4)) && !isOpen)
                        {                 
                            isOpen = true;
                            startMove(PieceType.ROAD, true, false);
                        }
                        else if(((roads == 14 && settlements == 5) || (roads == 13 && settlements == 4)))
                        {
                            startMove(PieceType.SETTLEMENT, true, false);
                        }
                    }
                    break;
                }
                case "Playing":
                case "Discarding":
                case "Rolling": ModelFacade.getInstance().getPoller().stop(); break;
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
        boolean isSetup = false;
        String status = ModelFacade.getInstance().getState();
        if(status.equals("FirstRound") || status.equals("SecondRound") || isRoadBuilding)
        {
            isFree = true;
            isSetup = true;
        }
        
        ModelFacade.getInstance().buildRoad(edgeLoc, isFree);
        PlayerInfo player = ModelFacade.getInstance().getPlayerInfo();
        getView().placeRoad(edgeLoc, player.getColor());
        if(isSetup)
        {
            isOpen = false;
        }
        if(isRoadBuilding && isFirst)
        {
            first = edgeLoc;
            ModelFacade.getInstance().updateGameModel();
            isFirst = false;
            startMove(PieceType.ROAD, true, false);
        }
        else if(isRoadBuilding)
        {
            isRoadBuilding = false;
            ModelFacade.getInstance().playRoadCard(first, edgeLoc);
        }
    }

    public void placeSettlement(VertexLocation vertLoc) 
    {
        boolean isFree = false;
        boolean isEnd = false;
        String status = ModelFacade.getInstance().getState();
        if(status.equals("FirstRound") || status.equals("SecondRound"))
        {
            isFree = true;
            isEnd = true;
        }
        
        ModelFacade.getInstance().buildSettlement(vertLoc, isFree);
        PlayerInfo player = ModelFacade.getInstance().getPlayerInfo();
        getView().placeSettlement(vertLoc, player.getColor());
        if(isEnd)
        {
            ModelFacade.getInstance().finishTurn();
        }
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
            isOpen = false;
	}
	
	public void startMove(PieceType pieceType, boolean isFree, boolean allowCancel) 
        {
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
        if(isSoldier)
        {
            ModelFacade.getInstance().playSoldierCard(victim.getPlayerIndex(), victim.getLocation());
        }
        else
        {
            ModelFacade.getInstance().robPlayer(victim.getLocation(), victim.getPlayerIndex());
        }
	}
        
    @Override
    public void update(Observable o, Object o1) 
    {
    	System.out.println("==========State: " + ModelFacade.getInstance().getState());
        initFromModel();
    }
}

