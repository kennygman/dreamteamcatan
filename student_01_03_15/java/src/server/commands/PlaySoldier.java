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

	/**
	 * Plays rob dev card for the player that is using it, puts the robber in the new location the player indicated and robs the selected player.
	 */
	@Override
	public void execute(Game game)
	{
		// TODO Auto-generated method stub

	}

}
