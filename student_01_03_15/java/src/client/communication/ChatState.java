package client.communication;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import model.Game;
import model.Lines;
import model.ModelFacade;
import shared.definitions.CatanColor;
import shared.parameters.SendChatParam;
import client.data.PlayerInfo;

public class ChatState implements Observer
{
	private ChatController controller;
	
	//--------------------------------------------------------------------------------
	public ChatState(ChatController controller)
	{
		this.controller = controller;
		ModelFacade.getInstance().addObserver(this);
	}
	
	//--------------------------------------------------------------------------------
	@Override
	public void update(Observable arg0, Object arg1)
	{
		this.updateChat();
	}

	//--------------------------------------------------------------------------------
	public void updateChat()
	{
		Game game = ModelFacade.getInstance().getGame();
		if (game == null) return;
		controller.getView().setEntries(convertChat(game.getChat().getLines()));
	}
	
	//--------------------------------------------------------------------------------
	public void sendMessage(String message)
	{
		int index = ModelFacade.getInstance().getPlayerInfo().getPlayerIndex();
		ModelFacade.getInstance().sendChat(new SendChatParam(index,message));
	}
	
	//--------------------------------------------------------------------------------
	public List<LogEntry> convertChat(Lines[] lines)
	{
		List<LogEntry> log = new ArrayList<>();
		for (Lines line : lines)
		{
			log.add(new LogEntry(getColor(line.getSource()), line.getMessage()));
		}
		return log;
	}
	
	//--------------------------------------------------------------------------------
	public CatanColor getColor(String user)
	{
		for (PlayerInfo p : ModelFacade.getInstance().getGameInfo().getPlayers())
		{
			if (p.getName().equals(user))
			{
				return p.getColor();
			}
		}
		return CatanColor.WHITE;
	}

	//--------------------------------------------------------------------------------
}
