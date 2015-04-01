package server.commands;

import model.Game;
import model.TurnTracker;
import model.player.Player;
import model.player.Resources;
import shared.parameters.AcceptTradeParam;
import shared.parameters.ICommandParam;

public class AcceptTrade implements ICommand
{

	private AcceptTradeParam param;
	private transient Game game;
	
	public AcceptTrade(AcceptTradeParam param, Game game)
	{
		super();
		this.param = param;
		this.game=game;
	}

	/**
	 * Swaps the resources for the players in the trade if the trade was accepted
	 * then removes the trade offer from the game.
	 */
	@Override
	public void execute()
	{
		Player sender = game.getPlayer(game.getTradeOffer().getSender());
		Player receiver = game.getPlayer(game.getTradeOffer().getReceiver());
		Resources offer = game.getTradeOffer().getOffer();
		if (param.isWillAccept())
		{
			//System.out.println("AcceptTrade (sender): " + sender.getName());
			sender.getResources().doTrade(offer);
			//System.out.println("AcceptTrade (receiver): " + receiver.getName());
			receiver.getResources().doTrade(offer.invert());

			game.addLogEntry(receiver.getName(), receiver.getName() + " has accepted the trade");
		}
		else
		{
			game.addLogEntry(receiver.getName(), receiver.getName() + " has declined the trade");
		}
		game.setTradeOffer(null);
		game.getTurnTracker().setStatus(TurnTracker.PLAYING);
		game.increment();

	}
	@Override
	public ICommandParam getParam()
	{
		return param;
	}


}
