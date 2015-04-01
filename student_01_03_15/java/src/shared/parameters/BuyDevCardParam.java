package shared.parameters;

public class BuyDevCardParam implements ICommandParam {
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
