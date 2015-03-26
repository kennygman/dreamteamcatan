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
import shared.response.*;

public class GameFacade implements IGameFacade
{
    private static final CatanColor BLUE = null;
	private static final CatanColor YELLOW = null;
	private static final CatanColor WHITE = null;
	private static final CatanColor GREEN = null;
	private static final CatanColor ORANGE = null;
	private static final CatanColor RED = null;
	private static final CatanColor BROWN = null;
	private static final CatanColor PUCE = null;
	private static final CatanColor PURPLE = null;
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
			Game currentGame = games.getGame(id);
			
			int emptyIndex = currentGame.getEmptyPlayerIndex();
			
			if(emptyIndex != -1)
			{
				System.out.println("emptyIndex is " + emptyIndex);
				
			
				Random rn = new Random();
				for(int i=1;i<8;i++)
				{
					System.out.println(" number is:" + i);
					
					System.out.println(" id is:" + (i*(-1)));
					
					if(!currentGame.isPlayerInGame(i*(-1)))
					{	
						
						System.out.println("add ai is working");
					
						insertedAI=true;
						
						Player newPlayer = new Player();
						
						System.out.println("getting ai");
						
						User currentUser = users.getAi(i-1);
						
						System.out.println("AI name" + currentUser.getName());
						
						System.out.println("is about to choose a color");
						newPlayer.setColor(getColor(currentGame).toString());
						

						
						newPlayer.setName(currentUser.getName());
						newPlayer.setPlayerIndex(emptyIndex);
						newPlayer.setPlayerID(currentUser.getId());
						
						System.out.println("AI name" + newPlayer.getName());
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
	private CatanColor getColor(Game currentGame)
	{
		CatanColor[] colors = {RED, ORANGE, YELLOW, BLUE, GREEN, PURPLE, PUCE, WHITE, BROWN};//{"RED", "ORANGE", "YELLOW", "BLUE", "GREEN", "PURPLE", "PUCE", "WHITE", "BROWN"};
		Player[] players = currentGame.getPlayers();
		boolean noColorChosen = true;
		
		
		for(int i=0;i<colors.length;i++)
		{
			System.out.println("color length " + colors.length );
			for(int j=0;j<players.length;j++)
			{
				
				String playerColor = CatanColor.asString(players[j].getColor());
			
				System.out.println("comparing colors " + players[j].getColor() + "and" + CatanColor.asString(colors[i]));
				
				if(playerColor.equals(CatanColor.asString(colors[i])))
				{
					noColorChosen = false;
				}
			}
			
			if(noColorChosen)
			{
				//System.out.println("it chose a color =" + colors[i]);
				return colors[i];
			}
		}
		
		System.out.println("null");
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
