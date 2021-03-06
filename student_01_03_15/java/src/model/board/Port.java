package model.board;

import shared.locations.EdgeLocation;
import shared.locations.HexLocation;

public class Port
{

	private String resource;
	private HexLocation location;
	private String direction;
	private int ratio;

	public String getResource()
	{
		return resource;
	}

	public void setResource(String resource)
	{
		this.resource = resource;
	}

	public HexLocation getLocation()
	{
		return location;
	}

	public EdgeLocation getEdgeLocation()
	{
		return new EdgeLocation(location.getX(), location.getY(), direction);
	}

	public void setLocation(HexLocation location)
	{
		this.location = location;
	}

	public String getDirection()
	{
		return direction;
	}

	public void setDirection(String direction)
	{
		this.direction = direction;
	}

	public int getRatio()
	{
		return ratio;
	}

	public void setRatio(int ratio)
	{
		this.ratio = ratio;
	}

}
