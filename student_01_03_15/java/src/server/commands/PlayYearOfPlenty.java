package server.commands;

import model.Game;
import shared.parameters.PlayYearOfPlentyParam;

public class PlayYearOfPlenty implements ICommand
{
	private PlayYearOfPlentyParam param;

	public PlayYearOfPlenty(PlayYearOfPlentyParam param)
{
	super();
	this.param = param;
}

	/**
	 * Plays a 'Year of Plenty' card from your hand to gain the two specified resources
	 */
	@Override
	public void execute(Game game)
	{
		// TODO Auto-generated method stub

	}

}
