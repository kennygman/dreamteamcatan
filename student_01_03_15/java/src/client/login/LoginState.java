package client.login;

import client.data.PlayerInfo;
import shared.parameters.CredentialsParam;
import shared.response.LoginResponse;
import model.ModelFacade;

public class LoginState
{
	LoginController login;
	PlayerInfo info;
	CredentialsParam cred;
	
	//--------------------------------------------------------------------------------
	public LoginState(LoginController login)
	{
		this.login=login;
	}
	
	//--------------------------------------------------------------------------------
	public CredentialsParam getCredentials(boolean reg)
	{
		ILoginView view = (ILoginView)(login.getView());
		String un;
		String pw;
		if (reg)
		{
			un = view.getRegisterUsername();
			pw = view.getRegisterPassword();

			String pw2 = view.getRegisterPasswordRepeat();
			if (pw.equals("") || !pw.equals(pw2)) return null;

		}
		else
		{
			un = view.getLoginUsername();
			pw = view.getLoginPassword();
			
		}
		if (un.equals("") || pw.equals("")) return null;
		cred = new CredentialsParam(un,pw);
		return cred;
	}

	//--------------------------------------------------------------------------------
	public boolean canLogin()
	{
		CredentialsParam param = getCredentials(false);
		if (param == null) return false;
		
		LoginResponse response = ModelFacade.getInstance().login(param);
		
		if (response.isValid()) 
		{
			info = response.getPlayerInfo();
			System.out.println("\n================"+info);
			setPlayer();
			return true;
		}
		
		return false;
	}
	
	//--------------------------------------------------------------------------------
	public boolean canRegister()
	{
		CredentialsParam param = getCredentials(true);
		if (param == null) return false;
		
		LoginResponse response = ModelFacade.getInstance().register(param);
		
		if (response.isValid()) 
		{
			info = response.getPlayerInfo();
			System.out.println("\n================"+info);
			setPlayer();
			return true;
		}
		
		return false;
	}

	//--------------------------------------------------------------------------------
	public void setPlayer()
	{
		ModelFacade.getInstance().setPlayerInfo(info);
	}

}
