package server.commands;

import model.Game;
import shared.parameters.PlayMonopolyParam;

public class PlayMonopoly implements ICommand
{
	private PlayMonopolyParam param;
	
	public PlayMonopoly(PlayMonopolyParam param)
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
