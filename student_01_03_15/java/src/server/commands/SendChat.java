package server.commands;

import shared.parameters.SendChatParam;
import model.Game;

public class SendChat implements ICommand
{
	private SendChatParam param;
	private Game game;

	public SendChat(SendChatParam param, Game game)
	{
		super();
		this.param = param;
		this.game=game;
	}
	
	/**
	 * Sends a chat message
	 */
	@Override
	public void execute()
	{
		// TODO Auto-generated method stub

	}

}
