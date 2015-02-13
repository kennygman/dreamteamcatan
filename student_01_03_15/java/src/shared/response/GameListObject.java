package shared.response;

import java.util.ArrayList;

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
	}
	
	public int getGameId()
	{
		return id;
	}
}
