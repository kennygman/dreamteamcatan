package test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.commands.ICommand;
import server.facade.ServerFacade;
import shared.parameters.CommandsParam;
import shared.response.GameModelResponse;

public class GameFacadeTests 
{
	private String listOfCommands = "i need to put long string of commands here";
	private List<ICommand> commands;
	
	@Before
	public void setup()
	{
		ServerFacade.createInstance(true);
	}

	@After
	public void teardown()
	{
		
	}
	@Test
	public void resetGame()
	{
		
		
		GameModelResponse response = ServerFacade.resetGame(0);
		
		assertEquals(response.isValid(),true);
	}
	@Test
	public void commands()
	{
		CommandsParam param = new CommandsParam(commands);
		
		GameModelResponse response = ServerFacade.commands(param,0);
		
		assertEquals(response.isValid(), true);
	}

}
