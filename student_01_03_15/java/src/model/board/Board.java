package model.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import model.player.Resources;
import shared.definitions.ResourceType;
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
	private transient Map<Integer, Resources> playerPorts;

	private Hex[] hexes;
	private Port[] ports;
	private Road[] roads;
	private Settlement[] settlements;
	private City[] cities;
	private HexLocation robber;
	private int radius;

	// --------------------------------------------------------------------------------
	/**
	 * This function is called after JSON initializations JSON Model data is
	 * sorted into Maps
	 */
	public void sort()
	{
		hexNumbers = new HashMap<>();
		roadLocation = new HashMap<>();
		settlementLocation = new HashMap<VertexLocation, Settlement>();
		cityLocation = new HashMap<>();
		playerPorts = new HashMap<>();
		for (int i = 0; i < 4; i++)
		{
			playerPorts.put(i, null);
			playerPorts.put(i, new Resources(4, 4, 4, 4, 4));
		}
		sortNumbers();
		sortRoads();
		sortStructures();
		sortPorts();
	}

	// --------------------------------------------------------------------------------
	/**
	 * Updates the board with a new board. Useful when polling.
	 * 
	 * @param b
	 */
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

	// --------------------------------------------------------------------------------
	/**
	 * Sorts hexes by hex number. If hex number returns a null list, create a
	 * new arraylist.
	 */
	public void sortNumbers()
	{
		List<Hex> hexList;
		for (Hex hex : hexes)
		{
			int hexNum = hex.getNumber();
			hexList = hexNumbers.get(hexNum);
			if (hexList == null)
				hexList = new ArrayList<>();
			hexList.add(hex);
			hexNumbers.put(hexNum, hexList);
		}
	}

	// --------------------------------------------------------------------------------
	/**
	 * Adds roads to roadLocation by normalized location.
	 */
	public void sortRoads()
	{
		for (Road road : roads)
		{
			EdgeLocation edge = road.getEdgeLocation().getNormalizedLocation();
			roadLocation.put(edge, road);
		}
	}

	// --------------------------------------------------------------------------------
	/**
	 * Adds settlements to settlementLocation by normalized location. Adds
	 * cities to cityLocation by normalized location.
	 */
	public void sortStructures()
	{
		for (Settlement settlement : settlements)
		{

			VertexLocation vertex = settlement.getLocation()
					.getNormalizedLocation();

			settlementLocation.put(vertex, settlement);
		}

		for (City city : cities)
		{
			VertexLocation vertex = city.getLocation().getNormalizedLocation();
			cityLocation.put(vertex, city);
		}
	}

	// --------------------------------------------------------------------------------
	/**
	 * Sets resources by ResourceType if port resource is null or sets resource
	 * by port resource and port ratio.
	 * 
	 * @param port
	 * @param index
	 */
	public void updatePlayerPort(Port port, int index)
	{
		Resources resources = playerPorts.get(index);

		if (port.getResource() == null) // 3 for 1
		{
			for (ResourceType r : Resources.getResourceList())
			{
				resources.setResource(r, 3);
			}
		} else
		{
			resources.setResource(ResourceType.fromString(port.getResource()),
					port.getRatio());
		}
	}

	// --------------------------------------------------------------------------------
	/**
	 * Calls updatePlayerPort for each port if it contains a city or settlement
	 * as a neighbor.
	 */
	public void sortPorts()
	{
		int index = -1;
		for (Port port : ports)
		{
			EdgeLocation portEdge = new EdgeLocation(port.getLocation(),
					EdgeLocation.getEdgeDirection(port.getDirection()))
					.getNormalizedLocation();

			List<VertexLocation> neighbors = portEdge.getAdjacentVertices();

			for (VertexLocation v : neighbors)
			{
				if (cityLocation.containsKey(v))
				{
					index = cityLocation.get(v).getOwner();
					updatePlayerPort(port, index);
				}

				if (settlementLocation.containsKey(v))
				{
					index = settlementLocation.get(v).getOwner();
					updatePlayerPort(port, index);
				}
			}
		}
		return;
	}

	// --------------------------------------------------------------------------------
	/**
	 * @param index
	 * @param resource
	 * @param ratio
	 * @return whether or not there is a port at that playerPort index of that
	 *         resource and ratio
	 */
	public boolean hasPort(int index, String resource, int ratio)
	{
		Resources resources = playerPorts.get(index);
		return resources.getResourceAmount(resource) == ratio;
	}

	// --------------------------------------------------------------------------------
	public Road getRoad(EdgeLocation edge)
	{
		return roadLocation.get(edge);
	}

	// --------------------------------------------------------------------------------
	/**
	 * Whether or not roadLocation contains a road.
	 * 
	 * @param edge
	 * @return whether or not roadLocation contains a road.
	 */
	public boolean containsRoad(EdgeLocation edge)
	{
		return roadLocation.get(edge) != null;
	}

	// --------------------------------------------------------------------------------
	/**
	 * Checks to see if a vertex contains a city or a settlement.
	 * 
	 * @param vertex
	 * @return whether or not a vertex contains a city or settlement
	 */
	public boolean containsStructure(VertexLocation vertex)
	{
		Settlement s = settlementLocation.get(vertex);
		City c = cityLocation.get(vertex);
		if (s != null || c != null)
			return true;
		else
			return false;
	}

	/**
	 * Check if cityHexes and settlementHexes has a structure built on vert
	 * 
	 * @param vert
	 *            the Vertex Location
	 * @return City or Settlement Object or null otherwise
	 */
	public Object getStructure(VertexLocation vert)
	{
		Object obj = null;
		if (cityLocation.containsKey(vert))
		{
			obj = cityLocation.get(vert);
		} else if (settlementLocation.containsKey(vert))
		{
			obj = settlementLocation.get(vert);
		}
		return obj;
	}

	// --------------------------------------------------------------------------------
	/**
	 * Builds an Object[] that contains each object (city, settlement) that is
	 * on the vertexes of the given hex
	 * 
	 * @param hex
	 * @return array of objects that contains the objects (city, settlement) on
	 *         that hex
	 */
	public Object[] getStructure(HexLocation hex)
	{
		Object[] objects = new Object[3];
		int index = 0;
		Object struct;
		struct = getStructure(new VertexLocation(hex, VertexDirection.NorthEast));
		if (struct != null)
		{
			objects[index] = struct;
			index++;
		}
		struct = getStructure(new VertexLocation(hex, VertexDirection.NorthWest));
		if (struct != null)
		{
			objects[index] = struct;
			index++;
		}
		struct = getStructure(new VertexLocation(new HexLocation(
				hex.getX() - 1, hex.getY() + 1), VertexDirection.NorthEast));
		if (struct != null)
		{
			objects[index] = struct;
			index++;
		}
		struct = getStructure(new VertexLocation(new HexLocation(hex.getX(),
				hex.getY() + 1), VertexDirection.NorthWest));
		if (struct != null)
		{
			objects[index] = struct;
			index++;
		}
		struct = getStructure(new VertexLocation(new HexLocation(hex.getX(),
				hex.getY() + 1), VertexDirection.NorthEast));
		if (struct != null)
		{
			objects[index] = struct;
			index++;
		}
		struct = getStructure(new VertexLocation(new HexLocation(
				hex.getX() + 1, hex.getY()), VertexDirection.NorthWest));
		if (struct != null)
		{
			objects[index] = struct;
			index++;
		}
		return objects;
	}

	// --------------------------------------------------------------------------------
	/**
	 * Adds road to roads array in model
	 * 
	 * @param road
	 */
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

	// --------------------------------------------------------------------------------
	/**
	 * Adds settlement to settlements array in model
	 * 
	 * @param settlement
	 */
	public void setSettlement(Settlement settlement)
	{
		if (settlement == null)
			return;
		VertexLocation vertex = settlement.getLocation()
				.getNormalizedLocation();
		if (vertex == null)
		{
			System.out.println("vertex is null");
		}
		if (settlementLocation == null)
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

	// --------------------------------------------------------------------------------
	/**
	 * Adds city to cities array in model
	 * 
	 * @param city
	 */
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

	// --------------------------------------------------------------------------------
	/**
	 * Checks if there is a neighbor road according to that edge and player
	 * index
	 * 
	 * @param location
	 * @param playerIndex
	 * @param status
	 * @return whether or not there is a neighbor road
	 */
	public boolean hasNeighborRoad(EdgeLocation location, int playerIndex,
			String status) // already normalized
	{
		boolean hasNeighbor = false;
		if (status.equals("FirstRound") || status.equals("SecondRound"))
		{
			// Check to see if the 2 vertexes both contain settlements already,
			// else return true.
			List<VertexLocation> vertices = location.getAdjacentVertices();
			for (VertexLocation v : vertices)
			{
				if (!(containsStructure(v)
						|| hasWaterVertex(v.getHexLoc(), v.getDir()) || hasNeighborStructure(v)))
				{
					hasNeighbor = true;
					break;
				}
			}

		} else
		{
			List<EdgeLocation> edgeLoc = location.getAdjacentEdges();
			for (EdgeLocation edge : edgeLoc)
			{
				if (roadLocation.containsKey(edge)
						&& (roadLocation.get(edge).getOwner() == playerIndex))
				{
					VertexLocation similar = findSimilarVertex(location, edge);
					if (!containsStructure(similar))
					{
						hasNeighbor = true;
						break;
					} else
					{
						Settlement s = settlementLocation.get(similar);
						if (s != null)
						{
							if (s.getOwner() == playerIndex)
							{
								hasNeighbor = true;
								break;
							}
						} else
						{
							City c = cityLocation.get(similar);
							if (c.getOwner() == playerIndex)
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

	/**
	 * Compares to edge locations to find a vertex that is shared between the
	 * two
	 * 
	 * @param location
	 * @param edge
	 * @return the vertex location that is shared between the two edges
	 */
	private VertexLocation findSimilarVertex(EdgeLocation location,
			EdgeLocation edge)
	{
		List<VertexLocation> building = location.getAdjacentVertices();
		List<VertexLocation> adjacent = edge.getAdjacentVertices();
		for (VertexLocation buildVertex : building)
		{
			for (VertexLocation adjVertex : adjacent)
			{
				if (buildVertex.equals(adjVertex))
				{
					return adjVertex;
				}
			}
		}
		return null;
	}

	// --------------------------------------------------------------------------------
	/**
	 * Checks to see if there is a road from a certain player touches a certain
	 * vertex
	 * 
	 * @param location
	 * @param playerIndex
	 * @return whether or not there is a player's road adjacent to the given
	 *         vetex
	 */
	public boolean hasNeighborRoad(VertexLocation location, int playerIndex)
	{
		List<EdgeLocation> edgeLoc = location.getAdjacentEdges();
		for (EdgeLocation edge : edgeLoc)
		{
			if (roadLocation.containsKey(edge)
					&& (roadLocation.get(edge).getOwner() == playerIndex))
			{
				return true;
			}
		}
		return false;
	}

	// --------------------------------------------------------------------------------
	/**
	 * Checks to see if there is a structure on the vertex
	 * 
	 * @param location
	 * @return whether or not a structure is on the vertex
	 */
	public boolean hasNeighborStructure(VertexLocation location)
	{
		List<VertexLocation> vertexLoc = location.getAdjacentVertices();
		for (VertexLocation vertex : vertexLoc)
		{
			if (containsStructure(vertex))
			{
				return true;
			}
		}
		return false;
	}

	// --------------------------------------------------------------------------------
	/**
	 * Checks if there is a water vertex on that hex and direction
	 * 
	 * @param hex
	 * @param dir
	 * @return whether or not there is a water vertex on that hex and direction
	 */
	public boolean hasWaterVertex(HexLocation hex, VertexDirection dir)
	{
		int x = hex.getX();
		int y = hex.getY();
		if (Math.abs(x) > 3 || Math.abs(y) > 3)
			return true;
		else if (x == -3)
		{
			if (y == 0)
				return true;
			else if (dir == VertexDirection.NorthWest)
				return true;
		} else if (x == 3)
		{
			if (y == -3 || y == 1)
				return true;
			else if (dir == VertexDirection.NorthEast)
				return true;
		} else if (x == -2 && y == -1)
			return true;
		else if (x == -1 && y == -2)
			return true;
		else if (x == 0 && y == -3)
			return true;
		else if (x == 1 && Math.abs(y) == 3)
			return true;
		else if (x == 2 && (y == -3 || y == 2))
			return true;
		return false;
	}

	// --------------------------------------------------------------------------------
	/**
	 * Checks if there is a water hex on the hex in the given direction
	 * 
	 * @param hex
	 * @param dir
	 * @return whether or not there is a water hex on the hex in the given
	 *         direction
	 */
	public boolean hasWaterEdge(HexLocation hex, EdgeDirection dir)
	{
		int x = hex.getX();
		int y = hex.getY();
		if (Math.abs(x) > 3 || Math.abs(y) > 3)
			return true;
		else if (x == -3)
		{
			if (y == 0)
				return true;
			else if (dir != EdgeDirection.NorthEast)
				return true;
		} else if (x == 3)
		{
			if (y == -3 || y == 1)
				return true;
			else if (dir != EdgeDirection.NorthWest)
				return true;
		} else if (x == -2)
		{
			if (y == -1)
				return true;
			else if (y == 3 && dir == EdgeDirection.NorthWest)
				return true;
		} else if (x == 2)
		{
			if (y == -3 || y == 2)
				return true;
			else if (y == 1 && dir == EdgeDirection.NorthEast)
				return true;
		} else if (x == -1)
		{
			if (y == -2)
				return true;
			else if (y == 3 && dir == EdgeDirection.NorthWest)
				return true;
		} else if (x == 1)
		{
			if (y == -3 || y == 3)
				return true;
			else if (y == 2 && dir == EdgeDirection.NorthEast)
				return true;
		} else if (x == 0)
		{
			if (y == -3)
				return true;
			else if (y == 3 && dir != EdgeDirection.North)
				return true;
		}
		return false;
	}

	// --------------------------------------------------------------------------------
	/**
	 * Checks if the hex is a water hex
	 * 
	 * @param hex
	 * @return whether or not it is a water hex
	 */
	public boolean isWaterHex(HexLocation hex)
	{
		int x = hex.getX();
		int y = hex.getY();
		if (Math.abs(x) >= 3 || Math.abs(y) >= 3)
			return true;
		else if (x == 1 && y == 2)
			return true;
		else if (x == 2 && y == 1)
			return true;
		else if (x == -1 && y == -2)
			return true;
		else if (x == -2 && y == -1)
			return true;
		return false;
	}

	// --------------------------------------------------------------------------------
	public Resources getPorts(int index)
	{
		return playerPorts.get(index);
	}

	// --------------------------------------------------------------------------------
	// JSON Getters & Setters
	// --------------------------------------------------------------------------------
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
// --------------------------------------------------------------------------------
/*
 * public void _initBoard(boolean randTiles, boolean randNumbers, boolean
 * randPorts) { hexTypes = Arrays.asList( HexType.DESERT, HexType.BRICK,
 * HexType.BRICK, HexType.BRICK, HexType.ORE, HexType.ORE, HexType.ORE,
 * HexType.WOOD, HexType.WOOD, HexType.WOOD, HexType.WOOD, HexType.WHEAT,
 * HexType.WHEAT, HexType.WHEAT, HexType.WHEAT, HexType.SHEEP, HexType.SHEEP,
 * HexType.SHEEP, HexType.SHEEP); numbers =
 * Arrays.asList(2,3,3,4,4,5,5,6,6,8,8,9,9,10,10,11,11,12); ports =
 * Arrays.asList
 * (PortType.ORE,PortType.WHEAT,PortType.SHEEP,PortType.BRICK,PortType.WOOD,
 * PortType.THREE,PortType.THREE,PortType.THREE,PortType.THREE);
 * 
 * if(randTiles) shuffleHexTypes(); if(randNumbers) shuffleHexNumbers();
 * if(randPorts) shuffleHexPorts(); setHexes( _initHexes() ); setHexNum(); }
 * //--
 * --------------------------------------------------------------------------
 * ---- public void _initHexes() { List<Hex> hexes = new ArrayList<>(); for (int
 * i = 0; i < 18; i++) { if (hexTypes.get(i).equals(HexType.DESERT))
 * robber.setLocation(hexLocations.get(i)); hexes.add(new
 * Hex(hexLocations.get(i), hexTypes.get(i), numbers.get(i))); } return hexes; }
 * /
 * /----------------------------------------------------------------------------
 * ---- public void shuffleHexTypes() { Collections.shuffle(hexTypes); }
 * //------
 * --------------------------------------------------------------------------
 * public void shuffleHexNumbers() { Collections.shuffle(numbers); }
 * //----------
 * ---------------------------------------------------------------------- public
 * void shuffleHexPorts() { Collections.shuffle(ports); }
 * //----------------------
 * ---------------------------------------------------------- public void
 * setHexLocations() { hexLocations = new ArrayList<>(); setEdges(); int[] x =
 * {0,1,2,-1,0,1,2,-2,-1,0,1,2,-2,-1,0,1,-2,-1,0}; int[] y =
 * {-2,-2,-2,-1,-1,-1,-1,0,0,0,0,0,1,1,1,1,2,2,2}; for (int i = 0; i < x.length;
 * i++) { hexLocations.add(new HexLocation(x[i], y[i])); } } public void
 * setHexNum() { for (Integer n : numbers) { List<Hex> hexList = new
 * ArrayList<>(); hexNumbers.put(n, hexList); } }
 */