/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package airplanebooking.DB;

import java.sql.Timestamp;
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
     * Test of createCustomer method, of class DatabaseHandler.
     */
    @Test
    public void testCreateCustomer() {
        System.out.println("createCustomer");
        String maritalstatus = "";
        String firstname = "";
        String lastname = "";
        String addressStreet = "";
        int addressZip = 0;
        String addressCity = "";
        String addressCountry = "";
        String email = "";
        int phonenumber = 0;
        DatabaseHandler instance = new DatabaseHandler();
        instance.createCustomer(maritalstatus, firstname, lastname, addressStreet, addressZip, addressCity, addressCountry, email, phonenumber);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    /**
     * Test of createCustomer method, of class DatabaseHandler.
     */
    @Test
    public void testCreateCustomer1() {
        System.out.println("createCustomer");
        String maritalstatus = "Mr";
        String firstname = "Bob";
        String lastname = "Andersen";
        String addressStreet = "Bobs gade";
        int addressZip = 2300;
        String addressCity = "Bobs by";
        String addressCountry = "Bobs land";
        String email = "BobsMail@gmail.com";
        int phonenumber = 12345678;
        DatabaseHandler instance = new DatabaseHandler();
        instance.createCustomer(maritalstatus, firstname, lastname, addressStreet, addressZip, addressCity, addressCountry, email, phonenumber);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of editCustomer method, of class DatabaseHandler.
     */
    @Test
    public void testEditCustomer() {
        System.out.println("editCustomer");
        int customerID = 0;
        String maritalstatus = "";
        String firstname = "";
        String lastname = "";
        String addressStreet = "";
        int addressZip = 0;
        String addressCity = "";
        String addressCountry = "";
        String email = "";
        int phonenumber = 0;
        DatabaseHandler instance = new DatabaseHandler();
        instance.editCustomer(customerID, maritalstatus, firstname, lastname, addressStreet, addressZip, addressCity, addressCountry, email, phonenumber);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteCustomer method, of class DatabaseHandler.
     */
    @Test
    public void testDeleteCustomer() {
        System.out.println("deleteCustomer");
        int customerID = 0;
        DatabaseHandler instance = new DatabaseHandler();
        instance.deleteCustomer(customerID);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
     * This method tests if the ID of the returned customer matches the value of the parameter of the getCustomer method.
     * Test of getCustomer method, of class DatabaseHandler.
     */
    @Test
    public void testGetCustomer1() {
        System.out.println("getCustomer");
        int customerID = 1;
        DatabaseHandler instance = new DatabaseHandler();
        Customer myCostumer = instance.getCustomer(customerID);
        int expResult = 1;
        int result = myCostumer.getID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
     /**
     * revideret version.
     * Test of getCustomer method, of class DatabaseHandler.
     */
   // @Test
   // public void testGetCustomer() {
   //     System.out.println("getCustomer");
   //    
   //     DatabaseHandler db = new DatabaseHandler();
   //     Customer cs = new Customer(db.getCustomer(1));
   //     Customer expResult = cs;
   //     Customer result = new Customer(db.getCustomer(1));
   //     assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
   // }

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
        String Email = "";
        Integer Phone = null;
        DatabaseHandler instance = new DatabaseHandler();
        ArrayList<Customer> expResult = null;
        ArrayList<Customer> result = instance.getCustomers(firstName, lastName, Email, Phone);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of customerExists method, of class DatabaseHandler.
     */
    @Test
    public void testCustomerExists_Customer() {
        System.out.println("customerExists");
        Customer customer = null;
        DatabaseHandler instance = new DatabaseHandler();
        boolean expResult = false;
        boolean result = instance.customerExists(customer);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of customerExists method, of class DatabaseHandler.
     */
    @Test
    public void testCustomerExists_int() {
        System.out.println("customerExists");
        int customerID = 0;
        DatabaseHandler instance = new DatabaseHandler();
        boolean expResult = false;
        boolean result = instance.customerExists(customerID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of seatsExist method, of class DatabaseHandler.
     */
    @Test
    public void testSeatsExist() {
        System.out.println("seatsExist");
        ArrayList<Seat> seats = null;
        Flight flight = null;
        DatabaseHandler instance = new DatabaseHandler();
        boolean expResult = false;
        boolean result = instance.seatsExist(seats, flight);
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
        int food = 0;
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
        int reservationID = 0;
        int customerID = 0;
        String flightID = "";
        ArrayList<Seat> seats = null;
        int food = 0;
        int cost = 0;
        DatabaseHandler instance = new DatabaseHandler();
        instance.editReservation(reservationID, customerID, flightID, seats, food, cost);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteReservation method, of class DatabaseHandler.
     */
    @Test
    public void testDeleteReservation() {
        System.out.println("deleteReservation");
        int reservationID = 0;
        DatabaseHandler instance = new DatabaseHandler();
        instance.deleteReservation(reservationID);
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
    public void testGetReservation_int1() {
        System.out.println("getReservation");
        int reservationID = 1;
        DatabaseHandler instance = new DatabaseHandler();
        Booking myBooking = instance.getReservation(reservationID);
        int expResult = 1;
        int result = myBooking.getID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
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
        int airplaneID = 0;
        int firstCost = 0;
        int businessCost = 0;
        int economyCost = 0;
        String departurePlace = "";
        Timestamp departureTime = null;
        String arrivalPlace = "";
        Timestamp arrivalTime = null;
        DatabaseHandler instance = new DatabaseHandler();
        instance.createFlight(airplaneID, firstCost, businessCost, economyCost, departurePlace, departureTime, arrivalPlace, arrivalTime);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
     /**
     * Test of createFlight method, of class DatabaseHandler.
     */
    //@Test
    //public void testCreateFlight1() {
    //    System.out.println("createFlight");
    //    int airplaneID = 0;
    //    int firstCost = 0;
    //    int businessCost = 0;
    //    int economyCost = 0;
    //    String departurePlace = "";
    //    Timestamp departureTime = departureTime.valueOf("2005-12-11 00:00:00.000000");
    //    String arrivalPlace = "";
    //    Timestamp arrivalTime = arrivalTime.valueOf("2013-12-12 00:03:00.000000");
    //    DatabaseHandler instance = new DatabaseHandler();
    //    Flight myFlight = instance.createFlight(1, 20, 30, 40, "Copenhagen", arrivalTime.valueOf("2005-12-11 00:00:00.000000"), "London", departureTime.valueOf("2013-12-12 00:03:00.000000");
    //   int expResult = 0;
    //    int result = myFlight.getID();
    //    assertEquals(expResult, result);
    //    // TODO review the generated test code and remove the default call to fail.
    //    fail("The test case is a prototype.");
    //}
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
     * Test of getFlight method, of class DatabaseHandler.
     */
    @Test
    public void testGetFlight1() {
        System.out.println("getFlight");
        int flightID = 1;
        DatabaseHandler instance = new DatabaseHandler();
        Flight myFlight = instance.getFlight(flightID);
        int expResult = 1;
        int result = myFlight.getID();
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
        int flightID = 0;
        int airplaneID = 0;
        int firstCost = 0;
        int businessCost = 0;
        int economyCost = 0;
        String departurePlace = "";
        Timestamp departureTime = null;
        String arrivalPlace = "";
        Timestamp arrivalTime = null;
        DatabaseHandler instance = new DatabaseHandler();
    //////    instance.editFlight(flightID, airplaneID, firstCost, businessCost, economyCost, departurePlace, departureTime, arrivalPlace, arrivalTime);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteFlight method, of class DatabaseHandler.
     */
    @Test
    public void testDeleteFlight() {
        System.out.println("deleteFlight");
        int flightID = 0;
        DatabaseHandler instance = new DatabaseHandler();
        instance.deleteFlight(flightID);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteFlight method, of class DatabaseHandler.
     */
  //  @Test
  //  public void testDeleteFlight1() {
  //      System.out.println("deleteFlight");
  //      int flightID = 1;
  //     DatabaseHandler instance = new DatabaseHandler();
  //      createFlight(int airplaneID, int firstCost, int businessCost, int economyCost, String departurePlace, Timestamp departureTime, String arrivalPlace, Timestamp arrivalTime)
  //      instance.deleteFlight(flightID);
  //      // TODO review the generated test code and remove the default call to fail.
  //      fail("The test case is a prototype.");
  //  }
    
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
    
}
