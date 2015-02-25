package client.login;

import client.data.PlayerInfo;
import shared.parameters.CredentialsParam;
import shared.response.LoginResponse;
import model.ModelFacade;

public class LoginState
{
	LoginController login;
	PlayerInfo info;
	
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
		return new CredentialsParam(un,pw);
	}

	//--------------------------------------------------------------------------------
	public boolean canLogin()
	{
		CredentialsParam param = getCredentials(false);
		if (param == null) return false;
		
		LoginResponse response = ModelFacade.getInstance().login(param);

		if (response.isValid()) 
		{
			info = new PlayerInfo();
			info.setId(response.getPlayerId());
			ModelFacade.getInstance().setPlayerInfo(info);
			return true;
		}
		
		return false;
	}
	
	//--------------------------------------------------------------------------------
	public boolean canRegister()
	{
		CredentialsParam param = getCredentials(true);
		if (param == null) return false;
		
		return ModelFacade.getInstance().register(param).isValid();
	}

	//--------------------------------------------------------------------------------

}
