package client.domestic;

import java.util.Observable;
import java.util.Observer;

import model.ModelFacade;
import model.TradeOffer;
import shared.definitions.*;
import client.base.*;
import client.misc.*;


/**
 * Domestic trade controller implementation
 */
public class DomesticTradeController extends Controller implements IDomesticTradeController, Observer {

	private IDomesticTradeOverlay tradeOverlay;
	private IWaitView waitOverlay;
	private IAcceptTradeOverlay acceptOverlay;

	private boolean firstRun;
	private TradeOfferState offerState;
	private AcceptTradeState acceptState;
	


	/**
	 * DomesticTradeController constructor
	 * 
	 * @param tradeView Domestic trade view (i.e., view that contains the "Domestic Trade" button)
	 * @param tradeOverlay Domestic trade overlay (i.e., view that lets the user propose a domestic trade)
	 * @param waitOverlay Wait overlay used to notify the user they are waiting for another player to accept a trade
	 * @param acceptOverlay Accept trade overlay which lets the user accept or reject a proposed trade
	 */
	public DomesticTradeController(IDomesticTradeView tradeView, IDomesticTradeOverlay tradeOverlay,
									IWaitView waitOverlay, IAcceptTradeOverlay acceptOverlay) {

		super(tradeView);
		
		setTradeOverlay(tradeOverlay);
		setWaitOverlay(waitOverlay);
		setAcceptOverlay(acceptOverlay);
		
		ModelFacade.getInstance().addObserver(this);
		firstRun = true;
	}
	
	public IDomesticTradeView getTradeView() {
		
		return (IDomesticTradeView)super.getView();
	}

	public IDomesticTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IDomesticTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	public IWaitView getWaitOverlay() {
		return waitOverlay;
	}

	public void setWaitOverlay(IWaitView waitView) {
		this.waitOverlay = waitView;
	}

	public IAcceptTradeOverlay getAcceptOverlay() {
		return acceptOverlay;
	}

	public void setAcceptOverlay(IAcceptTradeOverlay acceptOverlay) {
		this.acceptOverlay = acceptOverlay;
	}

	//---------------------------------------------------------------------------------
	@Override
	public void startTrade() {
		if (ModelFacade.getInstance().getState().equals("Playing"))
		{
			offerState = new TradeOfferState(this);
			if (firstRun) {
				offerState.setPlayers();
				firstRun = false;
			}
		}
		else
		{
			this.getTradeOverlay().setTradeEnabled(false);
			this.getTradeOverlay().setPlayerSelectionEnabled(false);
			this.getTradeOverlay().setResourceSelectionEnabled(false);
			this.getTradeOverlay().setStateMessage(ModelFacade.getInstance().getState());
		}
		
		getTradeOverlay().showModal();
	}

	//---------------------------------------------------------------------------------
	@Override
	public void decreaseResourceAmount(ResourceType resource) {
		offerState.decreaseResourceAmount(resource);
	}

	//---------------------------------------------------------------------------------
	@Override
	public void increaseResourceAmount(ResourceType resource) {
		offerState.increaseResourceAmount(resource);
	}

	//---------------------------------------------------------------------------------
	@Override
	public void sendTradeOffer() {

//		if (ModelFacade.getInstance().CanOfferTrade(offerState.getOffer()))
//		{
			ModelFacade.getInstance().offerTrade(offerState.getRecipient(), offerState.getOffer());
			getTradeOverlay().closeModal();
			getWaitOverlay().setMessage("Waiting for recipient response.");
			getWaitOverlay().showModal();
//		}
	}

	@Override
	public void setPlayerToTradeWith(int playerIndex) {
		offerState.setRecipient(playerIndex);
	}

	@Override
	public void setResourceToReceive(ResourceType resource) {
		offerState.setGetResource(resource);
	}

	@Override
	public void setResourceToSend(ResourceType resource) {
		offerState.setSendResource(resource);
	}

	@Override
	public void unsetResource(ResourceType resource) {
		offerState.getOffer().resetResource(resource);
	}

	@Override
	public void cancelTrade() {
		getTradeOverlay().closeModal();
	}

	//---------------------------------------------------------------------------------
	@Override
	public void acceptTrade(boolean willAccept) {
		ModelFacade.getInstance().acceptTrade(willAccept);
		getAcceptOverlay().closeModal();
	}

	//---------------------------------------------------------------------------------
	@Override
	public void update(Observable arg0, Object arg1)
	{
		TradeOffer offer = ModelFacade.getInstance().getGame().getTradeOffer();
		
		if (offer == null && this.getWaitOverlay().isModalShowing())
		{
			this.getWaitOverlay().closeModal();
		}
		
		if (offer != null && offer.getReciever() == ModelFacade.getInstance().getPlayerInfo().getPlayerIndex())
		{
			boolean canAccept = ModelFacade.getInstance().canAcceptTrade();
			acceptState = new AcceptTradeState(this);
			acceptState.updateOverlay(canAccept);
		}
		
	}
	
}

