package server.commands;

import model.Game;
import shared.parameters.BuildCityParam;

public class BuildCity implements ICommand
{
	private BuildCityParam param;
	
	public BuildCity(BuildCityParam param)
	{
		super();
		this.param = param;
	}

	/**
	 * Decreases Player's resources for the cost of the City, adds City
	 * to the map at the specified location, returns a Settlement to the
	 * Player's pieces.
	 */
	@Override
	public void execute(Game game)
	{
		// TODO Auto-generated method stub

	}

}
