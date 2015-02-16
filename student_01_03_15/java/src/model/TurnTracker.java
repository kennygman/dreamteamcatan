package model;

import java.util.List;

import model.player.*;

public class TurnTracker 
{
//	private int currentPlayerId;
//	private State currentState;
//	private List<Player> playerList;
	private int currentTurn;
	private String status;
	int longestRoad;
	int largestArmy;
	
	public void nextTurn()
	{
	}
	
	public void alertPlayer()
	{
	}

	public int getCurrentTurn()
	{
		return currentTurn;
	}

	public void setCurrentTurn(int currentTurn)
	{
		this.currentTurn = currentTurn;
	}
	
	public String getStatus()
	{
		return status;
	}
	
}