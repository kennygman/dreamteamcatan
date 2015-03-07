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
	
	private boolean firstPass;
	public TurnTrackerController(ITurnTrackerView view) {		
		super(view);
		try
		{
			ModelFacade.getInstance().setPoller(new Poller(ModelFacade.getInstance().getProxy()));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		firstPass=true;
		ModelFacade.getInstance().addObserver(this);
	}
	
	
	@Override
	public ITurnTrackerView getView() 
	{
		
		return (ITurnTrackerView)super.getView();
	}

	@Override
	public void endTurn() {
		if (ModelFacade.getInstance().CanFinishTurn())
		{
			ModelFacade.getInstance().getPoller().start();
			ModelFacade.getInstance().finishTurn();
		}
		
	}
	
	private void initFromModel() {
		Player[] players = ModelFacade.getInstance().getGame().getPlayers();
		Player player = ModelFacade.getInstance().getGame().getPlayer();
		TurnTracker tracker = ModelFacade.getInstance().getGame().getTurnTracker();
		
		if (firstPass) {
                    firstPass = false;
                    getView().setLocalPlayerColor(player.getColor());
                    for (Player p : players)
                    {
                        if(p != null)
                        {
                                getView().initializePlayer(p.getPlayerIndex(),p.getName(),p.getColor());
                        }
                    }
           
		}
		
                for (Player p : players)
                {
                    getView().updatePlayer(
                        p.getPlayerIndex(),
                        p.getVictoryPoints(),
                        tracker.getCurrentTurn() == p.getPlayerIndex(),
                        tracker.getLargestArmy() == p.getPlayerIndex(),
                        tracker.getLongestRoad() == p.getPlayerIndex()
                        );
                }
		
		getView().updateGameState(tracker.getStatus(), true);
	}

	@Override
	public void update(Observable arg0, Object arg1)
	{
		initFromModel();
	}

}

