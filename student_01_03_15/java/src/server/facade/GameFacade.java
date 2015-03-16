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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommandResponse getCommands(int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StandardResponse commands(int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StandardResponse addAI(AddAiParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListAIResponse listAI(int id)
	{
		// TODO Auto-generated method stub
		return null;
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
