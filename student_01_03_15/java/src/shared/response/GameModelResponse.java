package shared.response;
import model.Game;

public class GameModelResponse 
{
	private boolean isValid;
	private Game game;
	
	public boolean isValid() 
	{
		return isValid;
	}
	public void setValid(boolean isValid) 
	{
		this.isValid = isValid;
	}
	public Game getGame() 
	{
		return game;
	}
	public void setGame(Game game) 
	{
		this.game = game;
	}
}
