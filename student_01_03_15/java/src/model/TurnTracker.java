package model;

public class TurnTracker 
{
//	private int currentPlayerId;
//	private State currentState;
//	private List<Player> playerList;
	private int currentTurn;
	private String status;
	int longestRoad;
	int largestArmy;
	
	public void update(TurnTracker t)
	{
		this.setCurrentTurn(t.getCurrentTurn());
		this.setStatus(t.getStatus());
		this.setLongestRoad(t.getLongestRoad());
		this.setLargestArmy(t.getLargestArmy());
	}
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

	public void setStatus(String status)
	{
		this.status = status;
	}

	public int getLongestRoad()
	{
		return longestRoad;
	}

	public void setLongestRoad(int longestRoad)
	{
		this.longestRoad = longestRoad;
	}

	public int getLargestArmy()
	{
		return largestArmy;
	}

	public void setLargestArmy(int largestArmy)
	{
		this.largestArmy = largestArmy;
	}

	
}