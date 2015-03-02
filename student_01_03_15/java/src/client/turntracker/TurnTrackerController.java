package client.turntracker;

import java.util.Observable;
import java.util.Observer;

import model.ModelFacade;
import model.TurnTracker;
import model.player.Player;
import client.base.*;
import client.data.PlayerInfo;
import client.poller.Poller;


/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController, Observer {
// TODO: add poller to this class :)
//	private Poller poller;
	
	private boolean firstPass;
	public TurnTrackerController(ITurnTrackerView view) {		
		super(view);
		firstPass=true;
		ModelFacade.getInstance().addObserver(this);
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
	
	private void initFromModel() {
		
		PlayerInfo playerInfo = ModelFacade.getInstance().getPlayerInfo();
		Player player = ModelFacade.getInstance().getGame().getPlayer();
		TurnTracker tracker = ModelFacade.getInstance().getGame().getTurnTracker();
		
		System.out.println("INIT TurnTracker");
		if (firstPass) {
			firstPass = false;
			getView().setLocalPlayerColor(playerInfo.getColor());
			getView().initializePlayer(
					playerInfo.getPlayerIndex(),
					playerInfo.getName(),
					playerInfo.getColor()
					);
		}
		
		getView().updatePlayer(
				player.getPlayerIndex(),
				player.getVictoryPoints(),
				tracker.getCurrentTurn() == playerInfo.getPlayerIndex(),
				tracker.getLargestArmy() == playerInfo.getPlayerIndex(),
				tracker.getLongestRoad() == playerInfo.getPlayerIndex()
				);
		
		getView().updateGameState(tracker.getStatus(), true);
		System.out.println("TurnTracker Game Status: " + tracker.getStatus());
	}

	@Override
	public void update(Observable arg0, Object arg1)
	{
		initFromModel();
	}

}

