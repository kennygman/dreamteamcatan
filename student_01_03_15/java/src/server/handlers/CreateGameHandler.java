package server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

import server.facade.ServerFacade;
import shared.parameters.CreateGameParam;
import shared.response.CreateGameResponse;
import shared.response.LoginResponse;

/**
 *
 * @author Drew
 */
public class CreateGameHandler extends ServerHandler implements HttpHandler
{

	@Override
	public void handle(HttpExchange exchange) throws IOException
	{
		Gson g = new Gson();
		String responseBody;
		int responseCode = 400;
//		System.out.println("CreateGameHandler-handle()");
		LoginResponse login = getLoginFromCookie(exchange.getRequestHeaders()
				.getFirst("Cookie"));
//		 System.out.println("CreateGameHandler-login()");

		if (!login.isValid())
		{
			responseBody = "\"Error: bad cookie\"";
		} else
		{
			String input = read(exchange.getRequestBody());
			// .out.println("CreateGameRequest: !" + input + "!");
			CreateGameParam param = g.fromJson(input, CreateGameParam.class);
			CreateGameResponse response = ServerFacade.create(param);

			if (response.isValid())
			{
				responseBody = g.toJson(response);
				// responseBody = "\"Success\"";
				responseCode = 200;
			} else
			{
				responseBody = "\"Error: invalid request\"";
			}
		}

		exchange.getResponseHeaders().add("Content-Type", "application/json");
		exchange.sendResponseHeaders(responseCode, 0);
		// System.out.println("CreateGameResponse: !" + responseBody + "!");
		write(exchange.getResponseBody(), responseBody);
	}

}
