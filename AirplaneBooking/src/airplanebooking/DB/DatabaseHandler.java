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

/**
 * If you are spawning more than one of these objects, you are most likely doing
 * it wrong.
 *
 * @author Aleksandar Jonovic
 */
public class DatabaseHandler implements DatabaseInterface {

    //DB & SQL fields
    private static final String user = "aljon";
    private static final String pass = "Jegeradministratorher123";
    private static final String jdbcurl = "jdbc:mysql://mysql.itu.dk/Airplanebooking";

    ArrayList<PreparedStatement> pstatements = new ArrayList();
    ArrayList<ResultSet> resultsets = new ArrayList();
    ArrayList<Connection> cons = new ArrayList();
    ArrayList<SQLWarning> warnings = new ArrayList();
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
        try {
            //contructor method to initiate (combo)DataSource for pooled connections on spawning an object.
            //initiateDataSource();
            initiateDataSource();
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while initializing the DataSource", ex);
        }

    }

    private void initiateDataSource() throws SQLException {
        // initiates the connection to the database, and sets the basic parameters for the connection pooling.
        cpds.setJdbcUrl(jdbcurl);
        cpds.setUser(user);
        cpds.setPassword(pass);
        cpds.setMinPoolSize(1);
        cpds.setAcquireIncrement(3);
        cpds.setMaxPoolSize(100);

    }

    private Connection getConnection() {
        try {
            //System.out.println(cpds.getNumConnectionsAllUsers());
            return cpds.getConnection();

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while getting the connection", ex);
        }

    }

    private void executeUpdate(PreparedStatement pstatement) {
        //SET method, for putting data into the database.
        try {
            //Execute the prepared statement.
            pstatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong while executing the update", e);
        }
    }

    private ResultSet executeQuery(PreparedStatement pstatement) throws SQLException {
        //GET method for getting data from the database.
        ResultSet results = pstatement.executeQuery();
        return results;
    }

    /*
     *
     *
     * Still testing this sucker. Not yet implemented.
     *
     */
    public void getNumCon() {
        try {
            System.out.println("Number of Connections: " + cpds.getNumConnections());
            System.out.println("Number of Idle Connections: " + cpds.getNumIdleConnections());
            System.out.println("Number of Busy Connections: " + cpds.getNumBusyConnections());
            System.out.println("Number of Connection Pools: " + cpds.getNumUserPools());
            System.out.println("");
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while getting the number of connections in the pool", ex);
        }
    }

    private void closeConnection(ArrayList<Connection> cons, ArrayList<PreparedStatement> pstatements, ArrayList<ResultSet> resultsets) {
        try {

            if (resultsets != null) {
                for (ResultSet results : resultsets) {
                    results.close();

                }
                this.resultsets.removeAll(resultsets);
            }

            if (cons != null && pstatements != null) {

                for (PreparedStatement pstatement : pstatements) {
                    pstatement.close();

                }

                for (Connection con : cons) {
                    con.close();

                }
                
                this.pstatements.removeAll(pstatements);
                this.cons.removeAll(cons);

            }

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while clearing the results connection", ex);
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
        Connection con = getConnection();
        try {
            //create sql statement, which we'd like to pass to the statement handler.
            //unpacks the Customer object, and inserts values at the correct columns in the database.
            String sql = "INSERT INTO customers VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstatement = con.prepareStatement(sql);
            pstatement.setString(1, customer.getMaritalStatus());
            pstatement.setString(2, customer.getFirstName());
            pstatement.setString(3, customer.getLastName());
            pstatement.setString(4, customer.getAddressStreet());
            pstatement.setInt(5, customer.getAddressZip());
            pstatement.setString(6, customer.getAddressCity());
            pstatement.setString(7, customer.getAddressCountry());
            pstatement.setString(8, customer.getEmail());
            pstatement.setInt(9, customer.getPhone());

            //execute the statement
            executeUpdate(pstatement);

            //Tidy up connection
            cons.add(con);
            pstatements.add(pstatement);
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while creating the customer", ex);
        } finally {
            closeConnection(cons, pstatements, null);
        }

    }

    /**
     *
     * @param customer
     *
     */
    @Override
    public void editCustomer(Customer customer) {
        Connection con = getConnection();
        try {
            //create string (sql statement), which we'd like to pass to the statement handler.
            String sql = "UPDATE customers SET maritalstatus = '?', firstname = '?', lastname = '?', addressstreet = '?', addresszip = ?, addresscity = '?', "
                    + "addresscountry = '?', email = '?', phonenumber = ? WHERE id = ?";
            PreparedStatement pstatement = con.prepareStatement(sql);
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
            executeUpdate(pstatement);

            //Tidy up connection
            cons.add(con);
            pstatements.add(pstatement);
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while editing the customer.", ex);
        } finally {
            closeConnection(cons, pstatements, null);
        }
    }

    /**
     *
     * @param customer
     */
    @Override
    public void deleteCustomer(Customer customer) {
        Connection con = getConnection();
        try {
            //create string (sql statement), which we'd like to pass to the statement handler.
            String sql = "DELETE FROM customers WHERE id = ?";
            PreparedStatement pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, customer.getID());
            //execute statement
            executeUpdate(pstatement);

            //Tidy up connection
            cons.add(con);
            pstatements.add(pstatement);
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while deleting the customer", ex);
        } finally {
            closeConnection(cons, pstatements, null);
        }

    }

    /**
     *
     * @param customerID
     * @return
     */
    @Override
    public Customer getCustomer(int customerID) {
        Connection con = getConnection();
        try {
            if (customerExists(customerID)) {
                String sql = "SELECT * FROM customers WHERE id = ?";

                PreparedStatement pstatement = con.prepareStatement(sql);
                pstatement.setInt(1, customerID);
                //pass query to query handler -> db. REMEMBER THAT THIS DOESN'T CLOSE DB CONNECTION, CLOSING IS PARAMOUNT!

                ResultSet results = executeQuery(pstatement);
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

                    //Tidy up the connection
                    pstatements.add(pstatement);
                    resultsets.add(results);
                    cons.add(con);
                }
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Somethng went wrong while getting the customer", ex);
        } finally {

            closeConnection(cons, pstatements, resultsets);
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
        Connection con = getConnection();
        String sql = "SELECT * FROM customers WHERE addresszip = ? OR phonenumber = ?";
        try {
            PreparedStatement pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, q);
            pstatement.setInt(2, q);

            ResultSet results = executeQuery(pstatement);
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

            //Tidy up the connection
            resultsets.add(results);
            pstatements.add(pstatement);
            cons.add(con);

            return customers;

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong in getting your customers", ex);
        } finally {
            closeConnection(cons, pstatements, resultsets);
        }
    }

    /**
     *
     * @param q
     * @return
     */
    @Override
    public ArrayList<Customer> getCustomers(String q) {
        Connection con = getConnection();
        String sql = "SELECT * FROM customers WHERE firstname = ? OR lastname = ? OR addressstreet = ? OR email = ?";

        try {
            PreparedStatement pstatement = con.prepareStatement(sql);
            pstatement.setString(1, q);
            pstatement.setString(2, q);
            pstatement.setString(3, q);
            pstatement.setString(4, q);

            ResultSet results = executeQuery(pstatement);

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

            //Tidy up the connection
            resultsets.add(results);
            pstatements.add(pstatement);
            cons.add(con);
            return customers;
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong in getting your customers", ex);
        } finally {
            closeConnection(cons, pstatements, resultsets);
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
        Connection con = getConnection();
        String recieverMatrix = "0:0:0:0";
        String[] parts = recieverMatrix.split(":");
        String[] columns = new String[4];
        String sql = "SELECT * FROM customers WHERE ";

        String[] sqlArr = new String[4];
        sqlArr[3] = "phonenumber = ?";

        if (firstName != null) {
            parts[0] = "1";
            columns[0] = firstName;
            sqlArr[0] = "firstname = ? ";
        }
        if (lastName != null) {
            parts[1] = "1";
            columns[1] = lastName;
            sqlArr[1] = "lastname = ? ";

        }
        if (email != null) {
            parts[2] = "1";
            columns[2] = email;
            sqlArr[2] = "email = ? ";

        }
        if (Phone != null) {
            parts[3] = "1";
            columns[3] = Phone.toString();
            //sqlArr[3] = "phonenumber = ?";
        }

        try {

            int k = 0;

            for (int i = 0; i < parts.length; i++) {
                if (parts[i].matches("1")) {

                    sql += sqlArr[i];
                    if (sqlArr[i] != null) {
                        sql += "AND ";
                    }

                }

            }
            sql = sql.substring(0, sql.length() - 4);

            PreparedStatement pstatement = con.prepareStatement(sql);
            for (int i = 0; i < parts.length; i++) {
                if (parts[i].matches("1")) {

                    k++;
                    pstatement.setString(k, columns[i].toString());

                }

            }

            ResultSet results = pstatement.executeQuery();

            while (results.next()) {
                int id = results.getInt(1);
                String maritalstatus = results.getString(2);
                String firstname = results.getString(3);
                String lastname = results.getString(4);
                String addressStreet = results.getString(5);
                int addressZip = results.getInt(6);
                String addressCity = results.getString(7);
                String addressCountry = results.getString(8);
                String eMail = results.getString(9);
                int phonenumber = results.getInt(10);

                Customer currentCustomer = new Customer(id, maritalstatus, firstname, lastname, addressStreet, addressZip, addressCity, addressCountry, phonenumber, eMail);
                customers.add(currentCustomer);
            }
            //Tidy up the connection
            resultsets.add(results);
            pstatements.add(pstatement);
            cons.add(con);

            return customers;
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong in getting your customers", ex);
        } finally {
            closeConnection(cons, pstatements, resultsets);
        }

    }

    /**
     *
     * @param customer
     * @return
     */
    private boolean customerExists(Customer customer) {
        Connection con = getConnection();
        String firstname = customer.getFirstName();
        String lastname = customer.getLastName();
        String email = customer.getEmail();

        boolean exists = false;
        try {
            String sql = "SELECT COUNT(*) FROM customers WHERE firstname= ? AND lastname = ? AND email = ? OR email = ?";

            PreparedStatement pstatement = con.prepareStatement(sql);
            pstatement.setString(1, firstname);
            pstatement.setString(2, lastname);
            pstatement.setString(3, email);
            pstatement.setString(4, email);
            ResultSet results = pstatement.executeQuery();
            results.first();
            exists = 0 != results.getInt(1);

            pstatements.add(pstatement);
            resultsets.add(results);
            cons.add(con);

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while checking if the customer exists", ex);
        } finally {
            closeConnection(cons, pstatements, resultsets);
        }
        return exists;
    }

    /**
     *
     * @param customerID
     * @return
     */
    private boolean customerExists(int customerID) {
        Connection con = getConnection();
        String sql = "SELECT * FROM customers WHERE id= ?";
        Boolean exists = false;
        try {
            PreparedStatement pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, customerID);
            ResultSet results = executeQuery(pstatement);
            results.first();
            exists = !results.wasNull();
            //Tidy up the connection
            cons.add(con);
            resultsets.add(results);
            pstatements.add(pstatement);

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while checking if the customer exists", ex);
        } finally {
            closeConnection(cons, pstatements, resultsets);
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
    private boolean seatsExist(ArrayList<Seat> seats, Flight flight) {
        Connection con2 = getConnection();
        boolean exists = false;
        for (Seat currentSeat : seats) {
            try {
                String sql = "SELECT f.id, r2s.seat_id FROM flights f, reservation2seat r2s WHERE f.id = ? AND r2s.seat_id = ?";
                PreparedStatement pstatement = con2.prepareStatement(sql);
                
                pstatement.setInt(1, flight.getID());
                pstatement.setInt(2, currentSeat.getSeatID());
                ResultSet results = executeQuery(pstatement);
                exists = results.getRow() != 0;

                //Tidy up the connection
                cons.add(con2);
                resultsets.add(results);
                pstatements.add(pstatement);
                
            } catch (SQLException ex) {
                throw new RuntimeException("Something went wrong while checking if the seat exists", ex);
            } finally {
               // closeConnection(cons, pstatements, resultsets);
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
        Connection con = getConnection();
        try {
            if (customerExists(currentCustomer)) {
                System.out.println(seats.get(1).getSeatID());
                System.out.println(flight.getID());
                if (!seatsExist(seats, flight)) {
                    customer = currentCustomer;
                    int customerID = customer.getID();
                    int flightID = flight.getID();

                    String sql = "INSERT INTO reservations "
                            + "VALUES (null, ?, ?, ?, ?)";

                    PreparedStatement pstatement = con.prepareStatement(sql, 1);
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
                        pstatement.setInt(2, currentSeat.getSeatID());
                        pstatement.setInt(3, flight.getID());
                        pstatement.executeUpdate();

                    }
                    //Tidy up the connection
                    cons.add(con);
                    pstatements.add(pstatement);
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
                        + "VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pstatement = con.prepareStatement(sql);
                pstatement.setString(1, maritalstatus);
                pstatement.setString(2, firstname);
                pstatement.setString(3, lastname);
                pstatement.setString(4, addressStreet);
                pstatement.setInt(5, addressZip);
                pstatement.setString(6, addressCity);
                pstatement.setString(7, addressCountry);
                pstatement.setString(8, email);
                pstatement.setInt(9, phonenumber);

                customer = getCustomer(pstatement.executeUpdate(sql, 1));
                createReservation(customer, flight, seats, food, cost);

                //Tidy up the connection
                cons.add(con);
                pstatements.add(pstatement);

            }

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while creating your reservation", ex);
        } finally {
            closeConnection(cons, pstatements, null);
        }

    }

    /**
     *
     * @param booking
     */
    @Override
    public void editReservation(Booking booking) {
        Connection con = getConnection();
        try {
            String sql = "UPDATE reservations SET "
                    + "customer_id = ?, flightid = ?, food = ?, cost = ? WHERE id = ?";
            customer = booking.getCustomer();
            flight = booking.getFlight();
            PreparedStatement pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, customer.getID());
            pstatement.setInt(2, flight.getID());
            pstatement.setBoolean(3, booking.getFood());
            pstatement.setInt(4, booking.getPrice());
            pstatement.setInt(5, booking.getID());

            executeUpdate(pstatement);
            seats = booking.getSeats();

            if (seats.isEmpty()) {
                sql = "DELETE FROM reservation2seat WHERE reservation_id=?";
                PreparedStatement pstatement2 = con.prepareStatement(sql);
                pstatement2.setInt(1, booking.getID());
                executeUpdate(pstatement2);

                pstatements.add(pstatement2);
            } else {
                sql = "UPDATE reservation2seat SET reservation_id = ?, seat_id= ?";
                PreparedStatement pstatement2 = con.prepareStatement(sql);

                for (Seat currentSeat : seats) {
                    pstatement2.setInt(1, booking.getID());
                    pstatement2.setInt(2, currentSeat.getSeatID());
                    executeUpdate(pstatement2);
                }
                pstatements.add(pstatement2);
            }
            //Further tidy up the connection
            pstatements.add(pstatement);
            cons.add(con);

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while editing your reservation", ex);
        } finally {
            closeConnection(cons, pstatements, resultsets);
        }

    }

    /**
     *
     * @param booking
     */
    @Override
    public void deleteReservation(Booking booking) {
        Connection con = getConnection();
        try {
            String sql = "DELETE FROM reservations WHERE id = ?";
            PreparedStatement pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, booking.getID());
            executeUpdate(pstatement);

            seats = booking.getSeats();

            sql = "DELETE FROM reservation2seat WHERE reservation_id=?";
            PreparedStatement pstatement2 = con.prepareStatement(sql);
            pstatement2.setInt(1, booking.getID());
            executeUpdate(pstatement2);

            //Tidy up conneciton
            pstatements.add(pstatement);
            pstatements.add(pstatement2);
            cons.add(con);
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while deleting your reservation.", ex);
        } finally {
            closeConnection(cons, pstatements, null);
        }

    }

    /**
     *
     * @param reservationID
     * @return
     */
    @Override
    public Booking getReservation(int reservationID) {
        Connection con = getConnection();
        String sql = "SELECT * FROM reservations WHERE id =?";
        try {
            PreparedStatement pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, reservationID);

            //pass query to query handler -> db. REMEMBER THAT THIS METHOD DOESN'T CLOSE STATEMENTS , CLOSING IS PARAMOUNT!
            ResultSet results = executeQuery(pstatement);

            while (results.next()) {
                int id = results.getInt("id");
                int customerid = results.getInt("customer_id");
                int flightid = results.getInt("flightid");
                Boolean food = results.getBoolean("food");
                int price = results.getInt("price");

                String getSeats = "SELECT r2s.seat_id "
                        + "FROM `reservation2seat` r2s "
                        + "INNER JOIN reservations rs "
                        + "ON r2s.reservation_id = ? "
                        + "WHERE r2s.reservation_id = rs.id ";
                PreparedStatement pstatement2 = con.prepareStatement(getSeats);
                pstatement2.setInt(1, id);
                ResultSet seatResults = executeQuery(pstatement2);
                while (seatResults.next()) {
                    seat = new Seat(seatResults.getInt("seat_id"));
                    seats.add(seat);
                    seat = null;
                }

                reservation = new Booking(id, getCustomer(customerid), getFlight(flightid), seats, food, price);

                //Tidy up the connection
                resultsets.add(seatResults);
                pstatements.add(pstatement2);

            }
            //Further tidy up the connection
            cons.add(con);
            resultsets.add(results);
            pstatements.add(pstatement);

            //statement.close();
            return reservation;

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while getting the reservation:", ex);
            /*
             Doesn't work yet. Dno about nullpointers so far.
            
             */
        } catch (NullPointerException e) {
            throw new RuntimeException("You didn't supply a valid reservation ID", e);
        } finally {

            closeConnection(cons, pstatements, resultsets);
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
        Connection con = getConnection();
        String sql = "SELECT reservation_id FROM reservation2seat WHERE seat_id = ? AND flight_id = ?";
        int reservationID;
        try {
            PreparedStatement pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, seatID);
            pstatement.setInt(2, flightID);
            //pass query to query handler -> 
            ResultSet results = executeQuery(pstatement);

            while (results.next()) {
                reservationID = results.getInt(1);

                sql = "SELECT * FROM reservations WHERE id = ?";
                PreparedStatement pstatement2 = con.prepareStatement(sql);
                pstatement2.setInt(1, reservationID);
                ResultSet rs = executeQuery(pstatement2);

                // results.first();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int customerid = rs.getInt("customer_id");
                    int flightid = rs.getInt("flightid");
                    Boolean food = rs.getBoolean("food");
                    int price = rs.getInt("price");
                    String getSeats = "SELECT r2s.seat_id "
                            + "FROM reservation2seat r2s "
                            + "INNER JOIN reservations rs "
                            + "ON r2s.reservation_id =? "
                            + "WHERE r2s.reservation_id = rs.id ";
                    PreparedStatement pstatement3 = con.prepareStatement(getSeats);
                    pstatement3.setInt(1, id);
                    ResultSet seatResults = executeQuery(pstatement3);
                    ArrayList<Seat> bookingSeats = new ArrayList();
                    while (seatResults.next()) {
                        seat = new Seat(seatResults.getInt("seat_id"));
                        bookingSeats.add(seat);
                    }

                    reservation = new Booking(id, getCustomer(customerid), getFlight(flightid), bookingSeats, food, price);

                    //Tidy up the connection
                    pstatements.add(pstatement3);
                    resultsets.add(seatResults);
                    pstatements.add(pstatement2);
                    resultsets.add(rs);
                }
            }
            //Further tidy up the connection
            pstatements.add(pstatement);

            resultsets.add(results);

            cons.add(con);

            return reservation;

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while getting the reservation:", ex);

        } catch (NullPointerException e) {
            throw new RuntimeException("You didn't supply a valid reservation ID", e);
        } finally {
            closeConnection(cons, pstatements, resultsets);
        }

    }

    /**
     *
     * @param flight
     */
    @Override
    public void createFlight(Flight flight) {
        Connection con = getConnection();
        try {
            String sql = "INSERT INTO flights "
                    + "VALUES (null, ?, ?, ?, ?, ?, ?, ? ,?, ?)";

            PreparedStatement pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, flight.getAirplaneID());
            pstatement.setInt(2, flight.getFirstClassSeatCost());
            pstatement.setInt(3, flight.getBusinessClassSeatCost());
            pstatement.setInt(4, flight.getEconomyClassSeatCost());
            pstatement.setTimestamp(5, flight.getDepartureTimestamp());
            pstatement.setString(6, flight.getDeparturePlace());
            pstatement.setTimestamp(7, flight.getArrivalTimestamp());
            pstatement.setString(8, flight.getArrivalPlace());
            pstatement.setBoolean(9, flight.isFull());

            executeUpdate(pstatement);

            //Tidy up the connection
            cons.add(con);
            pstatements.add(pstatement);
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while creating the flight.", ex);
        } finally {
            closeConnection(cons, pstatements, null);
        }

    }

    /**
     *
     * @param flightID
     * @return
     */
    @Override
    public Flight getFlight(int flightID) {
        Connection con = getConnection();
        try {
            String sql = "SELECT * FROM flights WHERE id = ?";
            //pass query to query handler -> db. REMEMBER THAT THIS DOESN'T CLOSE DB CONNECTION, CLOSING IS PARAMOUNT!
            PreparedStatement pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, flightID);
            ResultSet results = executeQuery(pstatement);

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
                PreparedStatement pstatement2 = con.prepareStatement(sqlGetSeats);
                pstatement2.setInt(1, id);
                ResultSet seatResults = executeQuery(pstatement2);

                while (seatResults.next()) {
                    int seatIndex = seatResults.getInt(1);
                    seat = new Seat(seatIndex);
                    seats.add(seat);
                }

                flight = new Flight(id, airplane, firstcost, businesscost, economycost, seats, dPlace, dTime, aPlace, aTime, isFull);

                //Tidy up the connection
                cons.add(con);
                pstatements.add(pstatement);
                pstatements.add(pstatement2);
                resultsets.add(results);
                resultsets.add(seatResults);
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while getting the flight.", ex);
        } finally {
            closeConnection(cons, pstatements, resultsets);
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
        Connection con = getConnection();
        String sql = "SELECT rsv.id "
                + "FROM `reservations` rsv "
                + "INNER JOIN customers cs "
                + "ON rsv.id = cs.id "
                + "WHERE rsv.id = ?";
        //pass query to query handler -> db. REMEMBER THAT THIS METHOD DOESN'T CLOSE STATEMENTS , CLOSING IS PARAMOUNT!
        try {
            PreparedStatement pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, customerID);
            //System.out.println(customerID);
            ResultSet results = executeQuery(pstatement);
            while (results.next()) {
                //System.out.println(results.getInt(1));
                Booking currentBooking = getReservation(results.getInt(1));
                reservations.add(currentBooking);

            }
            //Tidy up the connection
            cons.add(con);
            pstatements.add(pstatement);
            resultsets.add(results);

            return reservations;

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while getting the reservation:", ex);

        } catch (NullPointerException e) {
            throw new RuntimeException("You didn't supply a valid reservation ID", e);
        } finally {
            closeConnection(cons, pstatements, resultsets);
        }

    }

    /**
     *
     * @param flightID
     * @return
     */
    @Override
    public ArrayList<Seat> getFlightBookedSeats(int flightID) {
        Connection con = getConnection();
        try {
            String sql = "SELECT s.seat_id FROM reservation2seat s, reservations r WHERE flightid = ? AND r.id = s.reservation_id";
            PreparedStatement pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, flightID);
            ResultSet results = executeQuery(pstatement);
            while (results.next()) {
                seat = new Seat(results.getInt(1));
                seats.add(seat);
                seat = null;
            }
            cons.add(con);
            pstatements.add(pstatement);
            resultsets.add(results);
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while getting the booked seats on this flight", ex);
        } finally {
            closeConnection(cons, pstatements, resultsets);
        }
        return seats;
    }

    /**
     *
     * @param flight
     */
    @Override
    public void editFlight(Flight flight) {
        Connection con = getConnection();
        try {
            String sql = "UPDATE flights SET "
                    + "airplane_id = ?, firstcost = ?, businesscost = ?, economycost = ?, departuretime = '?', "
                    + "departureplace = '?', arrivaltime = '?', arrivalplace = '?', WHERE id = ?";

            PreparedStatement pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, flight.getAirplaneID());
            pstatement.setInt(2, flight.getFirstClassSeatCost());
            pstatement.setInt(3, flight.getBusinessClassSeatCost());
            pstatement.setInt(4, flight.getEconomyClassSeatCost());
            pstatement.setTimestamp(5, flight.getDepartureTimestamp());
            pstatement.setString(6, flight.getDeparturePlace());
            pstatement.setTimestamp(7, flight.getArrivalTimestamp());
            pstatement.setString(8, flight.getArrivalPlace());
            executeUpdate(pstatement);

            //Tidy up the connection
            cons.add(con);
            pstatements.add(pstatement);

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while editing your flight", ex);
        } finally {
            closeConnection(cons, pstatements, null);
        }
    }

    /**
     *
     * @param flight
     */
    @Override
    public void deleteFlight(Flight flight) {
        Connection con = getConnection();
        try {
            String sql = "DELETE FROM flights WHERE id =?";
            PreparedStatement pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, flight.getID());
            executeUpdate(pstatement);

            //Tidy up connection
            cons.add(con);
            pstatements.add(pstatement);
        } catch (SQLException ex) {
            throw new RuntimeException("Soemthing went wrong while deleting your flight", ex);
        } finally {
            closeConnection(cons, pstatements, null);
        }
    }

    /**
     *
     * @param airplaneID
     * @return
     */
    @Override
    public Airplane getAirplane(int airplaneID) {
        Connection con = getConnection();
        try {
            String sql = "SELECT * FROM airplanes WHERE id =?";
            PreparedStatement pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, airplaneID);
            ResultSet results = executeQuery(pstatement);
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

            //Tidy up the connection
            cons.add(con);
            pstatements.add(pstatement);
            resultsets.add(results);

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while getting the airplane", ex);
        } finally {
            closeConnection(cons, pstatements, resultsets);
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
        Connection con = getConnection();
        try {

            String sql;
            ResultSet results;
            PreparedStatement pstatement;
            if (freeSeatsOnly == true) {
                sql = "SELECT * FROM flights WHERE isfull = 0";
                pstatement = con.prepareStatement(sql);
            } else {
                sql = "SELECT * FROM flights";
                pstatement = con.prepareStatement(sql);
            }

            results = executeQuery(pstatement);

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

                seats = new ArrayList();

                sql = "SELECT seat_id FROM reservation2seat WHERE flight_id =?";
                PreparedStatement pstatement2 = con.prepareStatement(sql);
                pstatement2.setInt(1, id);
                ResultSet seatResults = executeQuery(pstatement2);
                while (seatResults.next()) {
                    int seatIndex = seatResults.getInt(1);
                    seat = new Seat(seatIndex);
                    seats.add(seat);
                }

                flight = new Flight(id, airplane, firstcost, businesscost, economycost, seats, dPlace, dTime, aPlace, aTime, isFull);
                flights.add(flight);

                //Tidy up the connection
                resultsets.add(seatResults);
                pstatements.add(pstatement2);
            }

            //Further tidy up the connection
            cons.add(con);
            resultsets.add(results);
            pstatements.add(pstatement);

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while getting the flights", ex);
        } finally {
            closeConnection(cons, pstatements, resultsets);
        }
        return flights;
    }
}
