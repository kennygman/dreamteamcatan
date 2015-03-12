package server.commands;

import model.Game;
import shared.parameters.MaritimeTradeParam;

public class MaritimeTrade implements ICommand
{
	private MaritimeTradeParam param;
	
	public MaritimeTrade(MaritimeTradeParam param)
	{
		super();
		this.param = param;
	}

	/**
	 * Trades with resources with bank depending on building built by ports
	 */
	@Override
	public void execute(Game game)
	{
		// TODO Auto-generated method stub

	}

}
