package server.commands;

import model.Game;
import shared.parameters.PlayMonumentParam;

public class PlayMonument implements ICommand
{
	private PlayMonumentParam param;
	
	public PlayMonument(PlayMonumentParam param)
	{
		super();
		this.param = param;
	}

	/**
	 * Plays monument dev card for the player that is using it
	 */
	@Override
	public void execute(Game game)
	{
		// TODO Auto-generated method stub

	}

}
