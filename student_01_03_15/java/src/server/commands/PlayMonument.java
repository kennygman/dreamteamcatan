package server.commands;

import model.Game;
import model.player.Player;
import shared.definitions.DevCardType;
import shared.parameters.PlayMonumentParam;

public class PlayMonument implements ICommand
{
	private PlayMonumentParam param;
	private Game game;
	
	public PlayMonument(PlayMonumentParam param, Game game)
	{
		super();
		this.param = param;
		this.game=game;
	}

	/**
	 * Plays monument dev card for the player that is using it
	 */
	@Override
	public void execute()
	{
		Player player = game.getPlayer(param.getPlayerIndex());
		player.setVictoryPoints(player.getVictoryPoints()+1);
		player.getOldDevCards().useCard(DevCardType.MONUMENT);
		game.increment();

		game.addLogEntry(player.getName(), player.getName() + " built a monument and gained a victory point");
}

}
