package client.join;

import shared.parameters.AddAiParam;
import shared.response.ListAIResponse;
import shared.response.StandardResponse;
import model.ModelFacade;
import client.base.*;
import client.data.PlayerInfo;


/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements IPlayerWaitingController {

	public PlayerWaitingController(IPlayerWaitingView view) {
            super(view);
            ListAIResponse aiList = ModelFacade.getInstance().listAi();
            getView().setAIChoices(aiList.getAiTypes());
	}

	@Override
	public IPlayerWaitingView getView() {
            return (IPlayerWaitingView)super.getView();
	}

	@Override
	public void start() {
            if(!ModelFacade.getInstance().checkGameFull())
            {
                    getView().showModal();
                    //getView().setPlayers(ModelFacade.getInstance().getGameInfo().getPlayers().toArray(new PlayerInfo[0]));
                    getView().setPlayers(ModelFacade.getInstance().getPlayerInfoList());
            }
	}

	@Override
	public void addAI() {
            AddAiParam param = new AddAiParam(getView().getSelectedAI());
            StandardResponse addAiResponse = ModelFacade.getInstance().addAi(param);
            if(addAiResponse.isValid())
            {
                    //Have to get game id from proxy via facade because stupid game model doesn't even know it's id.
                    if(ModelFacade.getInstance().checkGameFull())
                    {
                            getView().setPlayers(ModelFacade.getInstance().getPlayerInfoList());
                            getView().closeModal();
                            ModelFacade.getInstance().updateGameModel();
                    }
            }
	}

}

