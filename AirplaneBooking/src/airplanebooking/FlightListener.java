package airplanebooking;

import airplanebooking.DB.Flight;

/**
 * Interface for event listener.
 * Used when the current flight has changed.
 * @author Andreas Jacobsen
 */
public interface FlightListener {
    public void flightChanged(Flight flight);
    public void updateFlights();
}
