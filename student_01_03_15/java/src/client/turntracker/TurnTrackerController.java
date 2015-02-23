package client.turntracker;

import shared.definitions.CatanColor;
import client.base.*;
import client.poller.Poller;


/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController {
// TODO: add poller to this class :)
//	private Poller poller;
	
	public TurnTrackerController(ITurnTrackerView view) {
		
		super(view);
		initFromModel();
	}
	
	@Override
	public ITurnTrackerView getView() {
		
		return (ITurnTrackerView)super.getView();
	}

	@Override
	public void endTurn() {

	}
	
	private void initFromModel() {
		//<temp>
		getView().setLocalPlayerColor(CatanColor.RED);
		//</temp>
	}

}

