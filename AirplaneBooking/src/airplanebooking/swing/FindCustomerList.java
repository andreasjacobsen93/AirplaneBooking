package airplanebooking.swing;

import airplanebooking.CurrentBooking;
import airplanebooking.DB.Customer;
import airplanebooking.GUI;
import java.util.ArrayList;

/**
 * FindCustomerList gets a list of customers.
 * @author Andreas Jacobsen
 */
public class FindCustomerList extends javax.swing.JFrame implements GUI 
{
    // List of customers
    ArrayList<Customer> list;
    
    /**
     * Creates new form FindCustomerList and sets list of customers.
     * @param list List of customer objects.
     * @see Customer
     */
    public FindCustomerList(ArrayList<Customer> list) {
        initComponents();
        this.list = list;

        for (int i = 0; i < list.size(); i++)
        {
            // add customer to list
            list1.add(list.get(i).getFirstName() + " " + list.get(i).getLastName());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        list1 = new java.awt.List();
        buttonChooseCustomer = new java.awt.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        buttonChooseCustomer.setLabel("Choose customer...");
        buttonChooseCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                buttonChooseCustomerMouseClicked(evt);
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
                    .addComponent(buttonChooseCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(list1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonChooseCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Called when the choose-customer button is clicked.
     * Updates the current booking with the chosen customer.
     * @param evt 
     */
    private void buttonChooseCustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonChooseCustomerMouseClicked
        // Add customer from list to current booking.
        CurrentBooking.addCustomer(list.get(list1.getSelectedIndex()));
        CurrentBooking.update();
        
        // Close this frame.
        this.dispose();
    }//GEN-LAST:event_buttonChooseCustomerMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button buttonChooseCustomer;
    private java.awt.List list1;
    // End of variables declaration//GEN-END:variables

    /**
     * Used to load frame.
     */
    @Override
    public void run() {
        setVisible(true);
    }
}
