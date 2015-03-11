package server;

import model.Game;

public interface Command
{
	abstract void execute(Game game);
}
