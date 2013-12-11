package airplanebooking.DB;

/**
 * Seat class is used to create a seat with data.
 * @author Andreas Jacobsen
 */
public class Seat {

    // Identification number of seat.
    private final Integer seatID;
    
    // Whether seat is the last seat on flight or not.
    private Boolean isFinalSeat;

    /**
     * Constructor for creating seat object with identification number.
     * @param seatID Identification number for seat as integer.
     */
    public Seat(int seatID) 
    {
        // Set isFinal to false
        this.isFinalSeat = false;

        // The index of the seat
        this.seatID = seatID;
    }
    
    /**
     * Constructor for creating seat object with identification number and set isFinal for seat.
     * @param seatID Identification number for seat as integer.
     * @param isFinal Whether seat is the last seat on flight or not as boolean.
     */
    public Seat(int seatID, Boolean isFinal) 
    {
        // Whether seat is the last seat on flight or not
        this.isFinalSeat = isFinal;
        
        // The index of the seat
        this.seatID = seatID;
    }
    
    /**
     * This method gets the identification number for the seat.
     * @return Identification number as integer.
     */
    public int getSeatID() {
        return seatID;
    }

    /**
     * This method sets the seat as final seat on flight.
     */
    public void setFinalSeat() {
        isFinalSeat = true;
    }
    
    /**
     * This method gets whether the seat is final on flight or not.
     * @return Whether seat is the last seat on flight or not as boolean. 
     */
    protected boolean isFinalSeat(){
        return isFinalSeat;
    }
    
}
