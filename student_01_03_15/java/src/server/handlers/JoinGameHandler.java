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
import java.net.URLDecoder;
import java.util.List;
import server.User;
import server.facade.ServerFacade;
import shared.parameters.CredentialsParam;
import shared.parameters.JoinGameParam;
import shared.response.LoginResponse;
import shared.response.StandardResponse;

/**
 *
 * @author Drew
 */
public class JoinGameHandler  implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson g = new Gson();
        String info = "";
        
        StringBuilder stringBuffer = new StringBuilder();
        InputStreamReader reader = new InputStreamReader(exchange.getRequestBody());
        BufferedReader buffRead = new BufferedReader(reader);

        String inputLine;
        while((inputLine = buffRead.readLine()) != null)
        {
            stringBuffer.append(inputLine);
        }
        exchange.getRequestBody().close();
        

        String cookie = exchange.getRequestHeaders().getFirst("Cookie");
        String userInfo = URLDecoder.decode(cookie);
        CredentialsParam loginParam = g.fromJson(userInfo, CredentialsParam.class);
        LoginResponse loginResponse = ServerFacade.login(loginParam);
        int responseCode = 400;
        
        
        if(loginResponse.isValid())
        {
            User user = g.fromJson(userInfo, User.class);


            JoinGameParam joinParam = g.fromJson(stringBuffer.toString(), JoinGameParam.class);
            StandardResponse response = ServerFacade.join(joinParam, user);
            
            

            if(response.isValid())
            {
                info = "Success";
                responseCode = 200;
                
               cookie = "catan.game=" + joinParam.getId() + ";Path=/;";
               exchange.getResponseHeaders().add("Set-cookie", cookie); 
            }
            
        }
        exchange.sendResponseHeaders(responseCode, 0);
        
        OutputStreamWriter writer = new OutputStreamWriter(exchange.getResponseBody());
        writer.write(info);
        writer.close();
    }
    
}
