package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.database.DatabaseFacade;
import shared.parameters.ICommandParam;
import model.Game;

public class GameManager
{
	private List<Game> games;
	private Map<Integer, List<ICommandParam>> commands;
	private int limit;
	
	public GameManager()
	{
		games = new ArrayList<>();
		commands = new HashMap<>();
	}

	public void setLimit(int i)
	{
		limit = i;
	}
	
	/**
	 * This method synchronizes recent commands with the database. If the list is a multiple
	 * of the set limit then synchronize the latest commands in the list
	 * @param id the game id
	 * @param cl the command list
	 */
	public void synch(int id, List<ICommandParam> cl)
	{
		if (cl.size() % limit != 0) return; 
		String data = "";
		DatabaseFacade.getInstance().insertCommand(id, data);
	}
	
	/**
	 * This method is used for updating the game after a reset
	 * 
	 * @param index
	 *            game id
	 * @param game
	 *            the updated Game
	 * @return Game object with the specified id, null if invalid id
	 */
	public Game updateGame(int index, Game game)
	{
		if (!isValidGameId(index))
			return null;
		games.set(index, game);
		return games.get(index);
	}

	/**
	 * Return the Game object with the specified id
	 * 
	 * @param index
	 *            Game id
	 * @return Game object with the specified id, null if invalid id
	 */
	public Game getGame(int index)
	{
		if (!isValidGameId(index))
			return null;
		return games.get(index);
	}

	/**
	 * Adds game to the list
	 * 
	 * @param game
	 * @return True if game was added successfully, False otherwise
	 */
	public boolean addGame(Game game)
	{
		if (canAddGame(game))
		{
			games.add(game);
			return true;
		}
		return false;
	}

	/**
	 * Appends command to list for the specified game
	 * Send the list of commands off for synchronization
	 * 
	 * @param id
	 *            the game's id
	 * @param command
	 *            the command
	 */
	
	public void addCommand(int id, ICommandParam param)
	{
		List<ICommandParam> list = commands.get(id);
		if (list == null)
			list = new ArrayList<ICommandParam>();
		list.add(param);
		commands.put(id, list);
		
		synch(id, list);
	}

	/**
	 * Returns the list of commands for the specified game
	 * 
	 * @param id
	 *            the game's id
	 * @return the list
	 */
	public List<ICommandParam> getCommands(int id)
	{
		return commands.get(id);
	}

	/**
	 * Validates if game can be added
	 * 
	 * @param game
	 * @return True if game with title does not already exist, False otherwise
	 */
	private boolean canAddGame(Game game)
	{
		return game.getTitle() != null;
	}

	/**
	 * Validates the game id
	 * 
	 * @param index
	 *            the game id
	 * @return True if valid, false otherwise
	 */
	private boolean isValidGameId(int index)
	{
		return (index >= 0 && index < games.size());
	}

	public int gamesSize()
	{
		return games.size();
	}

	public int getGameId(Game game)
	{
		return games.indexOf(game);
	}

	public List<Game> getGames()
	{
		return games;
	}
}
