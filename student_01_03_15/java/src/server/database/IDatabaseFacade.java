package server.database;

import model.Game;
import server.User;
import shared.parameters.ICommandParam;

public interface IDatabaseFacade
{
	/**
	 * This method adds a user to the database
	 * @param user User object with name and password
	 */
	void insertUser(User user);
	
	/**
	 * This method adds a game to the database
	 * @param data the initial game blob
	 */
	void insertGame(String data);
	
	/**
	 * This method adds a command to the database
	 * @param id the game id
	 * @param data the command blob
	 */
	void insertCommand(int id, String data);
	
	/**
	 * This method fetches all users from the database
	 * @return a list of User objects
	 */
	User[] getUsers();
	
	/**
	 * This method fetches a game from the database
	 * @param id the game id
	 * @return a Game object
	 */
	Game getGame(int id);
	
	/**
	 * This method fetches all commands for the specified game
	 * @param id the game id
	 * @return a list of ICommandParam objects
	 */
	ICommandParam[] getCommands(int id);
	
	/**
	 * This method updates the game
	 * @param id the game id
	 * @param blob the game json
	 */
	void updateGame(int id, String blob);
	
	/**
	 * This method deletes a user from the database
	 * @param name the user's name
	 */
	void deleteUser(String name);
	
	/**
	 * This method deletes a game from the database
	 * @param id the game id
	 */
	void deleteGame(int id);
	
	/**
	 * This method deletes all commands from the database for the specified game
	 * @param id the game id
	 */
	void deleteCommands(int id);
}
	
