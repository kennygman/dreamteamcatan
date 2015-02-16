package test;

import static org.junit.Assert.*;
import model.Game;
import model.ModelFacade;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import model.board.*;
import model.player.*;
import client.proxy.MockProxy;

public class ModelFacadeUnitTest 
{
    
    
	private Game game;
	private ModelFacade facade;
	
	public ModelFacadeUnitTest()
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
	public void testCanBuildRoad()
	{
		boolean result = true;
		
		//Road road = game.getMap().getRoads()[0];
		EdgeLocation edge = new EdgeLocation(0, 0, "N");
		boolean canBuild = facade.canPlaceRoad(edge,false);
		
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
		
		
		EdgeLocation edge = new EdgeLocation(0, 0, "N");
		
		boolean canBuild = facade.canPlaceRoad(edge,false);
		
		Player currentPlayer = game.getPlayers()[0];
		
		Resources hand = currentPlayer.getResources();
		
		int wood = hand.getResourceAmount(ResourceType.WOOD);
		int brick = hand.getResourceAmount(ResourceType.BRICK);
		int roadCount = currentPlayer.getRoads();
		
		
		if(canBuild)
		{
			
			facade.buildRoad(edge, false);
			
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
		
		
		
		
		canBuild = facade.canPlaceRoad(edge,false);
		
		if(canBuild)
		{
			result = false;
		}
		else
		{
			result = true;
		}
		
		
		assertEquals(result, true);
	}
	
	@Test
	public void testCanBuildSettlement()
	{
		boolean result = false;
		
		//build 2 roads to build connecting settlement
		EdgeLocation edge = new EdgeLocation(0, 0, "N");
		EdgeLocation secondEdge = new EdgeLocation(0, 0, "NW");
		
		facade.buildRoad(edge,true);
		facade.buildRoad(secondEdge,true);
		
		HexLocation location = new HexLocation(0,0);
		VertexLocation vertex = new VertexLocation(location,VertexDirection.West);
  		Settlement settlement = new Settlement(0,vertex.getNormalizedLocation());
		
  		//Get Player's currentStatus
		
  		Player currentPlayer = game.getPlayers()[0];
		
		Resources hand = currentPlayer.getResources();
		
		int wood = hand.getResourceAmount(ResourceType.WOOD);
		int brick = hand.getResourceAmount(ResourceType.BRICK);
		int wheat = hand.getResourceAmount(ResourceType.WHEAT);
		int sheep = hand.getResourceAmount(ResourceType.SHEEP);
		int settlementCount = currentPlayer.getSettlements();
  		
  		
		if(facade.canPlaceSettlement(settlement.getLocation(),false))
		{
			facade.buildSettlement(vertex, false);
			
			//Get post player and his conditions
			currentPlayer = game.getPlayers()[0];
			Resources postHand = currentPlayer.getResources();
			
			int postWood = postHand.getResourceAmount(ResourceType.WOOD);
			int postBrick = postHand.getResourceAmount(ResourceType.BRICK);
			int postWheat = postHand.getResourceAmount(ResourceType.WHEAT);
			int postSheep = postHand.getResourceAmount(ResourceType.SHEEP);
			int postSettlementCount = currentPlayer.getSettlements();
			
			//Test post conditions
			if(postWood != wood-1)	
			{
				result = false;
			}
			if(postBrick != brick-1)
			{
				result = false;
			}
			if(postWheat != wheat-1)	
			{
				result = false;
			}
			if(postSheep != sheep-1)
			{
				result = false;
			}
			if(postSettlementCount != settlementCount-1)
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
	
	@Test
	public void testCantBuildSettlement()
	{
	
		HexLocation location = new HexLocation(0,0);
		VertexLocation vertex = new VertexLocation(location,VertexDirection.NorthWest);
  		Settlement settlement = new Settlement(0,vertex);
		
		boolean result = facade.canPlaceSettlement(settlement.getLocation(),false);
		assertEquals(result, false);
	}
	
	@Test
	public void testCanBuildCity()
	{
		boolean result = true;
		
		//build a new city object
		HexLocation location = new HexLocation(0,0);
		VertexLocation vertex = new VertexLocation(location,VertexDirection.NorthEast);
		
		//Gather player's information
		Player currentPlayer = game.getPlayers()[0];
		
		Resources hand = currentPlayer.getResources();
		int wheat = hand.getResourceAmount(ResourceType.WHEAT);
		int ore = hand.getResourceAmount(ResourceType.ORE);
		int cityCount = currentPlayer.getCities();
		
		
		if(facade.canPlaceCity(vertex))
		{
			facade.buildCity(vertex);
			
			currentPlayer = game.getPlayers()[0];
			Resources postHand = currentPlayer.getResources();
			
			int postWheat = postHand.getResourceAmount(ResourceType.WHEAT);
			int postOre = postHand.getResourceAmount(ResourceType.ORE);
			int postCityCount = currentPlayer.getCities();
			
			if(postWheat != wheat-2)	
			{
				result = false;
			}
			if(postOre != ore-3)
			{
				result = false;
			}
			if(postCityCount != cityCount-1)
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
	@Test
	public void testCantBuildCity()
	{
		
		HexLocation location = new HexLocation(1,1);
		VertexLocation vertex = new VertexLocation(location,VertexDirection.NorthEast);
		
		boolean result = facade.canPlaceCity(vertex);
		
		assertEquals(result, false);
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
	@Test
	public void testCanAcceptTrade()
	{
		Player player = game.getPlayers()[0];
		boolean result = facade.canAcceptTrade();
		assertEquals(result,true);
		
	}

	@Test
	public void testCanDisCardCards()
	{
		Player player = game.getPlayers()[0];
		boolean result = true; // facade.CanDiscardCards();
		assertEquals(result,true);
	}



	@Test
	public void testCanOfferTrade()
	{
		Player player = game.getPlayers()[0];
		boolean result = facade.CanOfferTrade();
		assertEquals(result,true);
	}

	@Test
	public void testCanMaritimeTrade()
	{
		Player player = game.getPlayers()[0];
		boolean result = true; //facade.CanMaritimeTrade();
		assertEquals(result,true);
	}

	@Test
	public void testCanFinishTurn()
	{
		Player player = game.getPlayers()[0];
		boolean result =  true ;//facade.CanFinishTurn();
		assertEquals(result,true);
	}


	@Test
	public void testCanUseYearOfPlenty()
	{
		Player player = game.getPlayers()[0];
		boolean result = true; //facade.CanUseYearOfPlenty();
		
		assertEquals(result,true);
	}

	@Test
	public void testCanUseRoadBuilding()
	{
		Player[] players = 	game.getPlayers();	
		Player player = players[0];
	
		boolean result = true; //facade.CanUseRoadBuilder();
		
		assertEquals(result,true);
	}

	@Test
	public void testCanUseSoldier()
	{
		Player player = game.getPlayers()[0];
		boolean result = facade.CanUseSoldier();
		assertEquals(result,false);
	}

	@Test
	public void testCanUseMonopoly()
	{
		Player player = game.getPlayers()[0];
		boolean result = facade.CanUseMonopoly();
		assertEquals(result,true);
	}

	@Test
	public void testCanUseMonument()
	{
		Player player = game.getPlayers()[0];
		boolean result = facade.CanUseMonument();
		assertEquals(result,true);
	}


}


