package server.facade;

import java.util.List;
import java.util.Map;
import server.commands.ICommand;
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

public class MovesFacade implements IMovesFacade
{
        private Map<Integer, List<ICommand>> commands;
    
	@Override
	public GameModelResponse sendChat(SendChatParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelResponse acceptTrade(AcceptTradeParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelResponse discardCards(DiscardCardsParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelResponse rollNumber(RollNumParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelResponse buildRoad(BuildRoadParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelResponse buildSettlement(BuildSettlementParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelResponse buildCity(BuildCityParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelResponse offerTrade(OfferTradeParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelResponse maritimeTrade(MaritimeTradeParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelResponse robPlayer(RobPlayerParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelResponse finishTurn(FinishTurnParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelResponse buyDevCard(BuyDevCardParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelResponse playSoldierCard(PlaySoldierParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelResponse playYearOfPlentyCard(PlayYearOfPlentyParam param,
			int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelResponse playRoadCard(PlayRoadBuildingParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelResponse playMonopolyCard(PlayMonopolyParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameModelResponse playMonumentCard(PlayMonumentParam param, int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
