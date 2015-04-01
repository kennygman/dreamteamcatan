package shared.parameters;

import shared.locations.HexLocation;

public class RobPlayerParam implements ICommandParam {
	private int playerIndex;
	private int victimIndex;
	private HexLocation location;
	private String type = "robPlayer";
	
	
	public RobPlayerParam(int playerIndex, int victimIndex, HexLocation location) {
		this.playerIndex = playerIndex;
		this.victimIndex = victimIndex;
		this.location = location;
	}
	public int getPlayerIndex() {
		return playerIndex;
	}
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
	public int getVictimIndex() {
		return victimIndex;
	}
	public void setVictimIndex(int victimIndex) {
		this.victimIndex = victimIndex;
	}
	public HexLocation getLocation() {
		return location;
	}
	public void setLocation(HexLocation location) {
		this.location = location;
	}


}
