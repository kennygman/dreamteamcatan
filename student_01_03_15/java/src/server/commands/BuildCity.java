package server.commands;

import model.Game;
import model.board.City;
import model.player.Player;
import shared.definitions.ResourceType;
import shared.parameters.BuildCityParam;
import shared.parameters.ICommandParam;

public class BuildCity implements ICommand
{
	private BuildCityParam param;
	private transient Game game;
	
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
		Player player = game.getPlayer(param.getPlayerIndex());
		City city = new City(param.getPlayerIndex(), param.getLocation());

		player.getResources().useResource(ResourceType.WHEAT, 2);
		player.getResources().useResource(ResourceType.ORE, 3);
		game.getBank().addResource(ResourceType.WHEAT, 2);
		game.getBank().addResource(ResourceType.ORE, 3);
		player.setCities(player.getCities()-1);
		player.setSettlements(player.getSettlements()+1);
		game.getBoard().setCity(city);
		player.setVictoryPoints(player.getVictoryPoints()+1);
		game.increment();

		game.addLogEntry(player.getName(), player.getName() + " built a City");
	}
	@Override
	public ICommandParam getParam()
	{
		return param;
	}


}
