/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package airplanebooking;

import airplanebooking.swing.SwingMain;

/**
 *
 * @author Andreas
 */
public class AirplaneBooking{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        GUI app = new SwingMain();
        app.loadFrame();
    }
}
