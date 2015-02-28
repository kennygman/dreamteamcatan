package shared.locations;

import java.util.ArrayList;
import java.util.List;
import static shared.locations.VertexDirection.*;

/**
 * Represents the location of a vertex on a hex map
 */
public class VertexLocation
{
	private int x;
	private int y;
	private String direction;
	private HexLocation hexLoc;
	private VertexDirection dir;
	
	public VertexLocation(int x, int y, String direction)
	{
		this.x = x;
		this.y = y;
		this.direction = direction;
                //translate();
	}
	
	public VertexLocation(HexLocation hexLoc, VertexDirection dir)
	{
		setHexLoc(hexLoc);
		setDir(dir);
	}
	
	public void translate()
	{
		hexLoc = new HexLocation(x,y);

		switch (direction)
		{
		case "W" :
			dir = VertexDirection.West;
			break;
		case "SW" :
			dir = VertexDirection.SouthWest;
			break;
		case "NW" :
			dir = VertexDirection.NorthWest;
			break;
		case "E" :
			dir = VertexDirection.East;
			break;
		case "SE" :
			dir = VertexDirection.SouthEast;
			break;
		case "NE" :
			dir = VertexDirection.NorthEast;
			break;
		default:
			assert false;
			return;
		}
	}
        public void convert()
	{
		switch (dir)
		{
		case West :
			direction = "W";
			break;
		case SouthWest :
			direction = "SW";
			break;
		case NorthWest :
			direction = "NW";
			break;
		case East :
			direction = "E";
			break;
		case SouthEast :
			direction = "SE";
			break;
		case NorthEast :
			direction = "NE";
			break;
		default:
			assert false;
			return;
		}
	}

	public HexLocation getHexLoc()
	{
		if (hexLoc==null) translate();
		return hexLoc;
	}
	
	private void setHexLoc(HexLocation hexLoc)
	{
		if(hexLoc == null)
		{
			throw new IllegalArgumentException("hexLoc cannot be null");
		}
		this.hexLoc = hexLoc;
                x = hexLoc.getX();
                y = hexLoc.getY();
	}
	
	public VertexDirection getDir()
	{
		return dir;
	}
	
	private void setDir(VertexDirection direction)
	{
		this.dir = direction;
                convert();
	}
        
        public List<EdgeLocation> getAdjacentEdges()
        {
            VertexLocation vertex = getNormalizedLocation();
            VertexDirection dir = vertex.getDir();
            HexLocation hex = vertex.getHexLoc();
            List<EdgeLocation> edges = new ArrayList<EdgeLocation>();
            
            if(dir.equals(NorthWest))
            {
                edges.add(new EdgeLocation(hex, EdgeDirection.North));
                edges.add(new EdgeLocation(hex, EdgeDirection.NorthWest));
                edges.add(new EdgeLocation(new HexLocation(hex.getX() - 1, hex.getY()), EdgeDirection.NorthEast));
            }
            else if (dir.equals(NorthEast))
            {
                edges.add(new EdgeLocation(hex, EdgeDirection.North));
                edges.add(new EdgeLocation(hex, EdgeDirection.NorthEast));
                edges.add(new EdgeLocation(new HexLocation(hex.getX() + 1, hex.getY() - 1), EdgeDirection.NorthWest));
            }
            else
            {
                System.err.println("getNormalized() error (Source: VertexLocation.getAdjacentEdges())");
            }
            return edges;
        }
        
         public List<VertexLocation> getAdjacentVertices()
        {
            VertexLocation vertex = getNormalizedLocation();
            VertexDirection dir = vertex.getDir();
            HexLocation hex = vertex.getHexLoc();
            List<VertexLocation> vertices = new ArrayList<VertexLocation>();
            if(dir.equals(NorthWest))
            {
                vertices.add(new VertexLocation(hex, VertexDirection.NorthEast));
                vertices.add(new VertexLocation(new HexLocation(hex.getX() - 1, hex.getY()), VertexDirection.NorthEast));
                vertices.add(new VertexLocation(new HexLocation(hex.getX() - 1, hex.getY() + 1), VertexDirection.NorthEast));
            }
            else if (dir.equals(NorthEast))
            {
                vertices.add(new VertexLocation(hex, VertexDirection.NorthWest));
                vertices.add(new VertexLocation(new HexLocation(hex.getX() + 1, hex.getY() - 1), VertexDirection.NorthWest));
                vertices.add(new VertexLocation(new HexLocation(hex.getX() + 1, hex.getY()), VertexDirection.NorthWest));
            }
            else
            {
                System.err.println("getNormalized() error (Source: VertexLocation.getAdjacentVertices())");
            }
            return vertices;
        }
	
	@Override
	public String toString()
	{
		if (hexLoc==null) translate();
		return "VertexLocation [hexLoc=" + hexLoc + ", dir=" + dir + "]";
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
		if (hexLoc==null) translate();
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		VertexLocation other = (VertexLocation)obj;
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
	 * Returns a canonical (i.e., unique) value for this vertex location. Since
	 * each vertex has three different locations on a map, this method converts
	 * a vertex location to a single canonical form. This is useful for using
	 * vertex locations as map keys.
	 * 
	 * @return Normalized vertex location
	 */
	public VertexLocation getNormalizedLocation()
	{
		if (hexLoc==null) translate();
		
		// Return location that has direction NW or NE
		
		switch (dir)
		{
			case NorthWest:
			case NorthEast:
				return this;
			case West:
				return new VertexLocation(
                                        hexLoc.getNeighborLoc(EdgeDirection.SouthWest),
                                        VertexDirection.NorthEast);
			case SouthWest:
				return new VertexLocation(
                                        hexLoc.getNeighborLoc(EdgeDirection.South),
                                        VertexDirection.NorthWest);
			case SouthEast:
				return new VertexLocation(
                                        hexLoc.getNeighborLoc(EdgeDirection.South),
                                        VertexDirection.NorthEast);
			case East:
				return new VertexLocation(
                                        hexLoc.getNeighborLoc(EdgeDirection.SouthEast),
                                        VertexDirection.NorthWest);
			default:
				assert false;
				return null;
		}
	}
}