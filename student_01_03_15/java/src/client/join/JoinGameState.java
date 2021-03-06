package client.join;

import shared.definitions.CatanColor;
import shared.parameters.CreateGameParam;
import shared.parameters.JoinGameParam;
import shared.response.StandardResponse;
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
		if (games == null) return;
		
		PlayerInfo player =  ModelFacade.getInstance().getPlayerInfo();
		
		view.setGames(games,player);
	}
	
	//--------------------------------------------------------------------------------
	public boolean joinGame(CatanColor color)
	{
		this.updateGameList();
		StandardResponse response = ModelFacade.getInstance().joinGame(
				new JoinGameParam(game.getId(), color.name().toLowerCase()));
		if (response.isValid())
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
		if (name.equals("")) return false;

		ModelFacade.getInstance().createGame(new CreateGameParam(name,hexes,numbers,ports));
		this.updateGameList();
		return true;
	}
	
	//--------------------------------------------------------------------------------
	public void setGame(GameInfo game)
	{
		this.game=game;
		disableColors();
	}

	// --------------------------------------------------------------------------------
	public void setGameInfo()
	{
		GameInfo joinedGame = ModelFacade.getInstance().listGames()
				.getGameListObject(game.getId());

		if (joinedGame != null)
		{
			ModelFacade.getInstance().setGameInfo(joinedGame);

			PlayerInfo player = ModelFacade.getInstance().getPlayerInfo();
			for (PlayerInfo p : joinedGame.getPlayers())
			{
				if (p.getId() == player.getId())
				{
					player.setPlayerIndex(joinedGame.getPlayers().indexOf(p));
					player = p;
					return;
				}
			}
		}
	}

	//--------------------------------------------------------------------------------
	public void disableColors()
	{
		controller.getSelectColorView().setColorEnabled(CatanColor.BLUE, true);
		controller.getSelectColorView().setColorEnabled(CatanColor.BROWN, true);
		controller.getSelectColorView().setColorEnabled(CatanColor.GREEN, true);
		controller.getSelectColorView().setColorEnabled(CatanColor.ORANGE, true);
		controller.getSelectColorView().setColorEnabled(CatanColor.PUCE, true);
		controller.getSelectColorView().setColorEnabled(CatanColor.PURPLE, true);
		controller.getSelectColorView().setColorEnabled(CatanColor.RED, true);
		controller.getSelectColorView().setColorEnabled(CatanColor.WHITE, true);
		controller.getSelectColorView().setColorEnabled(CatanColor.YELLOW, true);

		for (PlayerInfo p : game.getPlayers())
		{
			if (p.getId() != ModelFacade.getInstance().getPlayerInfo().getId())
			{
				controller.getSelectColorView().setColorEnabled(p.getColor(), false);
			}
		}
		
	}
}
