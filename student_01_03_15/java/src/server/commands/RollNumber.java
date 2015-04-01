package server.commands;

import model.Game;
import model.TurnTracker;
import model.player.Player;
import shared.parameters.ICommandParam;
import shared.parameters.RollNumParam;

public class RollNumber implements ICommand
{
	private RollNumParam param;
	private transient Game game;

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
		TurnTracker tt = game.getTurnTracker();

		tt.setStatus(TurnTracker.PLAYING);
		
		if (number == 7)
		{
			tt.setStatus(TurnTracker.ROBBING);
			for (Player p : game.getPlayers())
			{
				if (p.getResources().size() > 7)
				{
					tt.setDiscarding(p.getPlayerIndex());
					tt.setStatus(TurnTracker.DISCARDING);
				}
			}
			
		} else
		{
			game.distribute(number);
		}
		game.increment();

		game.addLogEntry(player.getName(), player.getName() + " rolled a "
				+ param.getNumber());
	}
	@Override
	public ICommandParam getParam()
	{
		return param;
	}


}
