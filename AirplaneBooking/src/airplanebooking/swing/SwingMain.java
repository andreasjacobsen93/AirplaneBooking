package airplanebooking.swing;

import airplanebooking.CurrentBooking;
import airplanebooking.CurrentFlight;
import airplanebooking.DB.Booking;
import airplanebooking.DB.Customer;
import airplanebooking.DB.Database;
import airplanebooking.DB.Flight;
import airplanebooking.DB.Seat;
import airplanebooking.FlightListener;
import airplanebooking.GUI;
import airplanebooking.SeatListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Andreas Jacobsen
 */
public class SwingMain extends javax.swing.JFrame implements GUI, FlightListener, SeatListener {

    private Boolean ready;
    private ArrayList<Flight> flights;
    private final ArrayList<javax.swing.JFrame> frames;
    private Booking booking;
    
    /**
     * Creates new form SwingMainFrame
     */
    public SwingMain() {
        frames = new ArrayList<>();
        
        initComponents();
        setTitle("Airplane Booking");
        ready = false;
        
        // Airplane
        jPanel3.setVisible(false); 
        
        // Options
        jPanel5.setVisible(false); 
        
        // Customer
        jPanel6.setVisible(false);
        checkboxLunchOnboard.setVisible(false);
        labelPrice.setVisible(false);
        labelTravelClass.setVisible(false); 
        labelSeats.setVisible(false);
        
        addFlightsToList(false);
    }
    
    private void addFlightsToList(Boolean freeSeatsOnly)
    {
        flights = Database.db().getFlights(freeSeatsOnly);
        
        for (Flight f : flights)
        {
            listFlights.add(f.getDeparturePlace() + " - " + f.getArrivalPlace() + " (" + f.getDepartureTime()+")");
        }
    }
        
