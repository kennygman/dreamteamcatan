package shared;

/**
 *
 * @author Drew
 */
public class InGameCookie {
    private String name;
    private String password;
    private int playerID;
    private int game;
    
    public InGameCookie(int id, String n, String p, int g)
    {
        name = n;
        password = p;
        playerID = id;
        game = g;
    }
    
}
