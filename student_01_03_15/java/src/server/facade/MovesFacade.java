package server.facade;

import client.data.PlayerInfo;
import client.proxy.Proxy;
import model.Game;
import model.ModelFacade;
import model.player.Player;
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
		ModelFacade.createInstance(new Proxy());
	}

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse sendChat(SendChatParam param, int id)
	{
		boolean valid = true;
		Game game = games.getGame(id);
		setGame(game, param.getPlayerIndex());
		SendChat cmd = new SendChat(param, game);
		
		cmd.execute();
		games.addCommand(id, cmd);

		return getResponse(id, valid);
	}

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse acceptTrade(AcceptTradeParam param, int id)
	{
		boolean valid = false;
		Game game = games.getGame(id);
		setGame(game, param.getPlayerIndex());
		
		AcceptTrade cmd = new AcceptTrade(param, game);
		
		if (ModelFacade.getInstance().canAcceptTrade())
		{
			cmd.execute();
			games.addCommand(id, cmd);
		}

		return getResponse(id, valid);
	}

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse discardCards(DiscardCardsParam param, int id)
	{
		boolean valid = false;
		Game game = games.getGame(id);
		setGame(game, param.getPlayerIndex());
		
		DiscardCards cmd = new DiscardCards(param, game);
		
		if (ModelFacade.getInstance().CanDiscardCards(param.getDiscardCards()))
		{
			cmd.execute();
			games.addCommand(id, cmd);
		}

		return getResponse(id, valid);
	}

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse rollNumber(RollNumParam param, int id)
	{
		boolean valid = false;
		Game game = games.getGame(id);
		setGame(game, param.getPlayerIndex());
		
		RollNumber cmd = new RollNumber(param, game);
		
		if (ModelFacade.getInstance().CanRollNumber())
		{
			valid = true;
			cmd.execute();
			games.addCommand(id, cmd);
		}

		return getResponse(id, valid);
	}

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse buildRoad(BuildRoadParam param, int id)
	{
		if (param == null) return getResponse(id, false);
		boolean valid = false;
		Game game = games.getGame(id);
		setGame(game, param.getPlayerIndex());

		BuildRoad cmd = new BuildRoad(param, game);
		
		if (ModelFacade.getInstance().canPlaceRoad(param.getRoadLocation(), param.isFree()))
		{
			valid = true;
			cmd.execute();
			games.addCommand(id, cmd);
		}

		return getResponse(id, valid);
	}

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse buildSettlement(BuildSettlementParam param, int id)
	{
		boolean valid = false;
		Game game = games.getGame(id);
		setGame(game, param.getPlayerIndex());
		
		BuildSettlement cmd = new BuildSettlement(param, game);
		
		if (ModelFacade.getInstance().canPlaceSettlement(param.getLocation(), param.isFree()))
		{
			valid = true;
			cmd.execute();
			games.addCommand(id, cmd);
		}

		return getResponse(id, valid);
	}

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse buildCity(BuildCityParam param, int id)
	{
		boolean valid = false;
		Game game = games.getGame(id);
		setGame(game, param.getPlayerIndex());
		
		BuildCity cmd = new BuildCity(param, game);
		
		if (ModelFacade.getInstance().canPlaceCity(param.getLocation()))
		{
			cmd.execute();
			games.addCommand(id, cmd);
		}

		return getResponse(id, valid);
	}

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse offerTrade(OfferTradeParam param, int id)
	{
		boolean valid = false;
		Game game = games.getGame(id);
		setGame(game, param.getPlayerIndex());
		
		OfferTrade cmd = new OfferTrade(param, game);
		
		if (ModelFacade.getInstance().CanOfferTrade(param.getOffer()))
		{
			cmd.execute();
			games.addCommand(id, cmd);
		}

		return getResponse(id, valid);
	}

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse maritimeTrade(MaritimeTradeParam param, int id)
	{
		boolean valid = false;
		Game game = games.getGame(id);
		setGame(game, param.getPlayerIndex());
		
		MaritimeTrade cmd = new MaritimeTrade(param, game);
		
		if (ModelFacade.getInstance().CanMaritimeTrade(
				param.getRatio(),param.getInputResource(),param.getOutResource()))
		{
			cmd.execute();
			games.addCommand(id, cmd);
		}

		return getResponse(id, valid);
	}

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse robPlayer(RobPlayerParam param, int id)
	{
		boolean valid = false;
		Game game = games.getGame(id);
		setGame(game, param.getPlayerIndex());
		
		RobPlayer cmd = new RobPlayer(param, game);
		
		if (ModelFacade.getInstance().canRobPlayer(
				param.getLocation(), param.getVictimIndex()))
		{
			cmd.execute();
			games.addCommand(id, cmd);
		}

		return getResponse(id, valid);
	}

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse finishTurn(FinishTurnParam param, int id)
	{
		boolean valid = false;
		Game game = games.getGame(id);
		setGame(game, param.getPlayerIndex());
		
		FinishTurn cmd = new FinishTurn(param, game);
		
		if (ModelFacade.getInstance().CanFinishTurn())
		{
			valid = true;
			cmd.execute();
			games.addCommand(id, cmd);
		}

		return getResponse(id, valid);
	}

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse buyDevCard(BuyDevCardParam param, int id)
	{
		boolean valid = false;
		Game game = games.getGame(id);
		setGame(game, param.getPlayerIndex());
		
		BuyDevCard cmd = new BuyDevCard(param, game);
		
		if (ModelFacade.getInstance().CanBuyDevCard())
		{
			cmd.execute();
			games.addCommand(id, cmd);
		}

		return getResponse(id, valid);
	}

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse playSoldierCard(PlaySoldierParam param, int id)
	{
		boolean valid = false;
		Game game = games.getGame(id);
		setGame(game, param.getPlayerIndex());
		
		PlaySoldier cmd = new PlaySoldier(param, game);
		
		if (ModelFacade.getInstance().CanUseSoldier(
				param.getVictimIndex(), param.getLocation()))
		{
			cmd.execute();
			games.addCommand(id, cmd);
		}

		return getResponse(id, valid);
	}

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse playYearOfPlentyCard(PlayYearOfPlentyParam param,
			int id)
	{
		boolean valid = false;
		Game game = games.getGame(id);
		setGame(game, param.getPlayerIndex());
		
		PlayYearOfPlenty cmd = new PlayYearOfPlenty(param, game);
		
		if (ModelFacade.getInstance().CanUseYearOfPlenty(
				param.getResource1(), param.getResource2()))
		{
			cmd.execute();
			games.addCommand(id, cmd);
		}

		return getResponse(id, valid);
	}

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse playRoadCard(PlayRoadBuildingParam param, int id)
	{
		boolean valid = false;
		Game game = games.getGame(id);
		setGame(game, param.getPlayerIndex());
		
		PlayRoadBuilding cmd = new PlayRoadBuilding(param, game);
		
		if (ModelFacade.getInstance().CanUseRoadBuilder(
				param.getSpot1(), param.getSpot2()))
		{
			cmd.execute();
			games.addCommand(id, cmd);
		}

		return getResponse(id, valid);
	}

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse playMonopolyCard(PlayMonopolyParam param, int id)
	{
		boolean valid = false;
		Game game = games.getGame(id);
		setGame(game, param.getPlayerIndex());
		
		PlayMonopoly cmd = new PlayMonopoly(param, game);
		
		if (ModelFacade.getInstance().CanUseMonopoly(param.getResource()))
		{
			cmd.execute();
			games.addCommand(id, cmd);
		}

		return getResponse(id, valid);
	}

	//------------------------------------------------------------------------------
	@Override
	public GameModelResponse playMonumentCard(PlayMonumentParam param, int id)
	{
		boolean valid = false;
		Game game = games.getGame(id);
		setGame(game, param.getPlayerIndex());
		
		PlayMonument cmd = new PlayMonument(param, game);
		
		if (ModelFacade.getInstance().CanUseMonument())
		{
			cmd.execute();
			games.addCommand(id, cmd);
		}

		return getResponse(id, valid);
	}

	//------------------------------------------------------------------------------
	private GameModelResponse getResponse(int id, boolean valid)
	{
		GameModelResponse response = new GameModelResponse();
		response.setGame(games.getGame(id));
		response.setValid(valid);
		return response;
	}
	
	//------------------------------------------------------------------------------
	private void setGame(Game game, int index)
	{
		try
		{
			ModelFacade.getInstance().setGame(game);
			Player player = game.getPlayer(index);
			PlayerInfo info = new PlayerInfo();
			info.setColor(player.getColor());
			info.setId(player.getPlayerID());
			info.setName(player.getName());
			info.setPlayerIndex(player.getPlayerIndex());
			ModelFacade.getInstance().setPlayerInfo(info);
		}
		catch(Exception e)
		{
			System.err.println(e);
			System.err.println(e.getLocalizedMessage());
		}
	}
	
	
	//------------------------------------------------------------------------------
}
