package shared.definitions;

public class ResourceConverter
{
	public static String getName(ResourceType resource)
	{
		String name;
		switch (resource)
		{
		case WOOD:
			name = "wood";
			break;
		case SHEEP:
			name = "sheep";
			break;
		case WHEAT:
			name = "wheat";
			break;
		case ORE:
			name = "ore";
			break;
		case BRICK:
			name = "brick";
			break;
			default:
				name = null;
				break;
		}
		return name;
	}
	
	public static ResourceType getType(String name)
	{
		ResourceType t;
		switch (name)
		{
		case "wood": t = ResourceType.WOOD; break;
		case "sheep": t = ResourceType.SHEEP; break;
		case "wheat": t = ResourceType.WHEAT; break;
		case "brick": t = ResourceType.BRICK; break;
		case "ore": t = ResourceType.ORE; break;
		default: t = null; break;
		}
		return t;
	}
	
}
