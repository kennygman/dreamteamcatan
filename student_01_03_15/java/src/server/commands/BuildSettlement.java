package server.commands;

import model.Game;
import shared.parameters.BuildSettlementParam;

public class BuildSettlement implements ICommand
{
	private BuildSettlementParam param;
	private Game game;
	
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
		// TODO Auto-generated method stub

	}

}
