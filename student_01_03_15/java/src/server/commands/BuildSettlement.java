package server.commands;

import model.Game;
import model.TurnTracker;
import model.board.Settlement;
import model.player.Player;
import shared.definitions.ResourceType;
import shared.parameters.BuildSettlementParam;
import shared.parameters.ICommandParam;

public class BuildSettlement implements ICommand
{
	private BuildSettlementParam param;
	private transient Game game;
	
	public BuildSettlement(){}
	public BuildSettlement(BuildSettlementParam param, Game game)
	{
		super();
		this.param = param;
		this.game=game;
	}

	/**
	 * If not free, Decreases Player's resources for the cost of the Settlement,
	 * adds Settlement to the map at the specified location.
	 */
	@Override
	public void execute()
	{
		Player player = game.getPlayer(param.getPlayerIndex());
		Settlement settlement = new Settlement(param.getPlayerIndex(), param.getLocation());

		if (!param.isFree())
		{
			player.getResources().useResource(ResourceType.WOOD, 1);
			player.getResources().useResource(ResourceType.BRICK, 1);
			player.getResources().useResource(ResourceType.SHEEP, 1);
			player.getResources().useResource(ResourceType.WHEAT, 1);
			game.getBank().addResource(ResourceType.WOOD, 1);
			game.getBank().addResource(ResourceType.BRICK, 1);
			game.getBank().addResource(ResourceType.SHEEP, 1);
			game.getBank().addResource(ResourceType.WHEAT, 1);
		}
		player.setSettlements(player.getSettlements()-1);
		game.getBoard().setSettlement(settlement);
		player.setVictoryPoints(player.getVictoryPoints()+1);
		game.increment();
		
		if (game.getTurnTracker().getStatus().equals(TurnTracker.SECONDROUND))
		{
			game.setupResources(player.getPlayerIndex(), settlement.getLocation());
		}

		game.addLogEntry(player.getName(), player.getName() + " built a Settlement");
	}
	@Override
	public ICommandParam getParam()
	{
		return param;
	}


}
