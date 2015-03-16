package server.facade;

import shared.parameters.CredentialsParam;
import shared.response.LoginResponse;

public interface IUserFacade
{
	/**
	 * This method validates if the credentials match a valid User for the server
	 * @param param Login parameters for the command
	 * @return login response: TRUE if valid and populated with name and UserId, FALSE otherwise
	 */
	LoginResponse login(CredentialsParam param);
	
	/**
	 * This method validates if the user can be registered then creates and adds a new User
	 * account to the UserManager
	 * @param param Register parameters for the command
	 * @return register response: TRUE if valid and populated with name and UserId, FALSE otherwise
	 */
	LoginResponse register(CredentialsParam param);
}
