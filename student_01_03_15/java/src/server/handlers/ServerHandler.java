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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import server.facade.ServerFacade;
import shared.PreGameCookie;
import shared.parameters.CredentialsParam;
import shared.response.LoginResponse;

public class ServerHandler
{
	Gson g = new Gson();

	public String read(InputStream input) throws IOException
	{
		StringBuilder stringBuffer = new StringBuilder();
		InputStreamReader reader = new InputStreamReader(input);
		BufferedReader buffRead = new BufferedReader(reader);

		String inputLine;
		while ((inputLine = buffRead.readLine()) != null)
		{
			stringBuffer.append(inputLine);
		}

		input.close();
		return stringBuffer.toString();
	}

	public void write(OutputStream output, String responseBody)
			throws IOException
	{
		OutputStreamWriter writer = new OutputStreamWriter(output);
		writer.write(responseBody);
		writer.close();
	}

	public LoginResponse getLoginFromCookie(String cookie)
	{
		String formattedCookie = cookie;
		if (!formattedCookie.startsWith("{")) formattedCookie = formattedCookie.substring(11); // remove "catan.user="

		
		String userInfo = null;
		CredentialsParam loginParam = null;
		try
		{
			userInfo = URLDecoder.decode(formattedCookie, "UTF-8");
		} catch (UnsupportedEncodingException e1)
		{
			e1.getLocalizedMessage();
			e1.printStackTrace();
		}

		if (!userInfo.endsWith("}")) userInfo = userInfo.substring(0, userInfo.length()-14); // remove "; catan.game=#"

		try
		{
			PreGameCookie param = g.fromJson(userInfo, PreGameCookie.class);
			loginParam = new CredentialsParam(param.getName(),
					param.getPassword());
		} catch (Exception e)
		{
			System.err.println(e.getMessage());
		}

		return ServerFacade.login(loginParam);
	}

	public int getGameIdFromCookie(String cookie)
	{
		String formattedCookie = cookie;
		if (!formattedCookie.startsWith("{"))formattedCookie = formattedCookie.substring(11); // remove "catan.user="

		String userInfo = null;

		try
		{
			userInfo = URLDecoder.decode(formattedCookie, "UTF-8");
		} catch (UnsupportedEncodingException e1)
		{
			e1.printStackTrace();
		}
		String game = userInfo.substring(userInfo.indexOf("=") + 1);

		return Integer.parseInt(game);
	}
}
