package client.join;

import java.util.Observable;
import java.util.Observer;
import shared.parameters.AddAiParam;
import shared.response.ListAIResponse;
import model.ModelFacade;
import client.base.*;
import client.data.PlayerInfo;

/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements
		IPlayerWaitingController, Observer
{

	public PlayerWaitingController(IPlayerWaitingView view)
	{
		super(view);
		ModelFacade.getInstance().addObserver(this);
	}

	@Override
	public IPlayerWaitingView getView()
	{
		return (IPlayerWaitingView) super.getView();
	}

	@Override
	public void start()
	{
	}

	@Override
	public void addAI()
	{
		AddAiParam param = new AddAiParam(getView().getSelectedAI());
		if (ModelFacade.getInstance().addAi(param).isValid())
		{
			refresh();
		}
	}

	private boolean first = true;

	@Override
	public void update(Observable arg0, Object arg1)
	{
		if (first)
		{
			ListAIResponse aiList = ModelFacade.getInstance().listAi();
			getView().setAIChoices(aiList.getAiTypes());
			first = false;
		}

		if (ModelFacade.getInstance().getGameInfo().getPlayers().size() > size)
		{
			refresh();
		}
	}

	public void refresh()
	{
		if (getView().isModalShowing())
			getView().closeModal();
		if (!ModelFacade.getInstance().isGameFull())
		{
			PlayerInfo[] players = ModelFacade.getInstance()
					.getPlayerInfoList();
			size = players.length;
			getView().setPlayers(players);
			getView().showModal();
		}

	}

	private int size = -1;

}
