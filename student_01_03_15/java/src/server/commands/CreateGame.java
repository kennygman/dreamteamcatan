package server.commands;

import model.Game;
import server.facade.ServerFacade;
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
	 * Creates and initializes  game object with the specified properties,
	 * Adds game to server games list
	 */
	@Override
	public void execute(Game game)
	{
            
	}

}
