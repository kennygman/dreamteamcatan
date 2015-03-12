package server.commands;

import model.Game;
import shared.parameters.DiscardCardsParam;

public class DiscardCards implements ICommand
{
	private DiscardCardsParam param;
	
	public DiscardCards(DiscardCardsParam param)
	{
		super();
		this.param = param;
	}

	/**
	 * Decrease Player's resources by specified amounts,
	 * If Player is last in list then sets model status to 'Robbing'.
	 */
	@Override
	public void execute(Game game)
	{
		// TODO Auto-generated method stub

	}

}
