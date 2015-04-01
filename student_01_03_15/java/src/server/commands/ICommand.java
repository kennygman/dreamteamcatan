package server.commands;

import shared.parameters.ICommandParam;

public interface ICommand
{
	abstract void execute();
	abstract ICommandParam getParam();
}
