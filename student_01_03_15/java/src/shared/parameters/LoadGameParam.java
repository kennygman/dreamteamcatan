package shared.parameters;

public class LoadGameParam 
{
	private String name;
	
	public LoadGameParam(String title)
	{
		name = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



}
