package shared.response;

import shared.parameters.ICommandParam;

public class CommandResponse 
{
	private boolean isValid;
	private ICommandParam[] commands;
	public CommandResponse(ICommandParam[] commands, boolean isValid)
	{
		super();
		this.isValid = isValid;
		this.commands = commands;
	}
	public boolean isValid()
	{
		return isValid;
	}
	public ICommandParam[] getCommands()
	{
		return commands;
	}

}