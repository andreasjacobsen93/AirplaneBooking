package airplanebooking;

import airplanebooking.DB.DatabaseHandler;
import airplanebooking.DB.Flight;
import airplanebooking.swing.SwingMain;

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
        
        //DatabaseHandler db = new DatabaseHandler();
        //Flight flight = db.getFlight(4);
        
        //System.out.println(flight.getArrivalTime());
        
        
    }
}
