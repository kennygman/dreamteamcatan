package client.join;

import shared.definitions.CatanColor;
import shared.parameters.CreateGameParam;
import shared.parameters.JoinGameParam;
import model.ModelFacade;
import client.data.GameInfo;
import client.data.PlayerInfo;

public class JoinGameState
{
	JoinGameController controller;
	GameInfo game;
	
	//--------------------------------------------------------------------------------
	public JoinGameState(JoinGameController controller)
	{
		this.controller=controller;
	}

	//--------------------------------------------------------------------------------
	public void updateGameList()
	{
		IJoinGameView view = controller.getJoinGameView();
		GameInfo[] games = ModelFacade.getInstance().listGames().getGameListObject();
		
		PlayerInfo player =  ModelFacade.getInstance().getPlayerInfo();
		
		view.setGames(games,player);
	}
	
	//--------------------------------------------------------------------------------
	public boolean joinGame(CatanColor color)
	{
		this.updateGameList();
		
		if (ModelFacade.getInstance().joinGame(new JoinGameParam(game.getId(), color.name().toLowerCase())).isValid())
		{
			setGameInfo();
			return true;
		}
		return false;
	}
	
	//--------------------------------------------------------------------------------
	public boolean createGame()
	{
		INewGameView view = controller.getNewGameView();
		String name = view.getTitle();
		boolean hexes = view.getRandomlyPlaceHexes();
		boolean numbers = view.getRandomlyPlaceNumbers();
		boolean ports = view.getUseRandomPorts();
		if (name == null) return false;

		ModelFacade.getInstance().createGame(new CreateGameParam(name,hexes,numbers,ports));
		this.updateGameList();
		return true;
	}
	
	//--------------------------------------------------------------------------------
	public void setGame(GameInfo game)
	{
		this.game=game;
	}

	//--------------------------------------------------------------------------------
	public void setGameInfo()
	{
		GameInfo joinedGame = ModelFacade.getInstance().listGames().getGameListObject()[game.getId()];
		ModelFacade.getInstance().setGameInfo(joinedGame);

		PlayerInfo player = ModelFacade.getInstance().getPlayerInfo();
		for (PlayerInfo p : joinedGame.getPlayers())
		{
			if (p.getId() == player.getId())
			{
				player = p;
				return;
			}
		}
	}
}
