package server.facade;

import model.Game;
import shared.parameters.AddAiParam;
import shared.parameters.CreateGameParam;
import shared.parameters.JoinGameParam;
import shared.response.CreateGameResponse;
import shared.response.GameListObject;
import shared.response.GameModelResponse;
import shared.response.ListAIResponse;
import shared.response.ListGamesResponse;
import shared.response.StandardResponse;

public class MockPreGameFacade implements IPreGameFacade
{

	@Override
	public StandardResponse join(JoinGameParam param)
	{
		StandardResponse response = new StandardResponse(true);
		return response;
	}

	@Override
	public CreateGameResponse create(CreateGameParam param)
	{
		Game game = new Game();
		game.initialize(param.getName(), param.isRandomTiles(),
				param.isRandomNumbers(), param.isRandomPorts());
		CreateGameResponse response = new CreateGameResponse(param.getName(),
				1, game.getPlayers(), true);
		return response;
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

	@Override
	public ListGamesResponse listGames()
	{
		GameListObject[] allGames = new GameListObject[1];
		Game game = new Game();
		GameListObject gameList = new GameListObject("Game Title", 0,
				game.getPlayerListObject());
		allGames[1] = gameList;
		return new ListGamesResponse(allGames, true);
	}

	@Override
	public GameModelResponse save(String name, int id)
	{
		GameModelResponse response = new GameModelResponse();
		Game game = new Game();
		response.setGame(game);
		response.setValid(true);
		return response;
	}

	@Override
	public GameModelResponse load(String name)
	{
		GameModelResponse response = new GameModelResponse();
		Game game = new Game();
		response.setGame(game);
		response.setValid(true);
		return response;
	}

}
