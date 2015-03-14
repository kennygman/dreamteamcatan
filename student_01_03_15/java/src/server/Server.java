package server;
import java.io.*;
import java.net.*;
import java.util.logging.*;

import com.sun.net.httpserver.*;

import server.facade.ServerFacade;

public class Server 
{
	
	private static int SERVER_PORT_NUMBER = 8081;
	private static final int MAX_WAITING_CONNECTIONS = 10;
	private HttpServer server;
	private static Logger logger;
	
	static 
	{
		try 
		{
			initLog();
		}
		catch (IOException e) 
		{
			System.out.println("Could not initialize log: " + e.getMessage());
		}
	}
		private static void initLog() throws IOException 
		{
			
			Level logLevel = Level.FINE;
			
			logger = Logger.getLogger("Servermanager"); 
			logger.setLevel(logLevel);
			logger.setUseParentHandlers(false);
			
			Handler consoleHandler = new ConsoleHandler();
			consoleHandler.setLevel(logLevel);
			consoleHandler.setFormatter(new SimpleFormatter());
			logger.addHandler(consoleHandler);

			FileHandler fileHandler = new FileHandler("log.txt", false);
			fileHandler.setLevel(logLevel);
			fileHandler.setFormatter(new SimpleFormatter());
			logger.addHandler(fileHandler);
		}
		
	
		
		private Server() 
		{
                    ServerFacade.createInstance();
		}
		private Server(String[]args)
		{
			SERVER_PORT_NUMBER = Integer.valueOf(args[0]);
		}
		private void run() 
		{
			
			logger.info("Initializing HTTP Server");
			
			try 
			{
				server = HttpServer.create(new InetSocketAddress(SERVER_PORT_NUMBER),
												MAX_WAITING_CONNECTIONS);
			} 
			catch (IOException e) 
			{
				logger.log(Level.SEVERE, e.getMessage(), e);			
				return;
			}

			server.setExecutor(null); // use the default executor
			
			/*server.createContext("/validateUser", validateUserHandler);
			server.createContext("/getProjects", getProjectsHandler);
			server.createContext("/getSampleImage", getSampleImageHandler);
			server.createContext("/downloadBatch", downloadBatchHandler);
			server.createContext("/getFields", getFieldsHandler);
			server.createContext("/search", searchHandler);
			server.createContext("/submitBatch", submitBatchHandler);
			server.createContext("/", downloadFileHandler);*/
			logger.info("Starting HTTP Server");

			server.start();
		}
		
		/*private HttpHandler downloadFileHandler = new downloadFileHandler();
		private HttpHandler downloadBatchHandler = new downloadBatchHandler();
		private HttpHandler getFieldsHandler = new getFieldsHandler();
		private HttpHandler getProjectsHandler = new getProjectsHandler();
		private HttpHandler getSampleImageHandler = new getSampleImageHandler();
		private HttpHandler searchHandler = new searchHandler();
		private HttpHandler submitBatchHandler = new submitBatchHandler();
		private HttpHandler validateUserHandler = new validateUserHandler();
		*/
		
		public static void main(String[] args) 
		{
			if(args.length<1)
			{
				new Server().run();
			}
			else
			{
				new Server(args).run();
			}
		}
		
}
