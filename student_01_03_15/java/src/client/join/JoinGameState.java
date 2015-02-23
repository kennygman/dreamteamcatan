package client.join;

public class JoinGameState
{
	IJoinGameController controller;
	
	public JoinGameState(IJoinGameController controller)
	{
		this.controller=controller;
		updateGameList();
	}
	
	public void updateGameList()
	{
/*		JoinGameView view = (JoinGameView) controller.getView();
		GameInfo[] games = facade.getProxy().listGames().getGameListObject();
		view.setGames(games, null);
*/	}
	

}
