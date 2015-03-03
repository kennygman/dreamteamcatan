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
	
}

