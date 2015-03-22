package shared.response;

import shared.definitions.CatanColor;
import client.data.PlayerInfo;

public class PlayerListObject
{
	public String color;
	public String name;
	public int id;
	
	

	public PlayerListObject(String color, String name, int id)
	{
		super();
		this.color = color;
		this.name = name;
		this.id = id;
	}
	public PlayerInfo getPlayerInfo()
	{
		PlayerInfo player = new PlayerInfo();
		player.setName(name);
		player.setId(id);
		player.setColor(getCatanColor(color));
		return player;
	}
	public static CatanColor getCatanColor(String color)
	{
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
		return cc;
	}
}
