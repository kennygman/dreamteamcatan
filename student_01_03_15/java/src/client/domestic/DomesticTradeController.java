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
	
	//---------------------------------------------------------------------------------
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
	public void startTrade()
	{
		offerState = new TradeOfferState(this);
		if (firstRun) {
			offerState.setPlayers();
			firstRun = false;
		}
		this.getTradeOverlay().reset();
		this.getTradeOverlay().setTradeEnabled(false);
		this.getTradeOverlay().setPlayerSelectionEnabled(false);
		this.getTradeOverlay().setResourceSelectionEnabled(true);
		this.getTradeOverlay().setStateMessage("Set the trade you want to make");
		
		getTradeOverlay().showModal();
	}

	//---------------------------------------------------------------------------------
	@Override
	public void decreaseResourceAmount(ResourceType resource) {
		offerState.decreaseResourceAmount(resource);
		if (!offerState.canTrade())
		{
			this.getTradeOverlay().setStateMessage("Set the trade you want to make");
			this.getTradeOverlay().setPlayerSelectionEnabled(false);
		}
	}

	//---------------------------------------------------------------------------------
	@Override
	public void increaseResourceAmount(ResourceType resource) {
		offerState.increaseResourceAmount(resource);
		if (offerState.canTrade())
		{
			this.getTradeOverlay().setStateMessage("Select a player");
			this.getTradeOverlay().setPlayerSelectionEnabled(true);
		}
	}

	//---------------------------------------------------------------------------------
	@Override
	public void sendTradeOffer() {

		getTradeOverlay().closeModal();
		getTradeOverlay().reset();
		
		if (ModelFacade.getInstance().CanOfferTrade(offerState.getOffer()))
		{
			ModelFacade.getInstance().offerTrade(offerState.getRecipient(), offerState.getOffer());
			
			getWaitOverlay().setMessage("Waiting for recipient's response");
			getWaitOverlay().showModal();
			ModelFacade.getInstance().getPoller().pollerStart();
		}
	}

	//---------------------------------------------------------------------------------
	@Override
	public void setPlayerToTradeWith(int playerIndex)
	{
		if (playerIndex >= 0)
		{
			offerState.setRecipient(playerIndex);
			this.getTradeOverlay().setTradeEnabled(true);
			this.getTradeOverlay().setStateMessage("Trade!");
		}
		else 
		{
			this.getTradeOverlay().setTradeEnabled(false);
			this.getTradeOverlay().setStateMessage("Select a player");
		}
	}

	//---------------------------------------------------------------------------------
	@Override
	public void setResourceToReceive(ResourceType resource) {
		offerState.setGetResource(resource);
	}

	//---------------------------------------------------------------------------------
	@Override
	public void setResourceToSend(ResourceType resource) {
		offerState.setSendResource(resource);
	}

	//---------------------------------------------------------------------------------
	@Override
	public void unsetResource(ResourceType resource) {
		offerState.getOffer().resetResource(resource);
	}

	//---------------------------------------------------------------------------------
	@Override
	public void cancelTrade()
	{	
		if (getTradeOverlay().isModalShowing()) getTradeOverlay().closeModal();
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
		if (!ModelFacade.getInstance().isGameFull()) return;
		TradeOffer offer = ModelFacade.getInstance().getGame().getTradeOffer();
		int index = ModelFacade.getInstance().getPlayerInfo().getPlayerIndex();
		
		getTradeView().enableDomesticTrade(
				ModelFacade.getInstance().getGame().getTurnTracker().getCurrentTurn() == index);

		if (offer == null)
		{
			 if (getWaitOverlay().isModalShowing()) 
			 {
				 getWaitOverlay().closeModal();
				 //ModelFacade.getInstance().getPoller().stop();
			 }
		}
		else if (offer.getSender() == index)
		{
			if (!getWaitOverlay().isModalShowing()) 
			{
				getWaitOverlay().setMessage("Waiting for recipient response.");
				getWaitOverlay().showModal();
			}
		}
		else if (offer.getReceiver() == index)
		{
			if (this.getAcceptOverlay().isModalShowing()) return;
			boolean canAccept = ModelFacade.getInstance().canAcceptTrade();
			acceptState = new AcceptTradeState(this);
			acceptState.updateOverlay(canAccept);
		}
		
	}
	
	//---------------------------------------------------------------------------------
}

