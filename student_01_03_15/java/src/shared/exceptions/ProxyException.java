package shared.exceptions;

public class ProxyException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private String message;
	public ProxyException(String errorMessage)
	{
		message = errorMessage;
	}
	
	public String getMessage()
	{
		return message;
	}
}
