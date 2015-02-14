package test;

import static org.junit.Assert.*;
import model.Game;
import model.ModelFacade;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;
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
	
	
	//This is testing the can do and the post conditions
	@Test
	public void testCanBuildRoad()
	{
		boolean result = true;
		
		//Road road = game.getMap().getRoads()[0];
		EdgeLocation edge = new EdgeLocation(0, 0, "N");
		
		boolean canBuild = facade.canPlaceRoad(edge);
		
		Player currentPlayer = game.getPlayers()[0];
		
		Resources hand = currentPlayer.getResources();
		
		int wood = hand.getResourceAmount(ResourceType.WOOD);
		int brick = hand.getResourceAmount(ResourceType.BRICK);
		int roadCount = currentPlayer.getRoads();
		
		
		if(canBuild)
		{
			boolean free = false;
			
			
			facade.buildRoad(edge, free);
			
			currentPlayer = game.getPlayers()[0];
			Resources postHand = currentPlayer.getResources();
			
			int postWood = postHand.getResourceAmount(ResourceType.WOOD);
			int postBrick = postHand.getResourceAmount(ResourceType.BRICK);
			int postRoadCount = currentPlayer.getRoads();
			
			if(postWood != wood-1)	
			{
				result = false;
			}
			if(postBrick != brick-1)
			{
				result = false;
			}
			if(postRoadCount != roadCount-1)
			{
				result = false;
			}
			
			
		}
		else
		{
			result = false;
		}
		
		assertEquals(result, true);
	}
	
	
	//This is testing to make sure that the Can Build road won't let me build when I shouldn't
	@Test
	public void testCanBuildRoad2()
	{
		boolean result = true;
		
		//Road road = game.getMap().getRoads()[0];
		EdgeLocation edge = new EdgeLocation(1, 1, "N");
		
		boolean canBuild = facade.canPlaceRoad(edge);
		
		
		if(!canBuild)
		{
			result = true;
		}
		else
		{
			result = false;
		}
		
		assertEquals(result, true);
	}
	/*
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


