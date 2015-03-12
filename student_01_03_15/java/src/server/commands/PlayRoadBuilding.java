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

	/**
	 * Plays roadbuilding dev card for the player that is using it and places 2 roads where player indicated
	 */
	@Override
	public void execute(Game game)
	{
		// TODO Auto-generated method stub

	}

}
