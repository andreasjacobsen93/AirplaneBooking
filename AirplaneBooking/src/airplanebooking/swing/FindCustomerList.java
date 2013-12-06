package airplanebooking.swing;

import airplanebooking.CurrentBooking;
import airplanebooking.Customer;
import airplanebooking.GUI;
import java.util.ArrayList;

/**
 *
 * @author Andreas
 */
public class FindCustomerList extends javax.swing.JFrame implements GUI {

    ArrayList<Customer> list;
    /**
     * Creates new form FindCustomerList
     * @param list
     */
    public FindCustomerList(ArrayList<Customer> list) {
        initComponents();
        this.list = list;

        for (int i = 0; i < list.size(); i++)
        {
            list1.add(list.get(i).getFirstName() + " " + list.get(i).getLastName());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        list1 = new java.awt.List();
        button1 = new java.awt.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        button1.setLabel("Choose customer...");
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
                    .addComponent(button1, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(list1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void button1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button1MouseClicked
        // TODO add your handling code here:
        CurrentBooking.addCustomer(list.get(list1.getSelectedIndex()));
        CurrentBooking.update();
        this.dispose();
    }//GEN-LAST:event_button1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button button1;
    private java.awt.List list1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        setVisible(true);
    }
}