    private void initComponents() {

        AirplaneCanvasPanel = new AirplaneCanvas();
        
        CurrentFlight.addListener(this);
        AirplaneCanvasPanel.addListener(this);
        
        jPanel1 = new javax.swing.JPanel();
        listFlights = new java.awt.List();
        buttonFindCustomer = new java.awt.Button();
        buttonFilter = new java.awt.Button();
        checkboxFreeSeatsOnly = new java.awt.Checkbox();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        buttonNewReservation = new java.awt.Button();
        labelAirplaneName = new java.awt.Label();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        buttonDeleteReservation = new java.awt.Button();
        buttonEditReservation = new java.awt.Button();
        labelSeats = new java.awt.Label();
        labelRoute = new java.awt.Label();
        labelTime = new java.awt.Label();
        checkboxLunchOnboard = new java.awt.Checkbox();
        labelTravelClass = new java.awt.Label();
        labelPrice = new java.awt.Label();
        jPanel6 = new javax.swing.JPanel();
        textMaritialStatus = new java.awt.TextField();
        textLastName = new java.awt.TextField();
        textAddress = new java.awt.TextArea();
        textPhone = new java.awt.TextField();
        textEmail = new java.awt.TextField();
        textFirstName = new java.awt.TextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1100, 800));

        listFlights.setMultipleMode(false);
        listFlights.addItemListener(new java.awt.event.ItemListener() {
            @Override
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                listFlightsItemStateChanged();
            }
        });

        buttonFindCustomer.setLabel("Find customer...");
        buttonFindCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonFindCustomerMouseClicked();
            }
        });

        buttonFilter.setLabel("Filter...");
        buttonFilter.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonFilterMouseClicked();
            }
        });

        checkboxFreeSeatsOnly.setLabel("Free seats only");
        checkboxFreeSeatsOnly.addItemListener(new java.awt.event.ItemListener() {
            @Override
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkboxFreeSeatsOnlyItemStateChanged();
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(listFlights, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(buttonFindCustomer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(checkboxFreeSeatsOnly, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(listFlights, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(buttonFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkboxFreeSeatsOnly, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonFindCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        buttonNewReservation.setLabel("New reservation...");
        buttonNewReservation.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonNewReservationMouseClicked();
            }
        });

        labelAirplaneName.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        labelAirplaneName.setText("");

        AirplaneCanvasPanel.setBackground(new java.awt.Color(153, 153, 153));
        AirplaneCanvasPanel.setMinimumSize(new java.awt.Dimension(0, 180));
        AirplaneCanvasPanel.setPreferredSize(new java.awt.Dimension(0, 180));

        javax.swing.GroupLayout AirplaneCanvasPanelLayout = new javax.swing.GroupLayout(AirplaneCanvasPanel);
        AirplaneCanvasPanel.setLayout(AirplaneCanvasPanelLayout);
        AirplaneCanvasPanelLayout.setHorizontalGroup(
            AirplaneCanvasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        AirplaneCanvasPanelLayout.setVerticalGroup(
            AirplaneCanvasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonNewReservation, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
                    .addComponent(labelAirplaneName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AirplaneCanvasPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelAirplaneName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AirplaneCanvasPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buttonNewReservation, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Information"));

        buttonDeleteReservation.setLabel("Delete reservation...");
        buttonDeleteReservation.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonDeleteReservationMouseClicked();
            }
        });
        
        buttonEditReservation.setLabel("Edit reservation...");
        buttonEditReservation.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonEditReservationMouseClicked();
            }
        });

        labelSeats.setText("Seats:");

        labelRoute.setText(" - ");

        labelTime.setText("");

        checkboxLunchOnboard.setEnabled(false);
        checkboxLunchOnboard.setLabel("Lunch on-board");
        checkboxLunchOnboard.setName(""); // NOI18N
        checkboxLunchOnboard.setState(true);

        labelTravelClass.setText("");

        labelPrice.setText("Price: 0 USD");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonDeleteReservation, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                    .addComponent(buttonEditReservation, javax.swing.GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
                    .addComponent(checkboxLunchOnboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelTravelClass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelPrice, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelRoute, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelSeats, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelSeats, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelRoute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkboxLunchOnboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelTravelClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(buttonEditReservation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(buttonDeleteReservation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Customer"));

        textMaritialStatus.setEditable(false);
        textMaritialStatus.setText("Mr.");

        textLastName.setEditable(false);
        textLastName.setName(""); // NOI18N
        textLastName.setText("customerLastname");
        

        textAddress.setEditable(false);
        textAddress.setMaximumSize(new java.awt.Dimension(100, 80));
        textAddress.setName(""); // NOI18N
        textAddress.setText("customerAddress");

        textPhone.setEditable(false);
        textPhone.setText("customerPhone");

        textEmail.setEditable(false);
        textEmail.setText("customerEmail");

        textFirstName.setEditable(false);
        textFirstName.setText("customerFirstName");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textLastName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textPhone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(textEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(textMaritialStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFirstName, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE))
                    .addComponent(textAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textMaritialStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textPhone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(textEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }   
    
    private void closeAllFrames()
    {
        for (javax.swing.JFrame f : frames)
        {
            f.dispose();
        }
    }
    
    @Override
    public void run()
    {
        setVisible(true);
    }
    
    private void buttonNewReservationMouseClicked() {                                                  

        if (CurrentFlight.getFlight().isFull())
        {
            JOptionPane.showMessageDialog(null, "All seats on flight is booked. 1");
        }
        else
        {
            if(ready == true) {
                // Only allow one new reservation frame at a time.
                closeAllFrames();
                
                CurrentBooking.reset();
                SwingNewReservation SNR = new SwingNewReservation(CurrentFlight.getFlight());
                CurrentBooking.addListener(SNR);
                frames.add(SNR);
                SNR.run();
            }
        }
    } 
    
    private void buttonFindCustomerMouseClicked(){
        GUI fcsForm = new FindCustomerSearch("reservations");
        fcsForm.run();
    } 
    
    private void buttonFilterMouseClicked(){
        GUI fsfForm = new FlightSearchFilter(this);
        fsfForm.run();
    }
    
    private void checkboxFreeSeatsOnlyItemStateChanged() {  
        listFlights.removeAll();
        
        addFlightsToList(checkboxFreeSeatsOnly.getState());
    }   
    
    // Variables declaration - do not modify
    private AirplaneCanvas AirplaneCanvasPanel;
    private java.awt.Button buttonDeleteReservation;
    private java.awt.Button buttonEditReservation;
    private java.awt.Button buttonFilter;
    private java.awt.Button buttonFindCustomer;
    private java.awt.Button buttonNewReservation;
    private java.awt.Checkbox checkboxFreeSeatsOnly;
    private java.awt.Checkbox checkboxLunchOnboard;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private java.awt.Label labelAirplaneName;
    private java.awt.Label labelPrice;
    private java.awt.Label labelRoute;
    private java.awt.Label labelSeats;
    private java.awt.Label labelTime;
    private java.awt.Label labelTravelClass;
    private java.awt.List listFlights;
    private java.awt.TextArea textAddress;
    private java.awt.TextField textEmail;
    private java.awt.TextField textFirstName;
    private java.awt.TextField textLastName;
    private java.awt.TextField textMaritialStatus;
    private java.awt.TextField textPhone;
    // End of variables declaration  
    
    public void listFlightsItemStateChanged()
    {
        CurrentFlight.setFlight(flights.get(listFlights.getSelectedIndex()));
    }
    
    public void buttonEditReservationMouseClicked()
    {
        if(ready == true) {
            // Only allow one new reservation frame at a time.
            closeAllFrames();

            CurrentBooking.reset();
            SwingEditReservation SER = new SwingEditReservation(booking);
            CurrentBooking.addListener(SER);
            frames.add(SER);
            SER.run();
        }
    }
    
    public void buttonDeleteReservationMouseClicked()
    {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog (null, "The reservation will be deleted from the system. Are you sure?","Warning",dialogButton);
        if(dialogResult == JOptionPane.YES_OPTION)
        {
            Database.db().deleteReservation(booking);
            CurrentBooking.reset();
            //CurrentBooking.addFlight(db.getFlight(booking.getFlight().getID()));
            CurrentFlight.setFlight(Database.db().getFlight(booking.getFlight().getID()));
            CurrentFlight.updateFlights();
        }
    }
    
    public void updateSearch()
    {
        
    }

    @Override
    public void flightChanged(Flight flight)
    {
        AirplaneCanvasPanel.setAirplaneCanvas(false, flight);
        
        jPanel3.setVisible(true); // Airplane
        jPanel5.setVisible(true); // Options
        
        // Customer
        jPanel6.setVisible(false);
        checkboxLunchOnboard.setVisible(false);
        labelPrice.setVisible(false);
        labelTravelClass.setVisible(false);
        labelSeats.setVisible(false);
        
        ready = true;
        
        labelAirplaneName.setText(flight.getAirplane().getName() + " " + flight.getAirplane().getID() + "A" + flight.getID());
        labelRoute.setText(flight.getDeparturePlace() + " - " + flight.getArrivalPlace());
        labelTime.setText(flight.getDepartureTime());
    }
    
    @Override
    public void seatChanged() {
        // Customer
        jPanel6.setVisible(true);
        checkboxLunchOnboard.setVisible(true);
        labelPrice.setVisible(true);
        labelTravelClass.setVisible(true); 
        labelSeats.setVisible(true);
        
        Booking b = Database.db().getReservation(CurrentFlight.getSeat(), CurrentFlight.getFlight().getID());
        booking = b;
        Customer c = b.getCustomer();
        
        textMaritialStatus.setText(c.getMaritalStatus());
        textFirstName.setText(c.getFirstName());
        textLastName.setText(c.getLastName());
        textAddress.setText(c.getAddressStreet()+"\n"+c.getAddressCity()+" "+c.getAddressZip()+"\n"+c.getAddressCountry());
        textPhone.setText(String.valueOf(c.getPhone()));
        textEmail.setText(c.getEmail());
        
        Boolean EconomyClass = false;
        Boolean BusinessClass = false;
        Boolean FirstClass = false;
        
        AirplaneCanvasPanel.clickSeats(b.getSeats());
        
        int i = 0;
        String seats = "Seats: ";
        for (Seat s : b.getSeats())
        {
            // Current seat is First Class
            if (b.getFlight().getAirplane().getFirstSeats() > i) {
                FirstClass = true;
            } 
            // Current seat is Business Class
            else if (b.getFlight().getAirplane().getFirstSeats() + b.getFlight().getAirplane().getBusinessSeats() > i) {
                BusinessClass = true;
            } 
            // Current seat is Economy Class
            else {
                EconomyClass = true;
            }
            
            if (i == 0) seats += ""+s.getSeatID();
            else seats += ", "+s.getSeatID();
            i++;
        }
        if (i > 0) labelSeats.setText(seats);
        else labelSeats.setText("No seats chosen.");

        // Classes label
        i = 0;
        String classes = "";
        if (FirstClass) {
            classes += "First Class";
            i++;
        }
        if (BusinessClass) {
            if (i>0) classes += ", ";
            classes += "Business Class";
            i++;
        }
        if (EconomyClass) {
            if (i>0) classes += ", ";
            classes += "Economy Class";
            i++;
        }
        if (i > 0) labelTravelClass.setText(classes);
        else labelTravelClass.setText("No seats chosen.");
        
        checkboxLunchOnboard.setState(b.getFood());

        labelPrice.setText("Price: " + b.getPrice() + " USD");
    }

    @Override
    public void updateFlights() {
        listFlights.removeAll();
        
        addFlightsToList(checkboxFreeSeatsOnly.getState());
    }
}
