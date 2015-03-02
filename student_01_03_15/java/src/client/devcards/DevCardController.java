package client.devcards;

import java.util.Observable;
import java.util.Observer;

import model.ModelFacade;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import client.base.*;


/**
 * "Dev card" controller implementation
 */
public class DevCardController extends Controller implements IDevCardController, Observer {

	private IBuyDevCardView buyCardView;
	private IAction soldierAction;
	private IAction roadAction;
	
	/**
	 * DevCardController constructor
	 * 
	 * @param view "Play dev card" view
	 * @param buyCardView "Buy dev card" view
	 * @param soldierAction Action to be executed when the user plays a soldier card.  It calls "mapController.playSoldierCard()".
	 * @param roadAction Action to be executed when the user plays a road building card.  It calls "mapController.playRoadBuildingCard()".
	 */
	public DevCardController(IPlayDevCardView view, IBuyDevCardView buyCardView, 
								IAction soldierAction, IAction roadAction) {

		super(view);
		
		this.buyCardView = buyCardView;
		this.soldierAction = soldierAction;
		this.roadAction = roadAction;
		ModelFacade.getInstance().addObserver(this);
	}

	public IPlayDevCardView getPlayCardView() {
		return (IPlayDevCardView)super.getView();
	}

	public IBuyDevCardView getBuyCardView() {
		return buyCardView;
	}

	@Override
	public void startBuyCard() 
	{
		if(ModelFacade.getInstance().CanBuyDevCard())
		{
			getBuyCardView().showModal();
		}
	}

	@Override
	public void cancelBuyCard() {
		
		getBuyCardView().closeModal();
	}

	@Override
	public void buyCard() 
	{
		getBuyCardView().closeModal();
	}

	@Override
	public void startPlayCard() {
		
		getPlayCardView().showModal();
	}

	@Override
	public void cancelPlayCard() {

		getPlayCardView().closeModal();
	}

	@Override
	public void playMonopolyCard(ResourceType resource) 
	{
		if(ModelFacade.getInstance().CanUseMonopoly(resource.toString()))
		{
			ModelFacade.getInstance().playMonopolyCard(resource.toString());
		}
		else
		{
			//throw exception or do something?
		}
	}

	@Override
	public void playMonumentCard() 
	{
		if(ModelFacade.getInstance().CanUseMonument())
		{
			ModelFacade.getInstance().playMonumentCard();
		}
		else
		{
			//throw exception or do something?
		}
	}

	@Override
	public void playRoadBuildCard() 
	{
		
		/*if(ModelFacade.getInstance().CanUseRoadBuilder(spot1, spot2))
		{
			ModelFacade.getInstance().playRoadCard(spot1, spot2);
			roadAction.execute();
		}
		else
		{
			//throw exception or do something?
		}*/
		
	}

	@Override
	public void playSoldierCard() 
	{
		/*if(ModelFacade.getInstance().CanUseSoldier(victimIndex, location))
		{
			ModelFacade.getInstance().playSoldierCard(victimIndex, location);
			soldierAction.execute();
		}
		else
		{
			//throw exception or do something?
		}*/
	
	}

	@Override
	public void playYearOfPlentyCard(ResourceType resource1, ResourceType resource2) 
	{
		if(ModelFacade.getInstance().CanUseYearOfPlenty(resource1.toString(), resource2.toString()))
		{
			ModelFacade.getInstance().playYearOfPlentyCard(resource1.toString(), resource2.toString());
		}
		else
		{
			//throw exception or do something?
		}
	}

	@Override
	public void update(Observable o, Object arg) 
	{
		
		if(ModelFacade.getInstance().canPlayDevCard(DevCardType.MONOPOLY))
		{
			getPlayCardView().setCardEnabled(DevCardType.MONOPOLY, true);
			getPlayCardView().setCardAmount(DevCardType.MONOPOLY, ModelFacade.getInstance().getGame().getPlayer().getOldDevCards().getMonopoly());
		}
		if(ModelFacade.getInstance().canPlayDevCard(DevCardType.MONUMENT))
		{
			getPlayCardView().setCardEnabled(DevCardType.MONUMENT, true);
			getPlayCardView().setCardAmount(DevCardType.MONUMENT, ModelFacade.getInstance().getGame().getPlayer().getOldDevCards().getMonument());
		}
		if(ModelFacade.getInstance().canPlayDevCard(DevCardType.ROAD_BUILD))
		{
			getPlayCardView().setCardEnabled(DevCardType.ROAD_BUILD, true);
			getPlayCardView().setCardAmount(DevCardType.ROAD_BUILD, ModelFacade.getInstance().getGame().getPlayer().getOldDevCards().getRoadBuilding());
		}
		if(ModelFacade.getInstance().canPlayDevCard(DevCardType.SOLDIER))
		{
			getPlayCardView().setCardEnabled(DevCardType.SOLDIER, true);
			getPlayCardView().setCardAmount(DevCardType.SOLDIER, ModelFacade.getInstance().getGame().getPlayer().getOldDevCards().getSoldier());
		}
		if(ModelFacade.getInstance().canPlayDevCard(DevCardType.YEAR_OF_PLENTY))
		{
			getPlayCardView().setCardEnabled(DevCardType.YEAR_OF_PLENTY, true);
			getPlayCardView().setCardAmount(DevCardType.YEAR_OF_PLENTY, ModelFacade.getInstance().getGame().getPlayer().getOldDevCards().getYearOfPlenty());
		}
		//IPlayDevCardView.setCardEnabled(DevCardType cardType, boolean enabled);		
	}

}

