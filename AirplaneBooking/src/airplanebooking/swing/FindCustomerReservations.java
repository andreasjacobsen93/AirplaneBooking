package airplanebooking.swing;

import airplanebooking.CurrentFlight;
import airplanebooking.DB.Booking;
import airplanebooking.DB.Customer;
import airplanebooking.DB.DatabaseHandler;
import airplanebooking.DB.DatabaseInterface;
import airplanebooking.DB.Flight;
import airplanebooking.GUI;
import java.util.ArrayList;

/**
 *
 * @author Andreas
 */
public class FindCustomerReservations extends javax.swing.JFrame implements GUI {
    private ArrayList<Booking> bookingList;
    private final ArrayList<Customer> list;
    /**
     * Creates new form FindCustomerReservations
     * @param list
     */
    public FindCustomerReservations(ArrayList<Customer> list) {
        this.list = list;
        
        initComponents();
        
        for (int i = 0; i < list.size(); i++)
        {
            listOfCustomers.add(list.get(i).getFirstName() + " " + list.get(i).getLastName());
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        listOfCustomers = new java.awt.List();
        listOfReservations = new java.awt.List();
        button1 = new java.awt.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        listOfCustomers.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                listOfCustomersItemStateChanged(evt);
            }
        });

        button1.setLabel("Choose reservation...");
        button1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button1MouseClicked(evt);
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
                    .addComponent(button1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void listOfCustomersItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_listOfCustomersItemStateChanged
        listOfReservations.removeAll();
        
        DatabaseInterface db = new DatabaseHandler();
        
        bookingList = db.getCustomerReservations(list.get(listOfCustomers.getSelectedIndex()).getID());
        
        if (bookingList == null) return;
        
        for (int i = 0; i < bookingList.size(); i++)
        {
            Flight f = bookingList.get(i).getFlight();
            listOfReservations.add(f.getDeparturePlace() + " - " + f.getArrivalPlace() + " @" + f.getDepartureTime());
        }
    }//GEN-LAST:event_listOfCustomersItemStateChanged

    private void button1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button1MouseClicked
        CurrentFlight.setFlight(bookingList.get(listOfReservations.getSelectedIndex()).getFlight());
        this.dispose();
    }//GEN-LAST:event_button1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button button1;
    private java.awt.List listOfCustomers;
    private java.awt.List listOfReservations;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        setVisible(true);
    }
}
