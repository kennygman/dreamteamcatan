package server.commands;

import model.Game;
import shared.parameters.AcceptTradeParam;

public class AcceptTrade implements ICommand
{

	private AcceptTradeParam param;
	
	public AcceptTrade(AcceptTradeParam param)
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
