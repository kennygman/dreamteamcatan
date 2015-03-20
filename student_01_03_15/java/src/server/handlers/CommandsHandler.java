/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;

/**
 *
 * @author Drew
 */
public class CommandsHandler  implements HttpHandler {

    @Override
    public void handle(HttpExchange he) throws IOException {
        //check to see if it is a get or post...
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
