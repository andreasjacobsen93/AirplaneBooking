/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airplanebooking.DB;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * If you are spawning more than one of these objects, you are most likely doing
 * it wrong.
 *
 * @author Alex
 */
public class DatabaseHandler implements DatabaseInterface {

    //DB & SQL fields
    private static final String user = "aljon";
    private static final String pass = "Jegeradministratorher123";
    private static final String jdbcurl = "jdbc:mysql://mysql.itu.dk/Airplanebooking";
    Connection con = null;
    PreparedStatement pstatement = null;

    ResultSet results = null;
    SQLWarning warning = new SQLWarning();
    ComboPooledDataSource cpds = new ComboPooledDataSource();

    //Customer field
    Customer customer;
    ArrayList<Customer> customers = new ArrayList();

    //Booking field
    Booking reservation = null;
    ArrayList<Booking> reservations = new ArrayList();

    //Flight field
    Flight flight = null;
    ArrayList<Flight> flights = new ArrayList();

    //Seat field
    Seat seat = null;
    ArrayList<Seat> seats = new ArrayList();

    //Airplane field
    Airplane airplane = null;

    /**
     *
     */
    public DatabaseHandler() {
        //contructor method to initiate (combo)DataSource for pooled connections on spawning an object.
        initiateDataSource();
    }

    private void initiateDataSource() {
        // initiates the connection to the database, and sets the basic parameters for the connection pooling.
        cpds.setJdbcUrl(jdbcurl);
        cpds.setUser(user);
        cpds.setPassword(pass);
        cpds.setMinPoolSize(1);
        cpds.setAcquireIncrement(3);
        cpds.setMaxPoolSize(10);
    }

    private void executeUpdate(String sql) {
        //SET method, for putting data into the database.
        try {
            //Execute the prepared statement.
            pstatement.executeUpdate(sql);

        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while executing the update", e);
        } finally {
            //  closeConnection();
        }
    }

    private ResultSet executeQuery(String sql) {
        //GET method for getting data from the database.

        try {

            //pass results from statement handler to ResultSet and save the it.
            results = pstatement.executeQuery(sql);

        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while executing the query.", e);
        } finally {
            // closeConnection();
        }
        return results;
    }

    /*
     *
     *
     * Still testing this sucker. Not yet implemented.
     *
     */
    private String getWarnings() {

        String status = warning.getMessage();
        //return status;
        if (status != null) {
            return status;
        } else {
            status = "Completed without error.";
            return status;
        }

    }

