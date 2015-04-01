package shared;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import shared.response.GameListObject;
import shared.response.GameModelResponse;
import client.data.GameInfo;

import com.google.gson.Gson;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import model.Game;
import shared.response.*;

public class Translator
{
	/**
	 * 
	 * @param jsonFile
	 * @return A game model based on the json file.
	 */
	private Gson g;

	public Translator()
	{
		g = new Gson();
	}

	public String convert(Object input)
	{
		String translation = g.toJson(input);
		return translation;
	}

	public GameModelResponse translateGetGameModel(String input)
	{
		Game game = g.fromJson(input, Game.class);
		GameModelResponse response = new GameModelResponse();

		if (game != null)
		{
			game.sortBoard();
			response.setGame(game);
			response.setValid(true);
		} else
		{
			response.setValid(false);
			// System.err.println(input);
		}

		return response;
	}

	public StandardResponse translateStandard(String input)
	{
		StandardResponse translation;
		if (input.equals("Success"))
		{
			translation = new StandardResponse(true);
		} else
		{
			translation = new StandardResponse(false);
			// System.err.println(input);
		}

		return translation;
	}

	public LoginResponse translateLogin(String input, int playerId)
	{
		LoginResponse translation;
		if (input.equals("Success"))
		{
			translation = new LoginResponse(true, playerId);
		} else
		{
			translation = new LoginResponse(false);
			// System.err.println(input);
		}

		return translation;
	}

	public ListAIResponse translateListAi(String input)
	{
		String[] aiList = g.fromJson(input, String[].class);
		if (aiList == null)
		{
			return new ListAIResponse(null, false);
		}

		ListAIResponse translation = new ListAIResponse(aiList, true);

		return translation;
	}

/*	public CommandResponse translateCommands(String input)
	{

		boolean valid = true;
		if (input == null)
		{
			valid = false;
		}

		CommandResponse translation = new CommandResponse(input, valid);

		return translation;
	}
*/
	public ListGamesResponse translateListGames(String input)
	{
		boolean valid = true;
		GameListObject[] gamesList = g.fromJson(input, GameListObject[].class);
		if (gamesList == null)
		{
			valid = false;
		}
		// ArrayList<GameListObject> gamesResponse = (ArrayList<GameListObject>)
		// Arrays.asList(gamesList);
		ListGamesResponse translation = new ListGamesResponse(gamesList, valid);

		return translation;
	}

	public CreateGameResponse translateCreateGame(String input)
	{
		CreateGameResponse response = g.fromJson(input,
				CreateGameResponse.class);
		if (response != null)
		{
			response.setValid(true);
		} else
		{
			response = new CreateGameResponse(false);
		}
		return response;
	}
}