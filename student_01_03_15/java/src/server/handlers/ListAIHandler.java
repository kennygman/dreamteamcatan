package server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import server.facade.ServerFacade;
import shared.response.ListAIResponse;
import shared.response.ListGamesResponse;
import shared.response.LoginResponse;

/**
 *
 * @author Drew
 */
public class ListAIHandler extends ServerHandler implements HttpHandler
{

	@Override
	public void handle(HttpExchange exchange) throws IOException
	{
		Gson g = new Gson();
		String responseBody;
		int responseCode = 400;

		String cookie = exchange.getRequestHeaders().getFirst("Cookie");
		LoginResponse login = getLoginFromCookie(cookie);

		if (!login.isValid())
		{
			responseBody = "\"Error: bad cookie\"";
		} else
		{
			// int gameId = getGameIdFromCookie(cookie);
			ListAIResponse response = ServerFacade.listAi();

			if (response.isValid())
			{
				responseBody = g.toJson(response.getAiTypes());
				responseCode = 200;
			} else
			{
				responseBody = "\"Failure\"";
			}
		}
		System.out.println("ListAIHandler Response: " + responseBody);
		exchange.getResponseHeaders().add("Content-Type", "application/json");
		exchange.sendResponseHeaders(responseCode, 0);

		write(exchange.getResponseBody(), responseBody);
	}

}
