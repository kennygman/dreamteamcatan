package model.player;

import shared.definitions.DevCardType;

public class Developments
{
	int monopoly;
	int monument;
	int roadBuilding;
	int soldier;
	int yearOfPlenty;

	/**
	 * Checks to see if the DevCardType given is contained in the developments
	 * 
	 * @param devCard
	 * @return whether or not the dev card is in the hand
	 */
	public boolean hasDevCard(DevCardType devCard)
	{
		switch (devCard)
		{
		case SOLDIER:
			return soldier > 0;
		case YEAR_OF_PLENTY:
			return yearOfPlenty > 0;
		case MONOPOLY:
			return monopoly > 0;
		case ROAD_BUILD:
			return roadBuilding > 0;
		case MONUMENT:
			return monument > 0;
		default:
			assert false;
			return false;

		}
	}

	/**
	 * Adds up the amount of each type of development and returns
	 * 
	 * @return the size of the development hand
	 */
	public int size()
	{
		return (monopoly + monument + roadBuilding + soldier + yearOfPlenty);
	}

	public int getMonopoly()
	{
		return monopoly;
	}

	public void setMonopoly(int monopoly)
	{
		this.monopoly = monopoly;
	}

	public int getMonument()
	{
		return monument;
	}

	public void setMonument(int monument)
	{
		this.monument = monument;
	}

	public int getRoadBuilding()
	{
		return roadBuilding;
	}

	public void setRoadBuilding(int roadBuilding)
	{
		this.roadBuilding = roadBuilding;
	}

	public int getSoldier()
	{
		return soldier;
	}

	public void setSolider(int soldier)
	{
		this.soldier = soldier;
	}

	public int getYearOfPlenty()
	{
		return yearOfPlenty;
	}

	public void setYearOfPlenty(int yearOfPlenty)
	{
		this.yearOfPlenty = yearOfPlenty;
	}

}