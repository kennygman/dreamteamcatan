package client.join;

import client.data.GameInfo;
import model.ModelFacade;

public class JoinGameState
{
	IJoinGameController controller;
	ModelFacade facade = ModelFacade.getInstance();
	
	public JoinGameState(IJoinGameController controller)
	{
		this.controller=controller;
		updateGameList();
	}
	
	public void updateGameList()
	{
		JoinGameView view = (JoinGameView) controller.getView();
		GameInfo[] games = facade.getProxy().listGames().getGameListObject();
		view.setGames(games, null);
	}
	

}
