package server.facade;

import shared.parameters.CredentialsParam;
import shared.response.LoginResponse;

public class MockUserFacade implements IUserFacade
{

	@Override
	public LoginResponse login(CredentialsParam param)
	{
		LoginResponse response = new LoginResponse(true, -1);
		if (param.getUser().equals("Sam") && param.getPassword().equals("sam"))
		{
			response.getPlayerInfo().setPlayerIndex(1);
		}
		else if (param.getUser().equals("Pete") && param.getPassword().equals("pete"))
		{
			response.getPlayerInfo().setPlayerIndex(2);
		}
		else
		{
			response.setValid(false);
		}
		return response;
	}

	@Override
	public LoginResponse register(CredentialsParam param)
	{
		LoginResponse response = new LoginResponse(false);

		if (param.getPassword() != null && param.getUser() != null)
		{
			if (param.getUser().equals("Sam") && param.getPassword().equals("sam"))
			{
				response = new LoginResponse(true, 1);
			}
			else if (param.getUser().equals("Pete") && param.getPassword().equals("pete"))
			{
				response = new LoginResponse(true, 2);
			}
		}

		return response;
	}

}
