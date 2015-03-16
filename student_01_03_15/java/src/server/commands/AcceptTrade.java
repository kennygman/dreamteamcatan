package server.commands;

import model.Game;
import shared.parameters.AcceptTradeParam;

public class AcceptTrade implements ICommand
{

	private AcceptTradeParam param;
	private Game game;
	
	public AcceptTrade(AcceptTradeParam param, Game game)
	{
		super();
		this.param = param;
		this.game=game;
	}

	/**
	 * Swaps the resources for the players in the trade if the trade was accepted
	 * then removes the trade offer from the game.
	 */
	@Override
	public void execute()
	{
		// TODO Auto-generated method stub
		
	}

}
