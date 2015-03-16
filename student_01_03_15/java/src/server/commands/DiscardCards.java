package server.commands;

import model.Game;
import shared.parameters.DiscardCardsParam;

public class DiscardCards implements ICommand
{
	private DiscardCardsParam param;
	private Game game;
	
	public DiscardCards(DiscardCardsParam param, Game game)
	{
		super();
		this.param = param;
		this.game=game;
	}

	/**
	 * Decrease Player's resources by specified amounts,
	 * If Player is last in list then sets model status to 'Robbing'.
	 */
	@Override
	public void execute()
	{
		// TODO Auto-generated method stub

	}

}
