package client.turntracker;

import java.util.Observable;
import java.util.Observer;

import model.ModelFacade;
import model.TurnTracker;
import model.player.Player;
import shared.definitions.CatanColor;
import client.base.*;
import client.data.PlayerInfo;
import client.poller.Poller;


/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController, Observer {
// TODO: add poller to this class :)
	private Poller poller;
	
	private boolean firstPass;
	public TurnTrackerController(ITurnTrackerView view) {		
		super(view);
		try
		{
			poller = new Poller(ModelFacade.getInstance().getProxy());
                        poller.start();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
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
			ModelFacade.getInstance().finishTurn();
			//poller.start();
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
			for (Player p : ModelFacade.getInstance().getGame().getPlayers())
			{
				getView().initializePlayer(
						p.getPlayerIndex(),
						p.getName(),
						p.getColor()
						);
			}
			
		}
		
		getView().updatePlayer(
				player.getPlayerIndex(),
				player.getVictoryPoints(),
				tracker.getCurrentTurn() == playerInfo.getPlayerIndex(),
				tracker.getLargestArmy() == playerInfo.getPlayerIndex(),
				tracker.getLongestRoad() == playerInfo.getPlayerIndex()
				);
		
		getView().updateGameState(tracker.getStatus(), true);
		this.poller.setClientVersion(ModelFacade.getInstance().getGame().getVersion());
		
//		System.out.println("TurnTracker Game Status: " + tracker.getStatus());
//		System.out.println("Current Turn: " + ModelFacade.getInstance().getGame()
//				.getPlayers()[tracker.getCurrentTurn()].getName());
	}

	@Override
	public void update(Observable arg0, Object arg1)
	{
		initFromModel();
	}

}

