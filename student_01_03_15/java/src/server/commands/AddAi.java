package server.commands;

import model.Game;
import shared.parameters.AddAiParam;

public class AddAi implements ICommand
{
	private AddAiParam param;
	
	public AddAi(AddAiParam param)
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
