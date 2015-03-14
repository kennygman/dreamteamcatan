package shared.parameters;

public class AddAiParam {
	private String AIType;
	
	public AddAiParam(String ai)
	{
		AIType = ai;
	}
	public String getType()
	{
		return AIType;
	}
}
