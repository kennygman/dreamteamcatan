package model.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

public class Board
{
	private transient Map<Integer, List<Hex>> hexNumbers;
	private transient Map<HexLocation, List<Port>> hexPorts;
	private transient Map<VertexLocation, Settlement> settlementHexes;
	private transient Map<VertexLocation, City> cityHexes;
	private transient Map<EdgeLocation, Road> roadHexes;
	
	private Hex[] hexes;
	private Port[] ports;
	private Road[] roads;
	private Settlement[] settlements;
	private City[] cities;
	private HexLocation robber;
	private int radius;
	
	public Board()
	{
		hexNumbers = new HashMap<>();
		hexPorts = new HashMap<>();
		roadHexes = new HashMap<>();
		settlementHexes = new HashMap<>();
		cityHexes = new HashMap<>();
	}

	//--------------------------------------------------------------------------------
	/**
	 * This function is called after JSON initializations
	 * JSON Model data is sorted into Maps
	 */
	public void sort()
	{
		sortNumbers();
		sortRoads();
		sortStructures();
		sortPorts();
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
			roadHexes.put(edge, road);
		}
	}

	//--------------------------------------------------------------------------------
	public void sortStructures()
	{
		for(Settlement settlement : settlements)
		{
			VertexLocation vertex = settlement.getLocation().getNormalizedLocation();
			settlementHexes.put(vertex, settlement);
		}
		
		for(City city :cities)
		{
			VertexLocation vertex = city.getLocation().getNormalizedLocation();
			cityHexes.put(vertex, city);
		}
	}

	//--------------------------------------------------------------------------------
	public void sortPorts()
	{
		List<Port> portList;
		for(Port port : ports)
		{
			HexLocation loc = port.getLocation();
			portList = hexPorts.get(loc);
			if (portList==null) portList = new ArrayList<>();
			portList.add(port);
			hexPorts.put(loc, portList);
		}
	}

	public Road getRoad(EdgeLocation edge)
	{
		return roadHexes.get(edge);
	}
	
	public boolean contains(EdgeLocation edge)
	{
		return roadHexes.containsKey(edge);
	}
	
	/**
	 * Check if cityHexes and settlementHexes has a structure built on vert
	 * @param vert the Vertex Location
	 * @return City or Settlement Object or null otherwise  
	 */
	public Object getStructure(VertexLocation vert)
	{
		Object obj = null;
		if (cityHexes.containsKey(vert))
		{
			obj = cityHexes.get(vert);
		}
		else if (settlementHexes.containsKey(vert))
		{
			obj = settlementHexes.get(vert);
		}
		return obj;
	}
	//--------------------------------------------------------------------------------
	public void setRoad(Road road)
	{
		EdgeLocation edge = road.getEdgeLocation().getNormalizedLocation();
		roadHexes.put(edge, road);
		
		List<Road> roadList = Arrays.asList(roads);
		roadList.add(road);
		Road[] arr = roadList.toArray(new Road[0]);
		this.setRoads(arr);
	}
	public void setSettlement(Settlement settlement)
	{
		VertexLocation vertex = settlement.getLocation().getNormalizedLocation();
		settlementHexes.put(vertex, settlement);

		List<Settlement> setList = Arrays.asList(settlements);
		setList.add(settlement);
		Settlement[] arr = setList.toArray(new Settlement[0]);
		this.setSettlements(arr);
	}
	public void setCity(City city)
	{
		VertexLocation vertex = city.getLocation().getNormalizedLocation();
		cityHexes.put(vertex, city);

		List<City> cityList = Arrays.asList(cities);
		cityList.add(city);
		City[] arr = cityList.toArray(new City[0]);
		this.setCities(arr);
	}
	
	//--------------------------------------------------------------------------------
	public List<VertexLocation> getVertices(EdgeLocation edge)
	{
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
	public List<EdgeLocation> getEdges(VertexLocation vert)
	{
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
	
	
	//--------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------

	// JSON Getters & Setters --------------------------------------------------------------------------------
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