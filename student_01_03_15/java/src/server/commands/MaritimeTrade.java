package server.commands;

import model.Game;
import shared.parameters.MaritimeTradeParam;

public class MaritimeTrade implements ICommand
{
	private MaritimeTradeParam param;
	private Game game;
	
	public MaritimeTrade(MaritimeTradeParam param, Game game)
	{
		super();
		this.param = param;
		this.game=game;
	}

	/**
	 * Trades with resources with bank depending on building built by ports
	 */
	@Override
	public void execute()
	{
		// TODO Auto-generated method stub

	}

}
