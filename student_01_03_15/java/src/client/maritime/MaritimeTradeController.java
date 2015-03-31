package client.maritime;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import model.ModelFacade;
import model.player.Resources;
import shared.definitions.*;
import client.base.*;


/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController, Observer
{
	private ResourceType giveResource;
	private ResourceType getResource;
	private IMaritimeTradeOverlay tradeOverlay;
	private List<ResourceType> giveResources;
	private Resources resources;
	private Resources portList;
	
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

	//--------------------------------------------------------------------------------
	@Override
	public void startTrade() 
	{
		if (getTradeOverlay().isModalShowing()) getTradeOverlay().closeModal();
		getTradeOverlay().reset();
		
		if (ModelFacade.getInstance().getState().equals("Playing"))
		{
			updatePorts();
			unsetGiveValue();
		}
		else
		{
			getTradeOverlay().setStateMessage("");
			getTradeOverlay().hideGiveOptions();
			getTradeOverlay().hideGetOptions();
			getTradeOverlay().setTradeEnabled(false);
		}
		
		getTradeOverlay().showModal();
	}
	
	//--------------------------------------------------------------------------------
	public void updatePorts()
	{
		giveResources = new ArrayList<ResourceType>();
		resources = ModelFacade.getInstance().getPlayer().getResources();
		portList = ModelFacade.getInstance().getPorts();
		
			for (ResourceType type : ResourceType.list)
			{
				if (resources.getResourceAmount(type) >= portList.getResourceAmount(type) )
				{
					giveResources.add(type);
				}
			}
	}

	//--------------------------------------------------------------------------------
	@Override
	public void makeTrade() {

		if (ModelFacade.getInstance().CanMaritimeTrade(
				portList.getResourceAmount(giveResource),
				ResourceType.getName(giveResource),
				ResourceType.getName(getResource)))
		{
			ModelFacade.getInstance().maritimeTrade(
				portList.getResourceAmount(giveResource),
				ResourceType.getName(giveResource),
				ResourceType.getName(getResource));
		}
		
		if (getTradeOverlay().isModalShowing()) getTradeOverlay().closeModal();
	}

	//--------------------------------------------------------------------------------
	@Override
	public void cancelTrade()
	{
		if (getTradeOverlay().isModalShowing()) getTradeOverlay().closeModal();
	}

	//--------------------------------------------------------------------------------
	@Override
	public void setGetResource(ResourceType resource) 
	{
		getResource = resource;
		getTradeOverlay().selectGetOption(resource, 1);
		getTradeOverlay().setStateMessage("Accept");
		getTradeOverlay().setTradeEnabled(true);
}

	//--------------------------------------------------------------------------------
	@Override
	public void setGiveResource(ResourceType resource) 
	{
		giveResource = resource;
		getTradeOverlay().selectGiveOption(resource, portList.getResourceAmount(resource));
		unsetGetValue();
	}
	
	//--------------------------------------------------------------------------------
	@Override
	public void unsetGetValue() {
		List<ResourceType> getList = new ArrayList<ResourceType>();
		Resources bank = ModelFacade.getInstance().getGame().getBank();

		for (ResourceType r : ResourceType.list)
		{
			if (bank.getResourceAmount(r) > 0) getList.add(r);
		}

		getTradeOverlay().setStateMessage("Select resource to Get");
		getTradeOverlay().showGetOptions(getList.toArray(new ResourceType[0]));
		getTradeOverlay().setTradeEnabled(false);
	}

	//--------------------------------------------------------------------------------
	@Override
	public void unsetGiveValue() {
		getTradeOverlay().showGiveOptions(giveResources.toArray(new ResourceType[0]));
		getTradeOverlay().setStateMessage("Select resource to Give");
		getTradeOverlay().setTradeEnabled(false);
		getTradeOverlay().hideGetOptions();
	}

	//--------------------------------------------------------------------------------
	@Override
	public void update(Observable o, Object arg) 
	{
		int index = ModelFacade.getInstance().getPlayerInfo().getPlayerIndex();
		getTradeView().enableMaritimeTrade(
				ModelFacade.getInstance().getGame().getTurnTracker().getCurrentTurn() == index);
		
	}

	//--------------------------------------------------------------------------------
}

