package test;

import static org.junit.Assert.*;
import model.Game;
import model.ModelFacade;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.board.*;
import model.player.*;
import client.proxy.MockProxy;

public class ModelFacadeUnitTest {
    
    
	private Game game;
	private ModelFacade facade;
	
	public ModelFacadeUnitTest()
	{
		facade = new ModelFacade(new MockProxy());
		game = facade.getGame();
	}
	@Before
	public void setup()
	{
	    
	}
	
	@After
	public void teardown() {}
	
	
	/*@Test
	public void testCanPlaceRoad()
	{
		Road road = game.getMap().getRoads()[0];
		boolean result = facade.canPlaceRoad(road.getEdgeLocation());
		assertEquals(result, false);
	}
	
	@Test
	public void testCanPlaceSettlement()
	{
		Settlement settlement = game.getMap().getSettlements()[0];
		boolean result = facade.canPlaceSettlement(settlement.getLocation());
		assertEquals(result, false);
	}
	
	@Test
	public void testCanPlaceCity()
	{
		Settlement settlement = game.getMap().getSettlements()[0];
		boolean result = facade.canPlaceCity(settlement.getLocation());
		assertEquals(result, true);
	}*/
	
	@Test
	public void testCanAcceptTrade()
	{
		Player player = game.getPlayers()[0];
		boolean result = facade.canAcceptTrade(player);
		assertEquals(result,true);
		
	}

	@Test
	public void testCanDisCardCards()
	{
		Player player = game.getPlayers()[0];
		boolean result = facade.CanDiscardCards(player);
		assertEquals(result,true);
	}

	@Test
	public void testCanRollNumber()
	{
		boolean result = facade.CanRollNumber(5);
		assertEquals(result,true);
	}

	@Test
	public void testCanNOTRollNumber()
	{
		boolean result = facade.CanRollNumber(1);
		assertEquals(result,false);
	}

	/*@Test
	public void testCanBuildRoad()
	{
		Player player = game.getPlayers()[0];
		boolean result = facade.CanBuildRoad(player);
		assertEquals(result,true);
	}

	@Test
	public void testCanBuildSettlement()
	{
		Player player = game.getPlayers()[0];
		boolean result = facade.CanBuildRoad(player);
		assertEquals(result,true);
	}

	@Test
	public void testCanBuildCity()
	{
		Player player = game.getPlayers()[0];
		boolean result = facade.CanBuildRoad(player);
		assertEquals(result,true);
	}*/

	@Test
	public void testCanOfferTrade()
	{
		Player player = game.getPlayers()[0];
		boolean result = facade.CanOfferTrade(player);
		assertEquals(result,true);
	}

	@Test
	public void testCanMaritimeTrade()
	{
		Player player = game.getPlayers()[0];
		boolean result = facade.CanMaritimeTrade(player);
		assertEquals(result,true);
	}

	@Test
	public void testCanFinishTurn()
	{
		Player player = game.getPlayers()[0];
		boolean result = facade.CanFinishTurn(player);
		assertEquals(result,true);
	}


	@Test
	public void testCanUseYearOfPlenty()
	{
		Player player = game.getPlayers()[0];
		boolean result = facade.CanUseYearOfPlenty(player);
		
		assertEquals(result,true);
	}

	@Test
	public void testCanUseRoadBuilding()
	{
		Player[] players = 	game.getPlayers();	
		Player player = players[0];
	
		boolean result = facade.CanUseRoadBuilder(player);
		assertEquals(result,true);
	}

	@Test
	public void testCanUseSoldier()
	{
		Player player = game.getPlayers()[0];
		boolean result = facade.CanUseSoldier(player);
		assertEquals(result,false);
	}

	@Test
	public void testCanUseMonopoly()
	{
		Player player = game.getPlayers()[0];
		boolean result = facade.CanUseMonopoly(player);
		assertEquals(result,true);
	}

	@Test
	public void testCanUseMonument()
	{
		Player player = game.getPlayers()[0];
		boolean result = facade.CanUseMonument(player);
		assertEquals(result,true);
	}

}