    private void closeConnection() {
        try {

            if (con != null) {
                pstatement.close();
                //
                results.close();
                // results = null;
                con.close();
                // con = null;
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong with closing the connection", ex);
        }
    }

    /**
     * This method creates a row in the database, containing the parameters that
     * match the input, and thus creates and stores a new Customer.
     *
     * @param customer This parameter is a
     *
     * String value for Marital Status. <br><b>Max 10 characters.</b><br>
     * firstname String value for First Name of the Customer. <br><b> Max 20
     * characters.</b><br>
     * lastname String value for Last Name of the Customer. <br><b>Max 20
     * characters.</b><br>
     * addressStreet String value for the Address Street of the Customer.
     * <br><b>Max 40 characters.</b><br>
     * addressZip Integer value for the Zip code of the Customers City.
     * <br><b>Ranges from 0 - 4294967295.</b><br>
     * addressCity String value for the Address City of the Customer.
     * <br><b>Max 30 characters.</b><br>
     * addressCountry String value for the Address Country of the Customer.
     * <br><b>Max 30 characters.</b><br>
     * email String value for the Email of the Customer. <br><b>Max 30
     * characters.</b><br>
     * <i>phonenumber</i> Integer value for the Phone Number of the customer.
     * <br><b>Ranges from 0 - 4294967295<b>.
     *
     */
    @Override
    public void createCustomer(Customer customer) {
        try {
            //create sql statement, which we'd like to pass to the statement handler.
            //unpacks the Customer object, and inserts values at the correct columns in the database.
            String sql = "INSERT INTO customers VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstatement = con.prepareStatement(sql);
            pstatement.setString(1, customer.getMaritalStatus());
            pstatement.setString(3, customer.getLastName());
            pstatement.setString(4, customer.getAddressStreet());
            pstatement.setInt(5, customer.getAddressZip());
            pstatement.setString(6, customer.getAddressCity());
            pstatement.setString(7, customer.getAddressCountry());
            pstatement.setString(8, customer.getEmail());
            pstatement.setInt(9, customer.getPhone());

            //execute the statement
            executeUpdate(sql);
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while creating the customer", ex);
        }

    }

    /**
     *
     * @param customer
     *
     */
    @Override
    public void editCustomer(Customer customer) {
        try {
            //create string (sql statement), which we'd like to pass to the statement handler.
            String sql = "UPDATE customers SET maritalstatus = '?', firstname = '?', lastname = '?', addressstreet = '?', addresszip = ?, addresscity = '?', "
                    + "addresscountry = '?', email = '?', phonenumber = ? WHERE id = ?";
            pstatement = con.prepareStatement(sql);
            pstatement.setString(1, customer.getMaritalStatus());
            pstatement.setString(3, customer.getLastName());
            pstatement.setString(4, customer.getAddressStreet());
            pstatement.setInt(5, customer.getAddressZip());
            pstatement.setString(6, customer.getAddressCity());
            pstatement.setString(7, customer.getAddressCountry());
            pstatement.setString(8, customer.getEmail());
            pstatement.setInt(9, customer.getPhone());
            pstatement.setInt(10, customer.getID());

            //execute statement
            executeUpdate(sql);
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while editing the customer.", ex);
        }
    }

    /**
     *
     * @param customer
     */
    @Override
    public void deleteCustomer(Customer customer) {
        try {
            //create string (sql statement), which we'd like to pass to the statement handler.
            String sql = "DELETE FROM customers WHERE id = ?";
            pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, customer.getID());
            //execute statement
            executeUpdate(sql);
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while deleting the customer", ex);
        }

    }

    /**
     *
     * @param customerID
     * @return
     */
    @Override
    public Customer getCustomer(int customerID) {
        try {
            if (customerExists(customerID)) {
                String sql = "SELECT * FROM customers WHERE id = ?";

                pstatement = con.prepareStatement(sql);
                pstatement.setInt(1, customerID);
                //pass query to query handler -> db. REMEMBER THAT THIS DOESN'T CLOSE DB CONNECTION, CLOSING IS PARAMOUNT!

                executeQuery(sql);
                while (results.next()) {
                    int id = results.getInt("id");
                    String maritalstatus = results.getString("maritalstatus");
                    String firstname = results.getString("firstname");
                    String lastname = results.getString("lastname");
                    String addressStreet = results.getString("addressstreet");
                    int addressZip = results.getInt("addresszip");
                    String addressCity = results.getString("addresscity");
                    String addressCountry = results.getString("addresscountry");
                    int phonenumber = results.getInt("phonenumber");
                    String email = results.getString("email");

                    customer = new Customer(id, maritalstatus, firstname, lastname, addressStreet, addressZip, addressCity, addressCountry, phonenumber, email);

                }
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Somethng went wrong while getting the customer", ex);
        } finally {
            closeConnection();
        }
        return customer;
    }

    /**
     *
     * @param q
     * @return
     */
    @Override
    public ArrayList<Customer> getCustomers(int q) {

        String sql = "SELECT * FROM customers WHERE addresszip = ? OR phonenumber = ?";
        try {
            pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, q);
            pstatement.setInt(2, q);

            executeQuery(sql);
            results.beforeFirst();

            while (results.next()) {
                int id = results.getInt("id");
                String maritalstatus = results.getString("maritalstatus");
                String firstname = results.getString("firstname");
                String lastname = results.getString("lastname");
                String addressStreet = results.getString("addressstreet");
                int addressZip = results.getInt("addresszip");
                String addressCity = results.getString("addresscity");
                String addressCountry = results.getString("addresscountry");
                int phonenumber = results.getInt("phonenumber");
                String email = results.getString("email");
                customer = new Customer(id, maritalstatus, firstname, lastname, addressStreet, addressZip, addressCity, addressCountry, phonenumber, email);
                customers.add(customer);
            }

            return customers;
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong in getting your customers", ex);
        } finally {
            closeConnection();
        }
    }

