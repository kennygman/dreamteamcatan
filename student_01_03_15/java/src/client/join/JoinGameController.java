package client.join;

import java.util.Timer;
import java.util.TimerTask;

import model.ModelFacade;
import shared.definitions.CatanColor;
import client.base.*;
import client.data.*;
import client.misc.*;
import client.roll.RollController;


/**
 * Implementation for the join game controller
 */
public class JoinGameController extends Controller implements IJoinGameController {

	private INewGameView newGameView;
	private ISelectColorView selectColorView;
	private IMessageView messageView;
	private IAction joinAction;
	private JoinGameState state;
	
	/**
	 * JoinGameController constructor
	 * 
	 * @param view Join game view
	 * @param newGameView New game view
	 * @param selectColorView Select color view
	 * @param messageView Message view (used to display error messages that occur while the user is joining a game)
	 */
	public JoinGameController(IJoinGameView view, INewGameView newGameView, 
								ISelectColorView selectColorView, IMessageView messageView) {

		super(view);

		setNewGameView(newGameView);
		setSelectColorView(selectColorView);
		setMessageView(messageView);
		state = new JoinGameState(this);
	}
	
	public IJoinGameView getJoinGameView() {
		
		return (IJoinGameView)super.getView();
	}
	
	/**
	 * Returns the action to be executed when the user joins a game
	 * 
	 * @return The action to be executed when the user joins a game
	 */
	public IAction getJoinAction() {
		
		return joinAction;
	}

	/**
	 * Sets the action to be executed when the user joins a game
	 * 
	 * @param value The action to be executed when the user joins a game
	 */
	public void setJoinAction(IAction value) {	
		
		joinAction = value;
	}
	
	public INewGameView getNewGameView() {
		
		return newGameView;
	}

	public void setNewGameView(INewGameView newGameView) {
		
		this.newGameView = newGameView;
	}
	
	public ISelectColorView getSelectColorView() {
		
		return selectColorView;
	}
	public void setSelectColorView(ISelectColorView selectColorView) {
		
		this.selectColorView = selectColorView;
	}
	
	public IMessageView getMessageView() {
		
		return messageView;
	}
	public void setMessageView(IMessageView messageView) {
		
		this.messageView = messageView;
	}

	private Timer timer = new Timer();
	private boolean isRunning;
	
	
	@Override
	public void start() {
		state.updateGameList();
		getJoinGameView().showModal();
		gameListRefresh();
	}
	private void stopTimer()
	{
		if(this.isRunning == true)
		{
			this.isRunning = false;
			timer.cancel();
			timer.purge();
		}
		
	}
	private void gameListRefresh()
	{
		this.isRunning = true;
		timer = new Timer();
		timer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				
				getJoinGameView().closeModal();
				state.updateGameList();
				getJoinGameView().showModal();
			}
		}, 0, 3000);
	}
	private void colorSelectRefresh()
	{
		this.isRunning = true;
		timer = new Timer();
		timer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				
				getSelectColorView().closeModal();
				state.disableColors();
				getSelectColorView().showModal();
			}
		}, 0, 2000);
	}

	@Override
	public void startCreateNewGame() {
		stopTimer();
		getJoinGameView().closeModal();
		getNewGameView().showModal();
	}

	@Override
	public void cancelCreateNewGame() {
		
		getNewGameView().closeModal();
        getJoinGameView().showModal();
        gameListRefresh();
	}

	@Override
	public void createNewGame() {
                if(!state.createGame())
                {
                    messageView.setTitle("Error");
                    messageView.setMessage("The game must have a name!");
                    messageView.showModal();
                }
                else
                {
                    getNewGameView().closeModal();
                    getJoinGameView().showModal();
                    gameListRefresh();
                }
	}

	@Override
	public void startJoinGame(GameInfo game) 
	{
		stopTimer();
        getJoinGameView().closeModal();
        getSelectColorView().showModal();
        colorSelectRefresh();
        state.setGame(game);
	}

	@Override
	public void cancelJoinGame() {
				stopTimer();
                getSelectColorView().closeModal();
		        getJoinGameView().showModal();
		        gameListRefresh();
	}

	@Override
	public void joinGame(CatanColor color) {
		
		if (state.joinGame(color))
		{
                    
			// If join succeeded\\
			 stopTimer();
			if (getSelectColorView().isModalShowing())
				getSelectColorView().closeModal();
            ModelFacade.getInstance().updateGameModel();
            ModelFacade.getInstance().getPoller().start();
            joinAction.execute();			
		}
	}

}

