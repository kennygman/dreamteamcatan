package client.points;

import java.util.Observable;
import java.util.Observer;

import model.ModelFacade;
import model.player.Player;
import client.base.*;

/**
 * Implementation for the points controller
 */
public class PointsController extends Controller implements IPointsController,
		Observer
{

	private IGameFinishedView finishedView;

	/**
	 * PointsController constructor
	 * 
	 * @param view
	 *            Points view
	 * @param finishedView
	 *            Game finished view, which is displayed when the game is over
	 */
	public PointsController(IPointsView view, IGameFinishedView finishedView)
	{

		super(view);

		setFinishedView(finishedView);

//		initFromModel();
		ModelFacade.getInstance().addObserver(this);
	}

	public IPointsView getPointsView()
	{

		return (IPointsView) super.getView();
	}

	public IGameFinishedView getFinishedView()
	{
		return finishedView;
	}

	public void setFinishedView(IGameFinishedView finishedView)
	{
		this.finishedView = finishedView;
	}

	private void initFromModel()
	{
		if (ModelFacade.getInstance() == null
				|| ModelFacade.getInstance().getGame() == null)
			return;
		Player[] players = ModelFacade.getInstance().getGame().getPlayers();
		int playerId = ModelFacade.getInstance().getPlayerInfo().getId();
		for (Player player : players)
		{
			int monument = player.getNewDevCards().getMonument();
			monument += player.getOldDevCards().getMonument();
			if (player.getVictoryPoints() + monument >= 10) 
			{
				this.getFinishedView().setWinner(player.getName(), player.getPlayerID()==playerId);
				this.getFinishedView().showModal();
				return;
			}
			if (player.getPlayerID() == playerId)
			{
				getPointsView().setPoints(player.getVictoryPoints());
				return;
			}
		}
		getPointsView().setPoints(-1);
		// </temp>
	}

	@Override
	public void update(Observable o, Object o1)
	{
		initFromModel();
	}
}
