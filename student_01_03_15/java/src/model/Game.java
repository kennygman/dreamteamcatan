package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.response.GameListObject;
import shared.response.PlayerListObject;
import model.player.Developments;
import model.player.Resources;
import model.player.Player;
import model.board.Board;
import model.board.City;
import model.board.Hex;
import model.board.Piece;
import model.board.Settlement;

public class Game
{
	private transient int playerId;
	private Resources bank;
	private Developments deck;
	private Chat chat;
	private Log log;
	private Board map;
	private transient Board initialMap;
	private Player[] players;
	private TradeOffer tradeOffer;
	private TurnTracker turnTracker;
	private String title;
	private int version;
	private int winner;

	// Add model initialize methods here
	public Game initialize(String title, boolean randHexes,
			boolean randNumbers, boolean randPorts)
	{
		this.title = title;
		version = 0;
		winner = -1;

		players = new Player[4];
		/*
		 * for (int i = 0; i < players.length; i++) { players[i] = new Player();
		 * players[i].setPlayerIndex(i); }
		 */map = new Board().init(randHexes, randNumbers, randPorts);
		initialMap = map;
		bank = new Resources().init();
		deck = new Developments().init();
		chat = new Chat().init();
		log = new Log().init();
		turnTracker = new TurnTracker().init();
		tradeOffer = null;
		return this;
	}

	public void distribute(int number)
	{
		List<Hex> hexes = map.getHexWithNumber(number);
		List<Piece> pieces;
		for (Hex hex : hexes)
		{
			ResourceType resource = ResourceType.fromString(hex.getResource());
			pieces = map.getPieces(hex.getLocation());
			for (Piece p : pieces)
			{
				Player owner;
				if (p instanceof City)
				{
					owner = getPlayer(((City) p).getOwner());
					owner.getResources().addResource(resource, 2);
				} else if (p instanceof Settlement)
				{
					owner = getPlayer(((Settlement) p).getOwner());
					owner.getResources().addResource(resource, 1);
				}
			}
		}
	}

	public GameListObject getGameListObject()
	{
		GameListObject gameObject = new GameListObject();
		gameObject.title = title;
		gameObject.players = getPlayerListObject();
		return gameObject;
	}

	public ArrayList<PlayerListObject> getPlayerListObject()
	{

		ArrayList<PlayerListObject> playerList = new ArrayList<>();
		for (Player p : players)
		{
			if (p == null || p.getName() == null)
				continue;
			try
			{
				CatanColor cc = p.getColor();
				PlayerListObject playerObject = new PlayerListObject(
						CatanColor.asString(cc), p.getName(), p.getPlayerID());
				playerList.add(playerObject);
			} catch (Exception e)
			{
				System.err.println(e);
				System.err.println(e.getLocalizedMessage());
			}
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

	@Override
	public String toString()
	{
		return "Game [playerId=" + playerId + ", bank=" + bank + ", deck="
				+ deck + ", chat=" + chat + ", log=" + log + ", map=" + map
				+ ", players=" + Arrays.toString(players) + ", tradeOffer="
				+ tradeOffer + ", turnTracker=" + turnTracker + ", title="
				+ title + ", version=" + version + ", winner=" + winner + "]";
	}

	public void addLogEntry(String source, String message)
	{
		Lines lines = new Lines();
		lines.setMessage(message);
		lines.setSource(source);
		log.addLine(lines);
	}

	public Game reset()
	{
		Player[] players = this.getPlayers();
		for (int i = 0; i < players.length; i++)
		{
			players[i].reset();
		}

		setMap(initialMap);

		getBank().init();
		getChat().init();
		getLog().init();
		getTurnTracker().init();

		return this;
	}

	public int getEmptyPlayerIndex()
	{
		for (int i = 0; i < this.players.length; i++)
		{
			if(this.players[i] == null || this.players[i].getName() == null)
				return i;
		}
		return -1;
	}
	
	public boolean isPlayerInGame(int playerid)
	{
		for(int i = 0; i < this.players.length; i++)
		{
			if(this.players[i].getPlayerID() == playerid)
				return true;
		}
		return false;
	}
}