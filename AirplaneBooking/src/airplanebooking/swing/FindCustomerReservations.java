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
    private final DatabaseInterface db;
    /**
     * Creates new form FindCustomerReservations
     * @param list
     */
    public FindCustomerReservations(ArrayList<Customer> list) {
        this.list = list;
        db = new DatabaseHandler();
        
        initComponents();
        
        for (int i = 0; i < list.size(); i++)
        {
            list1.add(list.get(i).getFirstName() + " " + list.get(i).getLastName());
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        list1 = new java.awt.List();
        list2 = new java.awt.List();
        button1 = new java.awt.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        list1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                list1ItemStateChanged(evt);
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
                    .addComponent(list1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(list2, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(button1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(list1, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(list2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void list1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_list1ItemStateChanged
        list2.removeAll();
        
        
        bookingList = db.getCustomerReservations(list.get(list1.getSelectedIndex()).getID());
        
        for (int i = 0; i < bookingList.size(); i++)
        {
            Flight f = db.getFlight(bookingList.get(i).getFlightID());
            list2.add(f.getDeparturePlace() + " - " + f.getArrivalPlace() + " @" + f.getDepartureTime());
        }
    }//GEN-LAST:event_list1ItemStateChanged

    private void button1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button1MouseClicked
        CurrentFlight.setFlight(db.getFlight(bookingList.get(list2.getSelectedIndex()).getFlightID()));
        this.dispose();
    }//GEN-LAST:event_button1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button button1;
    private java.awt.List list1;
    private java.awt.List list2;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        setVisible(true);
    }
}
