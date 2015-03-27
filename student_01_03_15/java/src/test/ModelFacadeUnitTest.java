package test;

import static org.junit.Assert.*;
import model.Game;
import model.ModelFacade;
import model.TradeOffer;
import model.TurnTracker;

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
	
	public ModelFacadeUnitTest()
	{
		//ModelFacade.getInstance() = new ModelFacade(new MockProxy());
		//game = ModelFacade.getInstance().getGame();
	}
	@Before
	public void setup()
	{
		ModelFacade.createInstance(new MockProxy());
		game = ModelFacade.getInstance().getGame();
	}
	
	@After
	public void teardown() {}
	
	
	//This is testing the can do and the post conditions
	@Test
	public void testCanBuildRoad()
	{
		boolean result = true;
		game.getTurnTracker().setStatus("Playing");
		
		game.getBoard().setRoad(new Road(0, new EdgeLocation(0,0,"NW")));
		EdgeLocation edge = new EdgeLocation(0, 0, "N");
		game.getTurnTracker().setStatus("Playing");
		boolean canBuild = ModelFacade.getInstance().canPlaceRoad(edge,false);
		
		Player currentPlayer = game.getPlayers()[0];
		Resources hand = currentPlayer.getResources();
		
		int wood = hand.getResourceAmount(ResourceType.WOOD);
		int brick = hand.getResourceAmount(ResourceType.BRICK);
		int roadCount = currentPlayer.getRoads();
		
		if(canBuild)
		{
			boolean free = false;
			game.getTurnTracker().setStatus("Playing");
			
			ModelFacade.getInstance().buildRoad(edge, free);
			
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
			if(postRoadCount != (roadCount-1))
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
		game.getTurnTracker().setStatus("Playing");
		boolean result = true;
		
		
		EdgeLocation edge = new EdgeLocation(0, 0, "N");
		
		boolean canBuild = ModelFacade.getInstance().canPlaceRoad(edge,false);
		
		Player currentPlayer = game.getPlayers()[0];
		
		Resources hand = currentPlayer.getResources();

		int wood = hand.getResourceAmount(ResourceType.WOOD);
		int brick = hand.getResourceAmount(ResourceType.BRICK);
		int roadCount = currentPlayer.getRoads();
		
		
		if(canBuild)
		{
			
			ModelFacade.getInstance().buildRoad(edge, false);
			
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
		
		canBuild = ModelFacade.getInstance().canPlaceRoad(edge,false);
		
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
		boolean result = true;
		game.getTurnTracker().setStatus("Playing");
		
		//build 2 roads to build connecting settlement
		EdgeLocation edge = new EdgeLocation(0, 0, "N");
		EdgeLocation secondEdge = new EdgeLocation(0, 0, "NW");
		
		ModelFacade.getInstance().buildRoad(edge,true);
		ModelFacade.getInstance().buildRoad(secondEdge,true);
		
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
  		

		if(ModelFacade.getInstance().canPlaceSettlement(settlement.getLocation(),false))
		{
			ModelFacade.getInstance().buildSettlement(vertex, false);
			
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
				System.out.println("wood is wrong");
				result = false;
			}
			if(postBrick != brick-1)
			{
				System.out.println("brick is wrong");
				result = false;
			}
			if(postWheat != wheat-1)	
			{
				System.out.println("wheat is wrong");
				result = false;
			}
			if(postSheep != sheep-1)
			{
				System.out.println("sheep is wrong");
				result = false;
			}
			if(postSettlementCount != settlementCount-1)
			{
				System.out.println("settlement is wrong");
				result = false;
			}
		}
		else
		{
                        System.out.println("Can't place settlement");
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
		
		boolean result = ModelFacade.getInstance().canPlaceSettlement(settlement.getLocation(),false);
		
		assertEquals(result, false);
	}
	
	@Test
	public void testCanBuildCity()
	{
		boolean result = true;
		game.getTurnTracker().setStatus("Playing");
		
		//build a new city object
		HexLocation location = new HexLocation(0,0);
		VertexLocation vertex = new VertexLocation(location,VertexDirection.NorthEast);
		
		//Gather player's information
		Player currentPlayer = game.getPlayers()[0];
		
		Resources hand = currentPlayer.getResources();
		int wheat = hand.getResourceAmount(ResourceType.WHEAT);
		int ore = hand.getResourceAmount(ResourceType.ORE);
		int cityCount = currentPlayer.getCities();
		//System.out.println("city count is " + cityCount);
		
		if(ModelFacade.getInstance().canPlaceCity(vertex))
		{
			
			ModelFacade.getInstance().buildCity(vertex);
			
			currentPlayer = game.getPlayers()[0];
			Resources postHand = currentPlayer.getResources();
			
			int postWheat = postHand.getResourceAmount(ResourceType.WHEAT);
			int postOre = postHand.getResourceAmount(ResourceType.ORE);
			int postCityCount = currentPlayer.getCities();
			
			
			
			if(postWheat != wheat-2)	
			{
				//System.out.println("wheat is wrong");
				result = false;
			}
			if(postOre != ore-3)
			{
				//System.out.println("ore is wrong");
				result = false;
			}
			if(postCityCount != cityCount-1)
			{
				//System.out.println("city is wrong");
				result = false;
			}
		
		}
		else
		{
			//System.out.println("it can't build city");
			result = false;
		}
	
		
		assertEquals(result, true);
	}
	@Test
	public void testCantBuildCity()
	{
		game.getTurnTracker().setStatus("Playing");
		HexLocation location = new HexLocation(1,1);
		VertexLocation vertex = new VertexLocation(location,VertexDirection.NorthEast);
		
		boolean result = ModelFacade.getInstance().canPlaceCity(vertex);
		
		assertEquals(result, false);
	}
	
	@Test
	public void testCanRollNumber()
	{
		game.getTurnTracker().setStatus("Rolling");
		
		boolean result = ModelFacade.getInstance().CanRollNumber();
	
		
		assertEquals(result,true);
	}

	@Test
	public void testCanNOTRollNumber()
	{
		game.getTurnTracker().setStatus("Playing");
		boolean result = ModelFacade.getInstance().CanRollNumber();
		assertEquals(result,false);
	}

	@Test
	public void testCanOfferTrade()
	{
		game.getTurnTracker().setStatus("Playing");
		boolean result = true;
		
		//Player's pre conditions
		Resources resources = new Resources(1,-1,0,0,0);

		if(ModelFacade.getInstance().CanOfferTrade(resources))
		{	
			ModelFacade.getInstance().offerTrade(1, resources);
			
			//Player's post conditions
			
			// Test if TradeOffer is in server Model:
			if (game.getTurnTracker() == null)
			{
				result = false;
			}
			
		}
		else
		{
			result = false;
		}
		
		assertEquals(result,true);
	}

	
	@Test
	public void testCanAcceptTrade()
	{
		game.getTurnTracker().setStatus("Waiting");
		boolean result = true;
		TradeOffer offer = new TradeOffer(0,1,new Resources(1,-1,0,0,0));
		game.setTradeOffer(offer);
		if(!ModelFacade.getInstance().canAcceptTrade())
		{
			result = false;
		}
		
		assertEquals(result,true);
		
	}

	@Test
	public void testCanDisCardCards()
	{
		game.getTurnTracker().setStatus("Discarding");
		boolean result = true;
		
		Player player = game.getPlayer(ModelFacade.getInstance().getPlayerInfo().getPlayerIndex());
		
		if(!ModelFacade.getInstance().CanDiscardCards(player.getResources()))
		{
			result = false;
		}
		
		assertEquals(result,true);
	}

	@Test
	public void testCanMaritimeTrade()
	{
		game.getTurnTracker().setStatus("Playing");
		boolean result = true;
		//Bank's pre condition
		Resources currentBank = game.getBank();
		int bankWood = currentBank.getResourceAmount(ResourceType.WOOD);
		
		//Player's pre conditions
				Player currentPlayer = game.getPlayers()[0];
				Resources hand = currentPlayer.getResources();
				int wood = hand.getResourceAmount(ResourceType.WOOD);
				int brick = hand.getResourceAmount(ResourceType.BRICK);
				
		if(ModelFacade.getInstance().CanMaritimeTrade(4,"wood","brick"))
		{
			ModelFacade.getInstance().maritimeTrade(4, "wood", "brick");
			
			//Banks post condition
			currentBank = game.getBank();
			int bankPostWood = currentBank.getResourceAmount(ResourceType.WOOD);
			
			//Player's post conditions
			currentPlayer = game.getPlayers()[0];
			Resources postHand = currentPlayer.getResources();
			int postWood = postHand.getResourceAmount(ResourceType.WOOD);
			int postBrick = postHand.getResourceAmount(ResourceType.BRICK);
			
			
			//Comparing and testing pre condition with post conditions
			if(postWood != wood-4)
			{
				result = false;
			}
			if(postBrick != brick + 1)
			{
				result = false;
			}
			if(bankPostWood != bankWood+4)
			{
				result = false;
			}
		}
		else
		{
			result = false;
		}
		
		
		assertEquals(result,true);
	}

	@Test
	public void testCanFinishTurn()
	{
		game.getTurnTracker().setStatus("Playing");
		boolean result =  true ;
		
		ModelFacade.getInstance().finishTurn();
		
		TurnTracker turnTracker = game.getTurnTracker();
		
		if(turnTracker.getCurrentTurn() != 1)
		{
			result = false;
		}
		if(turnTracker.getStatus() == "Playing")
		{
			result = false;
		}
		
		assertEquals(result,true);
	}


	@Test
	public void testCanUseYearOfPlenty()
	{
		game.getTurnTracker().setStatus("Playing");
		boolean result = true; 
		game.getPlayers()[0].getOldDevCards().setYearOfPlenty(1);
		
		//Preconditions
		Player currentPlayer = game.getPlayers()[0];
		Resources hand = currentPlayer.getResources();
		int wood = hand.getResourceAmount(ResourceType.WOOD);
		int brick = hand.getResourceAmount(ResourceType.BRICK);
		
		if(ModelFacade.getInstance().CanUseYearOfPlenty("wood","brick"))
		{
			ModelFacade.getInstance().playYearOfPlentyCard("wood","brick");
			//player's post condition
			currentPlayer = game.getPlayers()[0];
			Resources postHand = currentPlayer.getResources();
			int postWood = postHand.getResourceAmount(ResourceType.WOOD);
			int postBrick = postHand.getResourceAmount(ResourceType.BRICK);
		
			
			//Testing those conditions
			if(postWood != wood+1)	
			{
				System.out.println("post wood is" + postWood + "wood is" + wood);
				result = false;
			}
			if(postBrick != brick+1)
			{
				System.out.println("brick is wrong");
				result = false;
			}
		}
		else
		{
			System.out.println("canDo is wrong");
			result = false;
		}
		
		assertEquals(result,true);
	}

	@Test
	public void testCanUseRoadBuilding()
	{
		//builds road next to edge to meet prerequisites
		EdgeLocation begEdge = new EdgeLocation(0, 0, "NE");
		Road road = new Road(0,begEdge);
		game.getBoard().setRoad(road);
		
		game.getPlayers()[0].getOldDevCards().setRoadBuilding(1);
		
		
		game.getTurnTracker().setStatus("Playing");
		boolean result = true; 
		
		EdgeLocation edge = new EdgeLocation(0, 0, "N");
		EdgeLocation secondEdge = new EdgeLocation(0, 0, "NW");
		
	
		if(ModelFacade.getInstance().CanUseRoadBuilder(edge,secondEdge))
		{
			//Testing to see if road is taken these if statements should return false bc roads have been built
			
			if(ModelFacade.getInstance().canPlaceRoad(secondEdge,true))
			{
                                System.out.println("Can't place second");
				result = false;
			}
		}
		else
		{
                        System.out.println("can't use roadBuilding");
			result = false;
		}
		
		assertEquals(result,true);
	}

	@Test
	public void testCanUseSoldier()
	{
		game.getTurnTracker().setStatus("Playing");
		boolean result = true;
		
		HexLocation location = new HexLocation(1,1);
		game.getPlayers()[0].getOldDevCards().setSoldier(1);
		if(ModelFacade.getInstance().CanUseSoldier(1, location))
		{
			ModelFacade.getInstance().playSoldierCard(1, location);
			
			if(ModelFacade.getInstance().canPlaceRobber(location))
			{
				//System.out.println("robber is wrong");
				result = false;
			}
			//tests other dev cards
			if(ModelFacade.getInstance().CanUseMonopoly("wood"))
			{
				System.out.println("can use monopoly is wrong");
				
				result = false;
						
			}
			if(ModelFacade.getInstance().CanUseYearOfPlenty("wood","brick"))
			{
				System.out.println("can use year of plenty is wrong");
				result = false;
						
			}
		}
		else
		{
			result = false;
		}
		
		
		
		
		assertEquals(result,true);
	}

	@Test
	public void testCanUseMonopoly()
	{
		game.getTurnTracker().setStatus("Playing");
		boolean result = true;
		
		game.getPlayers()[0].getOldDevCards().setMonopoly(1);
		
		//current Player's pre cond
		Player currentPlayer = game.getPlayers()[0];
		Resources hand = currentPlayer.getResources();
		int currentWood = hand.getResourceAmount(ResourceType.WOOD);
		
		
		if(ModelFacade.getInstance().CanUseMonopoly("wood"))
		{
			
			ModelFacade.getInstance().playMonopolyCard("wood");
			
			//Only Opponent in the game
			currentPlayer = game.getPlayers()[1];
			hand = currentPlayer.getResources();
			int postOpponentWood = hand.getResourceAmount(ResourceType.WOOD);
			
			//Get post condition for current player
			currentPlayer = game.getPlayers()[0];
			hand = currentPlayer.getResources();
			int postCurrentWood = hand.getResourceAmount(ResourceType.WOOD);
			
			if(postCurrentWood != currentWood+7)
			{
				result = false;
			}
			if(postOpponentWood != 0)
			{
				result = false;
			}
			
			
		}
		else
		{
			result = false;
		}
		
		assertEquals(result,true);
	}

	@Test
	public void testCanUseMonument()
	{
		game.getTurnTracker().setStatus("Playing");
		boolean result = true;
		
		//get PreConditions
		Player currentPlayer = game.getPlayers()[0];
		int vp = currentPlayer.getVictoryPoints();
		
		
		if(ModelFacade.getInstance().CanUseMonument())
		{
			//Get post conditions
			currentPlayer = game.getPlayers()[0];
			int postVp = currentPlayer.getVictoryPoints();
			//Compare and test conditions
			if(postVp != vp+1)
			{
				result = false;
			}
		}
		assertEquals(result,true);
	}


	
}


