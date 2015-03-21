package server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.net.URLDecoder;
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
public class JoinGameHandler extends ServerHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Gson g = new Gson();
        String responseBody;
        int responseCode = 400;
        
        String input = read(exchange.getRequestBody());

        String cookie = exchange.getRequestHeaders().getFirst("Cookie");
        String userInfo = URLDecoder.decode(cookie);
        CredentialsParam loginParam = g.fromJson(userInfo, CredentialsParam.class);
        LoginResponse loginResponse = ServerFacade.login(loginParam);
        
        
        if(!loginResponse.isValid())
        {
            responseBody = "Error: bad cookie";
        }
        else
        {
            User user = g.fromJson(userInfo, User.class);
            JoinGameParam joinParam = g.fromJson(input, JoinGameParam.class);
            StandardResponse response = ServerFacade.join(joinParam, user);
            

            if(response.isValid())
            {
                responseBody = "Success";
                responseCode = 200;
                
               cookie = "catan.game=" + joinParam.getId() + ";Path=/;";
               exchange.getResponseHeaders().add("Set-cookie", cookie); 
            }
            else
            {
                responseBody = "Error: invalid request";
            }
        }
        exchange.sendResponseHeaders(responseCode, 0);
        write(exchange.getResponseBody(), responseBody);
    }
    
}
