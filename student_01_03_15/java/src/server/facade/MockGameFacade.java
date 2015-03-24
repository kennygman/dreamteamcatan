package server.facade;

import shared.parameters.AddAiParam;
import shared.response.CommandResponse;
import shared.response.GameModelResponse;
import shared.response.ListAIResponse;
import shared.response.StandardResponse;

public class MockGameFacade implements IGameFacade
{

	@Override
	public GameModelResponse getGameModel(int id)
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
	public ListAIResponse listAI()
	{
		// TODO Auto-generated method stub
		return null;
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

}
