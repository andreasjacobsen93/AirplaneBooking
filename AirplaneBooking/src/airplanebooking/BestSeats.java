/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package airplanebooking;

import airplanebooking.database.Seat;
import java.util.ArrayList;

/**
 *
 * @author Andreas
 */
public class BestSeats {
    public ArrayList<Seat> seats;
    public ArrayList<Integer> tempSeats;
    public int score;
    public BestSeats() {
        seats = new ArrayList<>();
        tempSeats = new ArrayList<>();
    }
    public void resetTempSeats()
    {
        tempSeats.clear();
    }
}
