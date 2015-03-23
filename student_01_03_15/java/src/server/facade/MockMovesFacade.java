package server.facade;

import shared.parameters.AcceptTradeParam;
import shared.parameters.BuildCityParam;
import shared.parameters.BuildRoadParam;
import shared.parameters.BuildSettlementParam;
import shared.parameters.BuyDevCardParam;
import shared.parameters.DiscardCardsParam;
import shared.parameters.FinishTurnParam;
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
import shared.response.GameModelResponse;

public class MockMovesFacade implements IMovesFacade
{
	
	@Override
	public GameModelResponse sendChat(SendChatParam param, int id)
	{
		return getResponse(id);
	}

	@Override
	public GameModelResponse acceptTrade(AcceptTradeParam param, int id)
	{
		return getResponse(id);
	}

	@Override
	public GameModelResponse discardCards(DiscardCardsParam param, int id)
	{
		return getResponse(id);
	}

	@Override
	public GameModelResponse rollNumber(RollNumParam param, int id)
	{
		return getResponse(id);
	}

	@Override
	public GameModelResponse buildRoad(BuildRoadParam param, int id)
	{
		return getResponse(id);
	}

	@Override
	public GameModelResponse buildSettlement(BuildSettlementParam param, int id)
	{
		return getResponse(id);
	}

	@Override
	public GameModelResponse buildCity(BuildCityParam param, int id)
	{
		return getResponse(id);
	}

	@Override
	public GameModelResponse offerTrade(OfferTradeParam param, int id)
	{
		return getResponse(id);
	}

	@Override
	public GameModelResponse maritimeTrade(MaritimeTradeParam param, int id)
	{
		return getResponse(id);
	}

	@Override
	public GameModelResponse robPlayer(RobPlayerParam param, int id)
	{
		return getResponse(id);
	}

	@Override
	public GameModelResponse finishTurn(FinishTurnParam param, int id)
	{
		return getResponse(id);
	}

	@Override
	public GameModelResponse buyDevCard(BuyDevCardParam param, int id)
	{
		return getResponse(id);
	}

	@Override
	public GameModelResponse playSoldierCard(PlaySoldierParam param, int id)
	{
		return getResponse(id);
	}

	@Override
	public GameModelResponse playYearOfPlentyCard(PlayYearOfPlentyParam param,
			int id)
	{
		return getResponse(id);
	}

	@Override
	public GameModelResponse playRoadCard(PlayRoadBuildingParam param, int id)
	{
		return getResponse(id);
	}

	@Override
	public GameModelResponse playMonopolyCard(PlayMonopolyParam param, int id)
	{
		return getResponse(id);
	}

	@Override
	public GameModelResponse playMonumentCard(PlayMonumentParam param, int id)
	{
		return getResponse(id);
	}

	//------------------------------------------------------------------------------
	private GameModelResponse getResponse(int id)
	{
		GameModelResponse gmr = new GameModelResponse();
		gmr.setValid(true);
		return gmr;
	}
}
