package shared.parameters;

import model.player.Resources;

public class DiscardCardsParam 
{
	private int playerIndex;
	private Resources discardedCards;
	private String type = "discardCards";
	
	public DiscardCardsParam(int playerIndex, Resources discardCards) {
		this.playerIndex = playerIndex;
		this.discardedCards = discardCards;
	}
	
	
	public int getPlayerIndex() {
		return playerIndex;
	}
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
	public Resources getDiscardCards() {
		return discardedCards;
	}
	public void setDiscardCards(Resources discardCards) {
		this.discardedCards = discardCards;
	}
	
}
