package airplanebooking;

import airplanebooking.database.Database;
import airplanebooking.gui.SwingMain;

/**
 * Main class
 * @author Andreas Jacobsen
 */
public class AirplaneBooking {

    /**
     * Main method
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GUI mainFrame = new SwingMain();
        mainFrame.run();
    }
}
