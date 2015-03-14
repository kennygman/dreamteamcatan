package model;

import java.util.ArrayList;
import shared.response.GameListObject;
import shared.response.PlayerListObject;
import model.player.Developments;
import model.player.Resources;
import model.player.Player;
import model.board.Board;

public class Game
{
	private int playerId;
	private Resources bank;
	private Developments deck;
	private Chat chat;
	private Log log;
	private Board map;
	private Player[] players;
	private TradeOffer tradeOffer;
	private TurnTracker turnTracker;
	private String title;
	private int version;
	private int winner;

	public void initialize()
	{
		// Add model initialize methods here
		players = new Player[4];
	}

	public GameListObject getGameListObject()
	{
		GameListObject gameObject = new GameListObject();
		gameObject.title = title;
		gameObject.players = getPlayerListObject(gameObject);
		return gameObject;
	}

	public ArrayList<PlayerListObject> getPlayerListObject(
			GameListObject gameObject)
	{
		PlayerListObject playerObject;
		ArrayList<PlayerListObject> playerList = new ArrayList<PlayerListObject>();
		for (Player p : players)
		{
			playerObject = new PlayerListObject();
			playerObject.color = p.getColor().name();
			playerObject.id = p.getPlayerID();
			playerObject.name = p.getName();
			playerList.add(playerObject);
		}
		return playerList;
	}

	public Player getPlayer(int index)
	{
		if (index > 3)
			return null;
		return players[index];
	}

	public int getPlayerId()
	{
		return playerId;
	}

	public void setPlayerId(int id)
	{
		this.playerId = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public void update(Game newGame)
	{
		map.update(newGame.getBoard());
		turnTracker.update(newGame.getTurnTracker());
		this.setTradeOffer(newGame.getTradeOffer());
		this.setChat(newGame.getChat());
		this.setLog(newGame.getLog());
	}

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

	public Board getBoard()
	{
		return map;
	}

	public void setBoard(Board board)
	{
		this.map = board;
	}

	public void sortBoard()
	{
		map.sort();
	}

	public Developments getDeck()
	{
		return deck;
	}

	public void setDeck(Developments deck)
	{
		this.deck = deck;
	}
}