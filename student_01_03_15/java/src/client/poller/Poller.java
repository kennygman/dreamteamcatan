package client.poller;

import java.util.Timer;
import java.util.TimerTask;

import shared.response.GameModelResponse;
import model.ModelFacade;
import client.proxy.IProxy;

/**
 * @author Cami Greenall Poller class that checks for differences between the
 *         server model and the client model
 */
public class Poller
{
	private int serverVersion;
	private int clientVersion;
	private IProxy proxyServer;
	private Timer timer;
	private int timesTimerRan;
	private int timeDelay;
	private boolean isRunning;

	private static int SPEED = 3000;

	/**
	 * @param clientModelGson
	 * @param proxyServer
	 *            Constructor. Sets the clientModelGson and proxyServer.
	 */
	public Poller(IProxy proxyServer)
	{
		this.proxyServer = proxyServer;
		this.timer = new Timer();
		this.clientVersion = 0;
		this.timesTimerRan = 0;
		this.isRunning = false;
	}

	/**
	 * Gets the Game Model from the server and saves it to serverModel
	 */
	public void pollServer()
	{
		GameModelResponse game = proxyServer.getGameModel();
		if (game.isValid())
		{
			this.serverVersion = game.getGame().getVersion();
		}
	}

	/**
	 * @param oldModel
	 * @param newModel
	 *            Sets the value of the newModel to the oldModel. Whichever has
	 *            the lower version is the oldModel.
	 */
	public void updateModel()
	{
		ModelFacade.getInstance().updateGameModel();
		this.clientVersion = ModelFacade.getInstance().getGame().getVersion();
	}

	/**
	 * Starts the polling service. Calls pollServer every 3-4 seconds. If
	 * pollServer results differ from clientModel update the older model to the
	 * newer model by calling updateModel(). Check using the model version
	 * number
	 */
	public void pollerStart()
	{
		if (isRunning) return;
		System.out.println("Poller started");
		this.isRunning = true;
		timer = new Timer();
		setTimesTimerRan(1);
		timer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				try
				{
					setTimesTimerRan(getTimesTimerRan() + 1);
					pollServer();
					if (serverVersion > clientVersion)
					{
						updateModel();
					} else if (!ModelFacade.getInstance().isGameFull())
					{
						updateModel();
					}
					ModelFacade.getInstance().modelChanged();
				} catch (Exception e){e.printStackTrace();}
			}
		}, 0, SPEED);
	}

	/**
	 * Stops the polling service
	 */
	public void stop()
	{
		if (!isRunning) return;
		if (this.isRunning == true)
		{
			System.out.println("Poller stopped");
			this.isRunning = false;
			timer.cancel();
			timer.purge();
			setTimesTimerRan(0);
		}
	}
	
	public boolean isRunning()
	{
		return isRunning;
	}

	public void setRunning(boolean isRunning)
	{
		this.isRunning = isRunning;
	}

	public int getServerVersion()
	{
		return serverVersion;
	}

	public void setServerVersion(int serverVersion)
	{
		this.serverVersion = serverVersion;
	}

	public int getClientVersion()
	{
		return clientVersion;
	}

	public void setClientVersion(int clientVersion)
	{
		this.clientVersion = clientVersion;
	}

	public IProxy getProxyServer()
	{
		return proxyServer;
	}

	public void setProxyServer(IProxy proxyServer)
	{
		this.proxyServer = proxyServer;
	}

	public Timer getTimer()
	{
		return timer;
	}

	public void setTimer(Timer timer)
	{
		this.timer = timer;
	}

	public int getTimesTimerRan()
	{
		return timesTimerRan;
	}

	public void setTimesTimerRan(int timesTimerRan)
	{
		this.timesTimerRan = timesTimerRan;
	}

	public int getTimeDelay()
	{
		return timeDelay;
	}

	public void setTimeDelay(int timeDelay)
	{
		this.timeDelay = timeDelay;
	}
}
