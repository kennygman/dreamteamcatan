package shared.parameters;

public class AcceptTradeParam implements ICommandParam {
	private int playerIndex;
	private boolean willAccept;
	private String type = "acceptTrade";
	
	public AcceptTradeParam(int playerIndex, boolean willAccept) {
		this.playerIndex = playerIndex;
		this.willAccept = willAccept;
	}
	
	public int getPlayerIndex() {
		return playerIndex;
	}
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
	public boolean isWillAccept() {
		return willAccept;
	}
	public void setWillAccept(boolean willAccept) {
		this.willAccept = willAccept;
	}

}
