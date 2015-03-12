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

	@Override
	public void execute(Game game)
	{
		// TODO Auto-generated method stub

	}

}
