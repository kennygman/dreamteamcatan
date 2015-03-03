package model.board;

import shared.locations.HexLocation;

public class Hex 
{
	private String resource;
	private HexLocation location;
	private int number;
	
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
	public void setLocation(HexLocation location)
	{
		this.location = location;
	}
	public int getNumber()
	{
		return number;
	}
	public void setNumber(int number)
	{
		this.number = number;
	}
}
