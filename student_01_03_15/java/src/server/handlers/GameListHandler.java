/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import server.facade.ServerFacade;
import shared.response.ListGamesResponse;

/**
 *
 * @author Drew
 */
public class GameListHandler  implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson g = new Gson();
        
        StringBuilder stringBuffer = new StringBuilder();
        InputStreamReader reader = new InputStreamReader(exchange.getRequestBody());
        BufferedReader buffRead = new BufferedReader(reader);

        String inputLine;
        while((inputLine = buffRead.readLine()) != null)
        {
            stringBuffer.append(inputLine);
        }

        exchange.getRequestBody().close();

        
        ListGamesResponse response = ServerFacade.listGames();
        String info = "";
        //int responseCode = 400;
        
        if(response.isValid())
        {
            info = g.toJson(response.getGameListObject());
            //responseCode = 200;
        }
        //set Response Code???
        
        
        OutputStreamWriter writer = new OutputStreamWriter(exchange.getResponseBody());
        writer.write(info);
        writer.close();
    }
    
}
