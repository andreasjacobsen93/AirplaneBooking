package airplanebooking.swing;

import airplanebooking.GUI;

/**
 *
 * @author Andreas
 */
public class FlightSearchFilter extends javax.swing.JFrame implements GUI {

    private final SwingMain swingMain;
    
    /**
     * Creates new form FlightSearchFilter
     * @param sm
     */
    public FlightSearchFilter(SwingMain sm) {
        this.swingMain = sm;
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
            public void mouseClicked(java.awt.event.MouseEvent evt) {
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
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonApplyFiltersMouseClicked(evt);
            }
        });

        buttonDeleteFilter.setEnabled(false);
        buttonDeleteFilter.setLabel("Delete selected filter");
        buttonDeleteFilter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonDeleteFilterMouseClicked(evt);
            }
        });

        label2.setText("Filters:");

        choiceComparer.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                choiceComparerItemStateChanged(evt);
            }
        });

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

    private void listFiltersItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_listFiltersItemStateChanged
        if (listFilters.getSelectedItem().equals("")) buttonDeleteFilter.setEnabled(false);
        else buttonDeleteFilter.setEnabled(true);
    }//GEN-LAST:event_listFiltersItemStateChanged

    private void buttonCreateFilterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonCreateFilterMouseClicked
        switch(choice.getSelectedItem())
        {
            case "First Class":
                    listFilters.add("First Class");
                break;
            case "Business Class":
                    listFilters.add("Business Class");
                break;
            case "Economy Class":
                    listFilters.add("Economy Class");
                break;
            case "From":
                    listFilters.add("From " + textField.getText());
                break;
            case "Destination":
                    listFilters.add("To " + textField.getText());
                break;
            case "Airplane name":
                    listFilters.add("Airplane " + textField.getText());
                break;
            case "Today":
                    listFilters.add("Today");
                break;
            case "Tomorrow":
                    listFilters.add("Tomorrow");
                break;
            case "Date":
                    listFilters.add("Date: " + textField.getText());
                break;
        }
    }//GEN-LAST:event_buttonCreateFilterMouseClicked

    private void buttonDeleteFilterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonDeleteFilterMouseClicked
        listFilters.remove(listFilters.getSelectedItem());
        buttonDeleteFilter.setEnabled(false);
    }//GEN-LAST:event_buttonDeleteFilterMouseClicked

    private void buttonApplyFiltersMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonApplyFiltersMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_buttonApplyFiltersMouseClicked

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

    private void choiceComparerItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_choiceComparerItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_choiceComparerItemStateChanged


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

    @Override
    public void run() {
        setVisible(true);
    }
}
