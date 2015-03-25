/*/*
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
import shared.parameters.SaveGameParam;
import shared.response.GameModelResponse;

/**
 *
 * @author Drew
 */
public class SaveGameHandler extends ServerHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson g = new Gson();
        String responseBody = "";
        int responseCode = 400;
        
        String input = read(exchange.getRequestBody());
        SaveGameParam param = g.fromJson(input, SaveGameParam.class);
        GameModelResponse response = ServerFacade.save(param);


        if(response.isValid())
        {
            responseBody = g.toJson(response.getGame());
            responseCode = 200;
        }
        else
        {
            responseBody = "\"Failure\"";
        }
	System.out.println("SaveGameHandler Response: " + responseBody);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(responseCode, 0);
        
        
        write(exchange.getResponseBody(), responseBody);
    }
    
}
