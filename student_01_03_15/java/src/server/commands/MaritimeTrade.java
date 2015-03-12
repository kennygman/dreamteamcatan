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

	@Override
	public void execute(Game game)
	{
		// TODO Auto-generated method stub

	}

}
