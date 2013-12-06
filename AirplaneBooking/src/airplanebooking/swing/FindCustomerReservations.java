package airplanebooking.swing;

import airplanebooking.Booking;
import airplanebooking.Customer;
import airplanebooking.DatabaseHandler;
import airplanebooking.DatabaseInterface;
import airplanebooking.GUI;
import java.util.ArrayList;
import static java.util.Collections.list;

/**
 *
 * @author Andreas
 */
public class FindCustomerReservations extends javax.swing.JFrame implements GUI {

    private ArrayList<Customer> list;
    /**
     * Creates new form FindCustomerReservations
     * @param list
     */
    public FindCustomerReservations(ArrayList<Customer> list) {
        this.list = list;
        
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
        DatabaseInterface db = new DatabaseHandler();
        ArrayList<Booking> bookingList = db.getCustomerReservations(list.get(list1.getSelectedIndex()).getID());
        for (int i = 0; i < bookingList.size(); i++)
        {
            
        }
    }//GEN-LAST:event_list1ItemStateChanged


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
