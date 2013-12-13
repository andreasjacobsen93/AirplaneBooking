/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airplanebooking.DB;

import java.util.ArrayList;

/**
 * This interface states all public methods available for interaction with the
 * database, and should provide sufficient coverage for developers wishing to
 * use it, and it's associated classes to build a user interface.
 * <p>
 *
 * Please note that while SQLExceptions are handled, NullPointerExceptions are
 * not, unless explicitly stated.
 *
 * @author Aleksandar Jonovic
 */
public interface DatabaseInterface {

    /*
     //Below are all customer related DB method declarations.
     */
    /**
     * This method creates a customer in the database, by taking a Customer
     * object, and reading the fields, and storing it in the database.<p>
     *
     * For more detailed information about the Customer object, please refer
     * to:<br> {@link airplanebooking.DB.Customer}
     *
     * @param customer {@link airplanebooking.DB.Customer}
     */
    public void createCustomer(Customer customer);

    /**
     * This method edits a customer in the database, by taking a Customer
     * object, and reading the fields, and editing the specific row in the
     * database.<p>
     *
     * For more detailed information about the Customer object, please refer
     * to:<br> {@link airplanebooking.DB.Customer}
     *
     * @param customer {@link airplanebooking.DB.Customer}
     */
    public void editCustomer(Customer customer);

    /**
     * This method deletes a customer in the database, by taking a Customer
     * object, and reading the fields, and deleting the specific row in the
     * database.<p>
     *
     * For more detailed information about the Customer object, please refer
     * to:<br> {@link airplanebooking.DB.Customer}
     *
     * @param customer {@link airplanebooking.DB.Customer}
     */
    public void deleteCustomer(Customer customer);

    /**
     * This method gets a Customer object from the database based on an integer
     * value.<p>
     *
     * For more detailed information about the Customer object, please refer
     * to:<br> {@link airplanebooking.DB.Customer}
     *
     * @param customerID Takes an Integer value, which corresponds to the
     * auto_incremented ID field of a Customer, and returns a Customer object
     * with the customer details.
     * @return Returns the customer as a Customer object, filled with
     * information from the database.
     */
    public Customer getCustomer(int customerID);

    /**
     * This method gets an ArrayList of Customer objects, by searching the
     * database based on a String value.<p>
     *
     * For more detailed information about the Customer object, please refer
     * to:<br> {@link airplanebooking.DB.Customer}
     *
     * @param q Takes a String value, which corresponds to either the Firstname,
     * Lastname or Email of customers.
     * @return Returns the customers as an ArrayList of Customer objects, filled
     * with information from the database.
     */
    public ArrayList<Customer> getCustomers(String q);

    /**
     * This method gets an ArrayList of Customer objects, by searching the
     * database based on an integer value.<p>
     *
     * For more detailed information about the Customer object, please refer
     * to:<br> {@link airplanebooking.DB.Customer}
     *
     * @param q Takes an Integer value, which corresponds to either the Zip
     * code, or the Phone number of customers.
     * @return Returns the customers as an ArrayList of Customer objects, filled
     * with information from the database.
     */
    public ArrayList<Customer> getCustomers(int q);

    /**
     * This method gets an ArrayList of Customer objects, by searching the
     * database based on three String values and/or an Integer value.<br>
     * Any combination of the parameters can work, and the parameters are
     * allowed to be NULL.<br>
     * The more search parameters that are used, the more specific the search
     * will become.<p>
     * For more detailed information about the Customer object, please refer
     * to:<br> {@link airplanebooking.DB.Customer}
     *
     * @param firstName String value which related to a potentially existing
     * customers Firstname.
     * @param lastName String value which related to a potentially existing
     * customers Lastname.
     * @param email String value which related to a potentially existing
     * customers Email.
     * @param Phone Integer value which related to a potentially existing
     * customers Phone.
     * @return
     */
    public ArrayList<Customer> getCustomers(String firstName, String lastName, String email, Integer Phone);

    /*
     //Below are all reservation related DB method declarations.
     */
    /**
     * This method creates a Booking in the database, by taking a Customer
     * object, a Flight object, an ArrayList of Seat objects, and two other
     * parameter.<p>
     * For more detailed information about the Booking object, please refer
     * to:<br> {@link airplanebooking.DB.Booking}
     * <p>
     * For more detailed information about the Customer object, please refer
     * to:<br> {@link airplanebooking.DB.Customer}
     * <p>
     * For more detailed information about the Flight object, please refer
     * to:<br> {@link airplanebooking.DB.Flight}
     * <p>
     * For more detailed information about the Seat Object, please refer to:<br>
     * {@link airplanebooking.DB.Seat}
     *
     * @param currentCustomer {@link airplanebooking.DB.Customer}
     * @param flight {@link airplanebooking.DB.Flight}
     * @param seats {@link airplanebooking.DB.Seat}
     * @param food Boolean value, where false indicates that the customer does
     * not want food on board the flight.
     * @param cost Integer value, which is the saved value for the total price
     * of this reservation.
     */
    public void createReservation(Customer currentCustomer, Flight flight, ArrayList<Seat> seats, Boolean food, int cost);

    /**
     * This method edits a reservation in the database, by taking a Booking
     * object, and reading the fields, and editing the specific row in the
     * database.<p>
     *
     * For more detailed information about the Booking object, please refer
     * to:<br> {@link airplanebooking.DB.Booking}
     *
     * @param booking {@link airplanebooking.DB.Booking}
     */
    public void editReservation(Booking booking);

    /**
     * This method deletes a reservation in the database, by taking a Booking
     * object, and reading the fields, and deleting the specific row in the
     * database.<p>
     *
     * For more detailed information about the Booking object, please refer
     * to:<br> {@link airplanebooking.DB.Booking}
     *
     * @param booking {@link airplanebooking.DB.Booking}
     */
    public void deleteReservation(Booking booking);

