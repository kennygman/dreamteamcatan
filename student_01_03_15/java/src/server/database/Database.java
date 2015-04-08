package server.database;
import java.sql.*;
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
	
	private static Database instance;
	private Connection connection;
	
	static 
	{
		logger = Logger.getLogger("usermanager");
	}


	public static Database getInstance()
	{
		return instance;
	}
	
	/**
	 * 
	 * @param chosenDriver
	 * @throws DatabaseException
	 */
	public static void initialize(String chosenDriver) throws DatabaseException 
	{
		instance = new Database();
		
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
       public void startTransaction() throws DatabaseException 
   	{
   		//System.out.print("start\n");
   		logger.entering("server.database.Database", "startTransaction");
   		
   		try 
   		{
   			assert (connection == null);			
   			connection = DriverManager.getConnection(DATABASE_URL);
   			connection.setAutoCommit(false);
   		}
   		catch (SQLException e) 
   		{
   			throw new DatabaseException("Could not connect to database. Make sure " + 
   				DATABASE_FILE + " is available in ./", e);
   		}
   		
   		logger.exiting("server.database.Database", "startTransaction");
   	}
   	
   	public void endTransaction(boolean commit) 
   	{
   		
   		logger.entering("server.database.Database", "endTransaction");
   		//System.out.print("end\n");
   		if (connection != null) 
   		{		
   			try 
   			{
   				if (commit) {
   					connection.commit();
   				}
   				else 
   				{
   					connection.rollback();
   				}
   			
   			}
   			catch (SQLException e)
   			{
   				System.out.println("Could not end transaction");
   				e.printStackTrace();
   			}
   			
   			finally 
   			
   			{
   				safeClose(connection);
   				connection = null;
   			}
   		}
   	
   		
   		logger.exiting("server.database.Database", "endTransaction");
   	}
	public static void safeClose(Connection conn) 
	{
		if (conn != null) 
		{
			try 
			{
				conn.close();
			}
			catch (SQLException e) 
			{
				System.out.print("Something is wrong with safeClose connection");
			}
		}
	}
   	public static void safeClose(Statement stmt) 
	{
		if (stmt != null) 
		
		{
			try 
			{
				stmt.close();
			}
			
			catch (SQLException e) 
			{
				System.out.print("Something is wrong with safeClose stmt");
			}
		}
	}
	
	public static void safeClose(PreparedStatement stmt) 
	{
		if (stmt != null) 
		{
			try {
				stmt.close();
			}
			catch (SQLException e) 
			{
				System.out.print("Something is wrong with safeClose prepares stmt");
			}
		}
	}
	
	public static void safeClose(ResultSet rs) 
	
	{
		if (rs != null) 
		{
			try 
			{
				rs.close();
			}
			catch (SQLException e) 
			{
				System.out.print("Something is wrong with safeClose result");
			}
		}
	}

}
