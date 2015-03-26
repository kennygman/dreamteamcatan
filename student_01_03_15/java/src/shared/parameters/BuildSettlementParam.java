package shared.parameters;

import shared.locations.VertexLocation;

public class BuildSettlementParam {
	private int playerIndex;
	private VertexLocation vertexLocation;
	private boolean free;
	private String type = "buildSettlement";
	
	public BuildSettlementParam(int playerIndex, VertexLocation location, boolean free) {
		this.playerIndex = playerIndex;
		vertexLocation = location;
		this.free = free;
	}
	
	public int getPlayerIndex() {
		return playerIndex;
	}
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
	public VertexLocation getLocation() {
		return vertexLocation;
	}
	public void setLocation(VertexLocation location) {
		vertexLocation = location;
	}
	public boolean isFree() {
		return free;
	}
	public void setFree(boolean free) {
		this.free = free;
	}


	@Override
	public String toString()
	{
		return "BuildSettlementParam [playerIndex=" + playerIndex
				+ ", vertexLocation=" + vertexLocation + ", free=" + free
				+ ", type=" + type + "]";
	}

	
}
