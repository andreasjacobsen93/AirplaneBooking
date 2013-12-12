package airplanebooking;

import airplanebooking.DB.Database;
import airplanebooking.swing.SwingMain;
import com.mysql.jdbc.CommunicationsException;

/**
 *
 * @author Andreas
 */
public class AirplaneBooking {

    /**
     * Main class
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Database.set();
        GUI mainFrame = new SwingMain();
        mainFrame.run();
    }
}
