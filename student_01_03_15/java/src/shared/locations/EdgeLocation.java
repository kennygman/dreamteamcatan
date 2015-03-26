package shared.locations;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the location of an edge on a hex map
 */
public class EdgeLocation
{
	private int x;
	private int y;
	private String direction;
	private transient HexLocation hexLoc;
	private transient EdgeDirection dir;
	
	
	
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
        
        public List<EdgeLocation> getAdjacentEdges()
	{
            EdgeLocation edge = getNormalizedLocation();
            List<EdgeLocation> edgeList = new ArrayList<>();
            EdgeDirection dir = edge.getDir();
            HexLocation location = edge.getHexLoc();
            HexLocation temp;
            
            if (dir.equals(EdgeDirection.NorthWest))
            {
                edgeList.add(new EdgeLocation(location, EdgeDirection.North));
                temp = new HexLocation(location.getX() - 1, location.getY() + 1);
                edgeList.add(new EdgeLocation(temp, EdgeDirection.NorthEast));
                edgeList.add(new EdgeLocation(temp, EdgeDirection.North));
                temp = new HexLocation(location.getX() - 1, location.getY());
                edgeList.add(new EdgeLocation(temp, EdgeDirection.NorthEast));
            } 
            else if (dir.equals(EdgeDirection.North))
            {
                edgeList.add(new EdgeLocation(location, EdgeDirection.NorthWest));
                edgeList.add(new EdgeLocation(location, EdgeDirection.NorthEast));
                temp = new HexLocation(location.getX() + 1, location.getY() - 1);
                edgeList.add(new EdgeLocation(temp, EdgeDirection.NorthWest));
                temp = new HexLocation(location.getX() - 1, location.getY());
                edgeList.add(new EdgeLocation(temp, EdgeDirection.NorthEast)); 
            }
            else if(dir.equals(EdgeDirection.NorthEast))
            {
                edgeList.add(new EdgeLocation(location, EdgeDirection.North));
                temp = new HexLocation(location.getX() + 1, location.getY());
                edgeList.add(new EdgeLocation(temp, EdgeDirection.NorthWest));
                edgeList.add(new EdgeLocation(temp, EdgeDirection.North));
                temp = new HexLocation(location.getX() + 1, location.getY() - 1);
                edgeList.add(new EdgeLocation(temp, EdgeDirection.NorthWest)); 
            }
            else
            {
                System.err.println("Bad getAdjacentEdge() in EdgeLocation class");
            }

            return edgeList;
	}
	
        public List<VertexLocation> getAdjacentVertices()
	{
            EdgeLocation edge = getNormalizedLocation();
            List<VertexLocation> vertexList = new ArrayList<>();
            EdgeDirection dir = edge.getDir();
            HexLocation location = edge.getHexLoc();
            
            if (dir.equals(EdgeDirection.NorthWest))
            {
                vertexList.add(new VertexLocation(location, VertexDirection.NorthWest));
                vertexList.add(new VertexLocation(new HexLocation(location.getX() - 1, location.getY() + 1), VertexDirection.NorthEast));
                //vertexList.add(new VertexLocation(location.getNeighborLoc(EdgeDirection.SouthWest), VertexDirection.NorthEast));
            } 
            else if (dir.equals(EdgeDirection.North))
            {
                vertexList.add(new VertexLocation(location, VertexDirection.NorthWest));
                vertexList.add(new VertexLocation(location, VertexDirection.NorthEast));
            }
            else if(dir.equals(EdgeDirection.NorthEast))
            {
                vertexList.add(new VertexLocation(location, VertexDirection.NorthEast));
                vertexList.add(new VertexLocation(new HexLocation(location.getX() + 1, location.getY()), VertexDirection.NorthWest));
                //vertexList.add(new VertexLocation(location.getNeighborLoc(EdgeDirection.SouthEast), VertexDirection.NorthWest));
            }
            else
            {
                System.err.println("Bad getAdjacentVertices() in EdgeLocation class");
            }

            return vertexList;
	}
        
        public boolean isNeighbor(EdgeLocation neighbor)
        {
            EdgeLocation edge = getNormalizedLocation();
            EdgeDirection dir = edge.getDir();
            HexLocation location = edge.getHexLoc();
            HexLocation temp;
            neighbor = neighbor.getNormalizedLocation();
            
            if (dir.equals(EdgeDirection.NorthWest))
            {
                if(neighbor.equals(new EdgeLocation(location, EdgeDirection.North))) return true;
                temp = new HexLocation(location.getX() - 1, location.getY() + 1);
                if(neighbor.equals(new EdgeLocation(temp, EdgeDirection.NorthEast))) return true;
                if(neighbor.equals(new EdgeLocation(temp, EdgeDirection.North))) return true;
                temp = new HexLocation(location.getX() - 1, location.getY());
                if(neighbor.equals(new EdgeLocation(temp, EdgeDirection.NorthEast))) return true;
            } 
            else if (dir.equals(EdgeDirection.North))
            {
                if(neighbor.equals(new EdgeLocation(location, EdgeDirection.NorthWest))) return true;
                if(neighbor.equals(new EdgeLocation(location, EdgeDirection.NorthEast))) return true;
                temp = new HexLocation(location.getX() + 1, location.getY() - 1);
                if(neighbor.equals(new EdgeLocation(temp, EdgeDirection.NorthWest))) return true;
                temp = new HexLocation(location.getX() - 1, location.getY());
                if(neighbor.equals(new EdgeLocation(temp, EdgeDirection.NorthEast))) return true; 
            }
            else if(dir.equals(EdgeDirection.NorthEast))
            {
                if(neighbor.equals(new EdgeLocation(location, EdgeDirection.North))) return true;
                temp = new HexLocation(location.getX() + 1, location.getY());
                if(neighbor.equals(new EdgeLocation(temp, EdgeDirection.NorthWest))) return true;
                if(neighbor.equals(new EdgeLocation(temp, EdgeDirection.North))) return true;
                temp = new HexLocation(location.getX() + 1, location.getY() - 1);
                if(neighbor.equals(new EdgeLocation(temp, EdgeDirection.NorthWest))) return true; 
            }
            else
            {
                System.err.println("Bad isNeighbor() in EdgeLocation class");
            }

            return false;
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
	
	public static EdgeDirection getEdgeDirection(String name)
	{
		EdgeDirection dir;
		switch (name)
		{
			case "S":dir = EdgeDirection.South;		break;
			case "SW":dir = EdgeDirection.SouthWest;	break;
			case "SE":dir = EdgeDirection.SouthEast;	break;
			case "N":dir = EdgeDirection.North;		break;
			case "NW":dir = EdgeDirection.NorthWest;	break;
			case "NE":dir = EdgeDirection.NorthEast;	break;
			default: dir = null;break;
		}
		return dir;
	}
	
}