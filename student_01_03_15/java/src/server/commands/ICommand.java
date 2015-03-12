package server.commands;

import model.Game;

public interface ICommand
{
	abstract void execute(Game game);
}
