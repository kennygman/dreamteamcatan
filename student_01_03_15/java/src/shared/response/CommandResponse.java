package shared.response;

import java.util.ArrayList;

public class CommandResponse 
{
	private boolean isValid;
	private String commands;
	
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
}