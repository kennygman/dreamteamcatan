package server.database;

import java.util.List;
import shared.parameters.ICommandParam;

public interface ICommandsDAO 
{
	/**
	 * insert specific commands
	 */
	boolean insert(ICommandParam command);
	
	/**
	 * get Commands from Table
	 */
	List<ICommandParam> get(int gameId);
	

	/**
	 *  delete commands from command table
	 */
	boolean delete(int gameId);
}
