package client.join;

import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import shared.parameters.AddAiParam;
import shared.response.ListAIResponse;
import shared.response.StandardResponse;
import model.ModelFacade;
import client.base.*;
import client.data.PlayerInfo;


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
       	getView().showModal();
	}

	@Override
	public void addAI() {
		AddAiParam param = new AddAiParam(getView().getSelectedAI());
		ModelFacade.getInstance().addAi(param);
	}

	@Override
	public void update(Observable arg0, Object arg1)
	{
		
		if (ModelFacade.getInstance().isGameFull() && getView().isModalShowing())
		{
            getView().closeModal();
		}
		else {
			PlayerInfo[] players = ModelFacade.getInstance().getPlayerInfoList();
			getView().setPlayers(players);
		}
	}

}

