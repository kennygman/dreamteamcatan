package shared.parameters;

public class FinishTurnParam implements ICommandParam {
	private int playerIndex;
	private String type = "finishTurn";
	
	public FinishTurnParam(int p)
	{
		playerIndex = p;
	}

	public int getPlayerIndex()
	{
		return playerIndex;
	}
}
