package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import client.proxy.Proxy;
import shared.parameters.CreateGameParam;
import shared.parameters.CredentialsParam;
import shared.parameters.JoinGameParam;
import shared.response.CreateGameResponse;
import shared.response.GameListObject;
import shared.response.ListGamesResponse;
import shared.response.StandardResponse;

public class ProxyRegisterTests
{
	private  Proxy proxy = new Proxy();
	
	@Test
	public void test_register()
	{
		CredentialsParam t1 = new CredentialsParam("test1","test1");
		CredentialsParam t2 = new CredentialsParam("test2","test2");
		CredentialsParam t3 = new CredentialsParam("test3","test3");
		CredentialsParam t4 = new CredentialsParam("test4","test4");
		
		//CredentialsParam t5 = new CredentialsParam(null, "test2");
		//CredentialsParam t6 = new CredentialsParam("test3",null);
		
		StandardResponse r1 = proxy.register(t1);
		StandardResponse r2 = proxy.register(t2);
		StandardResponse r3 = proxy.register(t3);
		StandardResponse r4 = proxy.register(t4);
		//StandardResponse r5 = proxy.register(t5);
		//StandardResponse r6 = proxy.register(t6);
		
		assertEquals(true,r1.isValid());
		assertEquals(true,r2.isValid());
		assertEquals(true,r3.isValid());
		assertEquals(true,r4.isValid());
		//assertEquals(false,r5.isValid());
		//assertEquals(false,r6.isValid());	
	}
	


}
