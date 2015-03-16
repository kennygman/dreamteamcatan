package server.commands;

import model.Game;
import shared.parameters.RobPlayerParam;

public class RobPlayer implements ICommand
{
	private RobPlayerParam param;
	private Game game;
	
	public RobPlayer(RobPlayerParam param, Game game)
	{
		super();
		this.param = param;
		this.game=game;
	}
	/**
	 * Moves the robber, selecting the new robber position and player to rob
	 */
	@Override
	public void execute()
	{
		// TODO Auto-generated method stub

	}

}
