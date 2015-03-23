package test;

import static org.junit.Assert.*;
import model.player.Resources;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.facade.ServerFacade;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.parameters.*;
import shared.response.GameModelResponse;

public class MovesFacadeTests
{

	@Before
	public void setup()
	{
		ServerFacade.createInstance(true);
	}

	@After
	public void teardown()
	{
	}

	@Test
	public void testSendChat()
	{
		GameModelResponse response = ServerFacade.sendChat(new SendChatParam(0,
				"You mad bro?"), 1);
		assertEquals(response.isValid(), true);
	}

	@Test
	public void testAcceptTrade()
	{
		GameModelResponse response = ServerFacade.acceptTrade(
				new AcceptTradeParam(2, true), 1);
		assertEquals(response.isValid(), true);
	}

	@Test
	public void testDiscardCards()
	{
		GameModelResponse response = ServerFacade.discardCards(
				new DiscardCardsParam(1, new Resources()), 1);
		assertEquals(response.isValid(), true);

	}

	@Test
	public void testRollNumber()
	{
		GameModelResponse response = ServerFacade.rollNumber(new RollNumParam(
				1, 7), 1);
		assertEquals(response.isValid(), true);

	}

	@Test
	public void testBuildRoad()
	{
		GameModelResponse response = ServerFacade.buildRoad(new BuildRoadParam(
				1,
				new EdgeLocation(new HexLocation(0, 0), EdgeDirection.North),
				false), 1);
		assertEquals(response.isValid(), true);

	}

	@Test
	public void testBuildCity()
	{
		GameModelResponse response = ServerFacade.buildCity(new BuildCityParam(
				1, new VertexLocation(0, 0, "NW")), 1);
		assertEquals(response.isValid(), true);

	}

	@Test
	public void testBuildSettlement()
	{
		GameModelResponse response = ServerFacade.buildSettlement(
				new BuildSettlementParam(1, new VertexLocation(0, 0, "NE"),
						false), 1);
		assertEquals(response.isValid(), true);

	}

	@Test
	public void testOfferTrade()
	{
		GameModelResponse response = ServerFacade.offerTrade(
				new OfferTradeParam(0, 1, new Resources()), 1);
		assertEquals(response.isValid(), true);

	}

	@Test
	public void testMaritimeTrade()
	{
		GameModelResponse response = ServerFacade.maritimeTrade(
				new MaritimeTradeParam(1, 3, "wood", "brick"), 1);
		assertEquals(response.isValid(), true);

	}

	@Test
	public void testRobPlayer()
	{
		GameModelResponse response = ServerFacade.robPlayer(new RobPlayerParam(
				1, 2, new HexLocation(0, 0)), 1);
		assertEquals(response.isValid(), true);

	}

	@Test
	public void testFinishTurn()
	{
		GameModelResponse response = ServerFacade.finishTurn(
				new FinishTurnParam(1), 1);
		assertEquals(response.isValid(), true);

	}

	@Test
	public void testBuyDevCard()
	{
		GameModelResponse response = ServerFacade.buyDevCard(
				new BuyDevCardParam(1), 1);
		assertEquals(response.isValid(), true);

	}

	@Test
	public void testMonopoly()
	{
		GameModelResponse response = ServerFacade.playMonopolyCard(
				new PlayMonopolyParam(1, "wood"), 1);
		assertEquals(response.isValid(), true);

	}

	@Test
	public void testRoadBuilding()
	{
		GameModelResponse response = ServerFacade.playRoadCard(
				new PlayRoadBuildingParam(1, new EdgeLocation(new HexLocation(
						0, 0), EdgeDirection.NorthEast), new EdgeLocation(
						new HexLocation(0, 0), EdgeDirection.NorthWest)), 1);
		assertEquals(response.isValid(), true);

	}

	@Test
	public void testSoldier()
	{
		GameModelResponse response = ServerFacade.playSoldierCard(
				new PlaySoldierParam(1, 2, new HexLocation(0, 0)), 1);
		assertEquals(response.isValid(), true);

	}

	@Test
	public void testYearOfPlenty()
	{
		GameModelResponse response = ServerFacade.playYearOfPlentyCard(
				new PlayYearOfPlentyParam(1, "wood", "wheat"), 1);
		assertEquals(response.isValid(), true);

	}

	@Test
	public void testMonument()
	{
		GameModelResponse response = ServerFacade.playMonumentCard(
				new PlayMonumentParam(1), 1);
		assertEquals(response.isValid(), true);

	}

}
