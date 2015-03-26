package server.commands;

import model.Game;
import server.facade.ServerFacade;
import shared.parameters.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class CommandFactory
{
	private static int gameId;

	public CommandFactory(int gameId)
	{
		super();
		CommandFactory.gameId = gameId;
	}

	public static ICommand buildCommand(String type, JsonObject jo)
	{
		ICommand command = null;
		Game game = ServerFacade.getGame(gameId);
		Gson g = new Gson();
		if (type != null && jo != null)
		{
			switch (type)
			{
			case "sendChat":
				command = new SendChat(g.fromJson(jo, SendChatParam.class), game);
				break;
			case "acceptTrade":
				command = new AcceptTrade(g.fromJson(jo, AcceptTradeParam.class), game);
				break;
			case "discardCards":
				command = new DiscardCards(g.fromJson(jo, DiscardCardsParam.class), game);
				break;
			case "rollNumber":
				command = new RollNumber(g.fromJson(jo, RollNumParam.class), game);
				break;
			case "buildRoad":
				command = new BuildRoad(g.fromJson(jo, BuildRoadParam.class), game);
				break;
			case "buildSettlement":
				command = new BuildSettlement(g.fromJson(jo, BuildSettlementParam.class), game);
				break;
			case "buildCity":
				command = new BuildCity(g.fromJson(jo, BuildCityParam.class), game);
				break;
			case "offerTrade":
				command = new OfferTrade(g.fromJson(jo, OfferTradeParam.class), game);
				break;
			case "maritimeTrade":
				command = new MaritimeTrade(g.fromJson(jo, MaritimeTradeParam.class), game);
				break;
			case "robPlayer":
				command = new RobPlayer(g.fromJson(jo, RobPlayerParam.class), game);
				break;
			case "finishTurn":
				command = new FinishTurn(g.fromJson(jo, FinishTurnParam.class), game);
				break;
			case "buyDevCard":
				command = new BuyDevCard(g.fromJson(jo, BuyDevCardParam.class), game);
				break;
			case "Soldier":
				command = new PlaySoldier(g.fromJson(jo, PlaySoldierParam.class), game);
				break;
			case "Year_of_Plenty":
				command = new PlayYearOfPlenty(g.fromJson(jo, PlayYearOfPlentyParam.class), game);
				break;
			case "Road_Building":
				command = new PlayRoadBuilding(g.fromJson(jo, PlayRoadBuildingParam.class), game);
				break;
			case "Monopoly":
				command = new PlayMonopoly(g.fromJson(jo, PlayMonopolyParam.class), game);
				break;
			case "Monument":
				command = new PlayMonument(g.fromJson(jo, PlayMonumentParam.class), game);
				break;
			default:
				break;
			}
		}

		return command;
	}

}
