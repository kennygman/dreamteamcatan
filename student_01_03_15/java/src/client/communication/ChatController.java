package client.communication;

import java.util.ArrayList;
import java.util.List;

import shared.parameters.SendChatParam;
import model.Chat;
import model.Lines;
import model.ModelFacade;
import client.base.*;


/**
 * Chat controller implementation
 */
public class ChatController extends Controller implements IChatController {

	public ChatController(IChatView view) {
		
		super(view);
	}

	@Override
	public IChatView getView() {
		return (IChatView)super.getView();
	}

	@Override
	public void sendMessage(String message) {
		ModelFacade.getInstance().getGame().getPlayer().getPlayerIndex();
		ModelFacade.getInstance().sendChat(new SendChatParam(
				ModelFacade.getInstance().getPlayerInfo().getPlayerIndex(),
				message));
		
		Chat chat = ModelFacade.getInstance().getGame().getChat();
		this.getView().setEntries(convertChat(chat.getLines()));
	}
	
	public List<LogEntry> convertChat(Lines[] lines)
	{
		List<LogEntry> log = new ArrayList<>();
		for (Lines line : lines)
		{
		}
		return log;
	}

}

