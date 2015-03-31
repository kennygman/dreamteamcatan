package server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.URLEncoder;

import server.facade.ServerFacade;
import shared.PreGameCookie;
import shared.parameters.CredentialsParam;
import shared.response.LoginResponse;

/**
 *
 * @author Drew
 */
public class LoginHandler extends ServerHandler implements HttpHandler
{

	@Override
	public void handle(HttpExchange exchange) throws IOException
	{
		Gson g = new Gson();
		String responseBody = "\"Error: invalid username or password\"";
		int responseCode = 400;

		try
		{
			String input = read(exchange.getRequestBody());
			CredentialsParam param = g.fromJson(input, CredentialsParam.class);
			LoginResponse response = ServerFacade.login(param);

			if (response.isValid())
			{
				responseBody = "\"Success\"";
				responseCode = 200;
				// Clears out the game
				exchange.getResponseHeaders().add("Set-cookie", "catan.game=;Path=/;Expires=Wed, 02 Mar 2011 00:00:00 GMT;");
				PreGameCookie u = new PreGameCookie(response.getPlayerInfo()
						.getId(), param.getUser(), param.getPassword());
				
				String cookie = "catan.user=";
				cookie += URLEncoder.encode(g.toJson(u), "UTF-8");
				cookie += ";Path=/;";
				exchange.getResponseHeaders().add("Set-cookie", cookie);
			}

		} catch (com.google.gson.JsonSyntaxException e1)
		{
			 responseBody = "\"Error: invalid json format\"";
			 //e1.printStackTrace();
		} catch (Exception e2)
		{
			e2.printStackTrace();
		} finally
		{
			exchange.getResponseHeaders().add("Content-Type",
					"application/json");
			exchange.sendResponseHeaders(responseCode, 0);
			write(exchange.getResponseBody(), responseBody);
			exchange.getResponseBody().close();
		}
	}

}
