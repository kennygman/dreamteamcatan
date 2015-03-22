package shared.response;

import java.util.ArrayList;

import client.data.GameInfo;
import client.data.PlayerInfo;

public class GameListObject
{
	public String title;
	public int id;
	public ArrayList<PlayerListObject> players;

	public GameListObject()
	{

	}

	/**
	 * @param title
	 * @param id
	 * @param players
	 */
	public GameListObject(String title, int id,
			ArrayList<PlayerListObject> players)
	{
		this.title = title;
		this.id = id;
		this.players = players;
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
