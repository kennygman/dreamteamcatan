package server.facade;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import model.Game;
import model.player.Player;
import server.GameManager;
import server.User;
import shared.Translator;
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

		if (game.isPlayerInGame(user.getId()))
		{
			return new StandardResponse(true);
		}
		else 
		{
			int pIndex = game.getEmptyPlayerIndex();
			if(pIndex > 0)
			{
				Player[] players = new Player[4];
				Player newPlayer = new Player();
				newPlayer.setColor(param.getColor());
				newPlayer.setName(user.getName());
				newPlayer.setPlayerIndex(pIndex);
				newPlayer.setPlayerID(user.getId());
				players[pIndex] = newPlayer;
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
		{
			return new CreateGameResponse(param.getName(), games.gamesSize()-1,
					game.getPlayers(), true);
		} else
		{
			return new CreateGameResponse(false);
		}
	}

	@Override
	public ListGamesResponse listGames()
	{
		List<GameListObject> allGames = new ArrayList<>();
		for (Game g : games.getGames())
		{
			allGames.add(new GameListObject(g.getTitle(), games.getGames()
					.indexOf(g), g.getPlayerListObject()));
		}
		return new ListGamesResponse(allGames.toArray(new GameListObject[0]),
				true);
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
			Translator t = new Translator();
			String gameJson = t.convert(game);
			byte[] gameInBytes = gameJson.getBytes();
			out.write(gameInBytes);
			out.flush();
			out.close();
			response.setValid(true);
		} catch (IOException e)
		{
			response.setValid(false);
		}
		return response;
	}

	@Override
	public GameModelResponse load(String name)
	{
		GameModelResponse response = new GameModelResponse();
		try
		{
			FileInputStream in = new FileInputStream(name);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			StringBuilder out = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null)
			{
				out.append(line);
			}
			Translator t = new Translator();
			return t.translateGetGameModel(out.toString());
		} catch (IOException e)
		{
			response.setValid(false);
		}
		return response;
	}
}
