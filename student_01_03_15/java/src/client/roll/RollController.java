package client.roll;

import java.util.Random;

import client.base.*;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import model.ModelFacade;


/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController, Observer {

	private IRollResultView resultView;
	private Timer timer;
	boolean hasRolled = false;

	/**
	 * RollController constructor
	 * 
	 * @param view Roll view
	 * @param resultView Roll result view
	 */
	public RollController(IRollView view, IRollResultView resultView) {

		super(view);
		this.timer = new Timer();
		ModelFacade.getInstance().addObserver(this);
		setResultView(resultView);
	}
	
	public IRollResultView getResultView() {
		return resultView;
	}
	public void setResultView(IRollResultView resultView) {
		this.resultView = resultView;
	}

	public IRollView getRollView() {
		return (IRollView)getView();
	}
	
	@Override
	public void rollDice() {
		if(hasRolled == false)
		{
			hasRolled = true;
			timer.cancel();
			Random generator = new Random(System.currentTimeMillis());
			int dice1 = generator.nextInt(6 - 1 + 1) + 1;
			int dice2 = generator.nextInt(6 - 1 + 1) + 1;
			int total = dice1 + dice2;
			resultView.setRollValue(total);
			getResultView().showModal();
                        
			ModelFacade.getInstance().rollNumber(dice1, dice2);
		}
	}
	
	public void runRollTimer()
	{
		class TimerToDo extends TimerTask
		{
			RollController control;
			TimerToDo(RollController cont){
				this.control = cont;
			}
			
			@Override
			public void run()
			{
				getRollView().closeModal();
				rollDice();
			}
			
		}
		
		TimerToDo task = new TimerToDo(this);
		timer = new Timer();
		timer.schedule(task, 5000);
	}
	

        @Override
        public void update(Observable o, Object o1) 
        {
        	if(ModelFacade.getInstance().CanRollNumber())
        	{
        		hasRolled = false;
        		this.getRollView().showModal();
        		this.runRollTimer();
        	}
        }        	
}

