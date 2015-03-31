package server.commands;

import model.Game;
import model.player.Player;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
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
		Player player = game.getPlayer(param.getPlayerIndex());
		DevCardType card = game.getDeck().drawCard();
		
		if (card.equals(DevCardType.MONUMENT))
		{
			 player.getOldDevCards().addCard(card);
		} else {
			player.getNewDevCards().addCard(card);
		}

		player.getResources().useResource(ResourceType.ORE, 1);
		player.getResources().useResource(ResourceType.SHEEP, 1);
		player.getResources().useResource(ResourceType.WHEAT, 1);
		game.getBank().addResource(ResourceType.ORE, 1);
		game.getBank().addResource(ResourceType.SHEEP, 1);
		game.getBank().addResource(ResourceType.WHEAT, 1);
		game.increment();
		
		game.addLogEntry(player.getName(), player.getName() + " bought a development card");

	}

}
