package shared.response;

public class CreateGameResponse 
{
	private boolean isValid;
	
	private String title;
	private int id;
	private PlayerListObject[] players;
	
	public CreateGameResponse(String t, int i, PlayerListObject[] p, boolean b)
	{
		title = t;
		id = i;
		players = p;
		isValid = b;
	}
	
	public CreateGameResponse(boolean b)
	{
		isValid = b;
	}
	
	public String getTitle()
	{
		return title;
	}
        
        public boolean isValid()
        {
            return isValid;
        }
        
        public int getGameId()
        {
            return id;
        }
	
	public void setValid(boolean v)
	{
		isValid = v;
	}
}
