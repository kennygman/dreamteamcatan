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

	/**
	 * Swaps the resources for the players in the trade if the trade was accepted
	 * then removes the trade offer from the game.
	 */
	@Override
	public void execute(Game game)
	{
		// TODO Auto-generated method stub
		
	}

}
