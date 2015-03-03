package shared.parameters;

public class SendChatParam 
{
	private String content;
	private int playerIndex;
	private String type = "sendChat";
	
	public SendChatParam(int p, String c)
	{
		playerIndex = p;
		content = c;
	}
	
}
