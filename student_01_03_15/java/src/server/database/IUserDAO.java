package server.database;

public interface IUserDAO 
{
	/**update user table
	 *  
	 */
	void update();
	
	/**
	 * get informations from user table
	 */
	void get();
	

	/**
	 * delete specific user
	 */
	void delete();
}
