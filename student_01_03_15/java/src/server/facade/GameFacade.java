package server.facade;

import model.Game;
import server.GameManager;
import shared.parameters.AddAiParam;
import shared.response.*;

public class GameFacade implements IGameFacade
{
    private GameManager games;
    
    public GameFacade(GameManager games)
    {
    	this.games=games;
    }
    
	@Override
	public GameModelResponse getGameModel(int id)
	{
		return buildGameResponse(games.getGame(id));
	}

	@Override
	public GameModelResponse resetGame(int id)
	{
		return buildGameResponse(games.getGame(id).reset());
	}

	@Override
	public CommandResponse getCommands(int id)
	{
		games.getCommands(id);
		return null;
	}

	@Override
	public StandardResponse commands(int id)
	{
		// exectues list of commands
		
		return null;
	}

	@Override
	public StandardResponse addAI(AddAiParam param, int id)
	{
		
		StandardResponse response = new StandardResponse(true);
		
		return response;
		
	}

	@Override
	public ListAIResponse listAI(int id)
	{
		String[] aiType = new String[]{"LARGEST_ARMY"};
		ListAIResponse response = new ListAIResponse(aiType,true);
		return response;
	}

	private GameModelResponse buildGameResponse(Game game)
	{
		GameModelResponse response = new GameModelResponse();
    	if (game != null)
    	{
    		 response.setGame(game);
    		 response.setValid(true);
    	}
    	else
    	{
    		response.setValid(false); 
    	}
    	
    	return response;
	}
}
