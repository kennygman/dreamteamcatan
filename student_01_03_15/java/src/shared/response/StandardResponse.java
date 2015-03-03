package shared.response;

public class StandardResponse 
{
	private boolean isValid;
	
	public StandardResponse(boolean b)
	{
		isValid = b;
	}
	
	public boolean isValid()
	{
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}


}
