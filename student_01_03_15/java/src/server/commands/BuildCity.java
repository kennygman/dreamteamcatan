package server.commands;

import model.Game;
import shared.parameters.BuildCityParam;

public class BuildCity implements ICommand
{
	private BuildCityParam param;
	private Game game;
	
	public BuildCity(BuildCityParam param, Game game)
	{
		super();
		this.param = param;
		this.game=game;
	}

	/**
	 * Decreases Player's resources for the cost of the City, adds City
	 * to the map at the specified location, returns a Settlement to the
	 * Player's pieces.
	 */
	@Override
	public void execute()
	{
		// TODO Auto-generated method stub

	}

}
