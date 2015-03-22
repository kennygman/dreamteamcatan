package server.facade;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import model.Game;
import model.player.Player;
import server.GameManager;
import server.User;
import shared.parameters.CreateGameParam;
import shared.parameters.JoinGameParam;
import shared.response.CreateGameResponse;
import shared.response.GameListObject;
import shared.response.GameModelResponse;
import shared.response.ListGamesResponse;
import shared.response.StandardResponse;

public class PreGameFacade implements IPreGameFacade
{
	private GameManager games;

	public PreGameFacade(GameManager games)
	{
		this.games = games;
	}

	@Override
	public StandardResponse join(JoinGameParam param, User user)
	{
		Game game = games.getGame(param.getId());
		Player[] players = game.getPlayers();
		for (int i = 0; i < players.length; i++)
		{
			if (players[i].equals(null))
			{
				Player newPlayer = new Player();
				// TODO: set the new players color
				// how does the player name get set?????
				players[i] = newPlayer;
				game.setPlayers(players);
				return new StandardResponse(true);
			}
		}
		return new StandardResponse(false);
	}

	@Override
	public CreateGameResponse create(CreateGameParam param)
	{
		Game game = new Game();
		game.initialize(param.getName(), param.isRandomTiles(),
				param.isRandomNumbers(), param.isRandomPorts());
		if (games.addGame(game))
			return new CreateGameResponse(param.getName(), games.gamesSize(),
					game.getPlayers(), true);
		else
			return new CreateGameResponse(param.getName(), -1,
					game.getPlayers(), false);
	}

	@Override
	public ListGamesResponse listGames()
	{
		GameListObject[] allGames = new GameListObject[games.gamesSize()];
		for (int i = 0; i < games.gamesSize(); i++)
		{
			GameListObject game = new GameListObject(games.getGame(i)
					.getTitle(), i, games.getGame(i).getPlayerListObject());
			allGames[i] = game;
		}
		return new ListGamesResponse(allGames, true);
	}

	@Override
	public GameModelResponse save(String name, int id)
	{
		GameModelResponse response = new GameModelResponse();
		Game game = games.getGame(id);
		response.setGame(game);
		try
		{
			FileOutputStream out = new FileOutputStream(name);
			response.setValid(true);
		} catch (FileNotFoundException e)
		{
			response.setValid(false);
		}
		return response;
	}

	@Override
	public GameModelResponse load(String name)
	{
		GameModelResponse response = new GameModelResponse();
		Game game = new Game();
		try
		{
			FileInputStream in = new FileInputStream(name);
			// TODO: do something with the game
			response.setValid(true);
		} catch (FileNotFoundException e)
		{
			response.setValid(false);
		}
		return response;
	}
}
