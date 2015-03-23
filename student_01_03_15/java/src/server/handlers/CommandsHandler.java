package server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import server.facade.ServerFacade;
import shared.parameters.AcceptTradeParam;
import shared.response.CommandResponse;
import shared.response.GameModelResponse;
import shared.response.LoginResponse;

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
                String input = read(exchange.getRequestBody());
                AcceptTradeParam param = g.fromJson(input, AcceptTradeParam.class);
                GameModelResponse response = ServerFacade.acceptTrade(param, gameId);


                if(response.isValid())
                {
                    responseBody = g.toJson(response.getGame());
                    responseCode = 200;
                }
                else
                {
                    responseBody = "\"Failure\"";
                }
            }
        }
        
        
        exchange.sendResponseHeaders(responseCode, 0);
        write(exchange.getResponseBody(), responseBody);
    }
    
}
