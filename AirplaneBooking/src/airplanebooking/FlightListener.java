/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package airplanebooking;

import airplanebooking.DB.Flight;

/**
 *
 * @author Andreas
 */
public interface FlightListener {
    public void flightChanged(Flight flight);
    public void updateFlights();
}
