package server.commands;

import model.Game;
import model.player.Player;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.parameters.PlayYearOfPlentyParam;

public class PlayYearOfPlenty implements ICommand
{
	private PlayYearOfPlentyParam param;
	private Game game;
	
	public PlayYearOfPlenty(PlayYearOfPlentyParam param, Game game)
{
	super();
	this.param = param;
	this.game=game;
}

	/**
	 * Plays a 'Year of Plenty' card from your hand to gain the two specified resources
	 */
	@Override
	public void execute()
	{
		Player player = game.getPlayer(param.getPlayerIndex());
		ResourceType r1 = ResourceType.fromString(param.getResource1());
		ResourceType r2 = ResourceType.fromString(param.getResource2());
		player.setPlayedDevCard(true);
		player.getOldDevCards().useCard(DevCardType.YEAR_OF_PLENTY);
		player.getResources().addResource(r1, 1);
		player.getResources().addResource(r2, 1);
		game.getBank().useResource(r1, 1);
		game.getBank().useResource(r2, 1);
		game.increment();

		game.addLogEntry(player.getName(), player.getName() + " gained two resources from year of plenty");
	}

}
