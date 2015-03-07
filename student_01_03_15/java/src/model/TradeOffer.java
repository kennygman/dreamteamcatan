package model;

import model.player.Resources;

public class TradeOffer 
{

	private int sender;
	private int receiver;
	private Resources offer;
	
	
	
	@Override
	public String toString()
	{
		return "TradeOffer [sender=" + sender + ", receiver=" + receiver
				+ ", offer=" + offer + "]";
	}
	public TradeOffer(int sender, int receiver, Resources offer)
	{
		this.sender = sender;
		this.receiver = receiver;
		this.offer = offer;
	}
	public String getSenderName()
	{
		return ModelFacade.getInstance().getGame().getPlayers()[sender].getName();
	}
	public int getSender()
	{
		return sender;
	}
	public void setSender(int sender)
	{
		this.sender = sender;
	}
	public int getReceiver()
	{
		return receiver;
	}
	public void setReceiver(int receiver)
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