package server.commands;

import model.Game;
import model.TradeOffer;
import model.player.Player;
import shared.parameters.OfferTradeParam;

public class OfferTrade implements ICommand
{
	private OfferTradeParam param;
	private Game game;
	
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
		game.addLogEntry(player.getName(), player.getName() + " has made a trade offer");
	}

}
