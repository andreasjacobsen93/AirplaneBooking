package airplanebooking;

import airplanebooking.database.Airplane;
import airplanebooking.database.Flight;
import airplanebooking.database.Seat;
import java.util.ArrayList;

/**
 * There can only be one of this class.
 * Is used to hold the current flight and inform
 * all listening classes that flight has been changed.
 * @author Andreas Jacobsen
 */
public class CurrentFlight 
{
    // List of listeners to notify when flight has changed
    private static final ArrayList<FlightListener> listeners = new ArrayList<>();
    
    // The current flight
    private static Flight flight;
    
    // The current airplane the flight is on
    private static Airplane airplane;
    
    // The current seat
    private static Seat seat;
    
    /**
     * This method sets the current flight.
     * @param f Flight object with data.
     * @see Flight
     */
    public static void setFlight(Flight f)
    {
        flight = f;
        airplane = flight.getAirplane();
        updated(f);
    }
    
    /**
     * This method gets the current flight.
     * @return Flight object with data.
     * @see Flight
     */
    public static Flight getFlight()
    {
        return flight;
    }
    
    /**
     * This method gets the current airplane.
     * @return Airplane object with data.
     * @see Airplane
     */
    public static Airplane getAirplane()
    {
        return airplane;
    }
    
    /**
     * This method sets the current seat on current flight.
     * @param s Seat object with seat number.
     * @see Seat
     */
    public static void setSeat(Seat s)
    {
        seat = s;
        updateSeat();
    }
    
    /**
     * This method gets the current seat on current flight.
     * @return Seat object with seat number.
     */
    public static Seat getSeat()
    {
        return seat;
    }
    
    /**
     * This method adds a listener to the list.
     * @param listener Flight listener to notify on update.
     */
    public static void addListener(FlightListener listener)
    {
        listeners.add(listener);
    }
    
    /**
     * This method is used to notify all listeners
     * that the current selected seat has been changed.
     */
    public static void updateSeat() {
        // Notify everybody that may be interested.
        for (FlightListener fl : listeners) {
            fl.seatChanged();
        }
    }
    
    /**
     * This method is used to notify all listeners
     * that list of flights should be updated.
     */
    public static void updateFlights() {
        // Notify everybody that may be interested.
        for (FlightListener fl : listeners) {
            fl.updateFlights();
        }
    }
    
    /**
     * This method is used to notify all listeners
     * that the current flight has been updated
     * @param flight
     * @see Flight
     */
    public static void updated(Flight flight) {
        // Notify everybody that may be interested.
        for (FlightListener fl : listeners) {
            fl.flightChanged(flight);
        }
    }
}
