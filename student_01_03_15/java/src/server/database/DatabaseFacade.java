package server.database;

import model.Game;
import server.User;
import shared.parameters.ICommandParam;

public class DatabaseFacade implements IDatabaseFacade
{
	private static DatabaseFacade instance;

	/**
	 * This method implements the Database Facade singleton
	 */
	public static void initialize()
	{
		instance = new DatabaseFacade();
	}

	/**
	 * This method returns the singleton instance of the DatabaseFacade
	 * 
	 * @return the instance
	 */
	public static DatabaseFacade getInstance()
	{
		return instance;
	}

	@Override
	public void insertUser(User user)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void insertGame(String data)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void insertCommand(int id, String data)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public User[] getUsers()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Game getGame(int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICommandParam[] getCommands(int id)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateGame(int id, String blob)
	{
		Database.getInstance().getGameDAO().update();
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteUser(String name)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteGame(int id)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCommands(int id)
	{
		// TODO Auto-generated method stub

	}

}
