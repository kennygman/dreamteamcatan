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
import model.ModelFacade;

public class PollerUnitTests
{
	private static Poller poller;
	private static IProxy mockProxy;

	@Before
	public void setup()
	{
		int versionNumber = 1;
		mockProxy = new MockProxy();
		ModelFacade.createInstance(mockProxy);
		poller = new Poller(mockProxy);
	}

	@After
	public void teardown()
	{
//		poller.stop();
	}

	@Test
	public void testPollServer()
	{
		poller.pollServer();
		int version = poller.getServerVersion();
		assertTrue(version >= 0);
	}

	@Test
	public void testUpdateModel()
	{
		Game game1 = new Game();
		int test = 4212012;
		game1 = ModelFacade.getInstance().getGame();
		game1.setVersion(test);
		poller.setServerVersion(game1.getVersion());
		poller.updateModel();
		assertEquals(ModelFacade.getInstance().getGame().getVersion(), test);
	}

	@Test
	public void testStart()
	{
		int test = -4212012;
		poller.setClientVersion(test);
		poller.pollerStart();
		assertTrue(poller.getTimesTimerRan() > 0);
	}

	@Test
	public void testStop()
	{
//		poller.stop();
		assertTrue(poller.getTimesTimerRan() == 0);
	}
}
