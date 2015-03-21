/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shared;

/**
 *
 * @author Drew
 */
public class Cookie {
    private String name;
    private String password;
    private int playerID;
    
    public Cookie(int id, String n, String p)
    {
        name = n;
        password = p;
        playerID = id;
    }
    
}
