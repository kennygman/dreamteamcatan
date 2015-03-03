package model.board;

import shared.locations.VertexLocation;

public class Settlement extends Piece
{
	private int owner;
	private VertexLocation location;
	
	public Settlement(int owner, VertexLocation location)
	{
		this.owner = owner;
		this.location = location;
	}
	
	public int getOwner()
	{
		return owner;
	}
	public void setOwner(int owner)
	{
		this.owner = owner;
	}
	public VertexLocation getLocation()
	{
		return location;
	}
	public void setLocation(VertexLocation location)
	{
		this.location = location;
	}

}