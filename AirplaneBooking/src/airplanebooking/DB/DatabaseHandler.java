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
    Connection con;
    PreparedStatement pstatement;

    ResultSet results;
    SQLWarning warning = new SQLWarning();
    ComboPooledDataSource cpds = new ComboPooledDataSource();

    //Customer field
    Customer customer;
    ArrayList<Customer> customers = new ArrayList();

    //Booking field
    Booking reservation;
    ArrayList<Booking> reservations = new ArrayList();

    //Flight field
    Flight flight;
    ArrayList<Flight> flights = new ArrayList();

    //Seat field
    Seat seat;
    ArrayList<Seat> seats = new ArrayList();

    //Airplane field
    Airplane airplane;

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

    private void getConnection() {
        try {
            con = cpds.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void executeUpdate() {
        //SET method, for putting data into the database.
        try {
            //Execute the prepared statement.
            con = cpds.getConnection();
            pstatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while executing the update", e);
        } finally {
            //  closeConnection();
        }
    }

    private ResultSet executeQuery() {
        //GET method for getting data from the database.

        try {
            con = cpds.getConnection();
            //pass results from statement handler to ResultSet and save the it.
            results = pstatement.executeQuery();

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
            executeUpdate();
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
            executeUpdate();
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
            executeUpdate();
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

                executeQuery();
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

            executeQuery();
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

            executeQuery();

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
            pstatement.executeQuery();

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
        try {
            String sql = "SELECT COUNT(*) FROM customers WHERE firstname='?' AND lastname = '?' AND email = '?' OR email = '?'";

            pstatement = con.prepareStatement(sql);
            pstatement.setString(1, firstname);
            pstatement.setString(2, lastname);
            pstatement.setString(3, email);
            pstatement.executeQuery();
            results.first();
            exists = 0 != results.getInt(1);
            System.out.println(exists);

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
        String sql = "SELECT * FROM customers WHERE id= ?";
        Boolean exists = false;
        try {
            pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, customerID);
            pstatement.executeQuery();

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
     * THIS METHOD IS PARTIALLY BROKEN IN FUNKTIONALITY, IF --*ANY*-- OF THE
     * SEATS EXISTS, IT WILL RETURN TRUE!
     *
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
                String sql = "SELECT f.id, r2s.seat_id FROM flights f, reservation2seat r2s WHERE f.id = ? AND r2s.seat_id = ?";
                pstatement = con.prepareStatement(sql);
                pstatement.setInt(1, flight.getID());
                pstatement.setInt(2, currentSeat.getIndex());
                executeQuery();
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
                            + "VALUES (null, ?, ?, ?, ?)";

                    pstatement = con.prepareStatement(sql);
                    pstatement.setInt(1, customerID);
                    pstatement.setInt(2, flightID);
                    pstatement.setInt(3, food);
                    pstatement.setInt(4, cost);

                    pstatement.executeUpdate();

                    ResultSet rs = pstatement.getGeneratedKeys();
                    rs.first();
                    int reservationID = rs.getInt(1);

                    for (Seat currentSeat : seats) {

                        sql = "INSERT INTO reservation2seat VALUES (?, ?, ?)";
                        pstatement = con.prepareStatement(sql);
                        pstatement.setInt(1, reservationID);
                        pstatement.setInt(2, currentSeat.getIndex());
                        pstatement.setInt(3, flight.getID());
                        pstatement.executeUpdate();

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
     * @param booking
     */
    @Override
    public void editReservation(Booking booking) {
        try {
            String sql = "UPDATE reservations SET "
                    + "customer_id = ?, flightid = ?, food = ?, cost = ? WHERE id = ?";
            customer = booking.getCustomer();
            flight = booking.getFlight();
            pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, customer.getID());
            pstatement.setInt(2, flight.getID());
            pstatement.setInt(3, booking.getFood());
            pstatement.setInt(4, booking.getPrice());
            pstatement.setInt(5, booking.getID());

            executeUpdate();
            seats = booking.getSeats();
            if (seats.isEmpty()) {
                sql = "DELETE FROM reservation2seat WHERE reservation_id=?";
                pstatement = con.prepareStatement(sql);
                pstatement.setInt(1, booking.getID());
                executeUpdate();
            } else {
                for (Seat currentSeat : seats) {
                    sql = "UPDATE reservation2seat SET reservation_id = ?, seat_id= ?";
                    pstatement = con.prepareStatement(sql);
                    pstatement.setInt(1, booking.getID());
                    pstatement.setInt(2, currentSeat.getIndex());

                    executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while editing your reservation", ex);
        }

    }

    /**
     *
     * @param booking
     */
    @Override
    public void deleteReservation(Booking booking) {
        try {
            String sql = "DELETE FROM reservations WHERE id = ?";
            pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, booking.getID());
            executeUpdate();

            seats = booking.getSeats();

            sql = "DELETE FROM reservation2seat WHERE reservation_id=?";
            pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, booking.getID());
            executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while deleting your reservation.", ex);
        }

    }

    /**
     *
     * @param reservationID
     * @return
     */
    @Override
    public Booking getReservation(int reservationID) {

        String sql = "SELECT * FROM reservations WHERE id = ?";
        try {
            pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, reservationID);

        //pass query to query handler -> db. REMEMBER THAT THIS METHOD DOESN'T CLOSE STATEMENTS , CLOSING IS PARAMOUNT!
            executeQuery();
            while (results.next()) {
                int id = results.getInt("id");
                int customerid = results.getInt("customer_id");
                int flightid = results.getInt("flightid");
                int food = results.getInt("food");
                int price = results.getInt("price");
                String getSeats = "SELECT r2s.seat_id "
                        + "FROM `reservation2seat` r2s "
                        + "INNER JOIN reservations rs "
                        + "ON r2s.reservation_id = ? "
                        + "WHERE r2s.reservation_id = rs.id ";
                pstatement = con.prepareStatement(getSeats);
                pstatement.setInt(1, id);
                ResultSet seatResults = pstatement.executeQuery();
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
            /*
             Doesn't work yet. Dno about nullpointers so far.
            
             */
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

        String sql = "SELECT reservation_id FROM reservation2seat WHERE seat_id = ? AND flight_id= ?";
        try {
            pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, seatID);
            pstatement.setInt(2, flightID);
            //pass query to query handler -> 
            executeQuery();

            results.first();
            int reservationID = results.getInt(1);
            sql = "SELECT * FROM reservations WHERE id = ?";
            pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, reservationID);

            executeQuery();
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
                        + "ON r2s.reservation_id = ? "
                        + "WHERE r2s.reservation_id = rs.id ";
                pstatement = con.prepareStatement(sql);
                pstatement.setInt(1, id);
                ResultSet seatResults = pstatement.executeQuery();

                while (seatResults.next()) {
                    seat = new Seat(seatResults.getInt("seat_id"));
                    seats.add(seat);
                    seat = null;
                }

                reservation = new Booking(id, getCustomer(customerid), getFlight(flightid), seats, food, price);

            }

            return reservation;

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while getting the reservation:", ex);

        } catch (NullPointerException e) {
            throw new RuntimeException("You didn't supply a valid reservation ID", e);
        }

    }

    /**
     *
     * @param flight
     */
    @Override
    public void createFlight(Flight flight) {
        try {
            String sql = "INSERT INTO flights "
                    + "VALUES (null, ?, ?, ?, ?, ?, ?, ? ,?)";

            pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, flight.getAirplaneID());
            pstatement.setInt(2, flight.getFirstClassSeatCost());
            pstatement.setInt(3, flight.getBusinessClassSeatCost());
            pstatement.setInt(4, flight.getEconomyClassSeatCost());
            pstatement.setString(5, flight.getDeparturePlace());
            pstatement.setTimestamp(6, flight.getDepartureTimestamp());
            pstatement.setString(7, flight.getArrivalPlace());
            pstatement.setTimestamp(8, flight.getArrivalTimestamp());

            executeUpdate();
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
            String sql = "SELECT * FROM flights WHERE id = ?";
            //pass query to query handler -> db. REMEMBER THAT THIS DOESN'T CLOSE DB CONNECTION, CLOSING IS PARAMOUNT!
            pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, flightID);
            executeQuery();

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

                String sqlGetSeats = "SELECT seat_id FROM reservation2seat WHERE flight_id = ?";
                pstatement = con.prepareStatement(sqlGetSeats);
                pstatement.setInt(1, id);
                ResultSet rs = pstatement.executeQuery();
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
                + "WHERE rsv.id = ?";
        //pass query to query handler -> db. REMEMBER THAT THIS METHOD DOESN'T CLOSE STATEMENTS , CLOSING IS PARAMOUNT!
        try {
            pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, customerID);
            executeQuery();
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
            String sql = "SELECT s.seat_id FROM reservation2seat s, reservations r WHERE flightid =? AND r.id = s.reservation_id";
            pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, flightID);
            executeQuery();
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
     * @param flight
     */
    @Override
    public void editFlight(Flight flight) {
        try {
            String sql = "UPDATE flights SET "
                    + "airplane_id = ?, firstcost = ?, businesscost = ?, economycost = ?, departuretime = '?', "
                    + "departureplace = '?', arrivaltime = '?', arrivalplace = '?', WHERE id = ?";

            pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, flight.getAirplaneID());
            pstatement.setInt(2, flight.getFirstClassSeatCost());
            pstatement.setInt(3, flight.getBusinessClassSeatCost());
            pstatement.setInt(4, flight.getEconomyClassSeatCost());
            pstatement.setTimestamp(5, flight.getDepartureTimestamp());
            pstatement.setString(6, flight.getDeparturePlace());
            pstatement.setTimestamp(7, flight.getArrivalTimestamp());
            pstatement.setString(8, flight.getArrivalPlace());
            executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while editing your flight", ex);
        }
    }

    /**
     *
     * @param flight
     */
    @Override
    public void deleteFlight(Flight flight) {
        try {
            String sql = "DELETE FROM flights WHERE id =?";
            pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, flight.getID());
            executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException("Soemthing went wrong while deleting your flight", ex);
        }
    }

    /**
     *
     * @param airplaneID
     * @return
     */
    @Override
    public Airplane getAirplane(int airplaneID) {
        try {
            String sql = "SELECT * FROM airplanes WHERE id =?";
            pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, airplaneID);
            executeQuery();
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
        getConnection();
        try {

            String sql;
            if (freeSeatsOnly == true) {
                sql = "SELECT * FROM flights WHERE isfull = 0";
                pstatement = con.prepareStatement(sql);
            } else {
                sql = "SELECT * FROM flights";
                pstatement = con.prepareStatement(sql);
            }

            ResultSet rs = executeQuery();
            
            
            while (rs.next()) {

                int id = rs.getInt(1);
                airplane = getAirplane(rs.getInt(2));
                int firstcost = rs.getInt(3);
                int businesscost = rs.getInt(4);
                int economycost = rs.getInt(5);
                Timestamp dTime = rs.getTimestamp(6);
                String dPlace = rs.getString(7);
                Timestamp aTime = rs.getTimestamp(8);
                String aPlace = rs.getString(9);
                boolean isFull = rs.getBoolean(10);

                sql = "SELECT seat_id FROM reservation2seat WHERE flight_id =?";

                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet seatResults = ps.executeQuery();

                while (seatResults.next()) {

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
            closeConnection();
        }
        return flights;
    }
}
