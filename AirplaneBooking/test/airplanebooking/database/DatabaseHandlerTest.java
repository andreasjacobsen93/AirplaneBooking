/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

/**
 *
 * @author Christian
 */
public class DatabaseHandlerTest {
    
    public DatabaseHandlerTest() {
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
     * Test of ignoreSQLException method, of class DatabaseHandler.
     */
    @Test
    public void testIgnoreSQLException() {
        System.out.println("ignoreSQLException");
        String sqlState = "";
        boolean expResult = false;
        boolean result = DatabaseHandler.ignoreSQLException(sqlState);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of printSQLException method, of class DatabaseHandler.
     */
    @Test
    public void testPrintSQLException() {
        System.out.println("printSQLException");
        SQLException ex = null;
        DatabaseHandler.printSQLException(ex);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createCustomer method, of class DatabaseHandler.
     */
    @Test
    public void testCreateCustomer() {
        System.out.println("createCustomer");
        Customer customer = null;
        DatabaseHandler instance = new DatabaseHandler();
        instance.createCustomer(customer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
   /**
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
        
        
         //TODO review the generated test code and remove the default call to fail.
        //fail(The test case is a prototype.);
    }
    
    /**
     * Test of editCustomer method, of class DatabaseHandler.
     */
    @Test
    public void testEditCustomer() {
        System.out.println("editCustomer");
        Customer customer = null;
        DatabaseHandler instance = new DatabaseHandler();
        instance.editCustomer(customer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    
     /**
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
        
        //Customer currentCustomer = instance.getCustomer(currentCustomerID);
        
       
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
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
    /**
     * Test of deleteCustomer method, of class DatabaseHandler.
     */
    @Test
    public void testDeleteCustomer() {
        System.out.println("deleteCustomer");
        Customer customer = null;
        DatabaseHandler instance = new DatabaseHandler();
        instance.deleteCustomer(customer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    /**
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
        
       
        //Customer myEditedCustomer = new Customer(currentCustomerID, "Mr", "Jenson", "Andersen", "Vesterbrogade", 2300, "København", "Danmark", 28718355, "jensandersen@gmail.com");
        
        instance.deleteCustomer(currentCustomer);
        
        
        
        assertEquals("Mr" ,instance.getCustomer(currentCustomerID).getMaritalStatus());
        assertEquals("Jenson" ,instance.getCustomer(currentCustomerID).getFirstName());
        assertEquals("Andersen" ,instance.getCustomer(currentCustomerID).getLastName());
        assertEquals("Vesterbrogade" ,instance.getCustomer(currentCustomerID).getAddressStreet());
        assertEquals(2300 ,instance.getCustomer(currentCustomerID).getAddressZip());
        assertEquals("København" ,instance.getCustomer(currentCustomerID).getAddressCity());
        assertEquals("Danmark" ,instance.getCustomer(currentCustomerID).getAddressCountry());
        assertEquals(28718355 ,instance.getCustomer(currentCustomerID).getPhone());
        assertEquals("jensandersen@gmail.com" ,instance.getCustomer(currentCustomerID).getEmail());
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    
       
    }
    
    
    

    /**
     * Test of getCustomer method, of class DatabaseHandler.
     */
    @Test
    public void testGetCustomer() {
        System.out.println("getCustomer");
        int customerID = 0;
        DatabaseHandler instance = new DatabaseHandler();
        Customer expResult = null;
        Customer result = instance.getCustomer(customerID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCustomers method, of class DatabaseHandler.
     */
    @Test
    public void testGetCustomers_int() {
        System.out.println("getCustomers");
        int q = 0;
        DatabaseHandler instance = new DatabaseHandler();
        ArrayList<Customer> expResult = null;
        ArrayList<Customer> result = instance.getCustomers(q);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCustomers method, of class DatabaseHandler.
     */
    @Test
    public void testGetCustomers_String() {
        System.out.println("getCustomers");
        String q = "";
        DatabaseHandler instance = new DatabaseHandler();
        ArrayList<Customer> expResult = null;
        ArrayList<Customer> result = instance.getCustomers(q);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCustomers method, of class DatabaseHandler.
     */
    @Test
    public void testGetCustomers_4args() {
        System.out.println("getCustomers");
        String firstName = "";
        String lastName = "";
        String email = "";
        Integer Phone = null;
        DatabaseHandler instance = new DatabaseHandler();
        ArrayList<Customer> expResult = null;
        ArrayList<Customer> result = instance.getCustomers(firstName, lastName, email, Phone);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createReservation method, of class DatabaseHandler.
     */
    @Test
    public void testCreateReservation() {
        System.out.println("createReservation");
        Customer currentCustomer = null;
        Flight flight = null;
        ArrayList<Seat> seats = null;
        Boolean food = null;
        int cost = 0;
        DatabaseHandler instance = new DatabaseHandler();
        instance.createReservation(currentCustomer, flight, seats, food, cost);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of editReservation method, of class DatabaseHandler.
     */
    @Test
    public void testEditReservation() {
        System.out.println("editReservation");
        Booking booking = null;
        DatabaseHandler instance = new DatabaseHandler();
        instance.editReservation(booking);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
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
     * Test of getReservation method, of class DatabaseHandler.
     */
    @Test
    public void testGetReservation_int() {
        System.out.println("getReservation");
        int reservationID = 0;
        DatabaseHandler instance = new DatabaseHandler();
        Booking expResult = null;
        Booking result = instance.getReservation(reservationID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getReservation method, of class DatabaseHandler.
     */
    @Test
    public void testGetReservation_int_int() {
        System.out.println("getReservation");
        int seatID = 0;
        int flightID = 0;
        DatabaseHandler instance = new DatabaseHandler();
        Booking expResult = null;
        Booking result = instance.getReservation(seatID, flightID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createFlight method, of class DatabaseHandler.
     */
    @Test
    public void testCreateFlight() {
        System.out.println("createFlight");
        Flight flight = null;
        DatabaseHandler instance = new DatabaseHandler();
        instance.createFlight(flight);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFlight method, of class DatabaseHandler.
     */
    @Test
    public void testGetFlight() {
        System.out.println("getFlight");
        int flightID = 0;
        DatabaseHandler instance = new DatabaseHandler();
        Flight expResult = null;
        Flight result = instance.getFlight(flightID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCustomerReservations method, of class DatabaseHandler.
     */
    @Test
    public void testGetCustomerReservations() {
        System.out.println("getCustomerReservations");
        int customerID = 0;
        DatabaseHandler instance = new DatabaseHandler();
        ArrayList<Booking> expResult = null;
        ArrayList<Booking> result = instance.getCustomerReservations(customerID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFlightBookedSeats method, of class DatabaseHandler.
     */
    @Test
    public void testGetFlightBookedSeats() {
        System.out.println("getFlightBookedSeats");
        int flightID = 0;
        DatabaseHandler instance = new DatabaseHandler();
        ArrayList<Seat> expResult = null;
        ArrayList<Seat> result = instance.getFlightBookedSeats(flightID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of editFlight method, of class DatabaseHandler.
     */
    @Test
    public void testEditFlight() {
        System.out.println("editFlight");
        Flight flight = null;
        DatabaseHandler instance = new DatabaseHandler();
        instance.editFlight(flight);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
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

    /**
     * Test of getAirplane method, of class DatabaseHandler.
     */
    @Test
    public void testGetAirplane() {
        System.out.println("getAirplane");
        int airplaneID = 0;
        DatabaseHandler instance = new DatabaseHandler();
        Airplane expResult = null;
        Airplane result = instance.getAirplane(airplaneID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFlights method, of class DatabaseHandler.
     */
    @Test
    public void testGetFlights() {
        System.out.println("getFlights");
        Boolean freeSeatsOnly = null;
        DatabaseHandler instance = new DatabaseHandler();
        ArrayList<Flight> expResult = null;
        ArrayList<Flight> result = instance.getFlights(freeSeatsOnly);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFilteredFlights method, of class DatabaseHandler.
     */
    @Test
    public void testGetFilteredFlights() {
        System.out.println("getFilteredFlights");
        String[] filters = null;
        String comparer = "";
        DatabaseHandler instance = new DatabaseHandler();
        ArrayList<Flight> expResult = null;
        ArrayList<Flight> result = instance.getFilteredFlights(filters, comparer);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
