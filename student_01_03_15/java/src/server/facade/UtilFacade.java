package server.facade;

import shared.parameters.ChangeLogLevelParam;
import shared.response.StandardResponse;

public class UtilFacade implements IUtilFacade
{
	@Override
	public StandardResponse changeLogLevel(ChangeLogLevelParam param)
	{
		return new StandardResponse(true);
	}

}
