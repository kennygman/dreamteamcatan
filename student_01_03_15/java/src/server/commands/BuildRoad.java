package server.commands;

import model.Game;
import model.board.Road;
import model.player.Player;
import shared.definitions.ResourceType;
import shared.parameters.BuildRoadParam;

public class BuildRoad implements ICommand
{
	private BuildRoadParam param;
	private Game game;

	public BuildRoad(){}
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
		Player player = game.getPlayer(param.getPlayerIndex());
		Road road = new Road(param.getPlayerIndex(), param.getRoadLocation());

		if (!param.isFree())
		{
			player.getResources().useResource(ResourceType.WOOD, 1);
			player.getResources().useResource(ResourceType.BRICK, 1);
			game.getBank().addResource(ResourceType.WOOD, 1);
			game.getBank().addResource(ResourceType.BRICK, 1);
		}
		player.setRoads(player.getRoads()-1);
		game.getBoard().setRoad(road);
		if (isLongestRoad()) game.getTurnTracker().setLongestRoad(player.getPlayerIndex());

		game.addLogEntry(player.getName(), player.getName() + " built a Road");
}

	private boolean isLongestRoad()
	{
		int index = game.getTurnTracker().getLongestRoad();
		if (index<0) return true;
		int longestRoad = game.getPlayer(index).getRoads();
		int playerRoads = game.getPlayer(param.getPlayerIndex()).getRoads();
		return playerRoads > longestRoad;
	}
}
