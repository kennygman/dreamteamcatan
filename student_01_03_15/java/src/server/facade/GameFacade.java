package server.facade;

import java.util.List;
import java.util.Random;

import model.Game;
import model.player.Player;
import server.GameManager;
import server.User;
import server.UserManager;
import server.commands.ICommand;
import shared.definitions.CatanColor;
import shared.parameters.AddAiParam;
import shared.parameters.CommandsParam;
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
		
		response.setLists(games.getCommands(id));
		
		return response;
	}

	@Override
	public GameModelResponse commands(CommandsParam param, int id)
	{
		
		Game game = games.getGame(id);

		for (ICommand c : param.getCommands())
		{
			c.execute();
		}
		
		games.updateGame(id, game);
		
		GameModelResponse response = new GameModelResponse();
		response.setValid(true);
		response.setGame(game);
		
		return response;
	}

	@Override
	public StandardResponse addAI(AddAiParam param, int id)
	{
		StandardResponse response = new StandardResponse(false);
		boolean insertedAI = false;
		if(param.getType()=="LARGEST_ARMY")
		{
			Game currentGame = games.getGame(id);
			
			int emptyIndex = currentGame.getEmptyPlayerIndex();
			
			if(emptyIndex != -1)
			{
				//System.out.println("emptyIndex is " + emptyIndex);
				
			
				Random rn = new Random();
				for(int i=rn.nextInt((5-0)+1);i<8;i++)
				{
					//System.out.println(" number is:" + i);
					
					//System.out.println(" id is:" + (i*(-1)));
					
					if(!currentGame.isPlayerInGame(i*(-1)))
					{	
						
						//System.out.println("add ai is working");
					
						insertedAI=true;
						
						Player newPlayer = new Player();
						
						//System.out.println("getting ai");
						
						User currentUser = users.getAi(i-1);
						
						//System.out.println("AI name" + currentUser.getName());
						
						//System.out.println("is about to choose a color");
						
						newPlayer.setColor(getColor(currentGame));   // "PUCE"); //CatanColor.asString(getColor(currentGame))
					
						//System.out.println("players color is " +CatanColor.asString(newPlayer.getColor()));
						
						newPlayer.setName(currentUser.getName());
						newPlayer.setPlayerIndex(emptyIndex);
						newPlayer.setPlayerID(currentUser.getId());
						
						//System.out.println("AI name" + newPlayer.getName());
						currentGame.getPlayers()[emptyIndex]=newPlayer;
						
						response = new StandardResponse(insertedAI);
						return response;
					}
				}
				
				
			}
			else
			{
				//System.out.println("the empty Index is not working");
			}
		}
		
		response = new StandardResponse(insertedAI);
		return response;
		
	}
	private String getColor(Game currentGame)
	{
		String[] colors = new String[]{"red", "orange", "yellow", "blue", "green", "purple", "puce", "white", "brown"};
		Player[] players = currentGame.getPlayers();
		boolean noColorChosen;
		
		
		for(int i=0;i<colors.length;i++)
		{
			noColorChosen = true;
			//System.out.println("Catan color is " + colors[i]);
			for(int j=0;j<players.length;j++)
			{
				//System.out.println("index j is" + j);
				if(players[j]!=null)
				{
					String playerColor = CatanColor.asString(players[j].getColor());
			
					//System.out.println("comparing colors " + playerColor + "and" + colors[i]);
				
					if(playerColor.equals(colors[i]))
					{
					
						 noColorChosen = false;
					}
				}
			}
			
			if(noColorChosen)
			{
				//System.out.println("it chose a color");
				return colors[i];
			}
		}
		//System.out.println("null");
		return null;
		
	}
	@Override
	public ListAIResponse listAI()
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
