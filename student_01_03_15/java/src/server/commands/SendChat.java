package server.commands;

import shared.parameters.SendChatParam;
import model.Game;

public class SendChat implements ICommand
{
	private SendChatParam param;

	public SendChat(SendChatParam param)
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
