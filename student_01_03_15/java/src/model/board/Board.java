package model.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class Board extends Observable
{
	private transient Map<Integer, List<Hex>> hexNumbers;
	private Map<VertexLocation, Settlement> settlementLocation;
	private transient Map<VertexLocation, City> cityLocation;
	private transient Map<EdgeLocation, Road> roadLocation;
	private transient Map<Integer, List<Port>> playerPorts;
	
	private Hex[] hexes;
	private Port[] ports;
	private Road[] roads;
	private Settlement[] settlements;
	private City[] cities;
	private HexLocation robber;
	private int radius;
	
	//--------------------------------------------------------------------------------
	/**
	 * This function is called after JSON initializations
	 * JSON Model data is sorted into Maps
	 */
        public void Board(){}
        
	public void sort()
	{
		hexNumbers = new HashMap<>();
		roadLocation = new HashMap<>();
		settlementLocation = new HashMap<VertexLocation,Settlement>();
		cityLocation = new HashMap<>();
		playerPorts = new HashMap<>();
		for (int i = 0; i < 4; i++)
			playerPorts.put(i, null);
		sortNumbers();
		sortRoads();
		sortPorts();
		sortStructures();
                
	}
	
	//--------------------------------------------------------------------------------
	public void update(Board b)
	{
		this.setHexes(b.getHexes());
		this.setPorts(b.getPorts());
		this.setRoads(b.getRoads());
		this.setSettlements(b.getSettlements());
		this.setCities(b.getCities());
		this.setRobber(b.getRobber());
		this.sort();

	}
	
	//--------------------------------------------------------------------------------
	public void sortNumbers()
	{
		List<Hex> hexList;
		for(Hex hex : hexes)
		{
			int hexNum = hex.getNumber();
			hexList = hexNumbers.get(hexNum);
			if (hexList==null) hexList = new ArrayList<>();
			hexList.add(hex);
			hexNumbers.put(hexNum, hexList);
		}
	}

	//--------------------------------------------------------------------------------
	public void sortRoads()
	{
		for(Road road : roads)
		{
			EdgeLocation edge = road.getEdgeLocation().getNormalizedLocation();
			roadLocation.put(edge, road);
		}
	}

	//--------------------------------------------------------------------------------
	public void sortStructures()
	{
		for(Settlement settlement : settlements)
		{
			
			VertexLocation vertex = settlement.getLocation().getNormalizedLocation();
			
			
			
			settlementLocation.put(vertex, settlement);
		}
		
		for(City city :cities)
		{
			VertexLocation vertex = city.getLocation().getNormalizedLocation();
			cityLocation.put(vertex, city);
		}
	}

	//--------------------------------------------------------------------------------
	public void sortPorts()
	{
		List<Port> portList;
		int index;
		for(Port port : ports)
		{
			VertexLocation v = new VertexLocation(port.getLocation().getX(),port.getLocation().getY(), port.getDirection());
			if (cityLocation.containsKey(v))
			{
				index = cityLocation.get(v).getOwner();
				portList = playerPorts.get(index);
				if (portList == null) portList = new ArrayList<>();
				portList.add(port);
				playerPorts.put(index, portList);
			}
		}
	}

	public boolean hasPort(int index, String resource)
	{
		List<Port> portList = playerPorts.get(index);
		for (Port p : portList)
			if (p.getResource().equals(resource)) return true;
		return false;
	}
	//--------------------------------------------------------------------------------
	public Road getRoad(EdgeLocation edge)
	{
		return roadLocation.get(edge);
	}
	
	//--------------------------------------------------------------------------------
	public boolean containsRoad(EdgeLocation edge)
	{
		return roadLocation.get(edge) != null;
	}
        
        public boolean containsStructure(VertexLocation vertex)
        {
            Settlement s = settlementLocation.get(vertex);
            City c =  cityLocation.get(vertex);
            if(s != null || c != null) return true;
            else return false;
        }
	
	/**
	 * Check if cityHexes and settlementHexes has a structure built on vert
	 * @param vert the Vertex Location
	 * @return City or Settlement Object or null otherwise  
	 */
	public Object getStructure(VertexLocation vert)
	{
		Object obj = null;
		if (cityLocation.containsKey(vert))
		{
			obj = cityLocation.get(vert);
		}
		else if (settlementLocation.containsKey(vert))
		{
			obj = settlementLocation.get(vert);
		}
		return obj;
	}
	
	//--------------------------------------------------------------------------------
	public void setRoad(Road road)
	{
		EdgeLocation edge = road.getEdgeLocation().getNormalizedLocation();
		roadLocation.put(edge, road);
		
		List<Road> roadList = new ArrayList<>();
		for (Road r : roads)
			roadList.add(r);
		roadList.add(road);
		Road[] arr = roadList.toArray(new Road[0]);
		this.setRoads(arr);
	}
	
	//--------------------------------------------------------------------------------
	public void setSettlement(Settlement settlement)
	{
		if (settlement == null) return;
	
		VertexLocation vertex = settlement.getLocation().getNormalizedLocation();
		
		if(vertex == null)
		{
			System.out.println("vertex is null");
		}
		if(settlementLocation == null)
		{
			System.out.println("hasMap is null");
		}
	
		settlementLocation.put(vertex, settlement);

		List<Settlement> setList = new ArrayList<>();
		for (Settlement s : settlements)
			setList.add(s);
		setList.add(settlement);
		Settlement[] arr = setList.toArray(new Settlement[0]);
		this.setSettlements(arr);
	}

	//--------------------------------------------------------------------------------
	public void setCity(City city)
	{
		VertexLocation vertex = city.getLocation().getNormalizedLocation();
		cityLocation.put(vertex, city);

		List<City> cityList = new ArrayList<>();
		for (City c : cities)
			cityList.add(c);
		cityList.add(city);
		City[] arr = cityList.toArray(new City[0]);
		this.setCities(arr);
	}
        
	//--------------------------------------------------------------------------------
 	public boolean hasNeighborRoad(EdgeLocation location, int playerIndex, boolean setup) //already normalized
 	{
            boolean hasNeighbor = false;
            //already checked if the space is occupied
            if(setup)
            {
                //Check to see if the 2 vertexes both contain settlements already, else return true.
            }
            else
            {
                List<EdgeLocation> edgeLoc = location.getAdjacentEdges();
                
                for(EdgeLocation edge : edgeLoc)
                {
                    if(roadLocation.containsKey(edge) && (roadLocation.get(edge).getOwner() == playerIndex))
                    {
                        VertexLocation similar = findSimilarVertex(location, edge);
                        if(!containsStructure(similar))
                        {
                           hasNeighbor = true;
                           break; 
                        }
                        else
                        {
                            Settlement s = settlementLocation.get(similar);
                            if(s != null)
                            {
                                if(s.getOwner() == playerIndex)
                                {
                                    hasNeighbor = true;
                                    break;
                                }
                            }
                            else
                            {
                                City c = cityLocation.get(similar);
                                if(c.getOwner() == playerIndex)
                                {
                                    hasNeighbor = true;
                                    break;
                                }
                            }
                        }                        
                    }
                }
            }
            return hasNeighbor;
 	}
        
        private VertexLocation findSimilarVertex(EdgeLocation location, EdgeLocation edge)
        {
            List<VertexLocation> building = location.getAdjacentVertices();
            List<VertexLocation> adjacent = edge.getAdjacentVertices();
            for(VertexLocation buildVertex : building)
            {
                for(VertexLocation adjVertex : adjacent)
                {
                    if(buildVertex.equals(adjVertex))
                    {
                        return adjVertex;
                    }
                }
            }
            return null;
        }
	//--------------------------------------------------------------------------------
 	public boolean hasNeighborRoad(VertexLocation location, int playerIndex)
 	{
            List<EdgeLocation> edgeLoc = location.getAdjacentEdges();

            for(EdgeLocation edge : edgeLoc)
            {
                if(roadLocation.containsKey(edge) && (roadLocation.get(edge).getOwner() == playerIndex))
                {
                    return true;
                }
            }
            return false;
 	}
	//--------------------------------------------------------------------------------
 	public boolean hasNeighborStructure(VertexLocation location)
 	{
            List<VertexLocation> vertexLoc = location.getAdjacentVertices();

            for(VertexLocation vertex : vertexLoc)
            {
                if(containsStructure(vertex))
                {
                    return true;
                }
            }
            return false;
 	}
	//--------------------------------------------------------------------------------
 	public boolean hasWaterVertex(HexLocation hex, VertexDirection dir)
 	{
            int x = hex.getX();
            int y = hex.getY();
            if(Math.abs(x) > 3 || Math.abs(y) > 3) return true;
            else if(x == - 3)
            {
                if(y == 0) return true;
                else if(dir == VertexDirection.NorthWest) return true;
            }
            else if(x == 3)
            {
                if(y == -3 || y == 1) return true;
                else if(dir == VertexDirection.NorthEast) return true;
            }
            else if(x == -2 && y == -1) return true;
            else if(x == -1 && y == -2) return true;
            else if(x == 0 && y == -3) return true;
            else if(x == 1 && Math.abs(y) == 3) return true; 
            else if(x == 2 && (y == -3 || y == 2)) return true;
            
            return false;
 	}
	
        public boolean hasWaterEdge(HexLocation hex, EdgeDirection dir)
 	{
            int x = hex.getX();
            int y = hex.getY();
            if(Math.abs(x) > 3 || Math.abs(y) > 3) return true;
            else if(x == -3)
            {
                if(y == 0) return true;
                else if(dir != EdgeDirection.NorthEast) return true;
            }
            else if (x == 3)
            {
                if(y == -3 || y == 1) return true;
                else if(dir != EdgeDirection.NorthWest) return true;
            }
            else if(x == -2)
            {
                if(y == -1) return true;
                else if(y == 3 && dir == EdgeDirection.NorthWest) return true;
            }
            else if(x == 2)
            {
                if(y == -3 || y == 2) return true;
                else if(y == 1 && dir == EdgeDirection.NorthEast) return true;
            }
            else if(x == -1)
            {
                if(y == -2) return true;
                else if(y == 3 && dir == EdgeDirection.NorthWest) return true;
            }
            else if(x == 1)
            {
                if(y == -3 || y == 3) return true;
                else if(y == 2 && dir == EdgeDirection.NorthEast) return true;
            }
            else if(x == 0)
            {
                if(y == -3) return true;
                else if(y == 3 && dir != EdgeDirection.North) return true;
            }
            
            
            return false;
 	}
	//--------------------------------------------------------------------------------
 	public List<Port> getPorts(int index)
 	{
 		List<Port> playerPorts = new ArrayList<>();
 		return playerPorts;
 	}
	
	//--------------------------------------------------------------------------------
 	/**
 	 * This method checks if one edge neighbors another edge
 	 * Get the vertices of the second edge then locate the edges connected to that vertex
 	 * Check if one of the connected edges is the same as the first edge
 	 * @param e1 the first edge
 	 * @param e2 the second edge
 	 * @return true if the edges are neighbors, false otherwise
 	 */
// 	public boolean areNeighbors(EdgeLocation e1, EdgeLocation e2)
// 	{
//// 		EdgeLocation neighbor = e1.getNormalizedLocation();
//// 		EdgeLocation edge = e2.getNormalizedLocation();
////		List<VertexLocation> vertLoc = this.getVertices(edge);
////		for (VertexLocation v : vertLoc)
////		{
////			List<EdgeLocation> neighbors = this.getEdges(v);
////			for (EdgeLocation e : neighbors)
////				if (e.equals(neighbor)) return true;
////		}
// 		
// 		return false;
// 	}
 	
	//--------------------------------------------------------------------------------
	// JSON Getters & Setters
 	//--------------------------------------------------------------------------------
	public Hex[] getHexes()
	{
		return hexes;
	}


	public void setHexes(Hex[] hexes)
	{
		this.hexes = hexes;
	}


	public Port[] getPorts()
	{
		return ports;
	}


	public void setPorts(Port[] ports)
	{
		this.ports = ports;
	}


	public Road[] getRoads()
	{
		return roads;
	}


	public void setRoads(Road[] roads)
	{
		this.roads = roads;
	}


	public Settlement[] getSettlements()
	{
		return settlements;
	}


	public void setSettlements(Settlement[] settlements)
	{
		this.settlements = settlements;
	}


	public City[] getCities()
	{
		return cities;
	}


	public void setCities(City[] cities)
	{
		this.cities = cities;
	}


	public int getRadius()
	{
		return radius;
	}


	public void setRadius(int radius)
	{
		this.radius = radius;
	}


	public HexLocation getRobber()
	{
		return robber;
	}


	public void setRobber(HexLocation loc)
	{
		this.robber = loc;
	}
	
}

