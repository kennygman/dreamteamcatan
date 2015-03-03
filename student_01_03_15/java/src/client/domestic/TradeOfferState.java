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
	private ResourceType resourceToSend;
	private ResourceType resourceToGet;
	private int recipient;
	private int resourceMax;
	private Resources resources;
	boolean send;
	
	public TradeOfferState(DomesticTradeController controller)
	{
		this.controller=controller;
		this.tradeOverlay=controller.getTradeOverlay();
		resources = ModelFacade.getInstance().getGame().getPlayer().getResources();
		
		controller.getTradeOverlay().setStateMessage(ModelFacade.getInstance().getState());
		controller.getTradeOverlay().setTradeEnabled(true);
		controller.getTradeOverlay().setPlayerSelectionEnabled(true);
		controller.getTradeOverlay().setResourceSelectionEnabled(true);
		tradeOverlay.setStateMessage("Make offer");
		resetTradeOffer();

		//addTempResources();	// REMOVE TEST
	}
	
	//---------------------------------------------------------------------------------
	public void addTempResources()
	{
		resources.resetResource(ResourceType.WOOD);
		resources.resetResource(ResourceType.BRICK);
		resources.addResource(ResourceType.WOOD, 3);
		resources.addResource(ResourceType.BRICK, 2);
		ModelFacade.getInstance().modelChanged();
	}
	
	//---------------------------------------------------------------------------------
	public void resetResourceValue(ResourceType resource)
	{
		if (resourceToSend != null && resourceToSend.equals(resourceToGet))
		{
			offer.resetResource(resource);
			tradeOverlay.setResourceAmount(resource, "0");
		}
	}

	//---------------------------------------------------------------------------------
	public void toggleSendResourceAdjust(ResourceType resource)
	{
		tradeOverlay.setResourceAmountChangeEnabled(
				resource,
				resourceMax > offer.getResourceAmount(resource),
				offer.getResourceAmount(resource) > 0
				);
	}
	
	//---------------------------------------------------------------------------------
	public void toggleGetResourceAdjust(ResourceType resource)
	{
		tradeOverlay.setResourceAmountChangeEnabled(
				resource,
				true,
				offer.getResourceAmount(resource) < 0
				);
	}
	
	//---------------------------------------------------------------------------------
	public void setSendResource(ResourceType resource)
	{
		send = true;
		resourceToSend=resource;
		setTradableResources(resource);
		resourceMax = ModelFacade.getInstance().getGame().getPlayer().getResources()
				.getResourceAmount(resource);
		resetResourceValue(resource);
		toggleSendResourceAdjust(resource);
	}
	
	//---------------------------------------------------------------------------------
	public void setGetResource(ResourceType resource)
	{
		send = false;
		resourceToGet=resource;
		setTradableResources(resource);
		resetResourceValue(resource);
		toggleGetResourceAdjust(resource);
	}
	
	//---------------------------------------------------------------------------------
	public void increaseResourceAmount(ResourceType resource)
	{
		if (send)
		{
			offer.addResource(resource, 1);
			toggleSendResourceAdjust(resource);
		}
		else
		{
			offer.useResource(resource, 1);
			toggleGetResourceAdjust(resource);
		}
	}
	
	//---------------------------------------------------------------------------------
	public void decreaseResourceAmount(ResourceType resource)
	{
		if (!send)
		{
			offer.addResource(resource, 1);
			toggleGetResourceAdjust(resource);
		}
		else
		{
			offer.useResource(resource, 1);
			toggleSendResourceAdjust(resource);
		}
	}
	
	//---------------------------------------------------------------------------------
	public void setTradableResources(ResourceType resource)
	{
		for (ResourceType type : Resources.getResourceList())
		{
			if (!type.equals(resource))
				tradeOverlay.setResourceAmountChangeEnabled(type, false, false);
		}
	}
	
	//---------------------------------------------------------------------------------
	public void resetTradeOffer()
	{
		offer = new Resources();
		recipient = -1;
		resourceToSend=null;
		resourceToGet=null;
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
	//---------------------------------------------------------------------------------
	public void setRecipient(int recipient)
	{
		this.recipient = recipient;
	}
	//---------------------------------------------------------------------------------
	public Resources getOffer()
	{
		return offer;
	}
	//---------------------------------------------------------------------------------
}
