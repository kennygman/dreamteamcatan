package shared.parameters;

public class JoinGameParam {
	private int id;
	private String color;
	
	public JoinGameParam(int i, String c)
	{
		id = i;
		color = c;
	}
        
        public String getColor()
        {
            return this.color;
        }
        public int getId()
        {
        	return id;
        }

}
