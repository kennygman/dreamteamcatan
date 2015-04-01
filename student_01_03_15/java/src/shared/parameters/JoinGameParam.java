package shared.parameters;

public class JoinGameParam implements ICommandParam {
	private int id;
	private String color;
	
	public JoinGameParam(int i, String c)
	{
		id = i;
		color = c;
	}
        
        public String getColor()
        {
            return this.color.toLowerCase();
        }
        public int getId()
        {
        	return id;
        }

}
