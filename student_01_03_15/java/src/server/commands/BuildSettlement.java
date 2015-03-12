package server.commands;

import model.Game;
import shared.parameters.BuildSettlementParam;

public class BuildSettlement implements ICommand
{
	private BuildSettlementParam param;
	
	public BuildSettlement(BuildSettlementParam param)
	{
		super();
		this.param = param;
	}

	/**
	 * If not free, Decreases Player's resources for the cost of the Settlement,
	 * adds Settlement to the map at the specified location.
	 */
	@Override
	public void execute(Game game)
	{
		// TODO Auto-generated method stub

	}

}
