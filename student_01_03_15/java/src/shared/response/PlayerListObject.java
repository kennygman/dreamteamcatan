package shared.response;

import shared.definitions.CatanColor;
import client.data.PlayerInfo;

public class PlayerListObject
{
	public String color;
	public String name;
	public Integer id;

	public PlayerListObject()
	{
	}

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
		player.setColor(CatanColor.fromString(color));
		return player;
	}
}
