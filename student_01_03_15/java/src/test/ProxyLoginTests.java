package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import client.proxy.Proxy;
import shared.parameters.CredentialsParam;
import shared.response.StandardResponse;

public class ProxyLoginTests 
{
	private  Proxy proxy = new Proxy();
	
	@Test
	public void test_login() 
	{
		CredentialsParam t1 = new CredentialsParam("test1","test1");
		//CredentialsParam t2 = new CredentialsParam(null, "test2");
		//CredentialsParam t3 = new CredentialsParam("test3",null);
		
		StandardResponse r1 = proxy.login(t1);
		//StandardResponse r2 = proxy.login(t2);
		//StandardResponse r3 = proxy.login(t3);
		
		assertEquals(true,r1.isValid());
		//assertEquals(false,r2.isValid());
		//assertEquals(false,r3.isValid());
	}	

}
