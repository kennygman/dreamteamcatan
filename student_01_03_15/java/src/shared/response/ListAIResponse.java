package shared.response;

import java.util.ArrayList;

public class ListAIResponse {
	private boolean isValid;
	private String[] aiTypes;
	
	public ListAIResponse(String[] a, boolean b)
	{
		aiTypes = a;
		isValid = b;
	}
	
	public boolean isValid()
	{
		return isValid;
	}

}