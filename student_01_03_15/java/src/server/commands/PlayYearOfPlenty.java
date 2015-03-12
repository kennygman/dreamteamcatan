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

	@Override
	public void execute(Game game)
	{
		// TODO Auto-generated method stub

	}

}
