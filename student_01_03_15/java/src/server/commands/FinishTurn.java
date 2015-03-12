package server.commands;

import model.Game;
import shared.parameters.FinishTurnParam;

public class FinishTurn implements ICommand
{
	private FinishTurnParam param;
	
	public FinishTurn(FinishTurnParam param)
	{
		super();
		this.param = param;
	}

	/**
	 * Moves cards from the newDevCardList to the oldDevCardList,
	 * Set's the current Player's turn to the next Player's turn.
	 */
	@Override
	public void execute(Game game)
	{
		// TODO Auto-generated method stub

	}

}
