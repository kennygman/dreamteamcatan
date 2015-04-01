package shared.parameters;

import shared.locations.VertexLocation;

public class BuildCityParam implements ICommandParam {
	private int playerIndex;
	private VertexLocation vertexLocation;
	private String type = "buildCity";
	
	public BuildCityParam(int playerIndex, VertexLocation location) {

		this.playerIndex = playerIndex;
		vertexLocation = location;
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

}
