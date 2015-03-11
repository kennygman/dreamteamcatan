package server;

import model.ModelFacade;
import java.util.*;
import server.database.Database;
import server.database.DatabaseException;
import shared.parameters.AcceptTradeParam;
import shared.parameters.AddAiParam;
import shared.parameters.BuildCityParam;
import shared.parameters.BuildRoadParam;
import shared.parameters.BuildSettlementParam;
import shared.parameters.BuyDevCardParam;
import shared.parameters.CreateGameParam;
import shared.parameters.CredentialsParam;
import shared.parameters.DiscardCardsParam;
import shared.parameters.FinishTurnParam;
import shared.parameters.JoinGameParam;
import shared.parameters.MaritimeTradeParam;
import shared.parameters.OfferTradeParam;
import shared.parameters.PlayMonopolyParam;
import shared.parameters.PlayMonumentParam;
import shared.parameters.PlayRoadBuildingParam;
import shared.parameters.PlaySoldierParam;
import shared.parameters.PlayYearOfPlentyParam;
import shared.parameters.RobPlayerParam;
import shared.parameters.RollNumParam;
import shared.parameters.SendChatParam;


public class ServerFacade implements IServerFacade
{

	public static void initialize() throws ServerException 
	{		
		try 
		{
			Database.initialize();	
		}
		
		catch (DatabaseException e) 	
		{
			throw new ServerException(e.getMessage(), e);
		}		
	}
	@Override
	public void sendChat(SendChatParam param)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void acceptTrade(AcceptTradeParam param)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void discardCards(DiscardCardsParam param)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rollNumber(RollNumParam param)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildRoad(BuildRoadParam param)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildSettlement(BuildSettlementParam param)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buildCity(BuildCityParam param)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void offerTrade(OfferTradeParam param)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void maritimeTrade(MaritimeTradeParam param)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void robPlayer(RobPlayerParam param)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void finishTurn(FinishTurnParam param)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buyDevCard(BuyDevCardParam param)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playSoldierCard(PlaySoldierParam param)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playYearOfPlentyCard(PlayYearOfPlentyParam param)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playRoadCard(PlayRoadBuildingParam param)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playMonopolyCard(PlayMonopolyParam param)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playMonumentCard(PlayMonumentParam param)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void login(CredentialsParam param)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void register(CredentialsParam param)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void join(JoinGameParam param)
	{
		JoinGame cmd = new JoinGame(param);
		// if canJoinGame();
		cmd.execute();
	}

	@Override
	public void create(CreateGameParam param)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addAI(AddAiParam param)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void listGames()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void listAI()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getGameModel()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void load()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetGame()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getCommands()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void commands()
	{
		// TODO Auto-generated method stub
		
	}

}
