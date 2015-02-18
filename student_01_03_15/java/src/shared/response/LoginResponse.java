package shared.response;

public class LoginResponse 
{
	private boolean isValid;
	private int playerId;
	
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
