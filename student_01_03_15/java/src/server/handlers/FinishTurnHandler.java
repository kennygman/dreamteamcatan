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
import shared.parameters.FinishTurnParam;
import shared.response.GameModelResponse;
import shared.response.LoginResponse;

/**
 *
 * @author Drew
 */
public class FinishTurnHandler extends ServerHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
    	try{
        Gson g = new Gson();
        String responseBody = "";
        int responseCode = 400;
        
        String cookie = exchange.getRequestHeaders().getFirst("Cookie");
        LoginResponse login = getLoginFromCookie(cookie);
        
        if(!login.isValid())
        {
            responseBody = "\"Error: bad cookie\"";
        }
        else
        {
            int gameId = getGameIdFromCookie(cookie);
            
            String input = read(exchange.getRequestBody());
            FinishTurnParam param = g.fromJson(input, FinishTurnParam.class);
            GameModelResponse response = ServerFacade.finishTurn(param, gameId);

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
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(responseCode, 0);
        
        
        write(exchange.getResponseBody(), responseBody);
    	}catch(Exception e){e.printStackTrace();}
    }
    
}
