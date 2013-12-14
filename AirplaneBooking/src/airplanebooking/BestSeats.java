package airplanebooking;

import airplanebooking.database.Seat;
import java.util.ArrayList;

/**
 * This class is used to store
 * seats, so the algorithm can compare them.
 * @author Andreas Jacobsen
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
