package server.database;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database 
{
	private static Logger logger;
	private static final String DATABASE_FILE = "db.sqlite";
	private static final String DATABASE_URL = "jdbc:sqlite:" + DATABASE_FILE;
	
	private static IUserDAO userDAO;
	private static IGameDAO gameDAO;
	private static ICommandsDAO commandsDAO;
	
	
	static 
	{
		logger = Logger.getLogger("usermanager");
	}


	public static void initialize(String chosenDriver) throws DatabaseException 
	{
		
		logger.entering("server.database.Database", "initialize");

		try 
		{
			if(chosenDriver == "sqlite")
			{
				userDAO = new SqlUserDAO();
				gameDAO = new SqlGameDAO();
				commandsDAO = new SqlCommandsDAO();
				
				final String driver = "org.sqlite.JDBC"; // chosenDriver string from server Args parameters
				Class.forName(driver);
			}
			else
			{
				userDAO = new MongoUserDAO();
				gameDAO = new MongoGameDAO();
				commandsDAO = new MongoCommandsDAO();
				
				final String driver = "org.sqlite.JDBC"; // chosenDriver string from server Args parameters mongo driver
				Class.forName(driver);
			}
			
			
		}
		catch(ClassNotFoundException e)
		{	
			DatabaseException serverEx = new DatabaseException("Could not load database driver", e);
			
			logger.throwing("server.database.Database", "initialize", serverEx);

			throw serverEx; 
		}
	
		logger.exiting("server.database.Database", "initialize");
	}
        
        public static boolean setLogLevel(String level)
        {
            switch(level)
            {
                case "ALL" :    logger.setLevel(Level.ALL); break;
                case "SEVERE":  logger.setLevel(Level.SEVERE); break;
                case "WARNING": logger.setLevel(Level.WARNING); break;
                case "INFO":    logger.setLevel(Level.INFO); break;
                case "CONFIG":  logger.setLevel(Level.CONFIG); break;
                case "FINE":    logger.setLevel(Level.FINE); break;
                case "FINER":   logger.setLevel(Level.FINER); break;
                case "FINEST":  logger.setLevel(Level.FINEST); break; 
                case "OFF":     logger.setLevel(Level.OFF); break;
                default:        return false;
            }
            return true;
        }
       public IUserDAO getUserDAO()
       {
    	   return userDAO;
       }
       public IGameDAO getGameDAO()
       {
    	   return gameDAO;
       }
       public ICommandsDAO getCommandsDAO()
       {
    	   return commandsDAO;
       }
}
