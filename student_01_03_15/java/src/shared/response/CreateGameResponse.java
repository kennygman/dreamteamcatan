package shared.response;

import model.player.Player;


public class CreateGameResponse 
{
	private boolean isValid;
	
	private String title;
	private int id;
	private Player[] players;
	
	public CreateGameResponse(String t, int i, Player[] p, boolean b)
	{
		title = t;
		id = i;
		players = p;
		isValid = b;
	}
	
	public CreateGameResponse(boolean b)
	{
		isValid = b;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void setValid(boolean v)
	{
		isValid = v;
	}
}
