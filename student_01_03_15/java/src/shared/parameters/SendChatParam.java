package shared.parameters;

public class SendChatParam implements ICommandParam 
{
	private String content;
	private int playerIndex;
	private String type = "sendChat";
	
	public SendChatParam(int p, String c)
	{
		playerIndex = p;
		content = c;
	}

	public String getMessae()
	{
		return content;
	}
	
	public int getSource()
	{
		return playerIndex;
	}
	public int getPlayerIndex()
	{
		return playerIndex;
	}
}
