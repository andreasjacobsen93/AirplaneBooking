package airplanebooking.gui;

import airplanebooking.CurrentFlight;
import airplanebooking.database.Booking;
import airplanebooking.database.Customer;
import airplanebooking.database.Database;
import airplanebooking.database.Flight;
import airplanebooking.database.Seat;
import airplanebooking.GUI;
import java.awt.Cursor;
import java.util.ArrayList;

/**
 * FindCustomerReservations is used to get a list of bookings by a customer.
 * @author Andreas Jacobsen
 */
public class FindCustomerReservations extends javax.swing.JFrame implements GUI {
    // List of bookings
    private ArrayList<Booking> bookingList;
    
    // List of customers
    private final ArrayList<Customer> list;
    
    /**
     * Creates new form FindCustomerReservations
     * @param list
     */
    public FindCustomerReservations(ArrayList<Customer> list) {
        this.list = list;
        
        initComponents();
        
        buttonChooseReservation.setEnabled(false);
        
        for (int i = 0; i < list.size(); i++)
        {
            listOfCustomers.add(list.get(i).getFirstName() + " " + list.get(i).getLastName());
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        listOfCustomers = new java.awt.List();
        listOfReservations = new java.awt.List();
        buttonChooseReservation = new java.awt.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        listOfCustomers.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                listOfCustomersItemStateChanged(evt);
            }
        });

        buttonChooseReservation.setLabel("Choose reservation...");
        buttonChooseReservation.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                buttonChooseReservationMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(listOfCustomers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(listOfReservations, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(buttonChooseReservation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(listOfCustomers, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(listOfReservations, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonChooseReservation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * This method is called when the chosen customer has changes.
     * Is used to update the reservation list from the chosen customer.
     * @param evt 
     */
    private void listOfCustomersItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_listOfCustomersItemStateChanged
        listOfReservations.removeAll();
        listOfReservations.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        listOfReservations.add("Searching...");
        
        bookingList = Database.db().getCustomerReservations(list.get(listOfCustomers.getSelectedIndex()).getID());
        
        listOfReservations.removeAll();
        if (bookingList == null) return;

        for (int i = 0; i < bookingList.size(); i++)
        {
            Flight f = bookingList.get(i).getFlight();
            listOfReservations.add(f.getDeparturePlace() + " - " + f.getArrivalPlace() + " (" + f.getDepartureTime() + ")");
        }
        
        if (bookingList.size() > 0) buttonChooseReservation.setEnabled(true);
        else buttonChooseReservation.setEnabled(false);
        listOfReservations.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_listOfCustomersItemStateChanged

    /**
     * This method is called when choose-reservation button is clicked.
     * Is used to set the current flight to that of the chosen reservation.
     * @param evt 
     */
    private void buttonChooseReservationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonChooseReservationMouseClicked
        buttonChooseReservation.setLabel("Getting reservation...");
        buttonChooseReservation.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        CurrentFlight.setFlight(bookingList.get(listOfReservations.getSelectedIndex()).getFlight());
        CurrentFlight.setSeat(bookingList.get(listOfReservations.getSelectedIndex()).getSeats().get(0));
        this.dispose();
    }//GEN-LAST:event_buttonChooseReservationMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button buttonChooseReservation;
    private java.awt.List listOfCustomers;
    private java.awt.List listOfReservations;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        setVisible(true);
    }
}
