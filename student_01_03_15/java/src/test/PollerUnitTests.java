package test;
//Tests
import static org.junit.Assert.*;
import model.Game;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import client.poller.Poller;
import client.proxy.IProxy;
import client.proxy.MockProxy;

public class PollerUnitTests
{
	private static Poller poller;
	private static Game game;
	private static IProxy mockProxy;

	@Before
	public void setup()
	{
		game = new Game();
		int versionNumber = 1;
		game.setVersion(versionNumber);
		mockProxy = new MockProxy();
		poller = new Poller(mockProxy, game);
	}

	@After
	public void teardown()
	{
		poller.stop();
	}

	@Test
	public void testPollServer()
	{
		poller.pollServer();
		int version = poller.getServerModel().getVersion();
		assertTrue(version >= 0);
	}

	@Test
	public void testUpdateModel()
	{
		Game game1 = new Game();
		int test = 4212012;
		game1.setVersion(test);
		poller.setServerModel(game1);
		poller.updateModel();
		assertEquals(poller.getClientModel().getVersion(), test);
	}

	@Test
	public void testStart()
	{
		Game game1 = new Game();
		int test = -4212012;
		game1.setVersion(test);
		poller.setClientModel(game1);
		poller.start();
		assertTrue(poller.getTimesTimerRan() > 0);
	}

	@Test
	public void testStop()
	{
		poller.stop();
		assertTrue(poller.getTimesTimerRan() == 0);
	}
}
