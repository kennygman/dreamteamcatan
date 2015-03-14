package server.facade;

import java.util.Map;
import server.User;
import shared.parameters.CredentialsParam;
import shared.response.LoginResponse;

public class UserFacade implements IUserFacade
{
    private Map<Integer, User> users;
    
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
