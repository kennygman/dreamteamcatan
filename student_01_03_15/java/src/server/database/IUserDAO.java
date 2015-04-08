package server.database;

import server.User;

public interface IUserDAO 
{
	/**update user table
	 *  
	 */
	boolean update(User user);
	/**
	 * get informations from user table
	 */
	boolean insert(User user);
	
	/**
	 * getUser from user table
	 */
	User get();
}