    /**
     *
     * @param q
     * @return
     */
    @Override
    public ArrayList<Customer> getCustomers(String q) {
        String sql = "SELECT * FROM customers WHERE firstname = \"?\" OR lastname =\"?\" OR addressstreet =\"?\" OR email =\"?\"";

        try {
            pstatement = con.prepareStatement(sql);
            pstatement.setString(1, q);
            pstatement.setString(2, q);
            pstatement.setString(3, q);
            pstatement.setString(4, q);

            executeQuery(sql);
            results.beforeFirst();
            while (results.next()) {
                int id = results.getInt("id");
                String maritalstatus = results.getString("maritalstatus");
                String firstname = results.getString("firstname");
                String lastname = results.getString("lastname");
                String addressStreet = results.getString("addressstreet");
                int addressZip = results.getInt("addresszip");
                String addressCity = results.getString("addresscity");
                String addressCountry = results.getString("addresscountry");
                int phonenumber = results.getInt("phonenumber");
                String email = results.getString("email");
                customer = new Customer(id, maritalstatus, firstname, lastname, addressStreet, addressZip, addressCity, addressCountry, phonenumber, email);
                customers.add(customer);
            }
            return customers;
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong in getting your customers", ex);
        } finally {
            closeConnection();
        }

    }

    /**
     *
     * @param firstName
     * @param lastName
     * @param email
     * @param Phone
     * @return
     */
    @Override
    public ArrayList<Customer> getCustomers(String firstName, String lastName, String email, Integer Phone) {
        String recieverMatrix = "1:1:1:1";
        String[] parts = recieverMatrix.split(":");
        String[] columns = new String[4];
        if (firstName == null) {
            parts[0] = "0";
            columns[0] = firstName;
        }
        if (lastName == null) {
            parts[1] = "0";
            columns[1] = lastName;
        }
        if (email == null) {
            parts[2] = "0";
            columns[2] = email;
        }
        if (firstName == null) {
            parts[3] = "0";
            columns[3] = Phone.toString();
        }

        String sql = "SELECT * FROM customers WHERE";
        
        String[] sqlArr = new String[4];
        sqlArr[0] = "firstname = \"?\" AND ";
        sqlArr[1] = "lastname = \"?\" AND ";
        sqlArr[2] = "AND email= \"?\" AND ";
        sqlArr[3] = "AND phone = ?";

        try {

            int k = 0;
            PreparedStatement[] pstatementArr = new PreparedStatement[4];
            for (int i = 0; i < parts.length; i++) {
                if (parts[i].matches("1")) {
                    sql += sqlArr[i];
                    k++;
                    pstatementArr[k].setString(k, columns[i]);
                }
            }

            System.out.println(sql);
            pstatement = con.prepareStatement(sql);

            executeQuery(sql);
            results.beforeFirst();
            while (results.next()) {
                int id = results.getInt("id");
                String maritalstatus = results.getString("maritalstatus");
                String firstname = results.getString("firstname");
                String lastname = results.getString("lastname");
                String addressStreet = results.getString("addressstreet");
                int addressZip = results.getInt("addresszip");
                String addressCity = results.getString("addresscity");
                String addressCountry = results.getString("addresscountry");
                int phonenumber = results.getInt("phonenumber");
                String eMail = results.getString("email");
                customer = new Customer(id, maritalstatus, firstname, lastname, addressStreet, addressZip, addressCity, addressCountry, phonenumber, eMail);
                customers.add(customer);
            }
            return customers;
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong in getting your customers", ex);
        } finally {
            closeConnection();
        }

    }

