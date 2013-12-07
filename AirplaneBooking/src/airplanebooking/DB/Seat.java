/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package airplanebooking.DB;

/**
 *
 * @author Christian
 */
public class Seat
{
    // instance variables - replace the example below with your own
    private final Integer index;
    /**
     * Constructor for objects of class Seat
     * @param i
     * 
     */
    public Seat(int i)
    {
        // initialise instance variables
        // The index of the seat
        index = i;
    }

    public int getIndex()
    {
        return index;
    }
    
}
