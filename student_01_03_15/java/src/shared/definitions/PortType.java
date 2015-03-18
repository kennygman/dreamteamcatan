package shared.definitions;

public enum PortType
{
	WOOD, BRICK, SHEEP, WHEAT, ORE, THREE;
        
    public static PortType fromString(String s)
    {
        PortType ht = THREE;
        if(s != null)
        {
            switch(s)
            {
                case "wood": ht = WOOD; break;
                case "brick": ht = BRICK; break;
                case "sheep": ht = SHEEP; break;
                case "wheat": ht = WHEAT; break;
                case "ore": ht = ORE; break;
            }
        }
        return ht;
    }
    public static PortType[] getPortList()
    {
    	PortType[] list = {WOOD, BRICK, SHEEP, WHEAT, ORE, THREE};
    	return list;
    }
    
    public static String asString(PortType pt)
    {
    	String s = null;
    	if (pt != null)
    	{
    		switch(pt)
    		{
	    		case WOOD: s = "wood"; break;
	    		case BRICK: s = "brick"; break;
	    		case SHEEP: s = "sheep"; break;
	    		case WHEAT: s = "wheat"; break;
	    		case ORE: s = "ore"; break;
	    		default: break;
    		}
    	}
    	return s;
    }
}

