package shared.definitions;

public class CatanColorConverter
{
	
	public static CatanColor getCatanColor(String color)
	{
		System.out.println("=================="+color);

		if (color == null) return CatanColor.WHITE;
		CatanColor cc;
		switch(color)
		{
		case "red":
			cc = CatanColor.RED;
			break;
		case"orange":
			cc = CatanColor.ORANGE;
			break;
		case"yellow":
			cc = CatanColor.YELLOW;
			break;
		case"green":
			cc = CatanColor.GREEN;
			break;
		case"blue":
			cc = CatanColor.BLUE;
			break;
		case"purple":
			cc = CatanColor.PURPLE;
			break;
		case"puce":
			cc = CatanColor.PUCE;
			break;
		case"brown":
			cc = CatanColor.BROWN;
			break;
			default :
			cc = CatanColor.WHITE;
			break;
		}
		System.out.println("=================="+cc);
		return cc;
	}

}
