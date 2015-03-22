package server.commands;

import model.Game;
import model.player.Player;
import shared.definitions.ResourceType;
import shared.parameters.RobPlayerParam;

public class RobPlayer implements ICommand
{
	private RobPlayerParam param;
	private Game game;
	
	public RobPlayer(RobPlayerParam param, Game game)
	{
		super();
		this.param = param;
		this.game=game;
	}
	/**
	 * Moves the robber, selecting the new robber position and player to rob
	 */
	@Override
	public void execute()
	{
		Player player = game.getPlayer(param.getPlayerIndex());
		Player victim = game.getPlayer(param.getVictimIndex());
		
		game.getBoard().setRobber(param.getLocation());

		ResourceType card = victim.getResources().robResource();
		player.getResources().addResource(card, 1);

		game.addLogEntry(player.getName(), player.getName()
				+ " moved the robber and robbed " + victim.getName());
	}

}
