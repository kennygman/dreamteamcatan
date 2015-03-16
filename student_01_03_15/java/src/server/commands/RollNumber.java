package server.commands;

import model.Game;
import shared.parameters.RollNumParam;

public class RollNumber implements ICommand
{
	private RollNumParam param;
	private Game game;
	
	public RollNumber(RollNumParam param, Game game)
	{
		super();
		this.param = param;
		this.game=game;
	}
	/**
	 * Roll a number at the beginning of your turn.
	 */
	@Override
	public void execute()
	{
		// TODO Auto-generated method stub

	}

}
