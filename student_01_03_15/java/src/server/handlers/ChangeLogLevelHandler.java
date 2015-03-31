/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

import server.facade.ServerFacade;
import shared.parameters.ChangeLogLevelParam;
import shared.response.StandardResponse;

/**
 *
 * @author Drew
 */
public class ChangeLogLevelHandler extends ServerHandler implements HttpHandler
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
			ChangeLogLevelParam param = g.fromJson(input,
					ChangeLogLevelParam.class);
			StandardResponse response = ServerFacade.changeLogLevel(param);

			if (response.isValid())
			{
				responseBody = "\"Success\"";
				responseCode = 200;
			} else
			{
				responseBody = "\"Failure\"";
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
