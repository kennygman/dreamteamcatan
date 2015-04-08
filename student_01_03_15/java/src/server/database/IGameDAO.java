package server.database;

import model.Game;



public interface IGameDAO 
{
	/**update game table
	 *  
	 */
	boolean update(Game game);
	
	/**
	 * get informations from game table
	 */
	Game get();
	

	/**
	 * delete specific game
	 */
	boolean insert(Game game);
}
