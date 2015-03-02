package client.maritime;

import java.util.Observable;

import java.util.Observer;

import model.ModelFacade;
import shared.definitions.*;
import client.base.*;


/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements IMaritimeTradeController, Observer
{
	private String inputTrade;
	private String outputTrade;
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
		
		getTradeOverlay().showModal();

	}

	@Override
	public void makeTrade() {

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
		inputTrade = resource.toString();
	}

	@Override
	public void setGiveResource(ResourceType resource) 
	{
		outputTrade = resource.toString();
		
		getRatio();
		
		
		if(ModelFacade.getInstance().CanMaritimeTrade(ratio, outputTrade, inputTrade))
		{
			ModelFacade.getInstance().maritimeTrade(ratio,outputTrade,inputTrade);
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

