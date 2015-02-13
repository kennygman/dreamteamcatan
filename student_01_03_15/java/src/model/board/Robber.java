package model.board;

import shared.definitions.PieceType;
import shared.locations.HexLocation;

public class Robber extends Piece {
	
	private HexLocation location;

	/**
	 * Constructor
	 * Initialize Robber on Desert Hex
	 * Set type to ROBBER
	 */
	public Robber()
	{
		this.setType(PieceType.ROBBER);
	}

	/**
	 * @return the location
	 */
	public HexLocation getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(HexLocation location) {
		this.location = location;
	}
	
	
	
}
