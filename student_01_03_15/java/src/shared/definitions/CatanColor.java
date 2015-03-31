package shared.definitions;

import java.awt.Color;

public enum CatanColor
{
	RED, ORANGE, YELLOW, BLUE, GREEN, PURPLE, PUCE, WHITE, BROWN;
	
	private Color color;
	
	static
	{
		RED.color = new Color(227, 66, 52);
		ORANGE.color = new Color(255, 165, 0);
		YELLOW.color = new Color(253, 224, 105);
		BLUE.color = new Color(111, 183, 246);
		GREEN.color = new Color(109, 192, 102);
		PURPLE.color = new Color(157, 140, 212);
		PUCE.color = new Color(204, 136, 153);
		WHITE.color = new Color(223, 223, 223);
		BROWN.color = new Color(161, 143, 112);
	}
	
	public Color getJavaColor()
	{
		return color;
	}
	
        public static CatanColor fromString(String color)
        {
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
            return cc;
        }
        public static String asString(CatanColor cc)
        {
        	String s = null;
        	if (cc != null) 
        	{
        		switch (cc)
        		{
        		case RED: s = "red";break;
        		case ORANGE: s = "orange";break;
        		case YELLOW: s = "yellow";break;
        		case GREEN: s = "green";break;
        		case BLUE: s = "blue";break;
        		case PURPLE: s = "purple";break;
        		case PUCE: s = "puce";break;
        		case BROWN: s = "brown";break;
        		case WHITE: s = "white";break;
        		default: break;
        		}
        	}
        	return s.toLowerCase();
        	
        }
}

