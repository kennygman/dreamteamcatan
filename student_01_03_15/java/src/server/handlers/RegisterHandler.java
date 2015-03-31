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
public class RegisterHandler extends ServerHandler implements HttpHandler
{

	@Override
	public void handle(HttpExchange exchange) throws IOException
	{
		Gson g = new Gson();
		String responseBody = "";
		int responseCode = 400;
		try
		{
			String input = read(exchange.getRequestBody());
			CredentialsParam param = g.fromJson(input, CredentialsParam.class);
			LoginResponse response = ServerFacade.register(param);

			if (response.isValid())
			{

				responseBody = "\"Success\"";
				responseCode = 200;
				PreGameCookie user = new PreGameCookie(response.getPlayerInfo()
						.getId(), param.getUser(), param.getPassword());
				String cookie = "catan.user=";
				cookie += URLEncoder.encode(g.toJson(user), "UTF-8");
				cookie += ";Path=/;";
				exchange.getResponseHeaders().add("Set-cookie", cookie);
			} else
			{
				responseBody = "\"Error: that username is already being used\"";
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
