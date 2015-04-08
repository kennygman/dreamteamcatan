package server.commands;

import model.Game;
import model.board.Road;
import model.player.Player;
import shared.definitions.DevCardType;
import shared.parameters.ICommandParam;
import shared.parameters.PlayRoadBuildingParam;

public class PlayRoadBuilding implements ICommand
{
	private PlayRoadBuildingParam param;
	private transient Game game;
	
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
		Player player = game.getPlayer(param.getPlayerIndex());
		player.setRoads(player.getRoads()-2);
		game.getBoard().setRoad(new Road(param.getPlayerIndex(), param.getSpot1()));
		game.getBoard().setRoad(new Road(param.getPlayerIndex(), param.getSpot2()));
		player.getOldDevCards().useCard(DevCardType.ROAD_BUILD);
		player.setPlayedDevCard(true);
		game.addLogEntry(player.getName(), player.getName() + " just built two roads");

		if (isLongestRoad())
		{
			game.getTurnTracker().setLongestRoad(player.getPlayerIndex());
			game.addLogEntry(player.getName(), player.getName() + " has the longest road");
		}
		game.increment();

}

	private boolean isLongestRoad()
	{
		int longestRoad = 2;
		int index = game.getTurnTracker().getLongestRoad();
		int playerRoads = game.getPlayer(param.getPlayerIndex()).getRoads();
		if (index >= 0)
		{
			longestRoad = 15 - game.getPlayer(index).getRoads();
		}
		return playerRoads > longestRoad;
	}

	@Override
	public ICommandParam getParam()
	{
		return param;
	}

}
