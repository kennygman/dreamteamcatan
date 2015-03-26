package server.commands;

import model.Game;
import model.TurnTracker;
import model.player.Player;
import shared.parameters.RollNumParam;

public class RollNumber implements ICommand
{
	private RollNumParam param;
	private Game game;

	public RollNumber(RollNumParam param, Game game)
	{
		super();
		this.param = param;
		this.game = game;
	}

	/**
	 * Roll a number at the beginning of your turn.
	 */
	@Override
	public void execute()
	{
		Player player = game.getPlayer(param.getPlayerIndex());
		int number = param.getNumber();

		game.getTurnTracker().setStatus(TurnTracker.PLAYING);
		
		if (number == 7)
		{
			game.getTurnTracker().setStatus(TurnTracker.ROBBING);
			for (Player p : game.getPlayers())
			{
				if (p.getResources().size() > 7)
				{
					game.getTurnTracker().setStatus(TurnTracker.DISCARDING);
					break;
				}
			}
			
		} else
		{
			game.distribute(number);
		}

		game.addLogEntry(player.getName(), player.getName() + " rolled a "
				+ param.getNumber());
	}

}
