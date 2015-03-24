package server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.net.URLDecoder;

import server.User;
import server.facade.ServerFacade;
import shared.PreGameCookie;
import shared.parameters.CredentialsParam;
import shared.parameters.JoinGameParam;
import shared.response.LoginResponse;
import shared.response.StandardResponse;

/**
 *
 * @author Drew
 */
public class JoinGameHandler extends ServerHandler implements HttpHandler
{

	@Override
	public void handle(HttpExchange exchange) throws IOException
	{
		Gson g = new Gson();
		String responseBody;
		int responseCode = 400;

		String input = read(exchange.getRequestBody());

		String cookie = exchange.getRequestHeaders().getFirst("Cookie");
		String userInfo = URLDecoder.decode(cookie, "UTF-8");

		if (!userInfo.startsWith("{"))
			userInfo = userInfo.substring(11);
		if (!userInfo.endsWith("}"))
			userInfo = userInfo.substring(0, userInfo.length() - 14);

		PreGameCookie cookieParam = null; // loginParam uses username, but the
											// cookie uses name
		try
		{
			cookieParam = g.fromJson(userInfo, PreGameCookie.class);
		} catch (Exception e)
		{
			System.err.println(e);
			System.err.println(e.getLocalizedMessage());
		}

		CredentialsParam loginParam = new CredentialsParam(
				cookieParam.getName(), cookieParam.getPassword());
		LoginResponse loginResponse = ServerFacade.login(loginParam);

		if (!loginResponse.isValid())
		{
			responseBody = "\"Error: bad cookie\"";
		} else
		{
			User user = g.fromJson(userInfo, User.class);
			JoinGameParam joinParam = g.fromJson(input, JoinGameParam.class);
			StandardResponse response = ServerFacade.join(joinParam, user);

			if (response.isValid())
			{
				responseBody = "\"Success\"";
				responseCode = 200;

				cookie = "catan.game=" + joinParam.getId() + ";Path=/;";
				exchange.getResponseHeaders().add("Set-cookie", cookie);
			} else
			{
				responseBody = "\"Error: invalid request\"";
			}
		}

		exchange.getResponseHeaders().add("Content-Type", "application/json");
		exchange.sendResponseHeaders(responseCode, 0);
		write(exchange.getResponseBody(), responseBody);
	}

}
