package shared.parameters;

public class CredentialsParam {
	private String username;
	private String password;
	
	public CredentialsParam (String u, String p)
	{
		username = u;
		password = p;
	}

	public String getUser()
	{
		return username;
	}
	
	public String getPassword()
	{
		return password;
	}

	@Override
	public String toString()
	{
		return "CredentialsParam [username=" + username + ", password="
				+ password + "]";
	}
	
}
