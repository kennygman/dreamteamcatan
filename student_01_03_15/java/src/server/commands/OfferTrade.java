package server.commands;

import model.Game;
import model.TradeOffer;
import model.TurnTracker;
import model.player.Player;
import shared.parameters.ICommandParam;
import shared.parameters.OfferTradeParam;

public class OfferTrade implements ICommand
{
	private OfferTradeParam param;
	private transient Game game;
	
	public OfferTrade(OfferTradeParam param, Game game)
	{
		super();
		this.param = param;
		this.game=game;
	}

	/**
	 * Offers trade to receiving player
	 */
	@Override
	public void execute()
	{
		Player player = game.getPlayer(param.getPlayerIndex());
		game.setTradeOffer(
				new TradeOffer(
						param.getPlayerIndex(),
						param.getreceiver(),
						param.getOffer()
						)
				);
		game.getTurnTracker().setStatus(TurnTracker.TRADING);
		game.increment();
		game.addLogEntry(player.getName(), player.getName() + " has made a trade offer");
	}
	@Override
	public ICommandParam getParam()
	{
		return param;
	}


}
