package server;

import model.Game;
import model.player.Player;
import shared.parameters.JoinGameParam;


public class JoinGame implements Command
{
	private int id;
	private String color;
	
	public JoinGame(JoinGameParam param)
	{
		this.id=param.getId();
		this.color=param.getColor();
	}
	
	@Override
	public void execute(Game game)
	{
		// Join or Re-Join?
		// Initialize Player, resources, and pieces
		// Add user to list of players in the game
	}

}
