package server.commands;

import model.Game;
import shared.parameters.PlaySoldierParam;

public class PlaySoldier implements ICommand
{
	private PlaySoldierParam param;
	private Game game;
	
	public PlaySoldier(PlaySoldierParam param, Game game)
	{
		super();
		this.param = param;
		this.game=game;
	}

	/**
	 * Plays rob dev card for the player that is using it, puts the robber in the new location the player indicated and robs the selected player.
	 */
	@Override
	public void execute()
	{
		// TODO Auto-generated method stub

	}

}
