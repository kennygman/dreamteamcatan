package server.facade;

import java.util.List;

import model.Game;
import model.player.Player;
import server.GameManager;
import server.UserManager;
import server.commands.ICommand;
import shared.parameters.AddAiParam;
import shared.response.*;

public class GameFacade implements IGameFacade
{
    private GameManager games;
    private UserManager users;

    
    public GameFacade(GameManager games, UserManager users)
    {
    	this.games=games;
    	this.users= users;
    }
    
	@Override
	public GameModelResponse getGameModel(int id)
	{
		return buildGameResponse(games.getGame(id));
	}

	@Override
	public GameModelResponse resetGame(int id)
	{
		return buildGameResponse(games.getGame(id).reset());
	}

	@Override
	public CommandResponse getCommands(int id)
	{
			
		String commands = convertToString(games.getCommands(id));
		
		CommandResponse response = new CommandResponse(commands,true);
		return response;
	}

	@Override
	public StandardResponse commands(int id)
	{
		
		List<ICommand> commands = games.getCommands(id);
	
		//execute list of commands
		for(int i=0; i<commands.size();i++)
		{
			commands.get(i).execute();
		}
		
		StandardResponse response = new StandardResponse(true);
		
		return response;
	}

	@Override
	public StandardResponse addAI(AddAiParam param, int id)
	{
		StandardResponse response = new StandardResponse(false);
		boolean insertedAI = false;
		if(param.getType()=="LARGEST_ARMY")
		{
			response = new StandardResponse(true);
			Game currentGame = games.getGame(id);
			Player[] players = currentGame.getPlayers();
			
			for(int i=0;i<4;i++)
			{
				if(players[i] == null)
				{
					
					insertedAI=true;
					
					
				}
			}
			
			
			
			
			
		}
		
	
		return response;
		
	}

	@Override
	public ListAIResponse listAI(int id)
	{
		String[] aiType = new String[]{"LARGEST_ARMY"};
		ListAIResponse response = new ListAIResponse(aiType,true);
		return response;
	}

	private GameModelResponse buildGameResponse(Game game)
	{
		GameModelResponse response = new GameModelResponse();
    	if (game != null)
    	{
    		 response.setGame(game);
    		 response.setValid(true);
    	}
    	else
    	{
    		response.setValid(false); 
    	}
    	
    	return response;
	}
	private String convertToString(List<ICommand> commands)
	{
		StringBuilder result = new StringBuilder();
		
		for(int i=0; i<commands.size();i++)
		{
			result.append(commands.get(i));
			result.append("\n");
		}
		
		return result.toString();
	}
}
