package client.turntracker;

import java.util.Observable;
import java.util.Observer;

import model.ModelFacade;
import model.TurnTracker;
import model.player.Player;
import client.base.*;
import client.poller.Poller;


/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController, Observer {
	
	public TurnTrackerController(ITurnTrackerView view) {		
		super(view);
		try
		{
			ModelFacade.getInstance().setPoller(new Poller(ModelFacade.getInstance().getProxy()));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		ModelFacade.getInstance().addObserver(this);
	}
	
	
	//---------------------------------------------------------------------------------
	@Override
	public ITurnTrackerView getView() 
	{
		
		return (ITurnTrackerView)super.getView();
	}

	//---------------------------------------------------------------------------------
	@Override
	public void endTurn() {
            if (ModelFacade.getInstance().CanFinishTurn())
            {
                    ModelFacade.getInstance().finishTurn();
            }
	}
	
	//---------------------------------------------------------------------------------
	private void initPlayers()
	{
		Player[] players = ModelFacade.getInstance().getPlayers();
		Player player = ModelFacade.getInstance().getPlayer();
		
		getView().setLocalPlayerColor(player.getColor());
		
		int count = 0;
		for (Player p : players)
		{
			if(p != null && p.getName() != null)
			{
				count++;
				getView().initializePlayer(p.getPlayerIndex(),p.getName(),p.getColor());
			}
		}
		if (count == 4) initialized = true;
		return;
	}
	private boolean initialized = false;
	
	//---------------------------------------------------------------------------------
	public void updatePlayers()
	{
		System.out.println("Updating player status");
            Player[] players = ModelFacade.getInstance().getGame().getPlayers();
            TurnTracker tracker = ModelFacade.getInstance().getGame().getTurnTracker();
            for (Player p : players)
            {
                if (p != null && p.getName() != null)
                {
                	System.out.println("Updating player:" + p.getName());
                    getView().updatePlayer(
                            p.getPlayerIndex(),
                            p.getVictoryPoints(),
                            tracker.getCurrentTurn() == p.getPlayerIndex(),
                            tracker.getLargestArmy() == p.getPlayerIndex(),
                            tracker.getLongestRoad() == p.getPlayerIndex()
                            );
                }
            }

            if (tracker.getStatus().equals(TurnTracker.PLAYING))
            {
            	System.out.println("Status is playing");
                getView().updateGameState(tracker.getStatus(), true);
            }
            else
            {
            	System.out.println("Status is not playing");
                getView().updateGameState(tracker.getStatus(), false);
            }
	}
	//---------------------------------------------------------------------------------
	private void initFromModel()
	{
		if (!initialized) initPlayers();
		updatePlayers();
	}

	//---------------------------------------------------------------------------------
	@Override
	public void update(Observable arg0, Object arg1)
	{
		System.out.println("Turn tracker controller is updating");
		initFromModel();
	}

}

