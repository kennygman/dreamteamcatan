package shared.parameters;

import shared.locations.EdgeLocation;

public class BuildRoadParam implements ICommandParam {
	private int playerIndex;
	private EdgeLocation roadLocation;
	private boolean free;
	private String type = "buildRoad";
	
	public BuildRoadParam(int playerIndex, EdgeLocation roadLocation, boolean free) {
		this.playerIndex = playerIndex;
		this.roadLocation = roadLocation;
		this.free = free;
	}
	
	
	public int getPlayerIndex() {
		return playerIndex;
	}
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
	public EdgeLocation getRoadLocation() {
		return roadLocation;
	}
	public void setRoadLocation(EdgeLocation roadLocation) {
		this.roadLocation = roadLocation;
	}
	public boolean isFree(){return free;}


	@Override
	public String toString()
	{
		return "BuildRoadParam [playerIndex=" + playerIndex + ", roadLocation="
				+ roadLocation + ", free=" + free + ", type=" + type + "]";
	}

	
}
