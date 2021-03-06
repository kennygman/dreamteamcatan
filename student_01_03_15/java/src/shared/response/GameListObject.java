package shared.response;

import java.util.ArrayList;
import java.util.Arrays;

import shared.definitions.CatanColor;
import client.data.GameInfo;
import client.data.PlayerInfo;

public class GameListObject
{
	public String title;
	public int id;
	public ArrayList<PlayerListObject> players;

	public GameListObject(){}

	/**
	 * @param title
	 * @param id
	 * @param players
	 */
	public GameListObject(String title, int id,
			PlayerListObject[] players)
	{
		this.title = title;
		this.id = id;
		this.players = new ArrayList<PlayerListObject>(Arrays.asList(players));
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
			String name = players.get(i).name;
			String color = players.get(i).color;
			if (name != null && !name.equals(""))
			{
				PlayerInfo info = players.get(i).getPlayerInfo();
				info.setColor(CatanColor.fromString(color.toLowerCase()));
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
