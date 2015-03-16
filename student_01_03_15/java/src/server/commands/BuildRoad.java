package server.commands;

import model.Game;
import shared.parameters.BuildRoadParam;

public class BuildRoad implements ICommand
{
	private BuildRoadParam param;
	private Game game;

	public BuildRoad(BuildRoadParam param, Game game)
	{
		super();
		this.param = param;
		this.game=game;
	}

	/**
	 * If not free, Decreases Player's resources for the cost of the Road,
	 * adds Road to the map at the specified location,
	 * updates longest road if applicable.
	 */
	@Override
	public void execute()
	{
		// TODO Auto-generated method stub

	}

}
