package shared.parameters;

public class PostCommandsParam implements ICommandParam {
	// For testing
	private String commands;
	
	public PostCommandsParam(String c)
	{
		commands = c;
	}
	
	public String getCommands()
	{
		return this.commands;
	}
}
