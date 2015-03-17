package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.facade.ServerFacade;
import shared.parameters.CredentialsParam;
import shared.response.LoginResponse;

public class UserFacadeTests
{
	
	@Before
	public void setup()
	{
		ServerFacade.createInstance(true);
	}
	
	@After
	public void teardown(){}

	@Test
	public void testRegister()
	{
		CredentialsParam param1 = new CredentialsParam("Sam", "sam");
		CredentialsParam param2 = new CredentialsParam("Pete", "pete");
		CredentialsParam param3 = new CredentialsParam("Sam", "foo");
		CredentialsParam param4 = new CredentialsParam(null, null);
		
		
		LoginResponse response1 = ServerFacade.register(param1);
		LoginResponse response2 = ServerFacade.register(param2);
		LoginResponse response3 = ServerFacade.register(param3);
		LoginResponse response4 = ServerFacade.register(param4);
		
		assertEquals(response1.getPlayerInfo().getId(), 1);
		assertEquals(response2.getPlayerInfo().getId(), 2);
		assertEquals(response3.isValid(), false);
		assertEquals(response4.isValid(), false);
	}
	
	@Test
	public void testLogin()
	{
		CredentialsParam param1 = new CredentialsParam("Sam", "sam");
		CredentialsParam param2 = new CredentialsParam("Pete", "pete");
		CredentialsParam param3 = new CredentialsParam("Sam", "foo");
		CredentialsParam param4 = new CredentialsParam("Bob", "meh");
		
		LoginResponse response1 = ServerFacade.login(param1);
		LoginResponse response2 = ServerFacade.login(param2);
		LoginResponse response3 = ServerFacade.login(param3);
		LoginResponse response4 = ServerFacade.login(param4);
		
		assertEquals(response1.getPlayerInfo().getPlayerIndex(), 1);
		assertEquals(response2.getPlayerInfo().getPlayerIndex(), 2);
		assertEquals(response3.isValid(), false);
		assertEquals(response4.isValid(), false);
	}

}
