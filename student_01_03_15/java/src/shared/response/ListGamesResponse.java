package shared.response;

import java.util.ArrayList;

public class ListGamesResponse {
	private boolean isValid;
	//private ArrayList<GameListObject> games;
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
	
	public GameListObject getGameListObject(int i)
	{
		return games[i];
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
