package airplanebooking;

import airplanebooking.DB.DatabaseHandler;
import airplanebooking.DB.Flight;
import airplanebooking.DB.Seat;
import airplanebooking.swing.SwingMain;
import java.util.ArrayList;

/**
 *
 * @author Andreas
 */
public class AirplaneBooking {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GUI mainFrame = new SwingMain();
        mainFrame.run();
        
<<<<<<< HEAD
<<<<<<< HEAD
        //DatabaseHandler db = new DatabaseHandler();
        //Flight flight = db.getFlight(4);
        
        //System.out.println(flight.getArrivalTime());
        
        
=======
=======
>>>>>>> bf5acae9a64d51ebf0c6de5e16252d3dd3ca04ea
        DatabaseHandler db = new DatabaseHandler();
       
        //Flight flight = db.getFlight(4);
        ArrayList<Flight> f = db.getFlights(true);
        for (Flight currentFlight : f){
            System.out.println(currentFlight.getArrivalPlace());
            ArrayList<Seat> seats = currentFlight.getSeats();
            for (Seat currentSeat : seats){
                System.out.println(currentSeat.getIndex());
            }
            
        }
               
<<<<<<< HEAD
>>>>>>> bf5acae9a64d51ebf0c6de5e16252d3dd3ca04ea
=======
>>>>>>> bf5acae9a64d51ebf0c6de5e16252d3dd3ca04ea
    }
}
