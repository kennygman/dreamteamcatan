package client.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

import com.google.gson.Gson;

import shared.Translator;
import shared.exceptions.ProxyException;
import shared.parameters.*;
import shared.response.*;

public class Proxy implements IProxy
{

	public Proxy()
	{
		translator = new Translator();
		cookie = null;
		gameId = null;
	}
	
	private Translator translator;
	private String cookie;
	private String gameId;
	
	
	

	/**
	 * Will attempt to send the Post Request to the Server.
	 */
	private String doPost(String path, String info)
	{		
		StringBuffer stringBuffer = new StringBuffer();	
		
		try
		{
			//String UrlString = "http://" + host + ":" + PortNumber + path;  
			String UrlString = "http://localhost:8081" + path;
			URL url = new URL(UrlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			setHeader(path,connection);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			writer.write(info);
			writer.close();
			
			
		
			int responseCode = connection.getResponseCode();
			getHeader(path,connection,responseCode);
		
			InputStreamReader reader = new InputStreamReader(connection.getInputStream());
			BufferedReader buffRead = new BufferedReader(reader);
			
			String inputLine;
			
			while((inputLine = buffRead.readLine()) != null)
			{
				stringBuffer.append(inputLine);
			}
			
			connection.getInputStream().close();
			connection.disconnect();
			
			if(responseCode != HttpURLConnection.HTTP_OK) 
			{
				if(responseCode == HttpURLConnection.HTTP_BAD_REQUEST)
				{
					throw new ProxyException(connection.getResponseMessage()); //Error in the request body
				}
				else if (responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR)
				{
					throw new ProxyException(connection.getResponseMessage()); //Error in the server
				}
				else 
				{
					throw new ProxyException(connection.getResponseMessage());
				}
			}
			
			
			
			
		}
		catch(ProxyException | IOException e)
		{
			System.err.print(e.getMessage());
		}
		
		return stringBuffer.toString();

	}
	
	/**
	 * Will attempt to send the Get request to the server.
	 */
	private String doGet(String path)
	{
		StringBuffer stringBuffer = new StringBuffer();	
		
		try
		{
			//String UrlString = "http://" + host + ":" + PortNumber + path;  
			String UrlString = "http://localhost:8081" + path;
			URL url = new URL(UrlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestMethod("GET");
			setHeader(path, connection);
			connection.getOutputStream().close();
			
			
							
			int responseCode = connection.getResponseCode();

			
			InputStreamReader reader = new InputStreamReader(connection.getInputStream());
			BufferedReader buffRead = new BufferedReader(reader);
			
			String inputLine;
			
			while((inputLine = buffRead.readLine()) != null)
			{
				stringBuffer.append(inputLine);
			}
			
			connection.getInputStream().close();
			connection.disconnect();
			
			if(responseCode != HttpURLConnection.HTTP_OK) 
			{
				if(responseCode == HttpURLConnection.HTTP_BAD_REQUEST)
				{
					throw new ProxyException(connection.getResponseMessage()); //Error in the request body
				}
				else if (responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR)
				{
					throw new ProxyException(connection.getResponseMessage()); //Error in the server
				}
				else 
				{
					throw new ProxyException(connection.getResponseMessage());
				}
			}
			
			
			
			
		}
		catch(ProxyException | IOException e)
		{
			System.err.println(e.getMessage());
		}
		
		return stringBuffer.toString();

	}
	
	
	
	private void setHeader(String path, HttpURLConnection connection)
	{
		if(!path.equals("/user/login") && !path.equals("/user/register"))
		{
			if(path.equals("/games/join") || path.equals("/games/list") || path.equals("/games/create") || path.equals("/games/save") || path.equals("/games/load"))
			{
				connection.setRequestProperty("Cookie", "catan.user=" + cookie);
			}
			else
			{
				connection.setRequestProperty("Cookie", "catan.user=" + cookie + "; catan.game=" + gameId);
			}
		}
	}
	
	private void getHeader(String path, HttpURLConnection connection,int responseCode)
	{
		if(path.equals("/user/login") || path.equals("/user/register"))
		{
			StringBuilder cookieBuilder = new StringBuilder(connection.getHeaderField("Set-cookie"));
			cookieBuilder = cookieBuilder.delete(0, cookieBuilder.indexOf("=") + 1);
			cookieBuilder = cookieBuilder.delete(cookieBuilder.indexOf(";"), cookieBuilder.length());
			
			cookie = cookieBuilder.toString();
		}
		else if(path.equals("/games/join"))
		{
			if(responseCode == 200)
			{
				StringBuilder gameIdBuilder = new StringBuilder(connection.getHeaderField("Set-cookie"));
				gameIdBuilder = gameIdBuilder.delete(0, gameIdBuilder.indexOf("=") + 1);
				gameIdBuilder = gameIdBuilder.delete(gameIdBuilder.indexOf(";"), gameIdBuilder.length());
			
				gameId = gameIdBuilder.toString();
			}
		}
	}
	

	@Override
	public StandardResponse login(CredentialsParam input) 
	{
		String result = doPost("/user/login", translator.convert(input));
		return translator.translateStandard(result);
	}

	@Override
	public StandardResponse register(CredentialsParam input) 
	{
		String result = doPost("/user/register", translator.convert(input));
		
		return translator.translateStandard(result);
	}

	@Override
	public ListGamesResponse listGames() 
	{
		return translator.translateListGames(doGet("/games/list"));
	}

	@Override
	public CreateGameResponse createGame(CreateGameParam input) 
	{
		String result = doPost("/games/create", translator.convert(input));
		return translator.translateCreateGame(result);
	}

	@Override
	public StandardResponse joinGame(JoinGameParam input) 
	{
		String result = doPost("/games/join", translator.convert(input));
		return translator.translateStandard(result);
	}

	@Override
	public StandardResponse saveGame(SaveGameParam input) 
	{
		String result = doPost("/games/save", translator.convert(input));
		return translator.translateStandard(result);
	}

	@Override
	public StandardResponse loadGame(LoadGameParam input) 
	{
		String result = doPost("/games/load", translator.convert(input));
		return translator.translateStandard(result);
	}

	@Override
	public GameModelResponse getGameModel() 
	{
		String result = doGet("/game/model");
		return translator.translateGetGameModel(result);
	}

	@Override
	public GameModelResponse resetGame() 
	{

		String result = doPost("/game/reset", "");
		return translator.translateGetGameModel(result);		
	
	}

	@Override
	public GameModelResponse postGameCommands(PostCommandsParam input) 
	{
		String result = doPost("/game/commands", input.getCommands());
		return translator.translateGetGameModel(result);
	}
	
	@Override
	public CommandResponse getGameCommands() 
	{
		return translator.translateCommands(doGet("/game/commands"));
	}

	@Override
	public StandardResponse addAi(AddAiParam input) 
	{
		String result = doPost("/game/addAI", translator.convert(input));
		return translator.translateStandard(result);
	}

	@Override
	public ListAIResponse listAi() 
	{
		return translator.translateListAi(doGet("/game/listAI"));
	}

	@Override
	public GameModelResponse sendChat(SendChatParam input) 
	{
		String result = doPost("/moves/sendChat", translator.convert(input));
		return translator.translateGetGameModel(result);				
	}

	@Override
	public GameModelResponse rollNumber(RollNumParam input) 
	{
		String result = doPost("/moves/rollNumber", translator.convert(input));
		return translator.translateGetGameModel(result);	
		
	}

	@Override
	public GameModelResponse robPlayer(RobPlayerParam input) 
	{
		String result = doPost("/moves/robPlayer", translator.convert(input));
		return translator.translateGetGameModel(result);	
		
	}

	@Override
	public GameModelResponse finishTurn(FinishTurnParam input) 
	{
		String result = doPost("/moves/finishTurn", translator.convert(input));
		return translator.translateGetGameModel(result);	
		
	}

	@Override
	public GameModelResponse buyDevCard(BuyDevCardParam input) 
	{
		String result = doPost("/moves/buyDevCard", translator.convert(input));
		return translator.translateGetGameModel(result);	
		
	}

	@Override
	public GameModelResponse playYearOfPlenty(PlayYearOfPlentyParam input) 
	{
		String result = doPost("/moves/Year_of_Plenty", translator.convert(input));
		return translator.translateGetGameModel(result);	
		
	}

	@Override
	public GameModelResponse playRoadBuilding(PlayRoadBuildingParam input) 
	{
		String result = doPost("/moves/Road_Building", translator.convert(input));
		return translator.translateGetGameModel(result);	
		
	}

	@Override
	public GameModelResponse playSoldier(PlaySoldierParam input) 
	{
		String result = doPost("/moves/Soldier", translator.convert(input));
		return translator.translateGetGameModel(result);	
		
	}

	@Override
	public GameModelResponse playMonopoly(PlayMonopolyParam input) 
	{
		String result = doPost("/moves/Monopoly", translator.convert(input));
		return translator.translateGetGameModel(result);	
		
	}

	@Override
	public GameModelResponse playMonument(PlayMonumentParam input) 
	{
		String result = doPost("/moves/Monument", translator.convert(input));
		return translator.translateGetGameModel(result);	
		
	}

	@Override
	public GameModelResponse buildRoad(BuildRoadParam input) 
	{
		String result = doPost("/moves/buildRoad", translator.convert(input));
		return translator.translateGetGameModel(result);	
		
	}

	@Override
	public GameModelResponse buildSettlement(BuildSettlementParam input) 
	{
		String result = doPost("/moves/buildSettlement", translator.convert(input));
		return translator.translateGetGameModel(result);	
		
	}

	@Override
	public GameModelResponse buildCity(BuildCityParam input) 
	{
		String result = doPost("/moves/buildCity", translator.convert(input));
		return translator.translateGetGameModel(result);	
		
	}

	@Override
	public GameModelResponse offerTrade(OfferTradeParam input) 
	{
		String result = doPost("/moves/offerTrade", translator.convert(input));
		return translator.translateGetGameModel(result);	
		
	}

	@Override
	public GameModelResponse acceptTrade(AcceptTradeParam input) 
	{
		String result = doPost("/moves/acceptTrade", translator.convert(input));
		return translator.translateGetGameModel(result);	
		
	}

	@Override
	public GameModelResponse maritimeTrade(MaritimeTradeParam input) 
	{
		String result = doPost("/moves/maritimeTrade", translator.convert(input));
		return translator.translateGetGameModel(result);	
		
	}

	@Override
	public GameModelResponse discardCards(DiscardCardsParam input) 
	{
		String result = doPost("/moves/discardCards", translator.convert(input));
		return translator.translateGetGameModel(result);	
		
	}

	@Override
	public StandardResponse ChangeLogLevel(ChangeLogLevelParam input) 
	{
		String result = doPost("/util/changeLogLevel", translator.convert(input));
		return translator.translateStandard(result);	
		
	}
}