package shared.parameters;

import java.util.List;

import server.commands.ICommand;

public class CommandsParam implements ICommandParam 
{
	private List<ICommand> commands;

	public CommandsParam(List<ICommand> commands)
	{
		super();
		this.commands = commands;
	}

	public List<ICommand> getCommands()
	{
		return commands;
	}
	
}
