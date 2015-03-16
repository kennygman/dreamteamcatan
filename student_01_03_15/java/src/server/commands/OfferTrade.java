package server.commands;

import model.Game;
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
		// TODO Auto-generated method stub

	}

}
