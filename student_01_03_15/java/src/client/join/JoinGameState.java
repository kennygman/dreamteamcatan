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
/*	public GameInfo[] validateGames(GameInfo[] games)
	{
		GameInfo[] newGames = new GameInfo[games.length];
		for (int i = 0; i < games.length; i++)
		{
			GameInfo newGameInfo = new GameInfo();
			newGameInfo.setId(games[i].getId());
			newGameInfo.setTitle(games[i].getTitle());
			if (games[i].getPlayers() != null && games[i].getPlayers().size() > 0)
			{
				for (PlayerInfo p : games[i].getPlayers())
				{
					if (p.getId() != -1)
						newGameInfo.addPlayer(p);;
				}
			}
			newGames[i] = newGameInfo;
		}

		return newGames;
	}
*/
	//--------------------------------------------------------------------------------
	public void updateGameList()
	{
		IJoinGameView view = controller.getJoinGameView();
		GameInfo[] games = ModelFacade.getInstance().listGames().getGameListObject();
		
		System.out.println("PLAYER LIST SIZE: " + games[games.length-1].getPlayers().size());
		PlayerInfo player =  ModelFacade.getInstance().getPlayerInfo();
		
		view.setGames(games,player);
	}
	
	//--------------------------------------------------------------------------------
	public boolean joinGame(CatanColor color)
	{
		this.updateGameList();
		return ModelFacade.getInstance().joinGame(new JoinGameParam(game.getId(), color.name().toLowerCase())).isValid();
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
}
