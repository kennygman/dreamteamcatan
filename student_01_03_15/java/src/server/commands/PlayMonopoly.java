package server.commands;

import model.Game;
import shared.parameters.PlayMonopolyParam;

public class PlayMonopoly implements ICommand
{
	private PlayMonopolyParam param;
	private Game game;
	
	public PlayMonopoly(PlayMonopolyParam param, Game game)
	{
		super();
		this.param = param;
		this.game=game;
	}

	/**
	 * Plays monopoly dev card for the player that is using it
	 */
	@Override
	public void execute()
	{
		// TODO Auto-generated method stub

	}

}
