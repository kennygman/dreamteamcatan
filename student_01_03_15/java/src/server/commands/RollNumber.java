package server.commands;

import model.Game;
import shared.parameters.RollNumParam;

public class RollNumber implements ICommand
{
	private RollNumParam param;
	
	public RollNumber(RollNumParam param)
	{
		super();
		this.param = param;
	}
	/**
	 * Roll a number at the beginning of your turn.
	 */
	@Override
	public void execute(Game game)
	{
		// TODO Auto-generated method stub

	}

}
