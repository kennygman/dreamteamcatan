package server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import server.facade.ServerFacade;
import shared.response.ListGamesResponse;

/**
 *
 * @author Drew
 */
public class GameListHandler extends ServerHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson g = new Gson();
        String responseBody;
        int responseCode = 400;

        
        ListGamesResponse response = ServerFacade.listGames();
        
        if(response.isValid())
        {
            responseBody = g.toJson(response.getGameListObject());
            responseCode = 200;
        }
        else
        {
            responseBody = "\"Failure\"";
        }
	//System.out.println("GameListHandler Response: " + responseBody);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(responseCode, 0);
        
        write(exchange.getResponseBody(), responseBody);
    }
    
}
