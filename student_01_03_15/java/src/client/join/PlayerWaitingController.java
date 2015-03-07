package client.join;

import java.util.Observable;
import java.util.Observer;

import shared.parameters.AddAiParam;
import shared.response.ListAIResponse;
import model.ModelFacade;
import client.base.*;

/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController, Observer {

	public PlayerWaitingController(IPlayerWaitingView view) {
            super(view);
            ListAIResponse aiList = ModelFacade.getInstance().listAi();
            getView().setAIChoices(aiList.getAiTypes());
            ModelFacade.getInstance().addObserver(this);
	}

	@Override
	public IPlayerWaitingView getView() {
            return (IPlayerWaitingView)super.getView();
	}

	@Override
	public void start() {
//		refresh = true;
//		refresh();
	}

	@Override
	public void addAI() {
		AddAiParam param = new AddAiParam(getView().getSelectedAI());
		if (ModelFacade.getInstance().addAi(param).isValid())
		{
			refresh = true;
			refresh();
		}
	}

	@Override
	public void update(Observable arg0, Object arg1)
	{
		refresh();
	}
	
	public void refresh()
	{
		if (ModelFacade.getInstance().isGameFull() && getView().isModalShowing())
		{
            getView().closeModal();
		}
		else {
			if (refresh) {
				if (getView().isModalShowing()) getView().closeModal();

				getView().setPlayers(ModelFacade.getInstance().getPlayerInfoList());
				
				if (!ModelFacade.getInstance().isGameFull())
					getView().showModal();
				
				refresh = false;
			}
		}
	}
	
	private boolean refresh = true;
}

