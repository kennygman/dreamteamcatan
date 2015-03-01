package client.communication;

import java.util.*;

import model.Lines;
import model.ModelFacade;
import client.base.*;
import client.data.PlayerInfo;
import shared.definitions.*;


/**
 * Game history controller implementation
 */
public class GameHistoryController extends Controller implements IGameHistoryController, Observer {

	public GameHistoryController(IGameHistoryView view) {
		
		super(view);
		
		ModelFacade.getInstance().addObserver(this);
	}
	
	//--------------------------------------------------------------------------------
	@Override
	public IGameHistoryView getView() {
		
		return (IGameHistoryView)super.getView();
	}
	
	//--------------------------------------------------------------------------------
	@Override
	public void update(Observable arg0, Object arg1)
	{
		initFromModel();
	}
	
	//--------------------------------------------------------------------------------
	private void initFromModel() {
		
		List<Lines> logEntries = ModelFacade.getInstance().getGame().getLog().getLines();
		getView().setEntries(convertLog(logEntries));
	
	}

	//--------------------------------------------------------------------------------
	public List<LogEntry> convertLog(List<Lines> lines)
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

