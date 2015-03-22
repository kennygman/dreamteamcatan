package server.commands;

import model.Game;
import model.player.Player;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.parameters.PlayMonopolyParam;

public class PlayMonopoly implements ICommand
{
	private PlayMonopolyParam param;
	private Game game;
	
	public PlayMonopoly(PlayMonopolyParam param, Game game)
	{
		super();
		this.param = param;
		this.game=game;
	}

	/**
	 * Plays monopoly dev card for the player that is using it
	 */
	@Override
	public void execute()
	{
		Player player = game.getPlayer(param.getPlayerIndex());
		ResourceType resource = ResourceType.fromString(param.getResource());
		for (Player p : game.getPlayers())
		{
			if (p.getName().equals(player.getName())) continue;
			int amount = p.getResources().getResourceAmount(resource);
			player.getResources().addResource(resource, amount);
			p.getResources().useResource(resource, amount);
		}
		player.setPlayedDevCard(true);
		player.getOldDevCards().useCard(DevCardType.MONOPOLY);

		game.addLogEntry(player.getName(), player.getName() + " played a monopoly card");
	}

}
