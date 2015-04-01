package server.commands;

import model.Game;
import model.player.Player;
import shared.definitions.ResourceType;
import shared.parameters.ICommandParam;
import shared.parameters.MaritimeTradeParam;

public class MaritimeTrade implements ICommand
{
	private MaritimeTradeParam param;
	private transient Game game;

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

		player.getResources().useResource(give, param.getRatio());
		game.getBank().addResource(give, param.getRatio());
		game.increment();

		game.addLogEntry(player.getName(), player.getName()
				+ " did a maritime trade");
	}

	@Override
	public ICommandParam getParam()
	{
		return param;
	}

}
