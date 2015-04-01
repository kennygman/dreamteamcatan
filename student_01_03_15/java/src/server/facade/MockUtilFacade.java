package server.facade;

import shared.parameters.ChangeLogLevelParam;
import shared.response.StandardResponse;

public class MockUtilFacade implements IUtilFacade
{

	@Override
	public StandardResponse changeLogLevel(ChangeLogLevelParam param)
	{
		return new StandardResponse(true);
	}

}
