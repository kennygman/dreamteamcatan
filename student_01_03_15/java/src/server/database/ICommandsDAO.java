package server.database;

public interface ICommandsDAO 
{
	/**update commands table
	 *  
	 */
	void update();
	
	/**
	 * get informations from commands table
	 */
	void get();
	

	/**
	 * delete specific commands
	 */
	void delete();

}
