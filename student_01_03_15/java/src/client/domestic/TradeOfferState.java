package client.domestic;

import java.util.ArrayList;
import java.util.List;

import client.data.PlayerInfo;
import model.ModelFacade;
import model.TradeOffer;
import model.player.Resources;
import shared.definitions.ResourceType;

public class TradeOfferState
{
	private DomesticTradeController controller;
	private IDomesticTradeOverlay tradeOverlay;

	private TradeOffer tradeOffer;
	private Resources offer;
	private ResourceType sendResource;
	private ResourceType receiveResource;
	private int recipient;
	
	
	public TradeOfferState(DomesticTradeController controller)
	{
		this.controller=controller;
		this.tradeOverlay=controller.getTradeOverlay();
		controller.getTradeOverlay().setStateMessage(ModelFacade.getInstance().getState());
		controller.getTradeOverlay().setTradeEnabled(true);
		resetTradeOffer();
		setTradableResources();
	}
	
	public boolean canDecreaseResourceAmount()
	{
		
		return false;
	}
	//---------------------------------------------------------------------------------
	public void setTradableResources()
	{
		Resources resources = ModelFacade.getInstance().getGame().getPlayer().getResources();
		
		resources.resetResource(ResourceType.WOOD);
		resources.resetResource(ResourceType.BRICK);
		resources.addResource(ResourceType.WOOD, 3);
		resources.addResource(ResourceType.BRICK, 2);
		ModelFacade.getInstance().modelChanged();

		controller.getTradeOverlay().setTradeEnabled(true);
		controller.getTradeOverlay().setPlayerSelectionEnabled(true);
		controller.getTradeOverlay().setResourceSelectionEnabled(true);
		tradeOverlay.setStateMessage("Make offer");

		for (ResourceType r : resources.getResourceList())
		{
			int amount = resources.getResourceAmount(r);
			tradeOverlay.setResourceAmount(r, Integer.toString(amount));
			tradeOverlay.setResourceAmountChangeEnabled(r, amount>0, amount>0);
		}
	}
	
	//---------------------------------------------------------------------------------
	public void resetTradeOffer()
	{
		offer = new Resources();
		recipient = -1;
		sendResource=null;
		receiveResource=null;
	}

	//---------------------------------------------------------------------------------
	public void setPlayers()
	{
		PlayerInfo[] players = ModelFacade.getInstance().getPlayerInfoList();
		List<PlayerInfo> opponents = new ArrayList<PlayerInfo>();
		for (PlayerInfo p : players)
		{
			if (p.getId()!=ModelFacade.getInstance().getPlayerInfo().getId())
				opponents.add(p);
		}
		controller.getTradeOverlay().setPlayers(opponents.toArray(new PlayerInfo[0]));
	}
	
	//---------------------------------------------------------------------------------
	public int getRecipient()
	{
		return recipient;
	}
	public void setRecipient(int recipient)
	{
		this.recipient = recipient;
	}
	public Resources getOffer()
	{
		return offer;
	}
	
}
