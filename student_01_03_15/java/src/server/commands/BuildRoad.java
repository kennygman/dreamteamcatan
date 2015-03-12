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

	/**
	 * If not free, Decreases Player's resources for the cost of the Road,
	 * adds Road to the map at the specified location,
	 * updates longest road if applicable.
	 */
	@Override
	public void execute(Game game)
	{
		// TODO Auto-generated method stub

	}

}
