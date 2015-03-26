package shared.parameters;

import server.commands.ICommand;

public class CommandsParam
{
	ICommand[] commands;
	public CommandsParam(ICommand[] commands)
	{
		this.commands=commands;
	}
	public ICommand[] getCommands()
	{
		return commands;
	}
}
