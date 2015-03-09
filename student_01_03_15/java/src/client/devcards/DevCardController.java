package client.devcards;

import java.util.Observable;
import java.util.Observer;

import model.ModelFacade;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import client.base.*;
import client.resources.ResourceBarController;
import client.resources.ResourceBarElement;


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

	public IPlayDevCardView getPlayCardView() 
	{
		return (IPlayDevCardView)super.getView();
	}

	public IBuyDevCardView getBuyCardView()
	{
		return buyCardView;
	}

	@Override
	public void startBuyCard() 
	{
			getBuyCardView().showModal();
	}

	@Override
	public void cancelBuyCard() 
	{
		
		getBuyCardView().closeModal();
	}

	@Override
	public void buyCard() 
	{
		getBuyCardView().closeModal();
		ModelFacade.getInstance().buyDevCard();
	}

	@Override
	public void startPlayCard()
	{
		
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
			System.out.println("not allowed to use");
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
			System.out.println("not allowed to use");
		}
	}

	@Override
	public void playRoadBuildCard() 
	{
            if(ModelFacade.getInstance().canUseRoadBuilderOfficial())
            {
                roadAction.execute();
            }
	}

	@Override
	public void playSoldierCard() 
	{
            if(ModelFacade.getInstance().canUseSoldierOfficial())
            {
                soldierAction.execute();
            }
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
			System.out.println("not allowed to use");
		}
	}

	@Override
	public void update(Observable o, Object arg) 
	{
		
		 /*System.out.println(" monopoly amount is : " + ModelFacade.getInstance().getGame().getPlayer().getOldDevCards().getMonopoly());
			System.out.println(" mon amount is : " +  ModelFacade.getInstance().getGame().getPlayer().getOldDevCards().getMonument());
			System.out.println(" road amount is : " + ModelFacade.getInstance().getGame().getPlayer().getOldDevCards().getRoadBuilding());
			System.out.println(" soldier amount is : " + ModelFacade.getInstance().getGame().getPlayer().getOldDevCards().getSoldier());
			System.out.println(" year amount is : " +  ModelFacade.getInstance().getGame().getPlayer().getOldDevCards().getYearOfPlenty());
		*/
			getPlayCardView().setCardEnabled(DevCardType.MONOPOLY, ModelFacade.getInstance().canPlayDevCard(DevCardType.MONOPOLY));
			getPlayCardView().setCardAmount(DevCardType.MONOPOLY, ModelFacade.getInstance().getGame().getPlayer().getOldDevCards().getMonopoly());
	
			getPlayCardView().setCardEnabled(DevCardType.MONUMENT,ModelFacade.getInstance().canPlayDevCard(DevCardType.MONUMENT));
			getPlayCardView().setCardAmount(DevCardType.MONUMENT, ModelFacade.getInstance().getGame().getPlayer().getOldDevCards().getMonument());
	
			getPlayCardView().setCardEnabled(DevCardType.ROAD_BUILD, ModelFacade.getInstance().canPlayDevCard(DevCardType.ROAD_BUILD));
			getPlayCardView().setCardAmount(DevCardType.ROAD_BUILD, ModelFacade.getInstance().getGame().getPlayer().getOldDevCards().getRoadBuilding());

			getPlayCardView().setCardEnabled(DevCardType.SOLDIER, ModelFacade.getInstance().canPlayDevCard(DevCardType.SOLDIER));
			getPlayCardView().setCardAmount(DevCardType.SOLDIER, ModelFacade.getInstance().getGame().getPlayer().getOldDevCards().getSoldier());
		
			getPlayCardView().setCardEnabled(DevCardType.YEAR_OF_PLENTY, ModelFacade.getInstance().canPlayDevCard(DevCardType.YEAR_OF_PLENTY));
			getPlayCardView().setCardAmount(DevCardType.YEAR_OF_PLENTY, ModelFacade.getInstance().getGame().getPlayer().getOldDevCards().getYearOfPlenty());

	}
}

