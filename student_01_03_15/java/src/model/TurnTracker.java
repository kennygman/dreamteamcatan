package model;

import java.util.ArrayList;
import java.util.List;

public class TurnTracker
{
	public static String PLAYING = "Playing";
	public static String ROBBING = "Robbing";
	public static String FIRSTROUND = "FirstRound";
	public static String SECONDROUND = "SecondRound";
	public static String TRADING = "Trading";
	public static String ROLLING = "Rolling";
	public static String DISCARDING = "Discarding";
	
	private transient List<Integer> discarding; 

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
		System.out.println("Turn tracker is updating");
		this.setCurrentTurn(t.getCurrentTurn());
		this.setStatus(t.getStatus());
		this.setLongestRoad(t.getLongestRoad());
		this.setLargestArmy(t.getLargestArmy());
	}

	public TurnTracker init()
	{
		currentTurn = 0;
		status = FIRSTROUND;
		longestRoad = -1;
		largestArmy = -1;
		discarding = new ArrayList<>();
		return this;
	}
	
	public void nextTurn()
	{
		if (status.equals(FIRSTROUND))
		{
			currentTurn++;
			if(currentTurn>3)
			{
				currentTurn=3;
				status = SECONDROUND;
			}
		}
		else if(status.equals(SECONDROUND))
		{
			currentTurn--;
			if (currentTurn < 0)
			{
				currentTurn=0;
				status = ROLLING;
			}
		}
		else
		{
			currentTurn++;
			if(currentTurn > 3) currentTurn = 0;
			status = ROLLING;
		}
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

	public void setDiscarding(int i)
	{
		discarding.add(i);
	}

	public boolean discarded(int i)
	{
		try{
			if (discarding.contains(i))
			{
				discarding.remove(discarding.indexOf(i));
			}
			
		} catch(Exception e){e.printStackTrace();}
		
		return discarding.isEmpty();
	}
}