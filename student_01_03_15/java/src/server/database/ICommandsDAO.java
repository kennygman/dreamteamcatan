package server.database;

import shared.parameters.ICommandParam;

public interface ICommandsDAO 
{
	/**
	 * inser specific commands
	 */
	boolean insert(ICommandParam command);
	
	/**
	 * get Commands from Table
	 */
	ICommandParam get();
	

	/**
	 *  delete commands from command table
	 */
	boolean delete(ICommandParam command);
}
