package test;

public class main {

	public static void main(String[] args)
	{

		String[] testClasses = new String[] 
		{
//				"test.ProxyRegisterTests","test.ProxyLoginTests"
//				,"test.ProxyGameStartTests","test.ProxyInGameTests",
//				"test.ModelFacadeUnitTest"
//				,"test.PollerUnitTests"
//				"test.UserFacadeTests"
				"test.MovesFacadeTests"
		};
		org.junit.runner.JUnitCore.main(testClasses);
	}


}
