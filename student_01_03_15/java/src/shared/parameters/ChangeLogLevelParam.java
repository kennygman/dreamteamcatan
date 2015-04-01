package shared.parameters;

public class ChangeLogLevelParam implements ICommandParam
{
	private String logLevel;

	public ChangeLogLevelParam(String l)
	{
		logLevel = l;
	}

	public String getLogLevel()
	{
		return logLevel.toUpperCase();
	}
}
