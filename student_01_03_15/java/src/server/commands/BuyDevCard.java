package server.commands;

import model.Game;
import shared.parameters.BuyDevCardParam;

public class BuyDevCard implements ICommand
{
	private BuyDevCardParam param;
	private Game game;
	
	public BuyDevCard(BuyDevCardParam param, Game game)
	{
		super();
		this.param = param;
		this.game = game;
	}

	/**
	 * Buys dev card for player, decreases resources, adds card to player inventory
	 */
	@Override
	public void execute()
	{
		// TODO Auto-generated method stub
		
	}

}
