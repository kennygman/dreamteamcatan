package client.maritime;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import model.ModelFacade;
import model.board.Port;
import model.player.Resources;
import shared.definitions.*;
import client.base.*;


/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController, Observer
{
	private String giveResource;
	private String getResource;
	private int ratio = 4;
	private IMaritimeTradeOverlay tradeOverlay;
	
	public MaritimeTradeController(IMaritimeTradeView tradeView, IMaritimeTradeOverlay tradeOverlay) 
	{
		
		super(tradeView);

		setTradeOverlay(tradeOverlay);
		ModelFacade.getInstance().addObserver(this);
	}
	
	public IMaritimeTradeView getTradeView() {
		
		return (IMaritimeTradeView)super.getView();
	}
	
	public IMaritimeTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IMaritimeTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	@Override
	public void startTrade() 
	{
		List<Port> ports = ModelFacade.getInstance().getPorts();
		List<ResourceType> giveResources = new ArrayList<ResourceType>();
		ResourceType[] resourceList = Resources.getResourceList();
		
		Resources resources = ModelFacade.getInstance().getGame().getPlayer().getResources();
		for (ResourceType r : resourceList)
		{
			if (resources.getResourceAmount(r) >=4 )
				giveResources.add(r);
		}
		
		if (ports != null) {
			for (Port p : ports)
			{
				giveResources.add(ResourceConverter.getType(p.getResource()));
			}
		}
		getTradeOverlay().showGiveOptions(giveResources.toArray(new ResourceType[0]));
		getTradeOverlay().showModal();

	}

	@Override
	public void makeTrade() {

		ModelFacade.getInstance().maritimeTrade(ratio,getResource,giveResource);
		getTradeOverlay().closeModal();
	}

	@Override
	public void cancelTrade() {

		getTradeOverlay().closeModal();
	}

	@Override
	public void setGetResource(ResourceType resource) 
	{
		//ModelFacade.getInstance().maritimeTrade(ratio, , outResource);
		getResource = ResourceConverter.getName(resource);
	}

	@Override
	public void setGiveResource(ResourceType resource) 
	{
		giveResource = ResourceConverter.getName(resource);
		
		getRatio();
		
		if(ModelFacade.getInstance().CanMaritimeTrade(ratio, getResource, giveResource))
		{
			
		}
	}
	public void getRatio()
	{
		
	}
	@Override
	public void unsetGetValue() {

	}

	@Override
	public void unsetGiveValue() {

	}

	@Override
	public void update(Observable o, Object arg) 
	{
		boolean canTrade = true;
		
		getTradeView().enableMaritimeTrade(canTrade);
		
		//Get the players ports and resources and see if any have more than 4 or see if they have a port then matvh ratio with that?
		
		/*if(ModelFacade.getInstance().CanMaritimeTrade(ratio, inputResource, outResource))
		{
			getTradeView().enableMaritimeTrade(canTrade);
		
		}*/
	}

}

