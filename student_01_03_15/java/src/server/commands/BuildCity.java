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

	@Override
	public void execute(Game game)
	{
		// TODO Auto-generated method stub

	}

}
