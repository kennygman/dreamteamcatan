package test;
//Tests
import static org.junit.Assert.*;
import model.Game;
import model.ModelFacade;
import model.player.Player;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import client.poller.Poller;
import client.proxy.IProxy;
import client.proxy.MockProxy;

public class PollerUnitTests
{
	private static Poller poller;
	private static ModelFacade modelFacade;
	private static IProxy mockProxy;

	@Before
	public void setup()
	{
		int versionNumber = 1;
		mockProxy = new MockProxy();
		modelFacade = new ModelFacade(mockProxy);
		poller = new Poller(mockProxy, modelFacade);
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
		game1 = modelFacade.getGame();
		game1.setVersion(test);
		poller.setServerModel(game1);
		poller.updateModel();
		assertEquals(modelFacade.getGame().getVersion(), test);
	}

	@Test
	public void testStart()
	{
		int test = -4212012;
		poller.setClientVersion(test);
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
