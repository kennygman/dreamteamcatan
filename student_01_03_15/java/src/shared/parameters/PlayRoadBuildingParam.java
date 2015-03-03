package shared.parameters;

import shared.locations.EdgeLocation;

public class PlayRoadBuildingParam {
	private int playerIndex;
	private EdgeLocation spot1;
	private EdgeLocation spot2;
	private String type = "Road_Building";
	
	public PlayRoadBuildingParam(int playerIndex, EdgeLocation spot1, EdgeLocation spot2) {
		this.playerIndex = playerIndex;
		this.spot1 = spot1;
		this.spot2 = spot2;
	}
	
	public int getPlayerIndex() {
		return playerIndex;
	}
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
	public EdgeLocation getSpot1() {
		return spot1;
	}
	public void setSpot1(EdgeLocation spot1) {
		this.spot1 = spot1;
	}
	public EdgeLocation getSpot2() {
		return spot2;
	}
	public void setSpot2(EdgeLocation spot2) {
		this.spot2 = spot2;
	}

}
