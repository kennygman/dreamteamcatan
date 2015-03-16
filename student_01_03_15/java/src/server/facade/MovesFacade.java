package server.facade;

import model.Game;
import model.ModelFacade;
import server.GameManager;
import server.commands.*;
import shared.parameters.*;
import shared.response.GameModelResponse;

public class MovesFacade implements IMovesFacade
{
	private GameManager games;
	
	public MovesFacade(GameManager games)
	{
		this.games=games;
	}
    
	@Override
	public GameModelResponse sendChat(SendChatParam param, int id)
	{
		Game game = games.getGame(id);
		SendChat cmd = new SendChat(param, game);
		
		// CanDo? goes here
		if (true)
		{
			cmd.execute();
			games.addCommand(id, cmd);
		}
		return getResponse(id);
	}

	@Override
	public GameModelResponse acceptTrade(AcceptTradeParam param, int id)
	{
		// TODO Auto-generated method stub
		if (ModelFacade.getInstance().canAcceptTrade())
		{
		
		}
		return getResponse(id);
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

	private GameModelResponse getResponse(int id)
	{
		GameModelResponse response = new GameModelResponse();
		response.setGame(games.getGame(id));
		return response;
	}
}
