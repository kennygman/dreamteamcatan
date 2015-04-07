package server.database;

public interface IGameDAO 
{
	/**update game table
	 *  
	 */
	void update();
	
	/**
	 * get informations from game table
	 */
	void get();
	

	/**
	 * delete specific game
	 */
	void delete();
}