// Extra Code for use in PHASE 2
//--------------------------------------------------------------------------------
/*	public void _initBoard(boolean randTiles, boolean randNumbers, boolean randPorts)
{
	hexTypes = Arrays.asList(
			HexType.DESERT,
			HexType.BRICK, HexType.BRICK, HexType.BRICK,
			HexType.ORE, HexType.ORE, HexType.ORE,
			HexType.WOOD, HexType.WOOD, HexType.WOOD, HexType.WOOD,
			HexType.WHEAT, HexType.WHEAT, HexType.WHEAT, HexType.WHEAT,
			HexType.SHEEP, HexType.SHEEP, HexType.SHEEP, HexType.SHEEP);
	numbers = Arrays.asList(2,3,3,4,4,5,5,6,6,8,8,9,9,10,10,11,11,12);
	ports = Arrays.asList(PortType.ORE,PortType.WHEAT,PortType.SHEEP,PortType.BRICK,PortType.WOOD,
			PortType.THREE,PortType.THREE,PortType.THREE,PortType.THREE);
	
	if(randTiles) shuffleHexTypes();
	if(randNumbers) shuffleHexNumbers();
	if(randPorts) shuffleHexPorts();
	setHexes( _initHexes() );
	setHexNum();
}
//--------------------------------------------------------------------------------
public void _initHexes()
{
	List<Hex> hexes = new ArrayList<>();
	for (int i = 0; i < 18; i++) {
		if (hexTypes.get(i).equals(HexType.DESERT)) robber.setLocation(hexLocations.get(i));
		hexes.add(new Hex(hexLocations.get(i), hexTypes.get(i), numbers.get(i)));
	}
	return hexes;
}
//--------------------------------------------------------------------------------
public void shuffleHexTypes()
{
	Collections.shuffle(hexTypes);
}
//--------------------------------------------------------------------------------
public void shuffleHexNumbers()
{
	Collections.shuffle(numbers);
}
//--------------------------------------------------------------------------------
public void shuffleHexPorts()
{
	Collections.shuffle(ports);
}
//--------------------------------------------------------------------------------
public void setHexLocations()
{
	hexLocations = new ArrayList<>();		setEdges();
	int[] x = {0,1,2,-1,0,1,2,-2,-1,0,1,2,-2,-1,0,1,-2,-1,0};
	int[] y = {-2,-2,-2,-1,-1,-1,-1,0,0,0,0,0,1,1,1,1,2,2,2};
	for (int i = 0; i < x.length; i++)
	{
		hexLocations.add(new HexLocation(x[i], y[i]));
	}
}
public void setHexNum()
{
	for (Integer n : numbers)
	{
		List<Hex> hexList = new ArrayList<>();
		hexNumbers.put(n, hexList);
	}
}
*/