package client.maritime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import model.ModelFacade;
import model.board.Port;
import model.player.Resources;
import shared.definitions.*;
import shared.parameters.MaritimeTradeParam;
import client.base.*;


/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController, Observer
{
	private ResourceType giveResource;
	private ResourceType getResource;
	private int ratio = 4;
	private IMaritimeTradeOverlay tradeOverlay;
	private Map<ResourceType, Integer> ports;
	private List<ResourceType> giveResources;
	
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
		ports = new HashMap<>();
		List<Port> portList = ModelFacade.getInstance().getPorts();
		giveResources = new ArrayList<ResourceType>();
		ResourceType[] resourceList = Resources.getResourceList();
		Resources resources = ModelFacade.getInstance().getGame().getPlayer().getResources();
		resources.addResource(ResourceType.WOOD, 5);
		
		if (ModelFacade.getInstance().getState().equals("Playing"))
		{
			for (ResourceType r : resourceList)
			{
				if (resources.getResourceAmount(r) >=4 )
				{
					ports.put(r, 4);
					giveResources.add(r);
				}
			}
			
			if (portList != null)
			{
				for (Port p : portList)
				{
					ResourceType type = ResourceConverter.getType(p.getResource());
					ports.put(type, p.getRatio());
					if (resources.getResourceAmount(type) >= p.getRatio()) {
						giveResources.add(type);
					}
				}
			}
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

	@Override
	public void makeTrade() {

		if (ModelFacade.getInstance().CanMaritimeTrade(
				ports.get(giveResource),
				ResourceConverter.getName(giveResource),
				ResourceConverter.getName(getResource)
				)
			)
		{
			ModelFacade.getInstance().maritimeTrade(
				ports.get(giveResource),
				ResourceConverter.getName(giveResource),
				ResourceConverter.getName(getResource)
				);
		}
		getTradeOverlay().closeModal();
	}

	@Override
	public void cancelTrade() {

		getTradeOverlay().closeModal();
	}

	@Override
	public void setGetResource(ResourceType resource) 
	{
		getResource = resource;
		getTradeOverlay().selectGetOption(resource, 1);
		getTradeOverlay().setStateMessage("Accept");
		getTradeOverlay().setTradeEnabled(true);
}

	@Override
	public void setGiveResource(ResourceType resource) 
	{
		giveResource = resource;
		getTradeOverlay().selectGiveOption(resource, ports.get(resource));
		unsetGetValue();
	}
	
	@Override
	public void unsetGetValue() {
		List<ResourceType> getList = new ArrayList<ResourceType>();
		ResourceType[] resourceList = Resources.getResourceList();
		
		for (ResourceType r : resourceList)
		{
			if (ModelFacade.getInstance().getGame().getBank().getResourceAmount(r) > 0)
				getList.add(r);
		}

		getTradeOverlay().setStateMessage("Select resource to Get");
		getTradeOverlay().showGetOptions(getList.toArray(new ResourceType[0]));
		getTradeOverlay().setTradeEnabled(false);
	}

	@Override
	public void unsetGiveValue() {
		getTradeOverlay().showGiveOptions(giveResources.toArray(new ResourceType[0]));
		getTradeOverlay().setStateMessage("Select resource to Give");
		getTradeOverlay().setTradeEnabled(false);
		getTradeOverlay().hideGetOptions();
	}

	@Override
	public void update(Observable o, Object arg) 
	{
		boolean canTrade = true;
		
		getTradeView().enableMaritimeTrade(canTrade);
		
	}

}

