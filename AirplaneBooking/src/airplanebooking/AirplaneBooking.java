package airplanebooking;

import airplanebooking.database.Database;
import airplanebooking.gui.SwingMain;

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
        GUI mainFrame = new SwingMain();
        mainFrame.run();
    }
}
