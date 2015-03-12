package server.commands;

import model.Game;
import shared.parameters.PlaySoldierParam;

public class PlaySoldier implements ICommand
{
	private PlaySoldierParam param;
	
	public PlaySoldier(PlaySoldierParam param)
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
