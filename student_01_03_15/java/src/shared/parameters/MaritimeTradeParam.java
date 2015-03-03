package shared.parameters;

public class MaritimeTradeParam 
{
	private int playerIndex;
	private int ratio;
	private String inputResource;
	private String outputResource;
	private String type = "maritimeTrade";
	
	public MaritimeTradeParam(int playerIndex, int ratio, String inputResource, String outResource) {
		this.playerIndex = playerIndex;
		this.ratio = ratio;
		this.inputResource = inputResource;
		this.outputResource = outResource;
	}
	
	public int getPlayerIndex() {
		return playerIndex;
	}
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
	public int getRatio() {
		return ratio;
	}
	public void setRatio(int ratio) {
		this.ratio = ratio;
	}
	public String getInputResource() {
		return inputResource;
	}
	public void setInputResource(String inputResource) {
		this.inputResource = inputResource;
	}
	public String getOutResource() {
		return outputResource;
	}
	public void setOutResource(String outResource) {
		this.outputResource = outResource;
	}
}
