package model.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import shared.definitions.HexType;
import shared.definitions.PortType;
import shared.locations.HexLocation;

public class BoardBuilder
{
	private List<Hex> hexes;
	private List<Port> ports;
	private HexLocation robber;
	
	// --------------------------------------------------------------------------------
	public void build(boolean randHexes, boolean randNumbers, boolean randPorts)
	{
		initHexes(randHexes, randNumbers);
		initPorts(randPorts);
	}
	
	public Hex[] getHexes(){return hexes.toArray(new Hex[0]);}
	public Port[] getPorts(){return ports.toArray(new Port[0]);}
	public HexLocation getRobber(){return robber;}
	
	// --------------------------------------------------------------------------------
	private void initHexes(boolean randHexes, boolean randNumbers)
	{
		hexes = new ArrayList<>();
		List<HexLocation> hexLocations = getHexLocations();

		List<HexType> hexTypes = Arrays.asList(hexList);
		List<Integer> numbers = Arrays.asList(numberList);
		
		if (randHexes) Collections.shuffle(hexTypes);
		if (randNumbers) Collections.shuffle(numbers);

		for (int i = 0; i < hexLocations.size(); i++)
		{
			Hex hex = new Hex();
			hex.setLocation(hexLocations.get(i));
			hex.setResource(HexType.asString(hexTypes.get(i)));
			hex.setNumber(i);
			
			if (hexTypes.get(i).equals(HexType.DESERT))
			{
				robber = hexLocations.get(i);
			}
			hexes.add(hex);
		}
	}
	
	// --------------------------------------------------------------------------------
	private void initPorts(boolean randPorts)
	{
		ports = new ArrayList<>();
		List<String> direction = Arrays.asList("S","S","SW","NE","N","NE","NW","SE","NW");
		List<PortType> portTypes = Arrays.asList(portList);
		List<HexLocation> portLocations = getPortLocations();
		
		if (randPorts) Collections.shuffle(portTypes);
		
		for (int i = 0; i < portLocations.size(); i++)
		{
			Port port = new Port();
			port.setLocation(portLocations.get(i));
			port.setDirection(direction.get(i));
			port.setRatio(3);
			
			PortType pt = portTypes.get(i);
			if (!pt.equals(PortType.THREE))
			{
				port.setRatio(2);
				port.setResource(PortType.asString(pt));
			}
			ports.add(port);
		}
		
	}

	// --------------------------------------------------------------------------------
	private List<HexLocation> getHexLocations()
	{
		List<HexLocation> hexLocations = new ArrayList<>();
		int[] x = {0,1,2,-1,0,1,2,-2,-1,0,1,2,-2,-1,0,1,-2,-1,0};
		int[] y = {-2,-2,-2,-1,-1,-1,-1,0,0,0,0,0,1,1,1,1,2,2,2};
		for (int i = 0; i < x.length; i++)
		{
			hexLocations.add(new HexLocation(x[i], y[i]));
		}
		return hexLocations;
	}
	
	// --------------------------------------------------------------------------------
	private List<HexLocation> getPortLocations()
	{
		List<HexLocation> portLocations = new ArrayList<>();
		int[] x = {0,-1,-1,1,-2,2,3,-3,-3};
		int[] y = {-2,-3,-3,2,3,3,1,0,-1};
		
		for (int i = 0; i < x.length; i++)
		{
			portLocations.add(new HexLocation(x[i], y[i]));
		}
		return portLocations;
	}
	
	// --------------------------------------------------------------------------------
	private final Integer[] numberList = {2,3,3,4,4,5,5,6,6,8,8,9,9,10,10,11,11,12};
	private final HexType[] hexList = 
		{
			HexType.DESERT,
			HexType.ORE, HexType.ORE, HexType.ORE,
			HexType.BRICK, HexType.BRICK, HexType.BRICK,
			HexType.WOOD, HexType.WOOD, HexType.WOOD, HexType.WOOD,
			HexType.WHEAT, HexType.WHEAT, HexType.WHEAT, HexType.WHEAT,
			HexType.SHEEP, HexType.SHEEP, HexType.SHEEP, HexType.SHEEP
		};
	private final PortType[] portList =
		{
			PortType.ORE,PortType.WHEAT,PortType.SHEEP,PortType.BRICK,PortType.WOOD,
			PortType.THREE,PortType.THREE,PortType.THREE,PortType.THREE
		};

	// --------------------------------------------------------------------------------
}