    /**
     *
     * @param customer
     * @return
     */
    @Override
    public boolean customerExists(Customer customer) {

        String firstname = customer.getFirstName();
        String lastname = customer.getLastName();
        String email = customer.getEmail();
        boolean exists = false;

        String sql = "SELECT COUNT(*) FROM customers WHERE firstname='" + firstname + "' AND lastname = '" + lastname + "' AND email = '" + email + "' OR email = '" + email + "'";
        try {
            executeQuery(sql);
            results.first();
            exists = 0 != results.getInt(1);
            System.out.println(exists);
            //statement.close();
            //  results.close();

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while checking if the customer exists", ex);
        } finally {
            // closeConnection();
        }

        return exists;

    }

    /**
     *
     * @param customerID
     * @return
     */
    public boolean customerExists(int customerID) {
        String sql = "SELECT * FROM customers WHERE id=" + customerID;
        executeQuery(sql);
        Boolean exists = false;
        try {
            results.first();
            exists = !results.wasNull();

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while checking if the customer exists", ex);
        } finally {
        }
        return exists;

    }

    /**
     *
     * @param seats
     * @param flight
     * @return
     */
    @Override
    public boolean seatsExist(ArrayList<Seat> seats, Flight flight) {
        boolean exists = false;
        for (Seat currentSeat : seats) {
            try {
                String sql = "SELECT f.id, r2s.seat_id FROM flights f, reservation2seat r2s WHERE f.id =" + flight.getID() + " AND r2s.seat_id =" + currentSeat;
                executeQuery(sql);
                exists = results.getRow() != 0;
            } catch (SQLException ex) {
                throw new RuntimeException("Something went wrong while checking if the seat exists", ex);
            }
        }
        return exists;
    }

    /**
     *
     * @param currentCustomer
     * @param flight
     * @param seats
     * @param food
     * @param cost
     */
    @Override
    public void createReservation(Customer currentCustomer, Flight flight, ArrayList<Seat> seats, int food, int cost) {
        try {
            if (customerExists(currentCustomer)) {
                if (!seatsExist(seats, flight)) {
                    customer = currentCustomer;
                    int customerID = customer.getID();
                    int flightID = flight.getID();

                    String sql = "INSERT INTO reservations "
                            + "VALUES (null, "
                            + "" + customerID + ", "
                            + "'" + flightID + "', "
                            + "" + food + ", "
                            + "" + cost + ")";

                    int[] key = new int[1];
                    key[0] = 1;
                    pstatement.executeUpdate(sql, key[0]);

                    ResultSet rs = pstatement.getGeneratedKeys();
                    rs.first();
                    int reservationID = rs.getInt(1);

                    for (Seat currentSeat : seats) {

                        sql = "INSERT INTO reservation2seat VALUES (" + reservationID + ", " + currentSeat.getIndex() + ")";

                        executeUpdate(sql);

                    }
                }

            } else {

                String maritalstatus = currentCustomer.getMaritalStatus();
                String firstname = currentCustomer.getFirstName();
                String lastname = currentCustomer.getLastName();
                String addressStreet = currentCustomer.getAddressStreet();
                int addressZip = currentCustomer.getAddressZip();
                String addressCity = currentCustomer.getAddressCity();
                String addressCountry = currentCustomer.getAddressCountry();
                String email = currentCustomer.getEmail();
                int phonenumber = currentCustomer.getPhone();

                String sql = "INSERT INTO customers "
                        + "VALUES (null, "
                        + "'" + maritalstatus + "', "
                        + "'" + firstname + "', "
                        + "'" + lastname + "', "
                        + "'" + addressStreet + "', "
                        + "" + addressZip + ", "
                        + "'" + addressCity + "', "
                        + "'" + addressCountry + "', "
                        + "'" + email + "', "
                        + "" + phonenumber + ")";
                pstatement.executeUpdate(sql);
                customer = getCustomer(pstatement.executeUpdate(sql, 1));
                createReservation(customer, flight, seats, food, cost);

            }

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while creating your reservation", ex);
        } finally {
            closeConnection();
        }

    }

