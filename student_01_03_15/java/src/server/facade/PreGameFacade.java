package server.facade;

import server.GameManager;
import shared.parameters.AddAiParam;
import shared.parameters.CreateGameParam;
import shared.parameters.JoinGameParam;
import shared.response.CreateGameResponse;
import shared.response.GameModelResponse;
import shared.response.ListAIResponse;
import shared.response.ListGamesResponse;
import shared.response.StandardResponse;

public class PreGameFacade implements IPreGameFacade
{
	private GameManager games;

	public PreGameFacade(GameManager games)
	{
		this.games=games;
	}
	
	@Override
	public StandardResponse join(JoinGameParam param)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CreateGameResponse create(CreateGameParam param)
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
	public ListGamesResponse listGames()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelResponse save(String name, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelResponse load(String name)
	{
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public ListAIResponse listAI(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
