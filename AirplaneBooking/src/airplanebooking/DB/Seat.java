package airplanebooking.DB;

/**
 *
 * @author Andreas Jacobsen
 */
public class Seat {

    // instance variables - replace the example below with your own
    private final Integer seatID;
    private Boolean isFinalSeat;

    /**
     * Constructor for objects of class Seat
     *
     * @param seatID
     */
    public Seat(int seatID) {
        this.isFinalSeat = false;
        // initialise instance variables
        // The index of the seat
        this.seatID = seatID;
    }
    public Seat(int seatID, Boolean isFinal) {
        this.isFinalSeat = isFinal;
        // initialise instance variables
        // The index of the seat
        this.seatID = seatID;
    }
    public int getSeatID() {
        return seatID;
    }

    public void setFinalSeat() {
        isFinalSeat = true;
    }
    
    protected boolean isFinalSeat(){
        return isFinalSeat;
    }
    
}
