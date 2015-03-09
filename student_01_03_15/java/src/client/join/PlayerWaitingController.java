package client.join;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import shared.parameters.AddAiParam;
import shared.response.ListAIResponse;
import model.ModelFacade;
import client.base.*;

/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController, Observer {

	public Timer timer = new Timer();
	public boolean isRunning;
	
	public PlayerWaitingController(IPlayerWaitingView view) {
            super(view);
            ListAIResponse aiList = ModelFacade.getInstance().listAi();
            getView().setAIChoices(aiList.getAiTypes());
            ModelFacade.getInstance().addObserver(this);
            isRunning = false;
	}

	@Override
	public IPlayerWaitingView getView() {
            return (IPlayerWaitingView)super.getView();
	}

	@Override
	public void start() {aiRefresh();}

	
	
	@Override
	public void addAI() {
			
            AddAiParam param = new AddAiParam(getView().getSelectedAI());
            if (ModelFacade.getInstance().addAi(param).isValid())
            {
            	
                refresh();
            }
	}

	@Override
	public void update(Observable arg0, Object arg1)
	{
            refresh();
	}
	
	private void aiRefresh()
	{
		this.isRunning = true;
		timer = new Timer();
		timer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				refresh();
			}
		}, 0, 1500);
	}
	private void stopTimer()
	{
		if(this.isRunning == true)
		{
			this.isRunning = false;
			timer.cancel();
			timer.purge();
		}
		
	}
	
	
	
	public void refresh()
	{

		int t = ModelFacade.getInstance().getGameInfo().getPlayers().size();
		if (t >= size && t != 4) {
			size = t;
			refresh = true;
		}

		if (refresh)
		{
			if (getView().isModalShowing()) getView().closeModal();
			getView().setPlayers(ModelFacade.getInstance().getPlayerInfoList());
			getView().showModal();
			refresh = false;
		}

		if (ModelFacade.getInstance().isGameFull() && getView().isModalShowing())
		{
					stopTimer();
                    getView().closeModal();
                    ModelFacade.getInstance().updateGameModel();
		}
		
	}
	
	private int size = -1;
	private boolean refresh = true;
}

