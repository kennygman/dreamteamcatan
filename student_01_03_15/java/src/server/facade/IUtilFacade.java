package server.facade;

import shared.parameters.ChangeLogLevelParam;
import shared.response.StandardResponse;

public interface IUtilFacade
{
	/**
	 * This method changes the log level
	 * @param param Log level to change to
	 * @return change log level response: TRUE always pretty much :P
	 */
	StandardResponse changeLogLevel(ChangeLogLevelParam param);
}
