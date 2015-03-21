package shared;

/**
 *
 * @author Drew
 */
public class PreGameCookie {
    private String name;
    private String password;
    private int playerID;
    
    public PreGameCookie(int id, String n, String p)
    {
        name = n;
        password = p;
        playerID = id;
    }
    
}
