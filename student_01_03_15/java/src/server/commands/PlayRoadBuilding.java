package server.commands;

import model.Game;
import shared.parameters.PlayRoadBuildingParam;

public class PlayRoadBuilding implements ICommand
{
	private PlayRoadBuildingParam param;
	private Game game;
	
	public PlayRoadBuilding(PlayRoadBuildingParam param, Game game)
	{
		super();
		this.param = param;
		this.game = game;
	}

	/**
	 * Plays roadbuilding dev card for the player that is using it and places 2 roads where player indicated
	 */
	@Override
	public void execute()
	{
		// TODO Auto-generated method stub

	}

}
