package client.discard;

import java.util.Observable;
import java.util.Observer;

import model.ModelFacade;
import model.player.Resources;
import shared.definitions.*;
import client.base.*;
import client.misc.*;

/**
 * Discard controller implementation
 */
public class DiscardController extends Controller implements
		IDiscardController, Observer
{

	private IWaitView waitView;
	private int maxBrick;
	private int brickDiscardAmount = 0;
	private int maxOre;
	private int oreDiscardAmount = 0;
	private int maxSheep;
	private int sheepDiscardAmount = 0;
	private int maxWheat;
	private int wheatDiscardAmount = 0;
	private int maxWood;
	private int woodDiscardAmount = 0;
	private int totalResources;
	private int totalDiscardAmount = 0;
	private boolean discarded;

	/**
	 * DiscardController constructor
	 * 
	 * @param view
	 *            View displayed to let the user select cards to discard
	 * @param waitView
	 *            View displayed to notify the user that they are waiting for
	 *            other players to discard
	 */
	public DiscardController(IDiscardView view, IWaitView waitView)
	{
		super(view);
		ModelFacade.getInstance().addObserver(this);
		this.getDiscardView().setDiscardButtonEnabled(false);
		this.waitView = waitView;
		discarded = false;
	}

	public IDiscardView getDiscardView()
	{
		return (IDiscardView) super.getView();
	}

	public IWaitView getWaitView()
	{
		return waitView;
	}

	@Override
	public void increaseAmount(ResourceType resource)
	{
		switch (resource)
		{
		case BRICK:
			this.getDiscardView().setResourceDiscardAmount(ResourceType.BRICK,
					++brickDiscardAmount);
			updateArrows(brickDiscardAmount, maxBrick, resource);
			break;
		case ORE:
			this.getDiscardView().setResourceDiscardAmount(ResourceType.ORE,
					++oreDiscardAmount);
			updateArrows(oreDiscardAmount, maxOre, resource);
			break;
		case SHEEP:
			this.getDiscardView().setResourceDiscardAmount(ResourceType.SHEEP,
					++sheepDiscardAmount);
			updateArrows(sheepDiscardAmount, maxSheep, resource);
			break;
		case WHEAT:
			this.getDiscardView().setResourceDiscardAmount(ResourceType.WHEAT,
					++wheatDiscardAmount);
			updateArrows(wheatDiscardAmount, maxWheat, resource);
			break;
		case WOOD:
			this.getDiscardView().setResourceDiscardAmount(ResourceType.WOOD,
					++woodDiscardAmount);
			updateArrows(woodDiscardAmount, maxWood, resource);
			break;
		}

		totalDiscardAmount++;
		if (totalDiscardAmount == totalResources / 2)
		{
			updateAllResourceValues();
			this.getDiscardView().setDiscardButtonEnabled(true);
		} else
		{
			this.getDiscardView().setDiscardButtonEnabled(false);
		}

		this.getDiscardView().setStateMessage(
				totalDiscardAmount + "/" + totalResources / 2);
	}

	@Override
	public void decreaseAmount(ResourceType resource)
	{
		switch (resource)
		{
		case BRICK:
			this.getDiscardView().setResourceDiscardAmount(ResourceType.BRICK,
					--brickDiscardAmount);
			updateArrows(brickDiscardAmount, maxBrick, resource);
			break;
		case ORE:
			this.getDiscardView().setResourceDiscardAmount(ResourceType.ORE,
					--oreDiscardAmount);
			updateArrows(oreDiscardAmount, maxOre, resource);
			break;
		case SHEEP:
			this.getDiscardView().setResourceDiscardAmount(ResourceType.SHEEP,
					--sheepDiscardAmount);
			updateArrows(sheepDiscardAmount, maxSheep, resource);
			break;
		case WHEAT:
			this.getDiscardView().setResourceDiscardAmount(ResourceType.WHEAT,
					--wheatDiscardAmount);
			updateArrows(wheatDiscardAmount, maxWheat, resource);
			break;
		case WOOD:
			this.getDiscardView().setResourceDiscardAmount(ResourceType.WOOD,
					--woodDiscardAmount);
			updateArrows(woodDiscardAmount, maxWood, resource);
			break;
		}
		totalDiscardAmount--;
		this.getDiscardView().setDiscardButtonEnabled(false);
		updateAllResourceValues();

		this.getDiscardView().setStateMessage(
				totalDiscardAmount + "/" + totalResources / 2);
	}

	@Override
	public void discard()
	{
		Resources resourceHand = new Resources(woodDiscardAmount,
				sheepDiscardAmount, wheatDiscardAmount, brickDiscardAmount,
				oreDiscardAmount);
		ModelFacade.getInstance().discardCards(resourceHand);
		this.getDiscardView().closeModal();
		this.getDiscardView().setDiscardButtonEnabled(false);
		woodDiscardAmount = 0;
		sheepDiscardAmount = 0;
		wheatDiscardAmount = 0;
		brickDiscardAmount = 0;
		oreDiscardAmount = 0;
		discarded = true;
		//this.getDiscardView().closeModal();
		ModelFacade.getInstance().updateGameModel();
	}

	public void updateArrows(int discardAmount, int max, ResourceType resource)
	{
		if (totalDiscardAmount == totalResources / 2)
		{
			if (discardAmount < max)
			{
				if (discardAmount == 0)
				{
					this.getDiscardView().setResourceAmountChangeEnabled(
							resource, false, false);
				} else
				{
					this.getDiscardView().setResourceAmountChangeEnabled(
							resource, false, true);
				}
			} else
			{
				if (discardAmount == 0)
				{
					this.getDiscardView().setResourceAmountChangeEnabled(
							resource, false, false);
				} else
				{
					this.getDiscardView().setResourceAmountChangeEnabled(
							resource, false, true);
				}
			}
		} else
		{
			if (discardAmount < max)
			{
				if (discardAmount == 0)
				{
					this.getDiscardView().setResourceAmountChangeEnabled(
							resource, true, false);
				} else
				{
					this.getDiscardView().setResourceAmountChangeEnabled(
							resource, true, true);
				}
			} else
			{
				if (discardAmount == 0)
				{
					this.getDiscardView().setResourceAmountChangeEnabled(
							resource, false, false);
				} else
				{
					this.getDiscardView().setResourceAmountChangeEnabled(
							resource, false, true);
				}
			}
		}
	}

	private void updateAllResourceValues()
	{
		updateArrows(brickDiscardAmount, maxBrick, ResourceType.BRICK);
		updateArrows(oreDiscardAmount, maxOre, ResourceType.ORE);
		updateArrows(sheepDiscardAmount, maxSheep, ResourceType.SHEEP);
		updateArrows(wheatDiscardAmount, maxWheat, ResourceType.WHEAT);
		updateArrows(woodDiscardAmount, maxWood, ResourceType.WOOD);
	}

	private void resetAllDiscardValues()
	{
		brickDiscardAmount = 0;
		oreDiscardAmount = 0;
		sheepDiscardAmount = 0;
		wheatDiscardAmount = 0;
		woodDiscardAmount = 0;
		totalDiscardAmount = 0;

		this.getDiscardView().setStateMessage(
				totalDiscardAmount + "/" + totalResources / 2);
		this.getDiscardView().setResourceDiscardAmount(ResourceType.BRICK, 0);
		this.getDiscardView().setResourceDiscardAmount(ResourceType.ORE, 0);
		this.getDiscardView().setResourceDiscardAmount(ResourceType.SHEEP, 0);
		this.getDiscardView().setResourceDiscardAmount(ResourceType.WHEAT, 0);
		this.getDiscardView().setResourceDiscardAmount(ResourceType.WOOD, 0);

		/*
		 * if(maxWood > 0) {
		 * this.getDiscardView().setResourceAmountChangeEnabled
		 * (ResourceType.WOOD, true, false); } if(maxBrick > 0) {
		 * this.getDiscardView
		 * ().setResourceAmountChangeEnabled(ResourceType.BRICK, true, false); }
		 * if(maxSheep > 0) {
		 * this.getDiscardView().setResourceAmountChangeEnabled
		 * (ResourceType.SHEEP, true, false); } if(maxOre > 0) {
		 * this.getDiscardView
		 * ().setResourceAmountChangeEnabled(ResourceType.ORE, true, false); }
		 * if(maxWheat > 0) {
		 * this.getDiscardView().setResourceAmountChangeEnabled
		 * (ResourceType.WHEAT, true, false); }
		 */
	}

	private void initDiscardValues()
	{
		Resources r = ModelFacade.getInstance().getGame().getPlayer()
				.getResources();
		this.maxBrick = r.getResourceAmount(ResourceType.BRICK);
		this.maxOre = r.getResourceAmount(ResourceType.ORE);
		this.maxSheep = r.getResourceAmount(ResourceType.SHEEP);
		this.maxWheat = r.getResourceAmount(ResourceType.WHEAT);
		this.maxWood = r.getResourceAmount(ResourceType.WOOD);
		totalResources = maxBrick + maxOre + maxSheep + maxWheat + maxWood;
		this.getDiscardView()
				.setResourceMaxAmount(ResourceType.BRICK, maxBrick);
		this.getDiscardView().setResourceMaxAmount(ResourceType.ORE, maxOre);
		this.getDiscardView()
				.setResourceMaxAmount(ResourceType.SHEEP, maxSheep);
		this.getDiscardView()
				.setResourceMaxAmount(ResourceType.WHEAT, maxWheat);
		this.getDiscardView().setResourceMaxAmount(ResourceType.WOOD, maxWood);

		resetAllDiscardValues();
	}

	@Override
	public void update(Observable o, Object arg)
	{
		if (ModelFacade.getInstance().getState().equals("Discarding"))// .getStatus().equals("Discarding"))
		{
			initDiscardValues();
			if (totalResources > 7 && !discarded)
			{
				updateAllResourceValues();
				if (!this.getDiscardView().isModalShowing())
				{
                                        if(!ModelFacade.getInstance().isPlayerTurn())
                                        {
                                            ModelFacade.getInstance().getPoller().stop();
                                        }
					this.getDiscardView().showModal();
				}
			} else if (totalResources < 7 || discarded)
			{
				if (!this.getWaitView().isModalShowing())
				{
					waitView.showModal();
				}
			}
		} else
		{
			if (waitView.isModalShowing())
			{
				waitView.closeModal();
			}

			if (this.getDiscardView().isModalShowing()
					&& ModelFacade.getInstance().getState().toLowerCase().equals("robbing"))
			{
				this.getDiscardView().closeModal();
                                if(!ModelFacade.getInstance().isPlayerTurn())
                                {
                                    ModelFacade.getInstance().getPoller().start();
                                }
			}

			discarded = false;
		}
	}
}
