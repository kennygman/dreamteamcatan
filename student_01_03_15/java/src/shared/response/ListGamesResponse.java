package shared.response;

import client.data.GameInfo;

public class ListGamesResponse {
	private boolean isValid;
	//private ArrayList<GameListObject> games;
	private GameInfo[] games;
	public ListGamesResponse(GameInfo[] a, boolean b)
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
		return games;
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
