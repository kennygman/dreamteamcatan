package server;

import java.util.ArrayList;
import java.util.List;

import server.commands.ICommand;
import model.Game;

public class GameManager
{
	private List<Game> games;
	private List<ICommand> commands;
	
	public GameManager()
	{
		games = new ArrayList<>();
		commands = new ArrayList<>(); 
	}
	
	/**
	 * This method is used for updating the game after a reset
	 * @param index game id
	 * @param game the updated Game
	 * @return Game object with the specified id, null if invalid id
	 */
	public Game updateGame(int index, Game game)
	{
		if (!isValidGameId(index)) return null;
		games.set(index, game);
		return games.get(index);
	}
	
	/**
	 * Return the Game object with the specified id
	 * @param index Game id
	 * @return Game object with the specified id, null if invalid id
	 */
	public Game getGame(int index)
	{
		if (!isValidGameId(index)) return null;
		return games.get(index);
	}
	
	/**
	 * Adds game to the list
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
	 * Appends command to the list
	 * @param command the command
	 */
	public void addCommand(ICommand command)
	{
		commands.add(command);
	}
	
	/**
	 * Returns the list of commands
	 * @return the list
	 */
	public List<ICommand> getCommands()
	{
		return commands;
	}
	
	/**
	 * Validates if game can be added
	 * @param game
	 * @return True if game with title does not already exist, False otherwise
	 */
	private boolean canAddGame(Game game)
	{
		return game.getTitle() != null;
	}
	
	/**
	 * Validates the game id
	 * @param index the game id
	 * @return True if valid, false otherwise
	 */
	private boolean isValidGameId(int index)
	{
		return (index >= 0 && index < games.size());
	}
}
