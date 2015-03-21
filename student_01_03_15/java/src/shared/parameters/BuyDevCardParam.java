package shared.parameters;

public class BuyDevCardParam {
	private int playerIndex;
	private String type = "buyDevCard";
	
	public BuyDevCardParam(int p)
	{
		playerIndex = p;
	}	
	public int getPlayerIndex()
	{
		return playerIndex;
	}
}
