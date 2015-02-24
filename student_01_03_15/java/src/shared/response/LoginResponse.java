package shared.response;

import client.data.PlayerInfo;

public class LoginResponse 
{
	private boolean isValid;
	private int playerId;
	PlayerInfo info;
	
	public LoginResponse(boolean b, int userId)
	{
		isValid = b;
		playerId = userId;
	}
	public LoginResponse(boolean b)
	{
		isValid = b;
		playerId = -1;
	}

	public PlayerInfo getInfo()
	{
		return info;
	}
	public void setInfo(PlayerInfo info)
	{
		this.info = info;
	}
	
	
	public boolean isValid()
	{
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	
	public int getPlayerId()
	{
		return playerId;
	}

	public void setPlayerId(int userId) {
		this.playerId = userId;
	}
	
}
