package shared.parameters;

import shared.locations.HexLocation;

public class PlaySoldierParam implements ICommandParam {
	private int playerIndex;
	private int victimIndex;
	private HexLocation location;
	private String type = "Soldier";
	
	public PlaySoldierParam(int playerIndex, int victumIndex, HexLocation location) {
		this.playerIndex = playerIndex;
		this.victimIndex = victumIndex;
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
	public void setVictimIndex(int victumIndex) {
		this.victimIndex = victumIndex;
	}
	public HexLocation getLocation() {
		return location;
	}
	public void setLocation(HexLocation location) {
		this.location = location;
	}

}