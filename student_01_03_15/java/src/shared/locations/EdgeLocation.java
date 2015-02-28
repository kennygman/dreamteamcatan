package shared.locations;

/**
 * Represents the location of an edge on a hex map
 */
public class EdgeLocation
{
	private int x;
	private int y;
	private String direction;
	private HexLocation hexLoc;
	private EdgeDirection dir;
	
	
	
	public EdgeLocation(int x, int y, String direction)
	{
		this.x = x;
		this.y = y;
		this.direction = direction;
                translate();
	}
	public EdgeLocation(HexLocation hexLoc, EdgeDirection dir)
	{
		setHexLoc(hexLoc);
		setDir(dir);
	}
	
	public void translate()
	{
		hexLoc = new HexLocation(x,y);

		switch (direction)
		{
		case "S" :
			dir = EdgeDirection.South;
			break;
		case "SW" :
			dir = EdgeDirection.SouthWest;
			break;
		case "SE" :
			dir = EdgeDirection.SouthEast;
			break;
		case "N" :
			dir = EdgeDirection.North;
			break;
		case "NE" :
			dir = EdgeDirection.NorthEast;
			break;
		case "NW" :
			dir = EdgeDirection.NorthWest;
			break;
		default:
			assert false;
			return;
		}
	}
        private void convert()
	{
		switch (dir)
		{
		case South :
			direction = "S";
			break;
		case SouthWest :
			direction = "SW";
			break;
		case SouthEast :
			direction = "SE";
			break;
		case North :
			direction = "N";
			break;
		case NorthEast :
			direction = "NE";
			break;
		case NorthWest :
			direction = "NW";
			break;
		default:
			assert false;
			return;
		}
	}
	public HexLocation getHexLoc()
	{
		return hexLoc;
	}
	
	private void setHexLoc(HexLocation hexLoc)
	{
		this.hexLoc = hexLoc;
                x = hexLoc.getX();
                y = hexLoc.getY();
	}
	
	public EdgeDirection getDir()
	{
		return dir;
	}
	
	private void setDir(EdgeDirection dir)
	{
		this.dir = dir;
                convert();
	}
	
	@Override
	public String toString()
	{
		if (hexLoc == null) translate();
		return "EdgeLocation [hexLoc=" + hexLoc + ", dir=" + dir + "]";
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dir == null) ? 0 : dir.hashCode());
		result = prime * result + ((hexLoc == null) ? 0 : hexLoc.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (hexLoc == null) translate();
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		EdgeLocation other = (EdgeLocation)obj;
		if(dir != other.dir)
			return false;
		if(hexLoc == null)
		{
			if(other.hexLoc != null)
				return false;
		}
		else if(!hexLoc.equals(other.hexLoc))
			return false;
		return true;
	}
	
	/**
	 * Returns a canonical (i.e., unique) value for this edge location. Since
	 * each edge has two different locations on a map, this method converts a
	 * hex location to a single canonical form. This is useful for using hex
	 * locations as map keys.
	 * 
	 * @return Normalized hex location
	 */
	public EdgeLocation getNormalizedLocation()
	{
		if (hexLoc == null) translate();
		
		// Return an EdgeLocation that has direction NW, N, or NE
		
		switch (dir)
		{
			case NorthWest:
			case North:
			case NorthEast:
				return this;
			case SouthWest:
			case South:
			case SouthEast:
				return new EdgeLocation(hexLoc.getNeighborLoc(dir),
						dir.getOppositeDirection());
			default:
				assert false;
				return null;
		}
	}
	
}