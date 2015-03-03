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
}

