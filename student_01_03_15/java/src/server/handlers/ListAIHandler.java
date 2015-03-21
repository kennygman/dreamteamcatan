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
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import server.facade.ServerFacade;
import shared.InGameCookie;
import shared.response.ListAIResponse;
import shared.response.ListGamesResponse;
import shared.response.LoginResponse;

/**
 *
 * @author Drew
 */
public class ListAIHandler extends ServerHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        /*Gson g = new Gson();
        String responseBody;
        int responseCode = 400;
        
        String cookie = exchange.getRequestHeaders().getFirst("Cookie");
        LoginResponse login = readCookie(cookie);
        cookie = URLDecoder.decode(cookie);
        InGameCookie user = g.fromJson(cookie, InGameCookie.class);
        
        ListAIResponse response = ServerFacade.listAI();
        
        
        if(response.isValid())
        {
            responseBody = g.toJson(response.getGameListObject());
            responseCode = 200;
        }
        else
        {
            responseBody = "Failure";
        }
        exchange.sendResponseHeaders(responseCode, 0);
        
        
        OutputStreamWriter writer = new OutputStreamWriter(exchange.getResponseBody());
        writer.write(responseBody);
        writer.close();*/
    }
    
}
