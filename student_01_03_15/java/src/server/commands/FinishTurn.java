package server.commands;

import model.Game;
import model.player.Player;
import shared.parameters.FinishTurnParam;

public class FinishTurn implements ICommand
{
	private FinishTurnParam param;
	private Game game;
	
	public FinishTurn(FinishTurnParam param, Game game)
	{
		super();
		this.param = param;
		this.game=game;
	}

	/**
	 * Moves cards from the newDevCardList to the oldDevCardList,
	 * Set's the current Player's turn to the next Player's turn.
	 */
	@Override
	public void execute()
	{
		Player player = game.getPlayer(param.getPlayerIndex());
		player.updateDevCards();
		game.getTurnTracker().nextTurn();
		
		game.addLogEntry(player.getName(), player.getName() + "'s turn just ended");
	}

}
