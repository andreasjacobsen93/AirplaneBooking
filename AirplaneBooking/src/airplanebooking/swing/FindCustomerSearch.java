package airplanebooking.swing;

import airplanebooking.DB.Customer;
import airplanebooking.DB.Database;
import airplanebooking.GUI;
import java.awt.Cursor;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * FindCustomerSearch is used to search for an existing customer in database.
 * @author Andreas Jacobsen
 */
public class FindCustomerSearch extends javax.swing.JFrame implements GUI {

    // Determines what to do next (reservations/customers).
    private final String nextStep;
    
    /**
     * Creates new form FindCustomerSearch
     * @param nextStep
     */
    public FindCustomerSearch(String nextStep) {
        this.nextStep = nextStep;
        initComponents();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel9 = new javax.swing.JPanel();
        textLastName3 = new java.awt.TextField();
        textPhone = new java.awt.TextField();
        textEmail = new java.awt.TextField();
        textFirstName = new java.awt.TextField();
        buttonSearch = new java.awt.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Search for customer"));

        textLastName3.setName(""); // NOI18N
        textLastName3.setText("customerLastName");

        textPhone.setText("customerPhone");

        textEmail.setText("customerEmail");

        textFirstName.setText("customerFirstName");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textPhone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(textFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textLastName3, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textLastName3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        buttonSearch.setLabel("Search for customer...");
        buttonSearch.setName(""); // NOI18N
        buttonSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                buttonSearchMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        buttonSearch.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonSearchMouseClicked
        buttonSearch.setLabel("Searching...");
        buttonSearch.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        String firstname = null;
        String lastname = null;
        String email = null;
        Integer phone = null;
        
        if (!textFirstName.getText().isEmpty() && !textFirstName.getText().equals("customerFirstName")) 
            firstname = textFirstName.getText();
        
        if (!textLastName3.getText().isEmpty() && !textLastName3.getText().equals("customerLastName")) 
            lastname = textLastName3.getText();
        
        if (!textEmail.getText().isEmpty() && !textEmail.getText().equals("customerEmail")) 
            email = textEmail.getText();
        
        if (!textPhone.getText().isEmpty() && !textPhone.getText().equals("customerPhone")) 
            phone = Integer.parseInt(textPhone.getText());
        
        if (firstname == null && lastname == null && email == null && phone == null)
        {
            JOptionPane.showMessageDialog(null, "No data is inputted!");
            return;
        }
        
        ArrayList<Customer> list = Database.db().getCustomers(firstname, lastname, email, phone);
        
        switch(nextStep)
        {
            case "reservations":
                    GUI fcrForm = new FindCustomerReservations(list);
                    fcrForm.run();
                    this.dispose();
                break;
            case "customers":
                    GUI fclForm = new FindCustomerList(list);
                    fclForm.run();
                    this.dispose();
                break;
        }
    }//GEN-LAST:event_buttonSearchMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button buttonSearch;
    private javax.swing.JPanel jPanel9;
    private java.awt.TextField textEmail;
    private java.awt.TextField textFirstName;
    private java.awt.TextField textLastName3;
    private java.awt.TextField textPhone;
    // End of variables declaration//GEN-END:variables

    /**
     * This method load the frame.
     */
    @Override
    public void run() {
        setVisible(true);
    }
}
