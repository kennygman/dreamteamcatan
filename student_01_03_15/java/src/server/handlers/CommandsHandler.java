package server.handlers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import server.commands.CommandFactory;
import server.commands.ICommand;
import server.facade.ServerFacade;
import shared.parameters.CommandsParam;
import shared.response.CommandResponse;
import shared.response.GameModelResponse;
import shared.response.LoginResponse;

/**
 *
 * @author Drew
 */
public class CommandsHandler extends ServerHandler implements HttpHandler
{

	@Override
	public void handle(HttpExchange exchange) throws IOException
	{
		Gson g = new Gson();
		String responseBody = "[]";
		int responseCode = 400;

		try
		{

			String requestType = exchange.getRequestMethod();
			String cookie = exchange.getRequestHeaders().getFirst("Cookie");
			LoginResponse login = getLoginFromCookie(cookie);

			if (!login.isValid())
			{
				responseBody = "\"Error: bad cookie\"";
			} else
			{
				int gameId = getGameIdFromCookie(cookie);
				if (requestType.equals("GET"))
				{
					CommandResponse response = ServerFacade.getCommands(gameId);

					if (response.isValid())
					{
						if (response.getCommands()!=null)
						{
							responseBody = g.toJson(response.getCommands());
						}
						responseCode = 200;
					} else
					{
						responseBody = "\"Failure\"";
					}

				} else if (requestType.equals("POST"))
				{
					String input = read(exchange.getRequestBody());

					JsonParser jsonParser = new JsonParser();
					JsonArray jsonArr = (JsonArray) jsonParser.parse(input);
					JsonObject[] jsonObjList = g.fromJson(jsonArr,
							JsonObject[].class);

					new CommandFactory(gameId);
					List<ICommand> commands = new ArrayList<ICommand>();
					for (JsonObject jo : jsonObjList)
					{
						ICommand cmd = CommandFactory.buildCommand(
								jo.get("type").getAsString(), jo);
						if (cmd == null)
							throw new Exception("BAD COMMAND");
						commands.add(cmd);
					}

					CommandsParam param = new CommandsParam(commands);
					GameModelResponse response = ServerFacade.commands(param,
							gameId);

					if (response.isValid())
					{
						responseBody = g.toJson(response.getGame());
						responseCode = 200;
					} else
					{
						responseBody = "\"Failure\"";
					}
				}
			}
		} catch (com.google.gson.JsonSyntaxException e1)
		{
			responseBody = "\"Error: invalid json format\"";
			// e1.printStackTrace();
		} catch (Exception e)
		{
			//e.printStackTrace();
		} finally
		{
			exchange.getResponseHeaders().add("Content-Type",
					"application/json");
			exchange.sendResponseHeaders(responseCode, 0);
			write(exchange.getResponseBody(), responseBody);

		}
	}

}
