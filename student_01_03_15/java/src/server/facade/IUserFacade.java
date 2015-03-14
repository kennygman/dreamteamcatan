package server.facade;

import shared.parameters.CredentialsParam;
import shared.response.LoginResponse;

public interface IUserFacade
{
	/**
	 * This method validates the credentials through the ModelFacade
	 * then validates if the credentials match a valid User for the server
	 * @param param Login parameters for the command
	 * @return login response
	 */
	LoginResponse login(CredentialsParam param);
	
	/**
	 * This method validates the credentials through the ModelFacade
	 * then creates and adds a new User account the the server User list
	 * @param param Register parameters for the command
	 * @return register response
	 */
	LoginResponse register(CredentialsParam param);
}
