package client.login;

import client.data.PlayerInfo;
import shared.parameters.CredentialsParam;
import shared.response.LoginResponse;
import model.ModelFacade;

public class LoginState
{
	LoginController login;
	CredentialsParam credentials;
	
	//--------------------------------------------------------------------------------
	public LoginState(LoginController login)
	{
		this.login=login;
	}
	
	//--------------------------------------------------------------------------------
	public void setCredentials(boolean reg)
	{
		ILoginView view = (ILoginView)(login.getView());
		String un;
		String pw;
		if (reg)
		{
			un = view.getRegisterUsername();
			pw = view.getRegisterPassword();
			String pw2 = view.getRegisterPasswordRepeat();
			if (!pw.equals(pw2)) return;
		}
		else
		{
			un = view.getLoginUsername();
			pw = view.getLoginPassword();
		}
		if (un == null || pw == null) return;
		credentials = new CredentialsParam(un,pw);
	}

	//--------------------------------------------------------------------------------
	public boolean canLogin()
	{
		setCredentials(false);
		if (credentials == null) return false;
		
		LoginResponse response = ModelFacade.getInstance().login(credentials);

		if (response.isValid()) 
		{
			setPlayerInfo(response);
			return true;
		}
		
		return false;
	}
	
	//--------------------------------------------------------------------------------
	public boolean canRegister()
	{
		setCredentials(true);
		if (credentials == null) return false;
		LoginResponse response =ModelFacade.getInstance().register(credentials);
		
		if (response.isValid()) 
		{
			setPlayerInfo(response);
			return true;
		}
		
		return false;
	}

	//--------------------------------------------------------------------------------
	public void setPlayerInfo(LoginResponse response)
	{
		PlayerInfo info = new PlayerInfo();
		info.setId(response.getPlayerId());
		info.setName(credentials.getUser());
		ModelFacade.getInstance().setPlayerInfo(info);
	}

}
