package server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

import server.facade.ServerFacade;
import shared.parameters.AcceptTradeParam;
import shared.parameters.CommandsParam;
import shared.response.CommandResponse;
import shared.response.GameModelResponse;
import shared.response.LoginResponse;
import shared.response.StandardResponse;

/**
 *
 * @author Drew
 */
public class CommandsHandler extends ServerHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson g = new Gson();
        String responseBody = "";
        int responseCode = 400;
        
        String requestType = exchange.getRequestMethod();
        String cookie = exchange.getRequestHeaders().getFirst("Cookie");
        LoginResponse login = getLoginFromCookie(cookie);

        if(!login.isValid())
        {
            responseBody = "\"Error: bad cookie\"";
        }
        else
        {
            int gameId = getGameIdFromCookie(cookie);
            if(requestType.equals("GET"))
            {
                CommandResponse response = ServerFacade.getCommands(gameId);

                if(response.isValid())
                {
                    responseBody = g.toJson(response.getCommands());
                    responseCode = 200;
                }
                else
                {
                    responseBody = "\"Failure\"";
                }
                
                
            }
            else if(requestType.equals("POST"))
            {
            	try{
            		
            	
                String input = read(exchange.getRequestBody());
                System.out.println(input);
                CommandsParam param = g.fromJson(input, CommandsParam.class);
                GameModelResponse response = ServerFacade.commands(param, gameId);

                System.out.println(param.getCommands().length);

                if(response.isValid())
                {
                    responseBody = g.toJson(response.getGame());
                    responseCode = 200;
                }
                else
                {
                    responseBody = "\"Failure\"";
                }
            	}catch(Exception e){e.printStackTrace();}
            }
        }
        
        
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(responseCode, 0);
        write(exchange.getResponseBody(), responseBody);
    }
    
}
