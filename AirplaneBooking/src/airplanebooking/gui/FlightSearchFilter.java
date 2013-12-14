package airplanebooking.gui;

import airplanebooking.database.Database;
import java.awt.Cursor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 * FlightSearchFilter is used to filter the list of flights.
 * @author Andreas Jacobsen
 */
public class FlightSearchFilter extends javax.swing.JFrame implements GUI {

    // The main frame to change list on.
    private final SwingMain swingMain;
    
    // List of filters to send.
    private final ArrayList<String> filterList;
    
    /**
     * Creates new form FlightSearchFilter.
     * Sets the swingMain object
     * and initializes components.
     * @param sm
     */
    public FlightSearchFilter(SwingMain sm) {
        this.swingMain = sm;
        filterList = new ArrayList<>();
        initComponents();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        button2 = new java.awt.Button();
        label1 = new java.awt.Label();
        choice = new java.awt.Choice();
        textField = new java.awt.TextField();
        buttonCreateFilter = new java.awt.Button();
        listFilters = new java.awt.List();
        buttonApplyFilters = new java.awt.Button();
        buttonDeleteFilter = new java.awt.Button();
        label2 = new java.awt.Label();
        choiceComparer = new java.awt.Choice();

        button2.setLabel("Create filter");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        label1.setText("Add filter:");

        choice.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                choiceItemStateChanged(evt);
            }
        });

        buttonCreateFilter.setLabel("Create filter");
        buttonCreateFilter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                buttonCreateFilterMouseClicked(evt);
            }
        });

        listFilters.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                listFiltersItemStateChanged(evt);
            }
        });

        buttonApplyFilters.setLabel("Apply filters...");
        buttonApplyFilters.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                buttonApplyFiltersMouseClicked(evt);
            }
        });

        buttonDeleteFilter.setEnabled(false);
        buttonDeleteFilter.setLabel("Delete selected filter");
        buttonDeleteFilter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                buttonDeleteFilterMouseClicked(evt);
            }
        });

        label2.setText("Filters:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(choice, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textField, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE))
                    .addComponent(listFilters, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(choiceComparer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonApplyFilters, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buttonCreateFilter, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonDeleteFilter, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(choice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonCreateFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(listFilters, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonDeleteFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(choiceComparer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonApplyFilters, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        choice.add("From");
        choice.add("Destination");
        choice.add("Airplane name");
        choice.add("Today");
        choice.add("Tomorrow");
        choice.add("Date");
        choice.add("First Class");
        choice.add("Business Class");
        choice.add("Economy Class");
        choiceComparer.add("All of the above");
        choiceComparer.add("Some of the above");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Called when selected item on list of filters is changed.
     * Changes the state of delete filter button to enabled or not.
     * @param evt 
     */
    private void listFiltersItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_listFiltersItemStateChanged
        if (listFilters.getSelectedItem().equals("")) buttonDeleteFilter.setEnabled(false);
        else buttonDeleteFilter.setEnabled(true);
    }//GEN-LAST:event_listFiltersItemStateChanged

    /**
     * Called when filter category is changed.
     * Set the textbox to enabled or not.
     * @param evt 
     */
    private void choiceItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_choiceItemStateChanged
        textField.setText("");
        switch(choice.getSelectedItem())
        {
            case "First Class":
                textField.setEnabled(false);
                break;
            case "Business Class":
                textField.setEnabled(false);
                break;
            case "Economy Class":
                textField.setEnabled(false);
                break;
            case "From":
                textField.setEnabled(true);
                break;
            case "Destination":
                textField.setEnabled(true);
                break;
            case "Airplane name":
                textField.setEnabled(true);
                break;
            case "Today":
                textField.setEnabled(false);
                break;
            case "Tomorrow":
                textField.setEnabled(false);
                break;
            case "Date":
                textField.setEnabled(true);
                break;
            default:
                textField.setEnabled(false);
                break;
        }
    }//GEN-LAST:event_choiceItemStateChanged

    private void buttonCreateFilterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonCreateFilterMouseClicked
        switch(choice.getSelectedItem())
        {
            case "First Class":
                    listFilters.add("First Class");
                    filterList.add("FirstClass");
                break;
            case "Business Class":
                    listFilters.add("Business Class");
                    filterList.add("BusinessClass");
                break;
            case "Economy Class":
                    listFilters.add("Economy Class");
                    filterList.add("EconomyClass");
                break;
            case "From":
                    listFilters.add("From " + textField.getText());
                    filterList.add("From." + textField.getText());
                break;
            case "Destination":
                    listFilters.add("To " + textField.getText());
                    filterList.add("To." + textField.getText());
                break;
            case "Airplane name":
                    listFilters.add("Airplane " + textField.getText());
                    filterList.add("Airplane." + textField.getText());
                break;
            case "Today":
                    listFilters.add("Today");
                    filterList.add("Today");
                break;
            case "Tomorrow":
                    listFilters.add("Tomorrow");
                    filterList.add("Tomorrow");
                break;
            case "Date":
                    try{
                        SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy");
                        String dateString = textField.getText().replaceAll("-", "/");
                        dateString = dateString.replaceAll("_", "/");
                        dateString = dateString.replaceAll("\\*", "/");
                        dateString = dateString.replaceAll(",", "/");
                        dateString = dateString.replaceAll("@", "/");
                        dateString = dateString.replaceAll(":", "/");
                        dateString = dateString.replaceAll(";", "/");
                        dateString = dateString.replaceAll("&", "/");
                        dateString = dateString.replaceAll("\\.", "/");
                        Date date = d.parse(dateString);

                        listFilters.add("Date: " + d.format(date));
                        filterList.add("Date." + d.format(date));
                    }
                    catch(ParseException e) {
                        JOptionPane.showMessageDialog(null, "Not a valid date format. Use dd/mm/yyy.");
                    }
                break;
        }
    }//GEN-LAST:event_buttonCreateFilterMouseClicked

    private void buttonDeleteFilterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonDeleteFilterMouseClicked
        listFilters.remove(listFilters.getSelectedIndex());
        filterList.remove(listFilters.getSelectedIndex());
        buttonDeleteFilter.setEnabled(false);
    }//GEN-LAST:event_buttonDeleteFilterMouseClicked

    private void buttonApplyFiltersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonApplyFiltersMouseClicked
        buttonApplyFilters.setLabel("Applying filters...");
        buttonApplyFilters.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        
        String comparer = "";
        switch (choiceComparer.getSelectedItem()){
            case "All of the above":
                comparer = "AND";
                break;
            case "Some of the above":
                comparer = "OR";
                break;
        }
        
        String[] filters = new String[filterList.size()];
        for (int i = 0; i < filterList.size(); i++)
        {
            filters[i] = filterList.get(i);
        }
        
        swingMain.updateSearch(Database.db().getFilteredFlights(filters, comparer));
        
        this.dispose();
    }//GEN-LAST:event_buttonApplyFiltersMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Button button2;
    private java.awt.Button buttonApplyFilters;
    private java.awt.Button buttonCreateFilter;
    private java.awt.Button buttonDeleteFilter;
    private java.awt.Choice choice;
    private java.awt.Choice choiceComparer;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private java.awt.List listFilters;
    private java.awt.TextField textField;
    // End of variables declaration//GEN-END:variables

    /**
     * This method load the frame.
     */
    @Override
    public void run() {
        setVisible(true);
    }
}
