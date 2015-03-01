package client.turntracker;

import java.util.Observable;
import java.util.Observer;

import model.ModelFacade;
import shared.definitions.CatanColor;
import client.base.*;
import client.poller.Poller;


/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController,Observer
{
	private Poller poller;
	
	public TurnTrackerController(ITurnTrackerView view) 
	{
		
		super(view);
		initFromModel();
	}
	
	@Override
	public ITurnTrackerView getView() 
	{
		
		return (ITurnTrackerView)super.getView();
	}

	@Override
	public void endTurn() 
	{
		if(ModelFacade.getInstance().CanFinishTurn())
		{
			ModelFacade.getInstance().finishTurn();
		}
	}
	
	private void initFromModel() 
	{
		
		getView().setLocalPlayerColor(ModelFacade.getInstance().getPlayerInfo().getColor());
	}

	@Override
	public void update(Observable o, Object arg) 
	{
		initFromModel();
	}

}

