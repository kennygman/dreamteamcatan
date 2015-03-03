package shared.definitions;

public enum ResourceType
{
	WOOD, BRICK, SHEEP, WHEAT, ORE;
        
        @Override
        public String toString()
        {
            String resource = "";
            switch(this)
            {
                case WOOD: resource = "wood"; break;
                case BRICK: resource = "brick"; break;
                case SHEEP: resource = "sheep"; break;
                case WHEAT: resource = "wheat"; break;
                case ORE: resource = "ore"; break;
            }
            return resource;
        }
	
	public static String getName(ResourceType t)
	{
		String n = null;
		switch (t)
		{
		case WOOD: n="wood";break;
		case BRICK: n="brick";break;
		case SHEEP: n="sheep";break;
		case WHEAT: n="wheat";break;
		case ORE: n="ore";break;
		}
		return n;
	}
	
	public static ResourceType fromString(String s)
	{
		ResourceType t;
		switch (s)
		{
		case "wood": t = WOOD;break;
		case "brick": t=BRICK;break;
		case "ore": t=ORE;break;
		case "wheat": t=WHEAT;break;
		case "sheep": t=SHEEP;break;
		default: t=null;break;
		}
		return t;
	}
}