    /**
     *
     * @param reservationID
     * @param customerID
     * @param flightID
     * @param seats
     * @param food
     * @param cost
     */
    @Override
    public void editReservation(int reservationID, int customerID, String flightID, ArrayList<Seat> seats, int food, int cost) {

        String sql = "UPDATE reservations SET "
                + "customer_id = " + customerID + ", "
                + "flightid = '" + flightID + "', "
                + "food = " + food + " "
                + "cost = " + cost + " "
                + "WHERE id = " + reservationID;

        executeUpdate(sql);
        if (seats.isEmpty()) {
            sql = "DELETE FROM reservation2seat WHERE reservation_id=" + reservationID;
            executeUpdate(sql);
        } else {
            for (Seat currentSeat : seats) {
                sql = "UPDATE reservation2seat SET "
                        + "reservation_id = " + reservationID + ", "
                        + "seat_id= " + currentSeat.getIndex();

                executeUpdate(sql);
            }
        }

    }

    /**
     *
     * @param reservationID
     */
    @Override
    public void deleteReservation(int reservationID) {

        String sql = "DELETE FROM reservations WHERE id = " + reservationID;

        executeUpdate(sql);

    }

    /**
     *
     * @param reservationID
     * @return
     */
    @Override
    public Booking getReservation(int reservationID) {

        String sql = "SELECT * FROM reservations WHERE id = " + reservationID;
        //pass query to query handler -> db. REMEMBER THAT THIS METHOD DOESN'T CLOSE STATEMENTS , CLOSING IS PARAMOUNT!

        try {
            executeQuery(sql);
            while (results.next()) {
                int id = results.getInt("id");
                int customerid = results.getInt("customer_id");
                int flightid = results.getInt("flightid");
                int food = results.getInt("food");
                int price = results.getInt("price");
                String getSeats = "SELECT r2s.seat_id "
                        + "FROM `reservation2seat` r2s "
                        + "INNER JOIN reservations rs "
                        + "ON r2s.reservation_id = " + id + " "
                        + "WHERE r2s.reservation_id = rs.id ";

                ResultSet seatResults = executeQuery(getSeats);
                while (seatResults.next()) {
                    seat = new Seat(seatResults.getInt("seat_id"));
                    seats.add(seat);
                    seat = null;
                }

                reservation = new Booking(id, getCustomer(customerid), getFlight(flightid), seats, food, price);

            }
            //statement.close();
            return reservation;

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while getting the reservation:", ex);

        } catch (NullPointerException e) {
            throw new RuntimeException("You didn't supply a valid reservation ID", e);
        }

    }

    /**
     *
     * @param seatID
     * @param flightID
     * @return
     */
    @Override
    public Booking getReservation(int seatID, int flightID) {

        String sql = "SELECT reservation_id FROM reservation2seat WHERE seat_id =" + seatID + " AND flight_id=" + flightID;
        //pass query to query handler -> db. REMEMBER THAT THIS METHOD DOESN'T CLOSE STATEMENTS , CLOSING IS PARAMOUNT!
        executeQuery(sql);
        try {
            results.first();
            int reservationID = results.getInt(1);
            sql = "SELECT * FROM reservations WHERE id = " + reservationID;
            executeQuery(sql);
            // results.first();
            while (results.next()) {
                int id = results.getInt("id");
                int customerid = results.getInt("customer_id");
                int flightid = results.getInt("flightid");
                int food = results.getInt("food");
                int price = results.getInt("price");
                String getSeats = "SELECT r2s.seat_id "
                        + "FROM `reservation2seat` r2s "
                        + "INNER JOIN reservations rs "
                        + "ON r2s.reservation_id = " + id + " "
                        + "WHERE r2s.reservation_id = rs.id ";

                ResultSet seatResults = executeQuery(getSeats);

                while (seatResults.next()) {
                    seat = new Seat(seatResults.getInt("seat_id"));
                    seats.add(seat);
                    seat = null;
                }

                reservation = new Booking(id, getCustomer(customerid), getFlight(flightid), seats, food, price);

            }
            //statement.close();
            return reservation;

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while getting the reservation:", ex);

        } catch (NullPointerException e) {
            throw new RuntimeException("You didn't supply a valid reservation ID", e);
        }

    }

