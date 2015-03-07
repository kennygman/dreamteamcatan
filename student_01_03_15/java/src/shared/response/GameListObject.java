package shared.response;

import java.util.ArrayList;

import shared.definitions.CatanColor;
import client.data.GameInfo;
import client.data.PlayerInfo;

public class GameListObject 
{
	public String title;
	public int id;
	public ArrayList<PlayerListObject> players;
	
	public class PlayerListObject 
	{
		public String color;
		public String name;
		public int id;
		public PlayerInfo getPlayerInfo()
		{
			PlayerInfo player = new PlayerInfo();
			player.setName(name);
			player.setId(id);
			player.setColor(getCatanColor(color));
			return player;
		}
		public CatanColor getCatanColor(String color)
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
	
	public int getId()
	{
		return id;
	}
	
	public GameInfo getGameInfo()
	{
		GameInfo game = new GameInfo();
		game.setId(id);
		game.setTitle(title);
		
		for (int i = 0; i < players.size(); i++)
		{
			if (players.get(i).name != null)
			{
				PlayerInfo info = players.get(i).getPlayerInfo();
				info.setPlayerIndex(i);
				game.addPlayer(info);
			}
		}
		return game;
	}
	
	public int size()
	{
		return players.size();
	}

}
