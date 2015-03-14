package server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import model.Game;
import model.player.Player;

public class ServerUtils
{
	static final String[] aiTypes = {"LARGEST_ARMY"};
	static final String[] aiNames = {"Sam", "Pete", "Sven", "Kunkka", "Ember Spirit", "Pudge", "Techies"};
	static final String[] colors ={"red", "green", "blue", "yellow", "puce", "brown", "white", "purple", "orange"};

	public static User getAi()
	{
		
		return null;
	}
	/**
	 * Pick a random color that is not currently used in the game.
	 * @param g the game
	 * @return the color
	 */
	private static String getAiColor(Game g)
	{
		List<String> availableColors = new ArrayList<>();
		for (Player p : g.getPlayers())
			availableColors.add(p.getColor().name());
		availableColors = getUnused(Arrays.asList(colors), availableColors);
		Collections.shuffle(availableColors);
		return availableColors.iterator().next();
	}
	
	/**
	 * Pick a random name that is not currently used in the game.
	 * @param g the game
	 * @return the name
	 */
	private static String getAiName(Game g)
	{
		List<String> availableNames = new ArrayList<>();
		for (Player p : g.getPlayers())
			if (p.getPlayerID() < 0) availableNames.add(p.getName());
		availableNames = getUnused(Arrays.asList(aiNames), availableNames);
		Collections.shuffle(availableNames);
		return availableNames.iterator().next();
	}
	
	private static List<String> getUnused(List<String> a, List<String> b)
	{
		List<String> list = new ArrayList<>();
		for (String s : a)
			if (!b.contains(s)) list.add(s);
		return list;
	}
	
	public static boolean isGameFull(Game g)
	{
		for (Player p : g.getPlayers())
			if (p.getName() == null) return false;
		return true;
	}
	
	public static boolean userInGame(String name, Game g)
	{
		for (Player p : g.getPlayers())
			if (p.getName().equals(name)) return true;
		return false;
	}
	
}
