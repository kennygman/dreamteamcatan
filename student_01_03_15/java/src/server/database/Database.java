package server.database;
import java.util.logging.Logger;

public class Database 
{
	private static Logger logger;
	private static final String DATABASE_FILE = "db.sqlite";
	private static final String DATABASE_URL = "jdbc:sqlite:" + DATABASE_FILE;
	
	static 
	{
		logger = Logger.getLogger("usermanager");
	}


	public static void initialize() throws DatabaseException 
	{
		
		logger.entering("server.database.Database", "initialize");

		try 
		{
			final String driver = "org.sqlite.JDBC";
			Class.forName(driver);
		}
		catch(ClassNotFoundException e)
		{	
			DatabaseException serverEx = new DatabaseException("Could not load database driver", e);
			
			logger.throwing("server.database.Database", "initialize", serverEx);

			throw serverEx; 
		}
	
		logger.exiting("server.database.Database", "initialize");
	}
}
