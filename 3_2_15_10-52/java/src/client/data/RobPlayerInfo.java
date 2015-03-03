package client.data;

import shared.locations.HexLocation;

/**
 * Used to pass player information into the rob view<br>
 * <br>
 * PROPERTIES:<br>
 * <ul>
 * <li>Id: Unique player ID</li>
 * <li>PlayerIndex: Player's order in the game [0-3]</li>
 * <li>Name: Player's name (non-empty string)</li>
 * <li>Color: Player's color (cannot be null)</li>
 * <li>NumCards: Number of development cards the player has (>= 0)</li>
 * </ul>
 * 
 */
public class RobPlayerInfo extends PlayerInfo
{
	
	private int numCards;
        private HexLocation location;
	
	public RobPlayerInfo()
	{
		super();
	}
        
        public RobPlayerInfo(PlayerInfo info)
        {
            this.setId(info.getId());
            this.setPlayerIndex(info.getPlayerIndex());
            this.setName(info.getName());
            this.setColor(info.getColor());
        }
	
	public int getNumCards()
	{
		return numCards;
	}
	
	public void setNumCards(int numCards)
	{
		this.numCards = numCards;
	}
        
        public HexLocation getLocation()
        {
            return location;
        }
        
        public void setLocation(HexLocation hex)
        {
            location = hex;
        }
}

