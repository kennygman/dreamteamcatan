package model.player;

import shared.definitions.ResourceType;


public class Resources {

	private int wood;
	private int sheep;
	private int wheat;
	private int brick;
	private int ore;
	
	public Resources(int wood, int sheep, int wheat, int brick, int ore) {
		this.wood = wood;
		this.sheep = sheep;
		this.wheat = wheat;
		this.brick = brick;
		this.ore = ore;
	}


	/**
	 * 
	 * Used to facilitate trading.
	 */
	public void invert()
	{
		wood = -wood;
		sheep = -sheep;
		wheat = -wheat;
		brick = -brick;
		ore = -ore;
	}


	/**
	 * This method is called when a 7 is rolled by the Dice
	 * @return the number of resources the player owns
	 */
	public int size()
	{
		return wood+sheep+wheat+brick+ore;
	}
	
	public Resources()
	{
		wood=wheat=sheep=brick=ore=0;
	}
	
	/**
	 * Adds resource to list
	 * @param type they type of resource
	 * @param n the number of resources
	 */
	public void addResource(ResourceType type, int n)
	{
		switch(type)
		{
		case WOOD: wood+=n; 
		break;
		case BRICK: brick+=n; 
		break;
		case WHEAT: wheat+=n; 
		break;
		case SHEEP: sheep+=n; 
		break;
		case ORE: ore+=n; 
		break;
			default:
				break;
		}
	}
	
	/**
	 * Removes resource from list
	 * @param type the type of resource
	 * @param n the number of resources
	 */
	public void useResource(ResourceType type, int n)
	{
		switch(type)
		{
		case WOOD: wood-=n; 
		break;
		case BRICK: brick-=n; 
		break;
		case WHEAT: wheat-=n; 
		break;
		case SHEEP: sheep-=n; 
		break;
		case ORE: ore-=n; 
		break;
			default:
				break;
		}
	}
	
	public int getResourceAmount(ResourceType type)
	{
		int amount = 0;
		switch(type)
		{
		case WOOD: amount = wood; 
		break;
		case BRICK: amount = brick;
		break;
		case WHEAT: amount = wheat;
		break;
		case SHEEP: amount = sheep;
		break;
		case ORE: amount = ore;
		break;
			default:
				break;
		}
		return amount;
		
	}
	
	/**
	 * This method compares each resource in each list
	 * @param r the resources to compare with
	 * @return true if each resource is greater than or equal to the amount in the comparison
	 */
	public boolean compare(Resources r)
	{
		boolean valid = true;
		if (r.getResourceAmount(ResourceType.WOOD) < 0
				&& this.wood < Math.abs(r.getResourceAmount(ResourceType.WOOD))) valid = false;
		if (r.getResourceAmount(ResourceType.WHEAT) < 0
				&& this.wheat < Math.abs(r.getResourceAmount(ResourceType.WHEAT))) valid = false;
		if (r.getResourceAmount(ResourceType.SHEEP) < 0
				&& this.sheep < Math.abs(r.getResourceAmount(ResourceType.SHEEP))) valid = false;
		if (r.getResourceAmount(ResourceType.BRICK) < 0
				&& this.brick < Math.abs(r.getResourceAmount(ResourceType.BRICK))) valid = false;
		if (r.getResourceAmount(ResourceType.ORE) < 0
				&& this.ore < Math.abs(r.getResourceAmount(ResourceType.ORE))) valid = false;
		return valid;
	}
}
