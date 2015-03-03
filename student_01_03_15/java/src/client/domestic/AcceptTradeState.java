package client.domestic;

import shared.definitions.ResourceType;
import model.ModelFacade;
import model.TradeOffer;
import model.player.Resources;

public class AcceptTradeState
{

	private DomesticTradeController controller;
	
	public AcceptTradeState(DomesticTradeController controller)
	{
		this.controller=controller;
	}
	
	public void updateOverlay(boolean canAccept)
	{
		TradeOffer offer = ModelFacade.getInstance().getGame().getTradeOffer();
		if (offer == null) return;
		
		controller.getAcceptOverlay().setAcceptEnabled(canAccept);
		controller.getAcceptOverlay().setPlayerName(offer.getSenderName());
		
		for (ResourceType resource : Resources.getResourceList())
		{
			int amount = offer.getOffer().getResourceAmount(resource);

			if (amount > 0) {
				//controller.getAcceptOverlay().addGetResource(resource, Math.abs(amount));
			}
			else if (amount < 0)
			{
				//controller.getAcceptOverlay().addGetResource(resource, Math.abs(amount));
			}
			else
				continue;	
		}
		
	}
	
}
