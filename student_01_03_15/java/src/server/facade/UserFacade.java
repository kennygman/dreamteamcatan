package server.facade;

import server.User;
import server.UserManager;
import shared.parameters.CredentialsParam;
import shared.response.LoginResponse;

public class UserFacade implements IUserFacade
{
    private UserManager users;
    
    public UserFacade(UserManager users)
    {
    	this.users=users;
    }
	
	//------------------------------------------------------------------------------
    @Override
    public LoginResponse login(CredentialsParam param)
    {
    	return buildResponse(users.getUser(param));
    }

	//------------------------------------------------------------------------------
    @Override
    public LoginResponse register(CredentialsParam param)
    {
    	return buildResponse(users.add(param));
    }
    
	//------------------------------------------------------------------------------
    private LoginResponse buildResponse(User user)
    {
       	LoginResponse response;
    	if (user != null)
    	{
    		 response = new LoginResponse(true, user.getId());
    		 response.setPlayerName(user.getName());
    	}
    	else
    	{
    		response = new LoginResponse(false);
    	}
    	
    	return response;
    }

	//------------------------------------------------------------------------------
}
