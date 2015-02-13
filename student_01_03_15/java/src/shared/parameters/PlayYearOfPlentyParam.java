package shared.parameters;

import shared.definitions.ResourceType;


public class PlayYearOfPlentyParam {
	private int playerIndex;
	private String resource1;
	private String resource2;
	private String type = "Year_of_Plenty";
	
	public PlayYearOfPlentyParam(int playerIndex, String resource1, String resource2) {
		this.playerIndex = playerIndex;
		this.resource1 = resource1;
		this.resource2 = resource2;
	}
	
	public int getPlayerIndex() {
		return playerIndex;
	}
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
	public String getResource1() {
		return resource1;
	}
	public void setResource1(String resource1) {
		this.resource1 = resource1;
	}
	public String getResource2() {
		return resource2;
	}
	public void setResource2(String resource2) {
		this.resource2 = resource2;
	}

}
