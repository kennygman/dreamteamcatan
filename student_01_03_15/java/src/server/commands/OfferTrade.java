package server.commands;

import model.Game;
import shared.parameters.OfferTradeParam;

public class OfferTrade implements ICommand
{
	private OfferTradeParam param;
	
	public OfferTrade(OfferTradeParam param)
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
