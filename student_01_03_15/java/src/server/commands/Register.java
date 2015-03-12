package server.commands;

import model.Game;
import shared.parameters.CredentialsParam;

public class Register implements ICommand
{
	private CredentialsParam param;
	
	public Register(CredentialsParam param)
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
