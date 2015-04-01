package shared.parameters;

public class SaveGameParam implements ICommandParam {
	private int id;
	private String name;
	
	public SaveGameParam(int i, String f)
	{
		id = i;
		name = f;
	}
        
        public String getName()
        {
            return name;
        }
        
        public int getId()
        {
            return id;
        }
}
