package test;

import static org.junit.Assert.*;
import model.Game;
import model.ModelFacade;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import client.proxy.MockProxy;

public class BoardTest
{
	private Game game;
	private ModelFacade facade;
	
	public BoardTest()
	{
		//facade = new ModelFacade(new MockProxy());
		//game = facade.getGame();
	}
	@Before
	public void setup()
	{
		facade = new ModelFacade(new MockProxy());
		game = facade.getGame();
	}
	
	@After
	public void teardown() {}
	
	
	//This is testing the can do and the post conditions
	@Test
	public void testBoardNotNull()
	{
		assertEquals(game.getBoard()!=null, true);
	}

}
