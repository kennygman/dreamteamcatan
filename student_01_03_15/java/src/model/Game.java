package model;

import model.player.Resources;
import model.player.Player;
import model.board.Board;



public class Game 
{
	private int playerId;
	private Resources bank;
	private Chat chat;
	private Log log;
	private Board map;
	private Player[] players;
	private TradeOffer tradeOffer;
	private TurnTracker turnTracker;
	private String title;
	private int version;
	private int winner;
	private Dice dice;
//	private Board board;
	
	public Player getPlayer()
	{
		for (Player p : players)
		{
			if (p.getPlayerID()==playerId) return p;
		}
		assert false;
		return null;
	}
	public int getPlayerId()
	{
		return playerId;
	}
	public void setPlayerId(int id)
	{
		this.playerId=id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

//	public boolean canInitGame()
//	{
//		//returns false if it cant initialize
//		return true;
//	}
//	public Board createBoard()
//	{
//		return new Board();
//	}
//	public void endGame()
//	{
//		
//	}
//	public void updateGame()
//	{
//		
//	}
	public Resources getBank() 
	{
		return bank;
	}
	public void setBank(Resources bank) 
	{
		this.bank = bank;
	}
	public Chat getChat()
	{
		return chat;
	}
	public void setChat(Chat chat)
	{
		this.chat = chat;
	}
	public Log getLog() 
	{
		return log;
	}
	public void setLog(Log log) 
	{
		this.log = log;
	}
	public Board getMap() 
	{
		return map;
	}
	public void setMap(Board map) 
	{
		this.map = map;
	}
	public Player[] getPlayers() 
	{
		return players;
	}
	public void setPlayers(Player[] players) 
	{
		this.players = players;
	}
	public TradeOffer getTradeOffer() 
	{
		return tradeOffer;
	}
	public void setTradeOffer(TradeOffer tradeOffer) 
	{
		this.tradeOffer = tradeOffer;
	}
	public TurnTracker getTurnTracker() 
	{
		return turnTracker;
	}
	public void setTurnTracker(TurnTracker turnTracker) 
	{
		this.turnTracker = turnTracker;
	}
	public int getVersion() 
	{
		return version;
	}
	public void setVersion(int version) 
	{
		this.version = version;
	}
	public int getWinner() 
	{
		return winner;
	}
	public void setWinner(int winner) 
	{
		this.winner = winner;
	}
	public Dice getDice() 
	{
		return dice;
	}
	public void setDice(Dice dice) 
	{
		this.dice = dice;
	}
	public Board getBoard() 
	{
		return map;
	}
	public void setBoard(Board board) 
	{
		this.map = board;
	}
}