package shared.parameters;

public class RollNumParam {
	private int playerIndex;
	private int number;
	private String type = "rollNumber";
	
	public RollNumParam(int p, int n)
	{
		playerIndex = p;
		number = n;
	}
	
	public int getNumber() {return number;}
	public int getPlayerIndex() {return playerIndex;}

}
