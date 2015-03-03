package shared.response;

import client.data.PlayerInfo;

public class LoginResponse 
{
	private boolean isValid;
	private PlayerInfo info;
	
	public LoginResponse(boolean b, int userId)
	{
		isValid = b;
		info = new PlayerInfo();
		info.setId(userId);
	}
	public LoginResponse(boolean b)
	{
		isValid = b;
	}
	
	
	public boolean isValid()
	{
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	
	public PlayerInfo getPlayerInfo()
	{
		return this.info;
	}

	public void setPlayerName(String name)
	{
		this.info.setName(name);
	}
	
}
