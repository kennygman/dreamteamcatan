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
	private transient Map<VertexLocation, Settlement> settlementLocation;
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
	
	public Board()	{}
	//--------------------------------------------------------------------------------
	/**
	 * This function is called after JSON initializations
	 * JSON Model data is sorted into Maps
	 */
	public void sort()
	{
		hexNumbers = new HashMap<>();
		roadLocation = new HashMap<>();
		settlementLocation = new HashMap<>();
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
	public boolean contains(EdgeLocation edge)
	{
		return roadLocation.containsKey(edge);
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
	public List<VertexLocation> getVertices(EdgeLocation location)
	{
 		EdgeLocation edge = location.getNormalizedLocation();
		List<VertexLocation> vertList = new ArrayList<>();
		
		if(edge.getDir().equals(EdgeDirection.NorthWest))
		{
			vertList.add(new VertexLocation(edge.getHexLoc(), VertexDirection.West).getNormalizedLocation());
			vertList.add(new VertexLocation(edge.getHexLoc(), VertexDirection.NorthWest).getNormalizedLocation());
		}
		else if (edge.getDir().equals(EdgeDirection.North))
		{
			vertList.add(new VertexLocation(edge.getHexLoc(), VertexDirection.NorthWest).getNormalizedLocation());
			vertList.add(new VertexLocation(edge.getHexLoc(), VertexDirection.NorthEast).getNormalizedLocation());
		}
		else
		{
			vertList.add(new VertexLocation(edge.getHexLoc(), VertexDirection.NorthEast).getNormalizedLocation());
			vertList.add(new VertexLocation(edge.getHexLoc(), VertexDirection.East).getNormalizedLocation());
		}
		
		return vertList;
	}

	//--------------------------------------------------------------------------------
	public List<EdgeLocation> getEdges(VertexLocation location)
	{
 		VertexLocation vert = location.getNormalizedLocation();
		List<EdgeLocation> edgeList = new ArrayList<>();
		
		// North Edge
		edgeList.add(new EdgeLocation(vert.getHexLoc(), EdgeDirection.North));

		// Edges for a NorthWest Vertex
		if (vert.getDir().equals(VertexDirection.NorthWest))
		{
			edgeList.add(new EdgeLocation(vert.getHexLoc(), EdgeDirection.NorthWest));
			edgeList.add(new EdgeLocation(vert.getHexLoc().getNeighborLoc(EdgeDirection.NorthWest), EdgeDirection.NorthEast));

		// Edges for a NorthEast Vertex
		} else {
			edgeList.add(new EdgeLocation(vert.getHexLoc(), EdgeDirection.NorthEast));
			edgeList.add(new EdgeLocation(vert.getHexLoc().getNeighborLoc(EdgeDirection.NorthEast), EdgeDirection.NorthWest));
		}
		
		return edgeList;
	}
	//--------------------------------------------------------------------------------
 	public boolean hasNeighborSettlement(EdgeLocation location, int index)
 	{
 		EdgeLocation edge = location.getNormalizedLocation();
		List<VertexLocation> vertLoc = this.getVertices(edge);
		for (VertexLocation v : vertLoc)
		{
			Object structure = this.getStructure(v);
			if (structure != null &&
				structure instanceof Settlement &&
				(((Settlement) structure).getOwner() == index))
			{
				return true;
			}
			
		}
		return false;
 	}
	//--------------------------------------------------------------------------------
 	public boolean hasNeighborRoad(EdgeLocation location, int index, boolean setup)
 	{
 		EdgeLocation edge = location.getNormalizedLocation();
		List<VertexLocation> vertLoc = this.getVertices(edge);
		for (VertexLocation v : vertLoc)
		{
			if (this.hasNeighborRoad(v, index, setup)) return true;
		}
 		return false;
 	}
	//--------------------------------------------------------------------------------
 	public boolean hasNeighborRoad(VertexLocation location, int index, boolean setup)
 	{
 		VertexLocation vert = location.getNormalizedLocation();
		List<EdgeLocation> edges = this.getEdges(vert);
		for (EdgeLocation e : edges)
		{
			Road road = this.getRoad(e);
			if (setup)
			{
				if (road != null) return true;
			}
			else
			{
				if (road != null && road.getOwner() == index)
				{
					return true;
				}
			}
		}
 		return false;
 	}
	//--------------------------------------------------------------------------------
 	public boolean hasNeighborStructure(VertexLocation location)
 	{
 		VertexLocation vert = location.getNormalizedLocation();
		if (vert.getDir().equals(VertexDirection.NorthWest))
		{
			if (	this.getStructure(new VertexLocation(vert.getHexLoc(),
							VertexDirection.NorthEast)) == null &&
					this.getStructure(new VertexLocation(vert.getHexLoc().getNeighborLoc(EdgeDirection.NorthWest),
							VertexDirection.NorthEast)) == null &&
					this.getStructure(new VertexLocation(vert.getHexLoc().getNeighborLoc(EdgeDirection.SouthWest),
							VertexDirection.NorthEast)) == null
				) return false;
		}
		else
		{
			if (	this.getStructure(new VertexLocation(vert.getHexLoc(),
							VertexDirection.NorthWest)) == null &&
					this.getStructure(new VertexLocation(vert.getHexLoc().getNeighborLoc(EdgeDirection.NorthEast),
							VertexDirection.NorthWest)) == null &&
					this.getStructure(new VertexLocation(vert.getHexLoc().getNeighborLoc(EdgeDirection.SouthEast),
							VertexDirection.NorthWest)) == null
				)
				{
					return false;
				}
		}

 		return true;
 	}
	//--------------------------------------------------------------------------------
 	public boolean hasNeighborWater(HexLocation hex)
 	{
 		return Math.abs(hex.getX()) > 2 && Math.abs(hex.getY()) > 2;
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
 	public boolean areNeighbors(EdgeLocation e1, EdgeLocation e2)
 	{
 		EdgeLocation neighbor = e1.getNormalizedLocation();
 		EdgeLocation edge = e2.getNormalizedLocation();
		List<VertexLocation> vertLoc = this.getVertices(edge);
		for (VertexLocation v : vertLoc)
		{
			List<EdgeLocation> neighbors = this.getEdges(v);
			for (EdgeLocation e : neighbors)
				if (e.equals(neighbor)) return true;
		}
 		
 		return false;
 	}
 	
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