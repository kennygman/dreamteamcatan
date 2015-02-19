package model;

import model.player.Resources;

public class TradeOffer 
{

	private int sender;
	private int reciever;
	private Resources offer;
	
	
	
	@Override
	public String toString()
	{
		return "TradeOffer [sender=" + sender + ", reciever=" + reciever
				+ ", offer=" + offer + "]";
	}
	public TradeOffer(int sender, int reciever, Resources offer)
	{
		this.sender = sender;
		this.reciever = reciever;
		this.offer = offer;
	}
	public int getSender()
	{
		return sender;
	}
	public void setSender(int sender)
	{
		this.sender = sender;
	}
	public int getReciever()
	{
		return reciever;
	}
	public void setReciever(int reciever)
	{
		this.reciever = reciever;
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