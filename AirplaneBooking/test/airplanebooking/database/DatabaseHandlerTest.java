/*
 * This testclass contains test methods for several of the central methods of the DatabaseHandler class.
 * The test methods must all be run in succession.
 */

package airplanebooking.database;

import java.sql.SQLException;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.Timestamp;

/**
 *
 * @author Christian
 */
public class DatabaseHandlerTest {
    private int currentFlightID;
    
    public DatabaseHandlerTest() {
       currentFlightID = 0;
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

   /**
    * WORKS!
    * Test of createCustomer method, of class DatabaseHandler.
    * This method tests the createCustomer method by testing if
    * the fields of the newly created customer are correctly assigned.
    */
    
    @Test
    public void testCreateCustomer1() {
        System.out.println("createCustomer");
        int currentCustomerID = 0; 
        Customer currentCustomer = new Customer("Mr", "Jens", "Andersen", "Vesterbrogade", 2300, "København", "Danmark", 28718355, "jensandersen@gmail.com");
        DatabaseHandler instance = new DatabaseHandler();
        instance.createCustomer(currentCustomer);
        
         ArrayList<Customer> customers = instance.getCustomers("Jens", "Andersen", "jensandersen@gmail.com", 28718355);
        for(Customer customer : customers) {
            currentCustomerID = customer.getID();
        }
        
        assertEquals("Mr" ,instance.getCustomer(currentCustomerID).getMaritalStatus());
        assertEquals("Jens" ,instance.getCustomer(currentCustomerID).getFirstName());
        assertEquals("Andersen" ,instance.getCustomer(currentCustomerID).getLastName());
        assertEquals("Vesterbrogade" ,instance.getCustomer(currentCustomerID).getAddressStreet());
        assertEquals(2300 ,instance.getCustomer(currentCustomerID).getAddressZip());
        assertEquals("København" ,instance.getCustomer(currentCustomerID).getAddressCity());
        assertEquals("Danmark" ,instance.getCustomer(currentCustomerID).getAddressCountry());
        assertEquals("jensandersen@gmail.com" ,instance.getCustomer(currentCustomerID).getEmail());
        assertEquals(28718355 ,instance.getCustomer(currentCustomerID).getPhone());
   
    }
    
    /**
     * WORKS!
     * Test of createFlight method, of class DatabaseHandler.
     */
    @Test
    public void testCreateFlight1() {
        System.out.println("createFlight");
        DatabaseHandler instance = new DatabaseHandler();
        Airplane currentAirplane = instance.getAirplane(1);
        ArrayList<Seat> seats = new ArrayList();
        Timestamp departureTime = java.sql.Timestamp.valueOf("2013-12-25 08:00:00.0"); 
        Timestamp arrivalTime = java.sql.Timestamp.valueOf("2013-12-25 10:00:00.0");
        Flight currentFlight = new Flight(currentAirplane, 100, 80, 50, seats, "Copenhagen", departureTime, "Bornholm", arrivalTime, false);
        instance.createFlight(currentFlight);
        int currentAirplaneID = currentFlight.getAirplaneID();
        
        
        // Store the ID of the current flight for later use.
        //currentFlightID = currentFlight.getID();
        ArrayList<Flight> flights = instance.getFlights(true);
        Flight newestFlight = flights.get(1);
        for (Flight thisFlight : flights){
            if (thisFlight.getID() > newestFlight.getID()){
                newestFlight = thisFlight;
                currentFlightID = newestFlight.getID();
            }
           
            //assertEquals(currentFlightID, currentFlight.getID());
        
            assertEquals(currentAirplane, currentFlight.getAirplane());
            assertEquals(currentAirplaneID, currentFlight.getAirplaneID());
            assertEquals(100, currentFlight.getFirstClassSeatCost());
            assertEquals(80, currentFlight.getBusinessClassSeatCost());
            assertEquals(50, currentFlight.getEconomyClassSeatCost());
        
            assertEquals(seats, currentFlight.getSeats());
            assertEquals("Copenhagen", currentFlight.getDeparturePlace());
            assertEquals(departureTime, currentFlight.getDepartureTimestamp());
            assertEquals("Bornholm", currentFlight.getArrivalPlace());
            assertEquals(arrivalTime, currentFlight.getArrivalTimestamp());
            assertEquals(false, currentFlight.isFull());
            
        }
        
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    /**
     * WORKS!
     * Test of createReservation method, of class DatabaseHandler.
     */
    @Test
    public void testCreateReservation1() {
        System.out.println("createReservation");
        int currentCustomerID = 0;
        int currentReservationID = 0;
        DatabaseHandler instance = new DatabaseHandler();
        
        ArrayList<Customer> customers = instance.getCustomers("Jens", "Andersen", "jensandersen@gmail.com", 28718355);
        for(Customer customer : customers) {
            currentCustomerID = customer.getID();
        }
        
        Customer currentCustomer = instance.getCustomer(currentCustomerID);
        
        // Retrieves the current flight by using the ID of that flight which was created and stored in a variable in previous methods.
                ArrayList<Flight> flights = instance.getFlights(true);
        Flight newestFlight = flights.get(1);
        for (Flight thisFlight : flights){
            if (thisFlight.getID() > newestFlight.getID()){
                newestFlight = thisFlight;
                currentFlightID = newestFlight.getID();
            }
           
        }
        Flight currentFlight = instance.getFlight(currentFlightID);
        
        ArrayList<Seat> seats = new ArrayList();
        
        seats.add(new Seat(1));
        seats.add(new Seat(2));
        
        Boolean food = true;
        int cost = 100;
        
        instance.createReservation(currentCustomer, currentFlight, seats, food, cost);
        
        ArrayList<Booking> bookings = instance.getCustomerReservations(currentCustomerID);
        for(Booking booking : bookings) {
            currentReservationID = booking.getID();
        }
        Booking currentReservation = instance.getReservation(currentReservationID);
        
        assertEquals(currentReservationID, instance.getReservation(currentReservationID).getID());
        assertEquals(currentCustomerID, currentCustomer.getID());
        assertEquals(currentFlightID, currentFlight.getID());
        assertEquals(2, currentReservation.getSeats().size());
        assertEquals(true, food);
        assertEquals(100, cost);
      
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    
    /**
     * WORKS!
     * Test of editCustomer method, of class DatabaseHandler.
     */
    @Test
    public void testEditCustomer1() {
        System.out.println("editCustomer");
        
        int currentCustomerID = 0; 
        
        DatabaseHandler instance = new DatabaseHandler();
        
        ArrayList<Customer> customers = instance.getCustomers("Jens", "Andersen", "jensandersen@gmail.com", 28718355);
        for(Customer customer : customers) {
            currentCustomerID = customer.getID();
        }  

        Customer currentEditedCustomer = new Customer(currentCustomerID, "Mr", "Jenson", "Andersen", "Vesterbrogade", 2300, "København", "Danmark", 28718355, "jensandersen@gmail.com");
        
        instance.editCustomer(currentEditedCustomer);
        
        
        
        assertEquals("Mr" ,instance.getCustomer(currentCustomerID).getMaritalStatus());
        assertEquals("Jenson" ,instance.getCustomer(currentCustomerID).getFirstName());
        assertEquals("Andersen" ,instance.getCustomer(currentCustomerID).getLastName());
        assertEquals("Vesterbrogade" ,instance.getCustomer(currentCustomerID).getAddressStreet());
        assertEquals(2300 ,instance.getCustomer(currentCustomerID).getAddressZip());
        assertEquals("København" ,instance.getCustomer(currentCustomerID).getAddressCity());
        assertEquals("Danmark" ,instance.getCustomer(currentCustomerID).getAddressCountry());
        assertEquals(28718355 ,instance.getCustomer(currentCustomerID).getPhone());
        assertEquals("jensandersen@gmail.com" ,instance.getCustomer(currentCustomerID).getEmail());
    }
    
    /**
     * WORKS!
     * Test of editFlight method, of class DatabaseHandler.
     */
    @Test
    public void testEditFlight1() {
        


        System.out.println("createFlight");
        DatabaseHandler instance = new DatabaseHandler();
        //Flight currentFlight = instance.getFlight(currentFlightID);
        Airplane currentAirplane = instance.getAirplane(1);
        ArrayList<Seat> seats = new ArrayList();

        Timestamp departureTime = java.sql.Timestamp.valueOf("2013-12-25 08:00:00.0"); 
        Timestamp arrivalTime = java.sql.Timestamp.valueOf("2013-12-25 10:00:00.0");
        
        ArrayList<Flight> flights = instance.getFlights(true);
        Flight newestFlight = flights.get(1);
        for (Flight thisFlight : flights){
            if (thisFlight.getID() > newestFlight.getID()){
                newestFlight = thisFlight;
                currentFlightID = newestFlight.getID();
            }
        Flight currentEditedFlight = new Flight(currentFlightID, currentAirplane, 120, 80, 50, seats, "Copenhagen", departureTime, "Bornholm", arrivalTime, false);
        instance.editFlight(currentEditedFlight);
        int currentAirplaneID = currentEditedFlight.getAirplaneID();
        
        
        // Store the ID of the current flight for later use.
        //currentFlightID = currentFlight.getID();

           
            //assertEquals(currentFlightID, currentFlight.getID());
        
            assertEquals(currentAirplane, currentEditedFlight.getAirplane());
            assertEquals(currentAirplaneID, currentEditedFlight.getAirplaneID());
            assertEquals(120,currentEditedFlight.getFirstClassSeatCost());
            assertEquals(80, currentEditedFlight.getBusinessClassSeatCost());
            assertEquals(50, currentEditedFlight.getEconomyClassSeatCost());
        
            assertEquals(seats, currentEditedFlight.getSeats());
            assertEquals("Copenhagen", currentEditedFlight.getDeparturePlace());
            assertEquals(departureTime, currentEditedFlight.getDepartureTimestamp());
            assertEquals("Bornholm", currentEditedFlight.getArrivalPlace());
            assertEquals(arrivalTime, currentEditedFlight.getArrivalTimestamp());
            assertEquals(false, currentEditedFlight.isFull());
    }
    }
        
    /**
     * DOES NOT WORK!
     * Test of editReservation method, of class DatabaseHandler.
     */
    @Test
    public void testEditReservation1() {
        System.out.println("editReservation");
        int currentCustomerID = 0;
        int currentReservationID = 0;
        DatabaseHandler instance = new DatabaseHandler();
        
        ArrayList<Customer> customers = instance.getCustomers("Jens", "Andersen", "jensandersen@gmail.com", 28718355);
        for(Customer customer : customers) {
            currentCustomerID = customer.getID();
        }
        
        Customer currentCustomer = instance.getCustomer(currentCustomerID);
        
        // Retrieves the current flight by using the ID of that flight which was created and stored in a variable in previous methods.
                ArrayList<Flight> flights = instance.getFlights(true);
        Flight newestFlight = flights.get(1);
        for (Flight thisFlight : flights){
            if (thisFlight.getID() > newestFlight.getID()){
                newestFlight = thisFlight;
                currentFlightID = newestFlight.getID();
            }
           
        }
        Flight currentFlight = instance.getFlight(currentFlightID);
        
        ArrayList<Seat> seats = new ArrayList();
        
        seats.add(new Seat(1));
        seats.add(new Seat(2));
        
        Boolean food = true;
        int cost = 100;
        
        
        instance.createReservation(currentCustomer, currentFlight, seats, food, cost);
        
        ArrayList<Booking> bookings = instance.getCustomerReservations(currentCustomerID);
        for(Booking booking : bookings) {
            currentReservationID = booking.getID();
        }
        Booking currentReservation = instance.getReservation(currentReservationID);
        
        assertEquals(currentReservationID, instance.getReservation(currentReservationID).getID());
        assertEquals(currentCustomerID, currentCustomer.getID());
        assertEquals(currentFlightID, currentFlight.getID());
        assertEquals(2, currentReservation.getSeats().size());
        assertEquals(true, food);
        assertEquals(100, cost);
    }

    /**
     * WORKS!
     * Test of deleteCustomer method, of class DatabaseHandler.
     */
    @Test
    public void testDeleteCustomer1() {
        System.out.println("deleteCustomer");
        
       int currentCustomerID = 0; 
        
        DatabaseHandler instance = new DatabaseHandler();
        
        ArrayList<Customer> customers = instance.getCustomers("Jenson", "Andersen", "jensandersen@gmail.com", 28718355);
        for(Customer customer : customers) {
            currentCustomerID = customer.getID();
       }
        
        Customer currentCustomer = instance.getCustomer(currentCustomerID);
   
        instance.deleteCustomer(currentCustomer);
        
        assertEquals("Mr" ,instance.getCustomer(currentCustomerID).getMaritalStatus());
        assertEquals("Jenson" ,instance.getCustomer(currentCustomerID).getFirstName());
        assertEquals("Andersen" ,instance.getCustomer(currentCustomerID).getLastName());
        assertEquals("Vesterbrogade" ,instance.getCustomer(currentCustomerID).getAddressStreet());
        assertEquals("København" ,instance.getCustomer(currentCustomerID).getAddressCity());
        assertEquals("Danmark" ,instance.getCustomer(currentCustomerID).getAddressCountry());
        assertEquals(28718355 ,instance.getCustomer(currentCustomerID).getPhone());
        assertEquals("jensandersen@gmail.com" ,instance.getCustomer(currentCustomerID).getEmail());
    }
    


    /**
     * NOT IMPLEMENTED!
     * Test of deleteReservation method, of class DatabaseHandler.
     */
    @Test
    public void testDeleteReservation() {
        System.out.println("deleteReservation");
        Booking booking = null;
        DatabaseHandler instance = new DatabaseHandler();
        instance.deleteReservation(booking);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


    /**
     * NOT IMPLEMENTED!
     * Test of deleteFlight method, of class DatabaseHandler.
     */
    @Test
    public void testDeleteFlight() {
        System.out.println("deleteFlight");
        Flight flight = null;
        DatabaseHandler instance = new DatabaseHandler();
        instance.deleteFlight(flight);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}