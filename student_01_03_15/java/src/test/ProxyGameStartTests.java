package test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import client.proxy.Proxy;
import shared.parameters.AddAiParam;
import shared.parameters.CreateGameParam;
import shared.parameters.CredentialsParam;
import shared.parameters.JoinGameParam;
import shared.response.CreateGameResponse;
import shared.response.GameListObject;
import shared.response.ListGamesResponse;
import shared.response.StandardResponse;

public class ProxyGameStartTests 
{
	private  Proxy proxy = new Proxy();
	
	@Before
	public  void setup() 
	{
		
		
	}
	
	@Test
	public void test_listGames() 
	{
		proxy.login(new CredentialsParam("test1","test1"));
		ListGamesResponse response = proxy.listGames();
		

		assertEquals(true, (response.numberOfGames() != 0));
	}
	@Test
	public void test_createGame()
	{
		proxy.login(new CredentialsParam("test1","test1"));
		CreateGameParam t1 = new CreateGameParam("t1", true, true, true);
		CreateGameParam t2 = new CreateGameParam("t2", false, false, false);
		//CreateGameParam t3 = new CreateGameParam("t3", true, false, true);
		
		CreateGameResponse r1 = proxy.createGame(t1);
		CreateGameResponse r2 = proxy.createGame(t2);
		//CreateGameResponse r3 = proxy.createGame(t3);
		
		assertEquals("t1",r1.getTitle());
		assertEquals("t2",r2.getTitle());
		//assertEquals("t3",r3.getTitle());
		
	}
	
	@Test
	public void test_joinGame()
	{
		CredentialsParam p1 = new CredentialsParam("test1","test1");
		CredentialsParam p2 = new CredentialsParam("test2","test2");
		CredentialsParam p3 = new CredentialsParam("test3","test3");
		CredentialsParam p4 = new CredentialsParam("test4","test4");
		
		ListGamesResponse response = proxy.listGames();
		GameListObject game = response.getGameListObject(response.numberOfGames()-1);
		
		proxy.login(p1);
		JoinGameParam t1 = new JoinGameParam(game.getGameId(), "red");
		StandardResponse r1 = proxy.joinGame(t1);
		
		proxy.login(p2);
		JoinGameParam t2 = new JoinGameParam(game.getGameId(), "orange");
		StandardResponse r2 = proxy.joinGame(t2);
		
		proxy.login(p3);
		JoinGameParam t3 = new JoinGameParam(game.getGameId(), "blue");
		StandardResponse r3 = proxy.joinGame(t3);
		
		proxy.login(p4);
		JoinGameParam t4 = new JoinGameParam(game.getGameId(), "white");
		StandardResponse r4 = proxy.joinGame(t4);
		
		
		assertEquals(true,r1.isValid());
		assertEquals(true,r2.isValid());
		assertEquals(true,r3.isValid());
		assertEquals(true,r4.isValid());
	}

	@Test
	public void test_addAi() 
	{
		CredentialsParam testPlayer5 = new CredentialsParam("Doris","Doris");
		proxy.register(testPlayer5);
		proxy.login(testPlayer5);
		
		proxy.createGame(new CreateGameParam("test2", true, true, true));
		
		ListGamesResponse response = proxy.listGames();
		GameListObject game = response.getGameListObject(response.numberOfGames()-1);
		
		proxy.joinGame(new JoinGameParam(game.getGameId(), "blue"));
		
				
		AddAiParam t1 = new AddAiParam("LARGEST_ARMY");
		StandardResponse r1 = proxy.addAi(t1);
		
		assertEquals(true, r1.isValid());
	}
	
}
