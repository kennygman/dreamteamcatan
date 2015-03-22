package server.commands;

import model.Game;
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
		String status = null;
		if (number == 7)
		{
			for (Player p : game.getPlayers())
			{
				if (p.getResources().size() > 7)
				{
					status = "Discarding";
					break;
				}
			}
			if (status == null)
			{
				status = "Robbing";
			}
		} else
		{
			status = "Playing";
			game.distribute(number);
		}

		game.addLogEntry(player.getName(), player.getName() + " rolled a "
				+ param.getNumber());
	}

}
