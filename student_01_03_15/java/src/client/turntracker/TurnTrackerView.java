package client.turntracker;

import java.awt.*;

import javax.swing.*;

import model.ModelFacade;
import shared.definitions.*;
import client.base.*;
import client.catan.*;
import client.utils.ImageUtils;
import model.player.Player;


/**
 * Implementation for the turn tracker view, which displays whose turn it is, and what state the game is in
 */
@SuppressWarnings("serial")
public class TurnTrackerView extends PanelView implements ITurnTrackerView {

	private TitlePanel titlePanel;
	private GameStatePanel gameStatePanel;
	private JPanel [] playerPanel;
	private JLabel [] playerPoints;
	private JLabel [] playerRoad;
	private JLabel [] playerArmy;
	private Image longestRoadImage;
	private Image largestArmyImage;

	private final int NUM_PLAYERS = 4;
	private final int FONT_SIZE = 13;
	
	public TurnTrackerView(TitlePanel titlePanel, GameStatePanel gameStatePanel) 
	{
		
		this.titlePanel = titlePanel;
		this.gameStatePanel = gameStatePanel;
		
		this.setPreferredSize(new Dimension(350, 100));
		this.setLayout(new GridLayout(2,2,3,3));
		this.setBorder(BorderFactory.createEmptyBorder(3,3,3,3)); 
		
		playerPanel = new JPanel[NUM_PLAYERS];
		for(int i = 0; i < NUM_PLAYERS; i++)
		{
			playerPanel[i] = new JPanel();
			this.add(playerPanel[i]);
		}
		
		playerPoints = new JLabel[NUM_PLAYERS];
		playerRoad = new JLabel[NUM_PLAYERS];
		playerArmy = new JLabel[NUM_PLAYERS];
		
		longestRoadImage = ImageUtils.loadImage("images/misc/road.png").getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		largestArmyImage = ImageUtils.loadImage("images/misc/army.png").getScaledInstance(30, 30, Image.SCALE_SMOOTH);
	}

	@Override
	public ITurnTrackerController getController() {
		return (ITurnTrackerController)super.getController();
	}
	
	@Override
	public void setController(IController controller) {
		super.setController(controller);
		this.gameStatePanel.setButtonAction(new IAction() {
			@Override
			public void execute()
			{
				getController().endTurn();
			}
		});
	}

	@Override
	public void setLocalPlayerColor(CatanColor value) {
		
		titlePanel.setLocalPlayerColor(value);
                gameStatePanel.setButtonColor(value.getJavaColor());
	}

        JPanel[] indicators = new JPanel[4];
        
        
	@Override
	public void initializePlayer(int playerIndex, String playerName,
			CatanColor playerColor) {
				
		playerPanel[playerIndex].setLayout(new BorderLayout());
		
		JLabel name = new JLabel(playerName);
		name.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
		Font labelFont = name.getFont();
		labelFont = labelFont.deriveFont(Font.BOLD, FONT_SIZE);
		name.setFont(labelFont);
		playerPanel[playerIndex].add(name, BorderLayout.WEST);
		
		indicators[playerIndex] = new JPanel();
		indicators[playerIndex].setBackground(playerColor.getJavaColor());
		playerPanel[playerIndex].add(indicators[playerIndex], BorderLayout.CENTER);
		
		playerArmy[playerIndex] = new JLabel();
		playerArmy[playerIndex].setIcon(new ImageIcon(largestArmyImage));
		indicators[playerIndex].add(playerArmy[playerIndex]);
		playerArmy[playerIndex].setVisible(false);
		
		playerRoad[playerIndex] = new JLabel();
		playerRoad[playerIndex].setIcon(new ImageIcon(longestRoadImage));
		indicators[playerIndex].add(playerRoad[playerIndex]);
		playerRoad[playerIndex].setVisible(false);
		
		playerPoints[playerIndex] = new JLabel("0");
		playerPoints[playerIndex].setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
		playerPoints[playerIndex].setFont(labelFont);
		playerPanel[playerIndex].add(playerPoints[playerIndex], BorderLayout.EAST);
		
		playerPanel[playerIndex].setBackground(playerColor.getJavaColor());
		playerPanel[playerIndex].setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
	}

	@Override
	public void updatePlayer(int playerIndex, int points, boolean highlight,
			boolean largestArmy, boolean longestRoad) {
                refreshColor(playerIndex);
		playerArmy[playerIndex].setVisible(largestArmy);
		playerRoad[playerIndex].setVisible(longestRoad);
                playerPoints[playerIndex].setText("");
		playerPoints[playerIndex].setText(String.format("%d", points));
		
		
		if(highlight)
			playerPanel[playerIndex].setBorder(BorderFactory.createLineBorder(new Color(0,0,0), 3));
		else
			playerPanel[playerIndex].setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		
	}
        
        private void refreshColor(int playerIndex)
        {            
            Player[] players = ModelFacade.getInstance().getPlayers();
            Color color = players[playerIndex].getColor().getJavaColor();
            playerPanel[playerIndex].setBackground(color);
            indicators[playerIndex].setBackground(color);
        }

	@Override
	public void updateGameState(String stateMessage, boolean enable) {
		if(ModelFacade.getInstance().isPlayerTurn())
		{
			if(stateMessage.equals("Rolling"))
			{
				gameStatePanel.updateGameState("Please Roll", enable);	
			}
			else
			{
				gameStatePanel.updateGameState("Finish Turn", enable);
			}
		}
		else 
		{
			gameStatePanel.updateGameState("Waiting for other players...", false);
		}
	}
	
}


