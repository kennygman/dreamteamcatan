package server.commands;

import model.Game;
import shared.parameters.RobPlayerParam;

public class RobPlayer implements ICommand
{
	private RobPlayerParam param;
	
	public RobPlayer(RobPlayerParam param)
	{
		super();
		this.param = param;
	}
	/**
	 * Moves the robber, selecting the new robber position and player to rob
	 */
	@Override
	public void execute(Game game)
	{
		// TODO Auto-generated method stub

	}

}
