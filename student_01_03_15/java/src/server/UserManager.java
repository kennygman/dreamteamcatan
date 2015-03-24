package server;

import java.util.HashMap;
import java.util.Map;

import shared.parameters.CredentialsParam;

public class UserManager
{
	private Map<String, User> users;
	
	public UserManager()
	{
		users = new HashMap<>();
	}
	
	public void initAi()
	{
		String[] aiNames = {"Sam", "Pete", "Sven", "Kunkka", "Ember Spirit", "Pudge", "Techies"};
		for (String s : aiNames)
		{
			User user = new User(-(users.size() + 1), s, s.toLowerCase());
			users.put(s, user);
		}
	}
	
	/**
	 * Validate if the user can be added
	 * Add user to the database
	 * @param param user Credentials
	 * @return User: If valid user then Populated User Object, null otherwise
	 */
	public User add(CredentialsParam param)
	{
		String n = param.getUser();
		String p = param.getPassword();

		if (canAddUser(n, p))
		{
			User user = new User(users.size()+1,n,p);
			users.put(n, user);
			return user;
		}
		return null;
	}

	/**
	 * Validates if the credentials match a valid user
	 * @param param user's credentials
	 * @return User object: populated if valid, null otherwise
	 */
	public User getUser(CredentialsParam param)
	{
		User user = null;
		if (param.getUser() != null && param.getPassword() != null && users.containsKey(param.getUser()))
		{
			User query = users.get(param.getUser());
			if (query.getPassword().equals(param.getPassword())) user = query;
		}
		return user;
	}
	
	/**
	 * Validate parameters
	 * @param n name
	 * @param p password
	 * @return True if parameters not null and if the name has not already been taken, False otherwise
	 */
	private boolean canAddUser(String n, String p)
	{
		if (n == null || p == null) return false;
		return !users.containsKey(n);
	}
	public User getAi(int id)
	{
		User user = null;
		int AIid = id*(-1);
		
		user = users.get(AIid);
		
		return user;
	}
}
