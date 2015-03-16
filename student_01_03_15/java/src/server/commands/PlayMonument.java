package server.commands;

import model.Game;
import shared.parameters.PlayMonumentParam;

public class PlayMonument implements ICommand
{
	private PlayMonumentParam param;
	private Game game;
	
	public PlayMonument(PlayMonumentParam param, Game game)
	{
		super();
		this.param = param;
		this.game=game;
	}

	/**
	 * Plays monument dev card for the player that is using it
	 */
	@Override
	public void execute()
	{
		// TODO Auto-generated method stub

	}

}
