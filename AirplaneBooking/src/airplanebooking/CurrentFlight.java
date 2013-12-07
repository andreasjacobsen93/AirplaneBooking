/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package airplanebooking;

import airplanebooking.DB.Airplane;
import airplanebooking.DB.DatabaseHandler;
import airplanebooking.DB.DatabaseInterface;
import airplanebooking.DB.Flight;
import java.util.ArrayList;

/**
 *
 * @author Andreas
 */
public class CurrentFlight {
    private static final ArrayList<FlightListener> listeners = new ArrayList<>();
    
    private static Flight flight;
    private static Airplane airplane;
    
    public static void setFlight(Flight f)
    {
        flight = f;
        DatabaseInterface db = new DatabaseHandler();
        airplane = db.getAirplane(flight.getAirplaneID());
        updated();
    }
    
    public static Flight getFlight()
    {
        return flight;
    }
    
    public static Airplane getAirplane()
    {
        return airplane;
    }
    
    public static void updated() {
        // Notify everybody that may be interested.
        for (FlightListener fl : listeners) {
            fl.flightChanged();
        }
    }
}