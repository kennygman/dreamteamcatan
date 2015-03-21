package server.commands;

import shared.parameters.SendChatParam;
import model.Game;
import model.Lines;

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
		Lines line = new Lines();
		line.setMessage(param.getMessae());
		line.setSource(game.getPlayer(param.getSource()).getName());
		game.getChat().addLine(line);
	}

}
