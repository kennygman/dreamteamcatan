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
import java.net.URLEncoder;
import server.facade.ServerFacade;
import shared.Cookie;
import shared.parameters.CredentialsParam;
import shared.response.LoginResponse;

/**
 *
 * @author Drew
 */
public class RegisterHandler  implements HttpHandler {

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

        
        CredentialsParam param = g.fromJson(stringBuffer.toString(), CredentialsParam.class);
        LoginResponse response = ServerFacade.register(param);
        
        String info = "";
        int responseCode = 400;
        String cookie = ""; 
        
        if(response.isValid())
        {
            info = "Success";
            responseCode = 200;
            Cookie user = new Cookie(response.getPlayerInfo().getId(), param.getUser(), param.getPassword());
            cookie = "catan.user=";
            cookie += URLEncoder.encode(g.toJson(user));
            cookie += ";Path=/;";
            System.out.println(info);
            System.out.println(cookie);
        }
        
        
        System.out.println("help9785389597845897");
        exchange.getResponseHeaders().add("Set-cookie", cookie);
        System.out.println("WHAT ARE YOU DOING MAN!?!?!?!?!?!?!?");
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        System.out.println("help2");
        exchange.sendResponseHeaders(responseCode, 0);
        System.out.println("help3");
        
        OutputStreamWriter writer = new OutputStreamWriter(exchange.getResponseBody());
        System.out.println("help4");
        writer.write(info);
        writer.close();
        System.out.println("help5");
    }
    
}
