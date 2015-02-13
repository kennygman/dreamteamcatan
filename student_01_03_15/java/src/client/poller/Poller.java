package client.poller;

import java.util.Timer;
import java.util.TimerTask;

import shared.response.GameModelResponse;
import model.Game;
import client.proxy.IProxy;

import com.google.gson.Gson;

/**
 * @author Cami Greenall Poller class that checks for differences between the
 *         server model and the client model
 */
public class Poller
{
	private Game serverModel;
	private Game clientModel;
	private IProxy proxyServer;
	private Timer timer;
	private int timesTimerRan;

	/**
	 * Default constructor. Creates a new updatedModelGson object
	 */
	public Poller()
	{
		this.serverModel = new Game();
		this.timer = new Timer();
		this.timesTimerRan = 0;
	}

	// NOTE: this is really sloppy because the Poller has huge dependency
	// problems.
	/**
	 * @param clientModelGson
	 * @param proxyServer
	 *            Constructor. Sets the clientModelGson and proxyServer.
	 */
	public Poller(IProxy proxyServer, Game clientModel)
	{
		this.proxyServer = proxyServer;
		this.timer = new Timer();
		this.clientModel = clientModel;
		this.timesTimerRan = 0;
		// call pollServer
		// if pollServer results differ from clientModelGson,
		// updateClientModel
	}

	/**
	 * Gets the Game Model from the server and saves it to serverModel
	 */
	public void pollServer()
	{
		GameModelResponse game = proxyServer.getGameModel();
		if(game.isValid())
		{
			this.serverModel = game.getGame();
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
		this.clientModel = this.serverModel;
	}

	/**
	 * Starts the polling service. Calls pollServer every 3-4 seconds. If
	 * pollServer results differ from clientModel update the older model to the
	 * newer model by calling updateModel(). Check using the model version
	 * number
	 */
	public void start()
	{
		setTimesTimerRan(1);
		timer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				setTimesTimerRan(getTimesTimerRan() + 1);
				pollServer();
				if (serverModel.getVersion() > clientModel.getVersion())
				{
					updateModel();
				}
			}
		}, 0, 3000);
	}

	/**
	 * Stops the polling service
	 */
	public void stop()
	{
		timer.cancel();
		timer.purge();
		setTimesTimerRan(0);
	}

	public Game getServerModel()
	{
		return serverModel;
	}

	public void setServerModel(Game serverModel)
	{
		this.serverModel = serverModel;
	}

	public Game getClientModel()
	{
		return clientModel;
	}

	public void setClientModel(Game clientModel)
	{
		this.clientModel = clientModel;
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
}
