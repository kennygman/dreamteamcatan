package server.commands;

import model.Game;
import model.player.Player;
import shared.parameters.JoinGameParam;


public class JoinGame implements ICommand
{
	private JoinGameParam param;
	
	public JoinGame(JoinGameParam param)
	{
		super();
		this.param = param;
	}

	/**
	 * Adds the player to the game
	 */
	@Override
	public void execute(Game game)
	{
		// Join or Re-Join?
		// Initialize Player, resources, and pieces
		// Add user to list of players in the game
	}

}
