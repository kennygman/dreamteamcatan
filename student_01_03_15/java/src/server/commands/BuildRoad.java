package server.commands;

import model.Game;
import shared.parameters.BuildRoadParam;

public class BuildRoad implements ICommand
{
	private BuildRoadParam param;
	
	public BuildRoad(BuildRoadParam param)
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
