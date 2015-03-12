package server.commands;

import model.Game;
import shared.parameters.FinishTurnParam;

public class FinishTurn implements ICommand
{
	private FinishTurnParam param;
	
	public FinishTurn(FinishTurnParam param)
	{
		super();
		this.param = param;
	}

	@Override
	public void execute(Game game)
	{
		// TODO Auto-generated method stub

	}

}
