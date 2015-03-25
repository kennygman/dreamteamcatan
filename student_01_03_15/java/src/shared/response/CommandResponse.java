package shared.response;

import java.util.ArrayList;
import java.util.List;

import server.commands.ICommand;

public class CommandResponse 
{
	private boolean isValid;
	private String commands;
	private List<ICommand> lists;
	
	public CommandResponse(String a, boolean b)
	{
		commands = a;
		isValid = b;
	}
	
	public boolean isValid()
	{
		return isValid;
	}
	
	public String getCommands()
	{
		return commands;
	}

	public List<ICommand> getLists() 
	{
		return lists;
	}

	public void setLists(List<ICommand> lists)
	{
		this.lists = lists;
	}
}