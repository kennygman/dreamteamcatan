package server.facade;

import shared.parameters.CredentialsParam;
import shared.response.LoginResponse;

public class UserFacade implements IUserFacade
{
	private static UserFacade instance;
	public static UserFacade getInstance()
	{
		if (instance == null) {
			throw new IllegalStateException("Tried to get instance of UserFacade"
					+ " without initializing it first!");
		}
		return instance;
	}

	@Override
	public LoginResponse login(CredentialsParam param)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoginResponse register(CredentialsParam param)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
