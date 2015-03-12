package model.board;

import shared.locations.EdgeLocation;

public class Road extends Piece
{
	private int owner;
	private EdgeLocation location;
	
	public Road(int owner, EdgeLocation edge)
	{
		this.owner = owner;
		this.location = edge;
	}
	public int getOwner()
	{
		return owner;
	}
	public void setOwner(int owner)
	{
		this.owner = owner;
	}

	public EdgeLocation getEdgeLocation()
	{
		return location;
	}
	public void setEdgeLocation(EdgeLocation edgeLocation)
	{
		this.location = edgeLocation;
	}
	
}