    /**
     *
     * @param airplaneID
     * @param firstCost
     * @param businessCost
     * @param economyCost
     * @param departurePlace
     * @param departureTime
     * @param arrivalPlace
     * @param arrivalTime
     */
    @Override
    public void createFlight(int airplaneID, int firstCost, int businessCost, int economyCost, String departurePlace, Timestamp departureTime, String arrivalPlace, Timestamp arrivalTime) {
        try {
            String sql = "INSERT INTO flights "
                    + "VALUES (null, ?, ?, ?, ?, ?, ?, ? ,?)";

            pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, airplaneID);
            pstatement.setInt(2, firstCost);
            pstatement.setInt(3, businessCost);
            pstatement.setInt(4, economyCost);
            pstatement.setString(5, departurePlace);
            pstatement.setTimestamp(6, departureTime);
            pstatement.setString(7, arrivalPlace);
            pstatement.setTimestamp(8, arrivalTime);

            executeUpdate(sql);
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while creating the flight.", ex);
        }

    }

    /**
     *
     * @param flightID
     * @return
     */
    @Override
    public Flight getFlight(int flightID) {
        try {
            String sql = "SELECT * FROM flights WHERE id = " + flightID;
            //pass query to query handler -> db. REMEMBER THAT THIS DOESN'T CLOSE DB CONNECTION, CLOSING IS PARAMOUNT!

            executeQuery(sql);

            while (results.next()) {
                int id = results.getInt(1);
                airplane = getAirplane(results.getInt(2));
                int firstcost = results.getInt(3);
                int businesscost = results.getInt(4);
                int economycost = results.getInt(5);
                Timestamp dTime = results.getTimestamp(6);
                String dPlace = results.getString(7);
                Timestamp aTime = results.getTimestamp(8);
                String aPlace = results.getString(9);
                boolean isFull = results.getBoolean(10);

                String sqlGetSeats = "SELECT seat_id FROM reservation2seat WHERE flight_id =" + id;
                Statement s = con.createStatement();
                ResultSet rs = s.executeQuery(sqlGetSeats);
                //ArrayList<Seat> seats = new ArrayList();
                while (rs.next()) {

                    int seatIndex = rs.getInt(1);

                    seat = new Seat(seatIndex);
                    seats.add(seat);
                    seat = null;
                }

                flight = new Flight(id, airplane, firstcost, businesscost, economycost, seats, dPlace, dTime, aPlace, aTime, isFull);

            }

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while getting the flight.", ex);
        } finally {
            closeConnection();
        }
        return flight;
    }

    /**
     *
     * @param customerID
     * @return
     */
    @Override
    public ArrayList<Booking> getCustomerReservations(int customerID) {

        String sql = "SELECT rsv.id "
                + "FROM `reservations` rsv "
                + "INNER JOIN customers cs "
                + "ON rsv.id = cs.id "
                + "WHERE rsv.id = " + customerID;
        //pass query to query handler -> db. REMEMBER THAT THIS METHOD DOESN'T CLOSE STATEMENTS , CLOSING IS PARAMOUNT!

        try {
            executeQuery(sql);
            while (results.next()) {
                Booking currentBooking = getReservation(results.getInt(1));
                reservations.add(currentBooking);

            }
            //statement.close();
            return reservations;

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while getting the reservation:", ex);

        } catch (NullPointerException e) {
            throw new RuntimeException("You didn't supply a valid reservation ID", e);
        }

    }

    /**
     *
     * @param flightID
     * @return
     */
    @Override
    public ArrayList<Seat> getFlightBookedSeats(int flightID) {
        try {
            String sql = "SELECT s.seat_id FROM reservation2seat s, reservations r WHERE flightid =" + flightID + " AND  r.id = s.reservation_id";
            executeQuery(sql);
            while (results.next()) {
                seat = new Seat(results.getInt(1));
                seats.add(seat);
                seat = null;
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while getting the booked seats on this flight", ex);
        }
        return seats;
    }

    /**
     *
     * @param flightID
     * @param airplaneID
     * @param firstCost
     * @param businessCost
     * @param economyCost
     * @param departurePlace
     * @param departureTime
     * @param arrivalPlace
     * @param arrivalTime
     */
    @Override
    public void editFlight(int flightID, int airplaneID, int firstCost, int businessCost, int economyCost, String departurePlace, Timestamp departureTime, String arrivalPlace, Timestamp arrivalTime) {

        String sql = "UPDATE flights SET "
                + "airplane_id = " + airplaneID + ", "
                + "firstcost = " + firstCost + ", "
                + "businesscost = " + businessCost + ", "
                + "economycost = " + economyCost + ", "
                + "departuretime = '" + departurePlace + "', "
                + "departureplace = '" + departureTime + "', "
                + "arrivaltime = '" + arrivalTime + "', "
                + "arrivalplace = '" + arrivalPlace + "', "
                + "WHERE id = " + flightID;

        executeUpdate(sql);
    }

    /**
     *
     * @param flightID
     */
    @Override
    public void deleteFlight(int flightID) {
        String sql = "DELETE FROM flights WHERE id =" + flightID;
        executeUpdate(sql);
    }

    /**
     *
     * @param airplaneID
     * @return
     */
    @Override
    public Airplane getAirplane(int airplaneID) {
        try {
            String sql = "SELECT * FROM airplanes WHERE id =" + airplaneID;
            executeQuery(sql);
            results.first();
            int id = results.getInt("id");
            int firstSeats = results.getInt("firstseats");
            int businessSeats = results.getInt("businessseats");
            int economySeats = results.getInt("economyseats");
            String name = results.getString("name");
            String fcSeatFormation = results.getString("fcseatformation");
            String bcSeatFormation = results.getString("bcseatformation");
            String ecSeatFormation = results.getString("ecseatformation");

            airplane = new Airplane(id, name, firstSeats, businessSeats, economySeats, fcSeatFormation, bcSeatFormation, ecSeatFormation);

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while getting the airplane", ex);
        }
        return airplane;
    }

    /*
     *   Below are unimplemented methods.
     *   Below are unimplemented methods.
     *   Below are unimplemented methods.
     *   Below are unimplemented methods.
     */
    @Override
    public ArrayList<Flight> getFlights(Boolean freeSeatsOnly) {

        try {
            String sql;
            if (freeSeatsOnly == true) {
                sql = "SELECT * FROM flights WHERE isfull = 0";
            } else {
                sql = "SELECT * FROM flights";
            }

            executeQuery(sql);

            while (results.next()) {

                int id = results.getInt(1);
                airplane = getAirplane(results.getInt(2));
                int firstcost = results.getInt(3);
                int businesscost = results.getInt(4);
                int economycost = results.getInt(5);
                Timestamp dTime = results.getTimestamp(6);
                String dPlace = results.getString(7);
                Timestamp aTime = results.getTimestamp(8);
                String aPlace = results.getString(9);
                boolean isFull = results.getBoolean(10);

                sql = "SELECT seat_id FROM reservation2seat WHERE flight_id =" + id;

                Statement s = con.createStatement();
                ResultSet rs = s.executeQuery(sql);

                while (rs.next()) {

                    int seatIndex = rs.getInt(1);
                    seat = new Seat(seatIndex);
                    seats.add(seat);

                }

                flight = new Flight(id, airplane, firstcost, businesscost, economycost, seats, dPlace, dTime, aPlace, aTime, isFull);
                flights.add(flight);
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while getting the flights", ex);
        } finally {
            // System.out.println(getWarnings());
        }
        return flights;
    }
}
