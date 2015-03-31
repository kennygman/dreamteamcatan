package server.commands;

import model.Game;
import model.TurnTracker;
import model.player.Player;
import shared.parameters.DiscardCardsParam;

public class DiscardCards implements ICommand
{
	private DiscardCardsParam param;
	private Game game;

	public DiscardCards(DiscardCardsParam param, Game game)
	{
		super();
		this.param = param;
		this.game = game;
	}

	/**
	 * Decrease Player's resources by specified amounts, If Player is last in
	 * list then sets model status to 'Robbing'.
	 */
	@Override
	public void execute()
	{
		Player player = game.getPlayer(param.getPlayerIndex());
		
		player.getResources().doTrade(param.getDiscardCards());
		game.getBank().doTrade(param.getDiscardCards().invert());

		boolean last_to_discard = game.getTurnTracker().discarded(player.getPlayerIndex());
		if (last_to_discard)
		{
			game.getTurnTracker().setStatus(TurnTracker.ROBBING);
		}
		game.increment();

		game.addLogEntry(player.getName(), player.getName() + " has discarded");
	}

}
