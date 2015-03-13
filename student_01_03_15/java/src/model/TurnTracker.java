package model;

public class TurnTracker
{
	private int currentTurn;
	private String status;
	int longestRoad;
	int largestArmy;

	/**
	 * This method updates the current TurnTracker with the new given
	 * TurnTracker
	 * 
	 * @param t
	 *            TurnTracker to pull updates from
	 */
	public void update(TurnTracker t)
	{
		this.setCurrentTurn(t.getCurrentTurn());
		this.setStatus(t.getStatus());
		this.setLongestRoad(t.getLongestRoad());
		this.setLargestArmy(t.getLargestArmy());
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