package server.commands;

import model.Game;
import shared.parameters.CredentialsParam;

public class Login implements ICommand
{
	private CredentialsParam param;
	
	public Login(CredentialsParam param)
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
