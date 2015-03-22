package server.commands;

import model.Game;
import model.player.Player;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.parameters.PlaySoldierParam;

public class PlaySoldier implements ICommand
{
	private PlaySoldierParam param;
	private Game game;

	public PlaySoldier(PlaySoldierParam param, Game game)
	{
		super();
		this.param = param;
		this.game = game;
	}

	/**
	 * Plays rob dev card for the player that is using it, puts the robber in
	 * the new location the player indicated and robs the selected player.
	 */
	@Override
	public void execute()
	{
		Player player = game.getPlayer(param.getPlayerIndex());
		Player victim = game.getPlayer(param.getVictimIndex());

		game.getBoard().setRobber(param.getLocation());
		player.setSoldiers(player.getSoldiers() + 1);
		player.getOldDevCards().useCard(DevCardType.SOLDIER);
		player.setPlayedDevCard(true);
		game.addLogEntry(player.getName(), player.getName() + " used a soldier");

		if (isLargestArmy())
		{
			game.getTurnTracker().setLargestArmy(player.getPlayerIndex());
			game.addLogEntry(player.getName(), player.getName()
					+ " has the largestArmy");
		}

		if (!isPlayerIndex(victim.getPlayerIndex()))
			return;

		game.addLogEntry(player.getName(), player.getName()
				+ " moved the robber and robbed " + victim.getName());

		ResourceType card = victim.getResources().robResource();
		player.getResources().addResource(card, 1);

	}

	private boolean isLargestArmy()
	{
		int index = game.getTurnTracker().getLargestArmy();
		int largestArmy = game.getPlayer(index).getSoldiers();
		int playerSoldiers = game.getPlayer(param.getPlayerIndex())
				.getSoldiers();
		return playerSoldiers > largestArmy;
	}

	private boolean isPlayerIndex(int index)
	{
		return index >= 0 && index <= 3 && index != param.getPlayerIndex();
	}

}
