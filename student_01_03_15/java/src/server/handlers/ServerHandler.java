/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.handlers;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLDecoder;
import server.facade.ServerFacade;
import shared.parameters.CredentialsParam;
import shared.response.LoginResponse;


public class ServerHandler {
    Gson g = new Gson();
    
    public String read(InputStream input) throws IOException
    {
        StringBuilder stringBuffer = new StringBuilder();
        InputStreamReader reader = new InputStreamReader(input);
        BufferedReader buffRead = new BufferedReader(reader);

        String inputLine;
        while((inputLine = buffRead.readLine()) != null)
        {
            stringBuffer.append(inputLine);
        }

        input.close();
        return stringBuffer.toString();
    }
    
    public void write(OutputStream output, String responseBody) throws IOException
    {
        OutputStreamWriter writer = new OutputStreamWriter(output);
        writer.write(responseBody);
        writer.close();
    }
    
    public LoginResponse getLoginFromCookie(String cookie)
    {
        String userInfo = URLDecoder.decode(cookie);
        CredentialsParam loginParam = g.fromJson(userInfo, CredentialsParam.class);
        return ServerFacade.login(loginParam);
    }
    
    public int getGameIdFromCookie(String cookie)
    {
        return -1;
    }
}
