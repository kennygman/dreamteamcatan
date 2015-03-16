package server.commands;

import model.Game;
import shared.parameters.PlayYearOfPlentyParam;

public class PlayYearOfPlenty implements ICommand
{
	private PlayYearOfPlentyParam param;
	private Game game;
	
	public PlayYearOfPlenty(PlayYearOfPlentyParam param, Game game)
{
	super();
	this.param = param;
	this.game=game;
}

	/**
	 * Plays a 'Year of Plenty' card from your hand to gain the two specified resources
	 */
	@Override
	public void execute()
	{
		// TODO Auto-generated method stub

	}

}
