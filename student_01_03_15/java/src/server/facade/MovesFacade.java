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

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse sendChat(SendChatParam param, int id)
	{
		Game game = games.getGame(id);
		setGame(game);
		
		SendChat cmd = new SendChat(param, game);
		
		cmd.execute();
		games.addCommand(id, cmd);

		return getResponse(id);
	}

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse acceptTrade(AcceptTradeParam param, int id)
	{
		Game game = games.getGame(id);
		setGame(game);
		
		AcceptTrade cmd = new AcceptTrade(param, game);
		
		if (ModelFacade.getInstance().canAcceptTrade())
		{
			cmd.execute();
			games.addCommand(id, cmd);
		}

		return getResponse(id);
	}

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse discardCards(DiscardCardsParam param, int id)
	{
		Game game = games.getGame(id);
		setGame(game);
		
		DiscardCards cmd = new DiscardCards(param, game);
		
		if (ModelFacade.getInstance().CanDiscardCards(param.getDiscardCards()))
		{
			cmd.execute();
			games.addCommand(id, cmd);
		}

		return getResponse(id);
	}

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse rollNumber(RollNumParam param, int id)
	{
		Game game = games.getGame(id);
		setGame(game);
		
		RollNumber cmd = new RollNumber(param, game);
		
		if (ModelFacade.getInstance().CanRollNumber())
		{
			cmd.execute();
			games.addCommand(id, cmd);
		}

		return getResponse(id);
	}

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse buildRoad(BuildRoadParam param, int id)
	{
		Game game = games.getGame(id);
		setGame(game);
		
		BuildRoad cmd = new BuildRoad(param, game);
		
		if (ModelFacade.getInstance().canPlaceRoad(param.getRoadLocation(), param.isFree()))
		{
			cmd.execute();
			games.addCommand(id, cmd);
		}

		return getResponse(id);
	}

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse buildSettlement(BuildSettlementParam param, int id)
	{
		Game game = games.getGame(id);
		setGame(game);
		
		BuildSettlement cmd = new BuildSettlement(param, game);
		
		if (ModelFacade.getInstance().canPlaceSettlement(param.getLocation(), param.isFree()))
		{
			cmd.execute();
			games.addCommand(id, cmd);
		}

		return getResponse(id);
	}

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse buildCity(BuildCityParam param, int id)
	{
		Game game = games.getGame(id);
		setGame(game);
		
		BuildCity cmd = new BuildCity(param, game);
		
		if (ModelFacade.getInstance().canPlaceCity(param.getLocation()))
		{
			cmd.execute();
			games.addCommand(id, cmd);
		}

		return getResponse(id);
	}

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse offerTrade(OfferTradeParam param, int id)
	{
		Game game = games.getGame(id);
		setGame(game);
		
		OfferTrade cmd = new OfferTrade(param, game);
		
		if (ModelFacade.getInstance().CanOfferTrade(param.getOffer()))
		{
			cmd.execute();
			games.addCommand(id, cmd);
		}

		return getResponse(id);
	}

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse maritimeTrade(MaritimeTradeParam param, int id)
	{
		Game game = games.getGame(id);
		setGame(game);
		
		MaritimeTrade cmd = new MaritimeTrade(param, game);
		
		if (ModelFacade.getInstance().CanMaritimeTrade(
				param.getRatio(),param.getInputResource(),param.getOutResource()))
		{
			cmd.execute();
			games.addCommand(id, cmd);
		}

		return getResponse(id);
	}

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse robPlayer(RobPlayerParam param, int id)
	{
		Game game = games.getGame(id);
		setGame(game);
		
		RobPlayer cmd = new RobPlayer(param, game);
		
		if (ModelFacade.getInstance().canRobPlayer(
				param.getLocation(), param.getVictimIndex()))
		{
			cmd.execute();
			games.addCommand(id, cmd);
		}

		return getResponse(id);
	}

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse finishTurn(FinishTurnParam param, int id)
	{
		Game game = games.getGame(id);
		setGame(game);
		
		FinishTurn cmd = new FinishTurn(param, game);
		
		if (ModelFacade.getInstance().CanFinishTurn())
		{
			cmd.execute();
			games.addCommand(id, cmd);
		}

		return getResponse(id);
	}

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse buyDevCard(BuyDevCardParam param, int id)
	{
		Game game = games.getGame(id);
		setGame(game);
		
		BuyDevCard cmd = new BuyDevCard(param, game);
		
		if (ModelFacade.getInstance().CanBuyDevCard())
		{
			cmd.execute();
			games.addCommand(id, cmd);
		}

		return getResponse(id);
	}

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse playSoldierCard(PlaySoldierParam param, int id)
	{
		Game game = games.getGame(id);
		setGame(game);
		
		PlaySoldier cmd = new PlaySoldier(param, game);
		
		if (ModelFacade.getInstance().CanUseSoldier(
				param.getVictimIndex(), param.getLocation()))
		{
			cmd.execute();
			games.addCommand(id, cmd);
		}

		return getResponse(id);
	}

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse playYearOfPlentyCard(PlayYearOfPlentyParam param,
			int id)
	{
		Game game = games.getGame(id);
		setGame(game);
		
		PlayYearOfPlenty cmd = new PlayYearOfPlenty(param, game);
		
		if (ModelFacade.getInstance().CanUseYearOfPlenty(
				param.getResource1(), param.getResource2()))
		{
			cmd.execute();
			games.addCommand(id, cmd);
		}

		return getResponse(id);
	}

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse playRoadCard(PlayRoadBuildingParam param, int id)
	{
		Game game = games.getGame(id);
		setGame(game);
		
		PlayRoadBuilding cmd = new PlayRoadBuilding(param, game);
		
		if (ModelFacade.getInstance().CanUseRoadBuilder(
				param.getSpot1(), param.getSpot2()))
		{
			cmd.execute();
			games.addCommand(id, cmd);
		}

		return getResponse(id);
	}

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse playMonopolyCard(PlayMonopolyParam param, int id)
	{
		Game game = games.getGame(id);
		setGame(game);
		
		PlayMonopoly cmd = new PlayMonopoly(param, game);
		
		if (ModelFacade.getInstance().CanUseMonopoly(param.getResource()))
		{
			cmd.execute();
			games.addCommand(id, cmd);
		}

		return getResponse(id);
	}

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse playMonumentCard(PlayMonumentParam param, int id)
	{
		Game game = games.getGame(id);
		setGame(game);
		
		PlayMonument cmd = new PlayMonument(param, game);
		
		if (ModelFacade.getInstance().CanUseMonument())
		{
			cmd.execute();
			games.addCommand(id, cmd);
		}

		return getResponse(id);
	}

	//------------------------------------------------------------------------------
	private GameModelResponse getResponse(int id)
	{
		GameModelResponse response = new GameModelResponse();
		response.setGame(games.getGame(id));
		return response;
	}
	
	//------------------------------------------------------------------------------
	private void setGame(Game game)
	{
		ModelFacade.getInstance().setGame(game);
	}
	
	
	//------------------------------------------------------------------------------
}
