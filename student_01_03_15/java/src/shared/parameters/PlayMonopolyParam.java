package shared.parameters;

public class PlayMonopolyParam implements ICommandParam {
	private int playerIndex;
	private String resource;
	private String type = "Monopoly";
	
	public PlayMonopolyParam(int playerIndex, String resource) {
		this.playerIndex = playerIndex;
		this.resource = resource;
	}
	
	public int getPlayerIndex() {
		return playerIndex;
	}
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	
	

}