    /**
     * This method gets a Booking object, by searching the database based on an
     * Integer value.<p>
     *
     * For more detailed information about the Booking object, please refer
     * to:<br> {@link airplanebooking.DB.Booking}
     *
     * @param reservationID Takes an Integer value, which corresponds to the
     * unique auto_incremented ID of a booking.
     * @return Returns the booking as a Booking object, filled with information
     * from the database.
     */
    public Booking getReservation(int reservationID);

    /**
     *
     * This method gets an ArrayList of Booking objects, by searching the
     * database based on an Integer value.<p>
     *
     * For more detailed information about the Booking object, please refer
     * to:<br> {@link airplanebooking.DB.Booking}
     *
     * @param customerID Takes an Integer value, which corresponds to the unique
     * auto_incremented ID of a customer.
     * @return Returns the given booking pertaining to a specific customer as an
     * ArrayList of Booking objects, filled with information from the database.
     */
    public ArrayList<Booking> getCustomerReservations(int customerID);

    /**
     * This method gets a Booking object, by searching the database based on an
     * two Integer values.<p>
     *
     * For more detailed information about the Booking object, please refer
     * to:<br> {@link airplanebooking.DB.Booking}
     *
     * @param seatID Takes an Integer value, which corresponds to the ID of a
     * given seat.
     * @param flightID Takes an Integer value, which corresponds to the unique
     * auto_incremented ID of a flight.
     * @return Returns the bookings as an ArrayList of Booking objects, filled
     * with information from the database.
     */
    public Booking getReservation(int seatID, int flightID);

    /*
     //Below are all flight related DB method declarations.
     */
    /**
     * This method creates a flight in the database, by taking a Flight object,
     * and reading the fields, and storing it in the database.<p>
     *
     * For more detailed information about the Flight object, please refer
     * to:<br> {@link airplanebooking.DB.Flight}
     *
     * @param flight {@link airplanebooking.DB.Flight}
     */
    public void createFlight(Flight flight);

    /**
     * This method edits a flight in the database, by taking a Flight object,
     * and reading the fields, and editing the specific row from the
     * database.<p>
     *
     * For more detailed information about the Flight object, please refer
     * to:<br> {@link airplanebooking.DB.Flight}
     *
     * @param flight {@link airplanebooking.DB.Flight}
     */
    public void editFlight(Flight flight);

    /**
     * This method deletes a flight in the database, by taking a Flight object,
     * and reading the fields, and deleting the specific row from the
     * database.<p>
     *
     * For more detailed information about the Flight object, please refer
     * to:<br> {@link airplanebooking.DB.Flight}
     *
     * @param flight {@link airplanebooking.DB.Flight}
     */
    public void deleteFlight(Flight flight);

    /**
     * This method gets a Flight object, by searching the database based on an
     * Integer value.<p>
     *
     * For more detailed information about the Flight object, please refer
     * to:<br> {@link airplanebooking.DB.Flight}
     *
     * @param flightID Takes an Integer value, which corresponds to the unique
     * auto_incremented ID of a flight.
     * @return Returns the given flight, filled with information from the
     * database as a Flight object.
     */
    public Flight getFlight(int flightID);

    /**
     * This method gets an ArrayList of Flight objects, by searching the
     * database based on an a Boolean value.<p>
     * This method returns only flights which have more than 2 hours left before
     * departure time - depending on the database SYSDATE() / NOW().
     * <p>
     *
     * For more detailed information about the Flight object, please refer
     * to:<br> {@link airplanebooking.DB.Flight}
     *
     * @param freeSeatsOnly Boolean value which determines whether or not this
     * method should return flights that are not full only, or not. 'TRUE' will
     * return only not full flights. 'FALSE' will return all flights from the
     * database.
     * @return Returns the flights as an ArrayList of Flight objects, filled
     * with information from the database. Only returns flights which have more
     * than 2 hours left before departure time.
     */
    public ArrayList<Flight> getFlights(Boolean freeSeatsOnly);

    /**
     * This method creates gets an ArrayList of flights from the database, by
     * taking an array of strings containing filter arguments, and values and
     * splitting them to construct a valid SQL statement and sending it to the
     * database.<p>
     *
     * For more detailed information about the Flight object, please refer
     * to:<br> {@link airplanebooking.DB.Flight}
     *
     * @param filters String array, which contains arguments and values
     * separated with a '.'.
     * @param comparer String, containing either AND, or, OR.
     */
    public ArrayList<Flight> getFilteredFlights(String[] filters, String comparer);

    /**
     * This method gets an ArrayList of Seat objects, by searching the database
     * based on an Integer value.<p>
     *
     * For more detailed information about the Seat object, please refer to:<br>
     * {@link airplanebooking.DB.Seat}
     *
     * @param flightID Takes an Integer value, which corresponds to the unique
     * auto_incremented ID of a flight.
     * @return Returns the booked seats of a specific flight, filled with
     * information from the database.
     */
    public ArrayList<Seat> getFlightBookedSeats(int flightID);

    //Below are all airplane related DB method declarations.
    /**
     * This method gets an Airplane object, by searching the database based on
     * an an Integer value.<p>
     *
     * For more detailed information about the Airplane object, please refer
     * to:<br> {@link airplanebooking.DB.Airplane}
     *
     * @param airplaneID Takes an Integer value, which corresponds to the unique
     * auto_incremented ID of an airplane.
     * @return Returns the airplane as a Airplane object, filled with
     * information from the database.
     */
    public Airplane getAirplane(int airplaneID);
}
