package model.player;

import shared.definitions.DevCardType;

public class DevCard
{
	private DevCardType type;
	private boolean isRefresh;

	/**
	 * 
	 * @return the type
	 */
	public DevCardType getType()
	{
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(DevCardType type)
	{
		this.type = type;
	}

	/**
	 * 
	 * @return the isRefresh
	 */
	public boolean isRefresh()
	{
		return isRefresh;
	}

	/**
	 * @param isRefresh
	 *            the isRefresh to set
	 */
	public void setRefresh(boolean isRefresh)
	{
		this.isRefresh = isRefresh;
	}

}
