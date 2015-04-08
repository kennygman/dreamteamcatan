package server.database;

import server.User;

public interface IUserDAO 
{
	/**
	 * get informations from user table
	 */
	boolean insert(User user);
	
	/**
	 * getUser from user table
	 */
	User get(String username);
}
