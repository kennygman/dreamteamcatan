package shared.parameters;

import model.player.Resources;

public class OfferTradeParam implements ICommandParam 
{
	private int playerIndex;
	private int receiver;
	private Resources offer;
	private String type = "offerTrade";
	
	public OfferTradeParam(int p, int r, Resources o)
	{
		playerIndex = p;
		receiver = r;
		offer = o;
	}
	
	public int getPlayerIndex() 
	{
		return playerIndex;
	}
	public void setPlayerIndex(int playerIndex) 
	{
		this.playerIndex = playerIndex;
	}
	public int getreceiver() 
	{
		return receiver;
	}
	public void setreceiver(int receiver) 
	{
		this.receiver = receiver;
	}
	public Resources getOffer() 
	{
		return offer;
	}
	public void setOffer(Resources offer) 
	{
		this.offer = offer;
	}
}
