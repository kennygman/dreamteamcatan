package client.roll;

import java.util.Random;

import client.base.*;
import client.poller.Poller;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import model.ModelFacade;

/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController,
		Observer
{

	private IRollResultView resultView;
	private Timer timer;

	/**
	 * RollController constructor
	 * 
	 * @param view
	 *            Roll view
	 * @param resultView
	 *            Roll result view
	 */
	public RollController(IRollView view, IRollResultView resultView)
	{

		super(view);
		this.timer = new Timer();
		ModelFacade.getInstance().addObserver(this);
		setResultView(resultView);
	}

	public IRollResultView getResultView()
	{
		return resultView;
	}

	public void setResultView(IRollResultView resultView)
	{
		this.resultView = resultView;
	}

	public IRollView getRollView()
	{
		return (IRollView) getView();
	}

	@Override
	public void rollDice()
	{

		try
		{
			timer.cancel();
			Random generator = new Random(System.currentTimeMillis());
			int dice1 = generator.nextInt(6) + 1;
                        int dice2 = generator.nextInt(6) + 1;
			int total = dice1 + dice2;
			//resultView.setRollValue(total);
                        resultView.setRollValue(7);

			getResultView().showModal();
		} catch (IllegalStateException e)
		{

		}

	}

	public void runRollTimer()
	{
		class TimerToDo extends TimerTask
		{
			RollController control;

			TimerToDo(RollController cont)
			{
				this.control = cont;
			}

			@Override
			public void run()
			{
				if (getRollView().isModalShowing())
				{
					getRollView().closeModal();
				}
				rollDice();
			}
		}

		TimerToDo task = new TimerToDo(this);
		timer = new Timer();
		timer.schedule(task, 5000);
		final Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask()
		{
			int i = 5;

			public void run()
			{
				getRollView().setMessage(
						"Rolling automatically in... " + i + " seconds");
				System.out.println(i--);
				if (i < 0)
					timer.cancel();
			}
		}, 0, 1000);
	}

	@Override
	public void update(Observable o, Object o1)
	{
		if (ModelFacade.getInstance().CanRollNumber())
		{
			if (!getRollView().isModalShowing())
			{
				ModelFacade.getInstance().getPoller().stop();
				this.getRollView().showModal();
				this.runRollTimer();
			}
		}
	}
}
