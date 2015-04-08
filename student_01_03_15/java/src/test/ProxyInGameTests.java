package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import model.player.Resources;

import org.junit.*;

import shared.locations.*;
import shared.parameters.*;
import shared.response.*;
import client.data.GameInfo;
import client.proxy.Proxy;
import static org.junit.Assert.*;


public class ProxyInGameTests 
{

	private static Proxy proxy = new Proxy();

	@BeforeClass
	public static  void setup() 

	{


			CredentialsParam testPlayer1 = new CredentialsParam("Sam","sam");
			proxy.login(testPlayer1);
			
			proxy.joinGame(new JoinGameParam(0, "red"));



	}
	
	@After
	public void teardown() {}
	
	@Test
	public void test_getGameModel() 
	{
		GameModelResponse r1 = proxy.getGameModel();
		assertEquals(true,r1.isValid());
	}

	@Test
	public void test_saveGame() 
	{
		ListGamesResponse response = proxy.listGames();
		GameInfo game = response.getGameListObject()[response.numberOfGames() - 1];
		
		SaveGameParam t1 = new SaveGameParam(game.getId(),"junit");
		StandardResponse r1 = proxy.saveGame(t1);
		
		assertEquals(true,r1.isValid());
	}

	//@Test
	public void test_loadGame() 
	{
		LoadGameParam t1 = new LoadGameParam("junit");
		StandardResponse r1 = proxy.loadGame(t1);
		
		assertEquals(true,r1.isValid());
	}

	@Test
	public void test_resetGame() 
	{
		GameModelResponse game = proxy.resetGame();
		
		assertEquals(true, game.isValid());
	}

	
	
	public void test_postGameCommands(String commands) 
	{
		PostCommandsParam t1 = new PostCommandsParam(commands);
		GameModelResponse r1 = proxy.postGameCommands(t1);
		
		assertEquals(true,r1.isValid());
	}

	//@Test
	public void test_getGameCommands() 
	{
		CommandResponse r1 = proxy.getGameCommands();
		assertEquals(true, r1.isValid());
		//test_postGameCommands(r1.getCommands());
	}



	@Test
	public void test_listAi() 
	{
		ListAIResponse r1 = proxy.listAi();
		
		assertEquals(true, r1.isValid());
	}

	@Test
	public void test_sendChat() 
	{
		SendChatParam t1 = new SendChatParam(0,"Hey, Wannna go on a date with me? We can play settlers, for reals.... #notcreepyatall");
		GameModelResponse r1 = proxy.sendChat(t1);
		
		assertEquals(true, r1.isValid());
	}

	@Test
	public void test_rollNumber() 
	{
		RollNumParam t1 = new RollNumParam(1,7);
		GameModelResponse r1 = proxy.rollNumber(t1);
		
		assertEquals(true, r1.isValid());
	}

	//@Test
	public void test_robPlayer() 
	{
		RobPlayerParam t1 = new RobPlayerParam(1,0,new HexLocation(0,0));
		GameModelResponse r1 = proxy.robPlayer(t1);
		
		assertEquals(true, r1.isValid());
	}

	@Test
	public void test_finishTurn() 
	{
		FinishTurnParam t1 = new FinishTurnParam(0);
		GameModelResponse r1 = proxy.finishTurn(t1);
		
		assertEquals(true, r1.isValid());
	}

	@Test
	public void test_buyDevCard() 
	{
		BuyDevCardParam t1 = new BuyDevCardParam(0);
		GameModelResponse r1 = proxy.buyDevCard(t1);
		
		assertEquals(true, !r1.isValid());
	}

	@Test
	public void test_playYearOfPlenty()
	{
		PlayYearOfPlentyParam t1 = new PlayYearOfPlentyParam(0, "wood", "ore");
		GameModelResponse r1 = proxy.playYearOfPlenty(t1);
		
		assertEquals(true, !r1.isValid());
	}

	@Test
	public void test_playRoadBuilding() 
	{
		PlayRoadBuildingParam t1 = new PlayRoadBuildingParam(0, new EdgeLocation(0,0,"SW"), 
																new EdgeLocation(0,0,"S"));
		GameModelResponse r1 = proxy.playRoadBuilding(t1);
		
		assertEquals(true, !r1.isValid());
	}

	@Test
	public void test_playSoldier() 
	{
		PlaySoldierParam t1 = new PlaySoldierParam(0, 1, new HexLocation(0,0));
		GameModelResponse r1 = proxy.playSoldier(t1);
		
		assertEquals(true, r1.isValid());
	}

	@Test
	public void test_playMonopoly() 
	{
		PlayMonopolyParam t1 = new PlayMonopolyParam(1, "brick");
		GameModelResponse r1 = proxy.playMonopoly(t1);
		
		assertEquals(true, !r1.isValid());
	}

	@Test
	public void test_playMonument() 
	{
		PlayMonumentParam t1 = new PlayMonumentParam(1);
		GameModelResponse r1 = proxy.playMonument(t1);
		
		assertEquals(true, !r1.isValid());
	}

	@Test
	public void test_buildRoad() 
	{
		BuildRoadParam t1 = new BuildRoadParam(0,new EdgeLocation(0,0,"SW"),true);
		GameModelResponse r1 = proxy.buildRoad(t1);
		
		assertEquals(true, r1.isValid());
	}

	@Test
	public void test_buildSettlement() 
	{
		BuildSettlementParam t1 = new BuildSettlementParam(0, new VertexLocation(0,0,"W"),true);
		GameModelResponse r1 = proxy.buildSettlement(t1);
		
		assertEquals(true, !r1.isValid());
	}

	@Test
	public void test_buildCity() 
	{
		BuildCityParam t1 = new BuildCityParam(0, new VertexLocation(0,0,"E"));
		GameModelResponse r1 = proxy.buildCity(t1);
		
		assertEquals(true, !r1.isValid());
	}

	@Test
	public void test_offerTrade() 
	{
		OfferTradeParam t1 = new OfferTradeParam(0, 1, new Resources(1,0,0,0,-1));
		GameModelResponse r1 = proxy.offerTrade(t1);
		
		assertEquals(true, !r1.isValid());
	}

	@Test
	public void test_acceptTrade() 
	{
	
	    proxy.offerTrade(new OfferTradeParam(0, 1, new Resources(1,0,0,0,-1)));
		AcceptTradeParam t1 = new AcceptTradeParam(0, true);
		GameModelResponse r1 = proxy.acceptTrade(t1);
		
		assertEquals(true, r1.isValid());
	}

	@Test
	public void test_maritimeTrade() 
	{
		MaritimeTradeParam t1 = new MaritimeTradeParam(0, 2, "wood", "brick");
		GameModelResponse r1 = proxy.maritimeTrade(t1);
		
		assertEquals(true, !r1.isValid());
	}

	@Test
	public void test_discardCards() 
	{

		DiscardCardsParam t1 = new DiscardCardsParam(1,new Resources(2,2,2,2,2));

		GameModelResponse r1 = proxy.discardCards(t1);
		
		assertEquals(true, !r1.isValid());
	}

	@Test
	public void test_ChangeLogLevel() 
	{
		ChangeLogLevelParam t1 = new ChangeLogLevelParam("ALL");
		StandardResponse r1 = proxy.ChangeLogLevel(t1);
		
		assertEquals(true, !r1.isValid());
	}
	
}
