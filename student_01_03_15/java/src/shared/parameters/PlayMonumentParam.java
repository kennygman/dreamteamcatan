package shared.parameters;

public class PlayMonumentParam implements ICommandParam {
	private int playerIndex;
	private String type = "Monument";


	public PlayMonumentParam(int playerIndex) {
		this.playerIndex = playerIndex;
	}
	
	public int getPlayerIndex() {
		return playerIndex;
	}
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	
}
