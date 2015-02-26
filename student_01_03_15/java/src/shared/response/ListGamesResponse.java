package shared.response;

import client.data.GameInfo;

public class ListGamesResponse {
	private boolean isValid;
	private GameListObject[] games;
	public ListGamesResponse(GameListObject[] a, boolean b)
	{
		games = a;
		isValid = b;
	}
	
	
	public int numberOfGames()
	{
		return games.length;
	}
	
	public GameInfo[] getGameListObject()
	{
		GameInfo[] gi = new GameInfo[games.length];
		for (int i = 0; i < games.length; i++)
		{
			gi[i] = games[i].getGameInfo();
		}
		return gi;
	}
	
	public boolean isValid()
	{
		return isValid;
	}
	
	public String toString()
	{
		return null;
	}
}
