package server.commands;

import model.Game;
import server.ServerFacade;
import shared.parameters.CreateGameParam;

public class CreateGame implements ICommand
{
	private CreateGameParam param;
	
	public CreateGame(CreateGameParam param)
	{
		super();
		this.param = param;
	}

	/**
	 * Create game object,
	 * Initialize objects in the game model,
	 * Add game to server games list
	 */
	@Override
	public void execute(Game game)
	{
		ServerFacade.getInstance().setGame(game);
	}

}
