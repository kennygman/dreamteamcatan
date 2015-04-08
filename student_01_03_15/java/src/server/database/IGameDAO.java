package server.database;

import java.util.List;
import model.Game;



public interface IGameDAO 
{
	/**update game table
	 *  
	 */
	boolean update(Game game);
	
	/**
	 * get games from game table
	 */
	List<Game> getGames();
	
        /**
	 * get games from game table
	 */
        Game getInitalGame(int gameId);

	/**
	 * delete specific game
	 */
	boolean insert(Game game);
}
