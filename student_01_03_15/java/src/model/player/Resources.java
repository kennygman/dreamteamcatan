package model.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shared.definitions.ResourceType;

public class Resources
{

	private int wood;
	private int sheep;
	private int wheat;
	private int brick;
	private int ore;
	
	public Resources()
	{
		wood = wheat = sheep = brick = ore = 0;
	}

	public Resources(int wood, int sheep, int wheat, int brick, int ore)
	{
		this.wood = wood;
		this.sheep = sheep;
		this.wheat = wheat;
		this.brick = brick;
		this.ore = ore;
	}

	public Resources init()
	{
		wood=24;
		sheep=24;
		wheat=24;
		brick=24;
		ore=24;
		return this;
	}
	
	/**
	 * Updates exisiting resources with new resources
	 * 
	 * @param r
	 *            Resources to base the update off of
	 */
	public void update(Resources r)
	{
		this.wood = r.getResourceAmount(ResourceType.WOOD);
		this.sheep = r.getResourceAmount(ResourceType.SHEEP);
		this.wheat = r.getResourceAmount(ResourceType.WHEAT);
		this.brick = r.getResourceAmount(ResourceType.BRICK);
		this.ore = r.getResourceAmount(ResourceType.ORE);
	}

	/**
	 * Used to facilitate trading.
	 */
	public Resources invert()
	{
		wood = -wood;
		sheep = -sheep;
		wheat = -wheat;
		brick = -brick;
		ore = -ore;
		return this;
	}

	/**
	 * This method is called when a 7 is rolled by the Dice
	 * 
	 * @return the number of resources the player owns
	 */
	public int size()
	{
		return wood + sheep + wheat + brick + ore;
	}

	/**
	 * Adds resource to list
	 * 
	 * @param type
	 *            they type of resource
	 * @param n
	 *            the number of resources
	 */
	public void addResource(ResourceType type, int n)
	{
		switch (type)
		{
		case WOOD:
			wood += n;
			break;
		case BRICK:
			brick += n;
			break;
		case WHEAT:
			wheat += n;
			break;
		case SHEEP:
			sheep += n;
			break;
		case ORE:
			ore += n;
			break;
		default:
			break;
		}
	}

	/**
	 * Removes resource from list
	 * 
	 * @param type
	 *            the type of resource
	 * @param n
	 *            the number of resources
	 */
	public void useResource(ResourceType type, int n)
	{
		switch (type)
		{
		case WOOD:
			wood -= n;
			break;
		case BRICK:
			brick -= n;
			break;
		case WHEAT:
			wheat -= n;
			break;
		case SHEEP:
			sheep -= n;
			break;
		case ORE:
			ore -= n;
			break;
		default:
			break;
		}
	}

	/**
	 * Returns the number of a specific resource in this particular resource
	 * hand
	 * 
	 * @param type
	 *            ResourceType to return amount of
	 * @return number of that resource in this resources hand
	 */
	public int getResourceAmount(ResourceType type)
	{
		int amount = 0;
		switch (type)
		{
		case WOOD:
			amount = wood;
			break;
		case BRICK:
			amount = brick;
			break;
		case WHEAT:
			amount = wheat;
			break;
		case SHEEP:
			amount = sheep;
			break;
		case ORE:
			amount = ore;
			break;
		default:
			break;
		}
		return amount;

	}

	/**
	 * Changes how many of a specific resource there is
	 * 
	 * @param type
	 *            ResourceType to set amount to
	 * @param amount
	 *            How much of this resource to set
	 */
	public void setResource(ResourceType type, int amount)
	{
		switch (type)
		{
		case WOOD:
			wood = amount;
			break;
		case BRICK:
			brick = amount;
			break;
		case WHEAT:
			wheat = amount;
			break;
		case SHEEP:
			sheep = amount;
			break;
		case ORE:
			ore = amount;
			break;
		default:
			break;
		}
	}

	/**
	 * Sets a specific resource to 0 amount
	 * 
	 * @param type
	 *            resource to set to 0
	 */
	public void resetResource(ResourceType type)
	{
		switch (type)
		{
		case WOOD:
			wood = 0;
			break;
		case BRICK:
			brick = 0;
			break;
		case WHEAT:
			wheat = 0;
			break;
		case SHEEP:
			sheep = 0;
			break;
		case ORE:
			ore = 0;
			break;
		default:
			break;
		}
	}

	/**
	 * This method verifies that the player has at least the same amount of
	 * resources in the specified list
	 * 
	 * @param r
	 *            the resources to compare with
	 * @return true if each resource is greater than or equal to the amount in
	 *         the comparison
	 */
	public boolean contains(Resources r)
	{
		for (ResourceType res : ResourceType.list)
		{
			int amount = r.getResourceAmount(res);
			if (amount > 0 && this.getResourceAmount(res) < amount)
				return false;
		}
		return true;
	}

	public void doTrade(Resources trade)
	{
		//System.out.println("Resources(doTrade) BEFORE: " + this);
		for (ResourceType r : ResourceType.list)
		{
			if (trade.getResourceAmount(r) > 0)
			{
				int give = trade.getResourceAmount(r);
				useResource(r, give);
			}
			else if (trade.getResourceAmount(r) < 0)
			{
				int get = Math.abs(trade.getResourceAmount(r));
				addResource(r, get);
			}
		}
		//System.out.println("Resources(doTrade) AFTER: " + this);
	}
	
	public ResourceType robResource()
	{
		List<ResourceType> resourceList = new ArrayList<>();
		for (ResourceType r : ResourceType.list)
		{
			int amount = getResourceAmount(r); 
			for (int i = 0; i < amount; i++)
				resourceList.add(r);
		}
		Collections.shuffle(resourceList);
		if (resourceList.isEmpty()) return null;
		this.useResource(resourceList.get(0), 1);

		return resourceList.get(0);
	}

	@Override
	public String toString()
	{
		return "Resources [wood=" + wood + ", sheep=" + sheep + ", wheat="
				+ wheat + ", brick=" + brick + ", ore=" + ore + "]";
	}

}
