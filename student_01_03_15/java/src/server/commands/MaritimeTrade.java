package server.commands;

import model.Game;
import model.player.Player;
import shared.definitions.ResourceType;
import shared.parameters.MaritimeTradeParam;

public class MaritimeTrade implements ICommand
{
	private MaritimeTradeParam param;
	private Game game;

	public MaritimeTrade(MaritimeTradeParam param, Game game)
	{
		super();
		this.param = param;
		this.game = game;
	}

	/**
	 * Trades with resources with bank depending on building built by ports
	 */
	@Override
	public void execute()
	{
		Player player = game.getPlayer(param.getPlayerIndex());
		ResourceType give = ResourceType.fromString(param.getInputResource());
		ResourceType get = ResourceType.fromString(param.getOutResource());
		player.getResources().addResource(get, 1);
		game.getBank().addResource(give, param.getRatio());

		game.addLogEntry(player.getName(), player.getName() + " did a maritime trade");
}

}
