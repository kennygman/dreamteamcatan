package server.commands;

import model.Game;
import shared.parameters.PlayRoadBuildingParam;

public class PlayRoadBuilding implements ICommand
{
	private PlayRoadBuildingParam param;
	
	public PlayRoadBuilding(PlayRoadBuildingParam param)
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
