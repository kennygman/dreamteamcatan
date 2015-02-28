package client.roll;

import java.util.Random;

import client.base.*;
import java.util.Observable;
import java.util.Observer;
import model.ModelFacade;


/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController, Observer {

	private IRollResultView resultView;

	/**
	 * RollController constructor
	 * 
	 * @param view Roll view
	 * @param resultView Roll result view
	 */
	public RollController(IRollView view, IRollResultView resultView) {

		super(view);
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
		Random generator = new Random(System.currentTimeMillis());
		int dice1 = generator.nextInt(6 - 1 + 1) + 1;
		int dice2 = generator.nextInt(6 - 1 + 1) + 1;
		int total = dice1 + dice2;
		resultView.setRollValue(total);
		getResultView().showModal();
	}

        @Override
        public void update(Observable o, Object o1) {
            
        }
}

