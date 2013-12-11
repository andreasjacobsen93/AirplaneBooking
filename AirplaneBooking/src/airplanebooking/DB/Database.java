/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package airplanebooking.DB;

/**
 *
 * @author Andreas
 */
public class Database {
    private static DatabaseInterface db;
    
    public static void set()
    {
        db = new DatabaseHandler();
    }
    
    public static DatabaseInterface db()
    {
        return db;
    }
}
