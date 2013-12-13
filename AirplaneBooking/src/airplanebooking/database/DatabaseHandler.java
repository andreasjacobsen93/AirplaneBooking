/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airplanebooking.database;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * If you are looking for more details on the methods of this class, please
 * refer to the Interface that this class implements<p>
 *
 * SQLExceptions are handled by each and every method in this class.<p>
 *
 * NullPointerExceptions are not handled, unless explicitly stated.<p>
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
     * Constructor for this object. For more detail about the API methods of
     * this class, please refer to:
     * {@link airplanebooking.DB.DatabaseInterface DatabaseInterface}
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
        cpds.setIdleConnectionTestPeriod(10);
        cpds.setTestConnectionOnCheckin(true);
        cpds.setMaxIdleTimeExcessConnections(5);
    }

    private Connection getConnection() throws SQLException {

        return cpds.getConnection();

    }

    /**
     * This method is borrowed from:<br>
     *
     * http://docs.oracle.com/javase/tutorial/jdbc/basics/sqlexception.html<p>
     *
     * Direct download link to original source code:<br>
     * http://docs.oracle.com/javase/tutorial/jdbc/basics/examples/zipfiles/JDBCTutorial.zip<p>
     *
     * It provides a way of handling Exceptions, while ignoring common errors
     * which are not critical for application function.
     *
     *
     * @param sqlState SQL-state value
     * @return Boolean value TRUE or FALSE
     */
    public static boolean ignoreSQLException(String sqlState) {
        if (sqlState == null) {
            System.out.println("The SQL state is not defined!");
            return false;
        }
        // X0Y32: Jar file already exists in schema
        if (sqlState.equalsIgnoreCase("X0Y32")) {
            return true;
        }
        // 42Y55: Table already exists in schema
        if (sqlState.equalsIgnoreCase("42Y55")) {
            return true;
        }
        return false;
    }

    /**
     * This method is borrowed from:<br>
     *
     * http://docs.oracle.com/javase/tutorial/jdbc/basics/sqlexception.html<p>
     *
     * Direct download link to original source code:<br>
     * http://docs.oracle.com/javase/tutorial/jdbc/basics/examples/zipfiles/JDBCTutorial.zip<p>
     *
     * It provides a way of handling Exceptions, while ignoring common errors
     * which are not critical for application function.
     *
     *
     * @param ex
     */
    public static void printSQLException(SQLException ex) {

        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                if (ignoreSQLException(
                        ((SQLException) e).
                        getSQLState()) == false) {

                    e.printStackTrace(System.err);
                    System.err.println("SQLState: "
                            + ((SQLException) e).getSQLState());

                    System.err.println("Error Code: "
                            + ((SQLException) e).getErrorCode());

                    System.err.println("Message: " + e.getMessage());

                    Throwable t = ex.getCause();
                    while (t != null) {
                        System.out.println("Cause: " + t);
                        t = t.getCause();
                    }
                }
            }
        }
    }

    private void executeUpdate(PreparedStatement pstatement) {
        //SET method, for putting data into the database.
        try {
            //Execute the prepared statement.
            pstatement.executeUpdate();

        } catch (SQLException ex) {
            printSQLException(ex);
        }
    }

    private ResultSet executeQuery(PreparedStatement pstatement) throws SQLException {
        //GET method for getting data from the database.
        ResultSet results = pstatement.executeQuery();
        return results;
    }

    /**
     *
     */
    private void getNumCon() {
        //Maintenance debug method, for getting all number of alive connections and connection pools - use to debug current state of the pool.
        try {
            System.out.println("Number of Connections: " + cpds.getNumConnections());
            System.out.println("Number of Idle Connections: " + cpds.getNumIdleConnections());
            System.out.println("Number of Busy Connections: " + cpds.getNumBusyConnections());
            System.out.println("Number of Connection Pools: " + cpds.getNumUserPools());
            System.out.println("");
        } catch (SQLException ex) {
            printSQLException(ex);
        }
    }

    private void closeConnection(ArrayList<Connection> cons, ArrayList<PreparedStatement> pstatements, ArrayList<ResultSet> resultsets) {
        //This is where all the "tidy'd" up Connections, preparedStatements and ResultSets go to die (and get closed).
        try {
            //Not all methods produce ResulSets, so check these first, and weed out.
            if (resultsets != null) {
                for (ResultSet results : resultsets) {
                    results.close();

                }
                this.resultsets.removeAll(resultsets);
            }
            //All methods create Connections and preparedStatements, so collect these, close them and destroy them.
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
            printSQLException(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createCustomer(Customer customer) {
        //Method for creating a customer
        try {
            Connection con = getConnection();
            //Create prepared statement string.
            String sql = "INSERT INTO customers VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            //Prepare the statement.
            PreparedStatement pstatement = con.prepareStatement(sql);
            //Unpack the Customer object, and insert values relative to the the correct column positions in the database.
            pstatement.setString(1, customer.getMaritalStatus());
            pstatement.setString(2, customer.getFirstName());
            pstatement.setString(3, customer.getLastName());
            pstatement.setString(4, customer.getAddressStreet());
            pstatement.setInt(5, customer.getAddressZip());
            pstatement.setString(6, customer.getAddressCity());
            pstatement.setString(7, customer.getAddressCountry());
            pstatement.setString(8, customer.getEmail());
            pstatement.setInt(9, customer.getPhone());

            //Execute the prepared statement 
            executeUpdate(pstatement);

            //Tidy up connection
            cons.add(con);
            pstatements.add(pstatement);
        } catch (SQLException ex) {
            printSQLException(ex);
        } finally {
            closeConnection(cons, pstatements, null);
        }

    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public void editCustomer(Customer customer) {
        //Method for editing a specific customer.
        try {
            Connection con = getConnection();
            //Create the prepared statement string.
            String sql = "UPDATE customers SET maritalstatus = ?, firstname = ?, lastname = ?, addressstreet = ?, addresszip = ?, addresscity = ?, "
                    + "addresscountry = ?, email = ?, phonenumber = ? WHERE id = ?";
            //Prepare the statement
            PreparedStatement pstatement = con.prepareStatement(sql);
            //Unpack the Customer object, and insert values relative to the the correct column positions in the database.
            pstatement.setString(1, customer.getMaritalStatus());
            pstatement.setString(2, customer.getFirstName());
            pstatement.setString(3, customer.getLastName());
            pstatement.setString(4, customer.getAddressStreet());
            pstatement.setInt(5, customer.getAddressZip());
            pstatement.setString(6, customer.getAddressCity());
            pstatement.setString(7, customer.getAddressCountry());
            pstatement.setString(8, customer.getEmail());
            pstatement.setInt(9, customer.getPhone());
            pstatement.setInt(10, customer.getID());

            //Execute the prepared statement
            executeUpdate(pstatement);

            //Tidy up connection
            cons.add(con);
            pstatements.add(pstatement);
        } catch (SQLException ex) {
            printSQLException(ex);
        } finally {
            closeConnection(cons, pstatements, null);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteCustomer(Customer customer) {
        //Method for deleting customer
        try {
            Connection con = getConnection();
            //Create the prepared statement string.
            String sql = "DELETE FROM customers WHERE id = ?";
            //Prepare the statement.
            PreparedStatement pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, customer.getID());
            //Execute statement.
            executeUpdate(pstatement);

            //Tidy up connection
            cons.add(con);
            pstatements.add(pstatement);
        } catch (SQLException ex) {
            printSQLException(ex);
        } finally {
            closeConnection(cons, pstatements, null);
        }

    }

    /**
     * {@inheritDoc}
     * 
     */
    @Override
    public Customer getCustomer(int customerID) {
        try {
            Connection con = getConnection();
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
            printSQLException(ex);
        } finally {

            closeConnection(cons, pstatements, resultsets);
        }
        return customer;
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public ArrayList<Customer> getCustomers(int q) {
        //Method for getting multiple customers
        String sql = "SELECT * FROM customers WHERE addresszip = ? OR phonenumber = ?";
        try {
            Connection con = getConnection();
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

        } catch (SQLException ex) {
            printSQLException(ex);
        } finally {
            closeConnection(cons, pstatements, resultsets);
        }
        return customers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Customer> getCustomers(String q) {
        //Method for getting multiple customers.
        String sql = "SELECT * FROM customers WHERE firstname = ? OR lastname = ? OR addressstreet = ? OR email = ?";
        try {
            Connection con = getConnection();
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

        } catch (SQLException ex) {
            printSQLException(ex);
        } finally {
            closeConnection(cons, pstatements, resultsets);
        }
        return customers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Customer> getCustomers(String firstName, String lastName, String email, Integer Phone) {

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
            Connection con = getConnection();
            int k = 0;

            for (int i = 0; i < parts.length; i++) {
                if (parts[i].matches("1")) {

                    sql += sqlArr[i];
                    if (sqlArr[i] != null) {
                        sql += "AND ";
                        System.out.println(sql);
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
            customers = new ArrayList();
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

        } catch (SQLException ex) {
            printSQLException(ex);
        } finally {
            closeConnection(cons, pstatements, resultsets);
        }
        return customers;
    }

    /**
     * For more detailed information about the Customer object, please refer
     * to:<br> {@link airplanebooking.DB.Customer}
     * @param customer {@link airplanebooking.DB.Customer}
     * @return TRUE or FALSE
     */
    private boolean customerExists(Customer customer) {

        String firstname = customer.getFirstName();
        String lastname = customer.getLastName();
        String email = customer.getEmail();

        boolean exists = false;
        try {
            Connection con = getConnection();
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
            printSQLException(ex);
        } finally {
            closeConnection(cons, pstatements, resultsets);
        }
        return exists;
    }

    /**
     *
     * @param customerID Must be a valid customer ID which relates to the ID
     * field in the table 'customers' in the database.
     * @return TRUE or FALSE
     */
    private boolean customerExists(int customerID) {

        String sql = "SELECT * FROM customers WHERE id= ?";
        Boolean exists = false;
        try {
            Connection con = getConnection();
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
            printSQLException(ex);
        } finally {
            closeConnection(cons, pstatements, resultsets);
        }
        return exists;

    }

    /**
     *
     * THIS METHOD IS PARTIALLY 'BROKEN' IN FUNKTIONALITY, IF --*ANY*-- OF THE
     * SEATS EXISTS, IT WILL RETURN TRUE!
     *
     *
     * @param seats {@link airplanebooking.DB.Seat}
     * @param flight {@link airplanebooking.DB.Flight}
     * @return Beware; returns TRUE if ANY of the seats sent, exist in the
     * database, even if the rest do not.
     */
    private boolean seatsExist(ArrayList<Seat> seats, Flight flight) {

        boolean exists = false;
        for (Seat currentSeat : seats) {
            try {
                Connection con2 = getConnection();
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
                printSQLException(ex);
            } finally {
                // closeConnection(cons, pstatements, resultsets);
            }

        }
        return exists;
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public void createReservation(Customer currentCustomer, Flight flight, ArrayList<Seat> seats, Boolean food, int cost) {

        try {
            Connection con = getConnection();
            if (customerExists(currentCustomer)) {
                if (!seatsExist(seats, flight)) {
                    customer = currentCustomer;
                    int customerID = customer.getID();
                    int flightID = flight.getID();

                    String sql = "INSERT INTO reservations "
                            + "VALUES (null, ?, ?, ?, ?)";

                    PreparedStatement pstatement = con.prepareStatement(sql, 1);
                    pstatement.setInt(1, customerID);
                    pstatement.setInt(2, flightID);
                    pstatement.setBoolean(3, food);
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

                String sql = "INSERT INTO customers VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement pstatement2 = pstatement2 = con.prepareStatement(sql, 1);
                pstatement2.setString(1, maritalstatus);
                pstatement2.setString(2, firstname);
                pstatement2.setString(3, lastname);
                pstatement2.setString(4, addressStreet);
                pstatement2.setInt(5, addressZip);
                pstatement2.setString(6, addressCity);
                pstatement2.setString(7, addressCountry);
                pstatement2.setString(8, email);
                pstatement2.setInt(9, phonenumber);

                pstatement2.executeUpdate();
                ResultSet rs = pstatement2.getGeneratedKeys();
                rs.first();
                int id = rs.getInt(1);
                System.out.println("ID of customer: " + id);
                Customer newCustomer = getCustomer(id);

                createReservation(newCustomer, flight, seats, food, cost);

                //Tidy up the connection
                cons.add(con);
                pstatements.add(pstatement2);

            }

        } catch (SQLException ex) {
            printSQLException(ex);
        } finally {
            closeConnection(cons, pstatements, null);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void editReservation(Booking booking) {

        try {
            Connection con = getConnection();
            String sql = "UPDATE reservations SET "
                    + "customer_id = ?, flightid = ?, food = ?, price = ? WHERE id = ?";
            customer = booking.getCustomer();
            flight = booking.getFlight();
            PreparedStatement pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, customer.getID());
            pstatement.setInt(2, flight.getID());
            pstatement.setBoolean(3, booking.getFood());
            pstatement.setInt(4, booking.getPrice());
            pstatement.setInt(5, booking.getID());

            editCustomer(customer);
            executeUpdate(pstatement);
            seats = booking.getSeats();

            if (seats.isEmpty()) {
                sql = "DELETE FROM reservation2seat WHERE reservation_id=?";
                PreparedStatement pstatement2 = con.prepareStatement(sql);
                pstatement2.setInt(1, booking.getID());
                executeUpdate(pstatement2);

                //Tidy up the connection
                pstatements.add(pstatement2);

                //If a reservation has no seats, it is no longer a reservation - therefore delete it!
                deleteReservation(booking);

            } else {
                //First we delete all current seats for this particular reservation
                sql = "DELETE FROM reservation2seat WHERE reservation_id=?";
                PreparedStatement pstatement2 = con.prepareStatement(sql);
                pstatement2.setInt(1, booking.getID());
                executeUpdate(pstatement2);

                //Then we add all the seats which were given to us, by the Booking object
                sql = "INSERT INTO reservation2seat VALUES(?, ?, ?)";
                PreparedStatement pstatement3 = con.prepareStatement(sql);

                for (Seat currentSeat : seats) {
                    pstatement3.setInt(1, booking.getID());
                    pstatement3.setInt(2, currentSeat.getSeatID());
                    pstatement3.setInt(3, flight.getID());
                    executeUpdate(pstatement3);
                }
                //Tidy up the connection
                pstatements.add(pstatement3);
            }
            //Further tidy up the connection
            pstatements.add(pstatement);
            cons.add(con);

        } catch (SQLException ex) {
            printSQLException(ex);
        } finally {
            closeConnection(cons, pstatements, null);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteReservation(Booking booking) {

        try {
            Connection con = getConnection();
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
            printSQLException(ex);
        } finally {
            closeConnection(cons, pstatements, null);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Booking getReservation(int reservationID) {

        String sql = "SELECT * FROM reservations WHERE id =?";
        try {
            Connection con = getConnection();
            PreparedStatement pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, reservationID);

            //pass query to query handler -> db. REMEMBER THAT THIS METHOD DOESN'T CLOSE STATEMENTS , CLOSING IS PARAMOUNT!
            ResultSet results = executeQuery(pstatement);
            results.beforeFirst();
            while (results.next()) {
                int id = results.getInt("id");
                int customerid = results.getInt("customer_id");
                int flightid = results.getInt("flightid");
                Boolean food = results.getBoolean("food");
                int price = results.getInt("price");
                System.out.println("Reservation ID: " + id);
                System.out.println("Customer ID: " + customerid);
                System.out.println("Flight ID: " + flightid);
                System.out.println("Boolean: " + food);
                System.out.println("Cost: " + price);
                System.out.println("");
                String getSeats = "SELECT r2s.seat_id FROM `reservation2seat` r2s, reservations rs, customers cs  WHERE r2s.reservation_id = ? AND rs.id = ? AND r2s.flight_id = ? AND cs.id = ?";
                PreparedStatement pstatement2 = con.prepareStatement(getSeats);
                pstatement2.setInt(1, id);
                pstatement2.setInt(2, id);
                pstatement2.setInt(3, flightid);
                pstatement2.setInt(4, customerid);

                ResultSet seatResults = executeQuery(pstatement2);
                seat = null;
                ArrayList<Seat> bookingSeats = new ArrayList();
                while (seatResults.next()) {
                    seat = null;
                    seat = new Seat(seatResults.getInt(1));
                    System.out.println(seat.getSeatID());
                    bookingSeats.add(seat);
                    seat = null;
                }
                reservation = null;
                reservation = new Booking(id, getCustomer(customerid), getFlight(flightid), bookingSeats, food, price);
                System.out.println(reservation.getSeats());
                //Tidy up the connection
                resultsets.add(seatResults);
                pstatements.add(pstatement2);

            }
            //Further tidy up the connection
            cons.add(con);
            resultsets.add(results);
            pstatements.add(pstatement);

            //statement.close();
        } catch (SQLException ex) {
            printSQLException(ex);

        } finally {

            closeConnection(cons, pstatements, resultsets);
        }
        return reservation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Booking getReservation(int seatID, int flightID) {

        String sql = "SELECT reservation_id FROM reservation2seat WHERE seat_id = ? AND flight_id = ?";
        int reservationID;
        try {
            Connection con = getConnection();
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

        } catch (SQLException ex) {
            printSQLException(ex);

        } finally {
            closeConnection(cons, pstatements, resultsets);
        }
        return reservation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createFlight(Flight flight) {

        try {
            Connection con = getConnection();
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
            printSQLException(ex);
        } finally {
            closeConnection(cons, pstatements, null);
        }

    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public Flight getFlight(int flightID) {

        try {
            Connection con = getConnection();
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
            printSQLException(ex);
        } finally {
            closeConnection(cons, pstatements, resultsets);
        }
        return flight;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Booking> getCustomerReservations(int customerID) {

        String sql = "SELECT rsv.id FROM reservations rsv WHERE rsv.customer_id = ?";
        //pass query to query handler -> db. REMEMBER THAT THIS METHOD DOESN'T CLOSE STATEMENTS , CLOSING IS PARAMOUNT!
        try {
            Connection con = getConnection();
            PreparedStatement pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, customerID);
            //System.out.println(customerID);
            reservations = new ArrayList();
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

        } catch (SQLException ex) {
            printSQLException(ex);

        } finally {
            closeConnection(cons, pstatements, resultsets);
        }
        return reservations;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Seat> getFlightBookedSeats(int flightID) {

        try {
            Connection con = getConnection();
            String sql = "SELECT s.seat_id FROM reservation2seat s, reservations r WHERE flightid = ? AND r.id = s.reservation_id";
            PreparedStatement pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, flightID);
            ResultSet results = executeQuery(pstatement);
            seats = new ArrayList();
            while (results.next()) {
                seat = new Seat(results.getInt(1));
                seats.add(seat);
                seat = null;
            }
            cons.add(con);
            pstatements.add(pstatement);
            resultsets.add(results);
        } catch (SQLException ex) {
            printSQLException(ex);
        } finally {
            closeConnection(cons, pstatements, resultsets);
        }
        return seats;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void editFlight(Flight flight) {

        try {
            Connection con = getConnection();
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
            printSQLException(ex);
        } finally {
            closeConnection(cons, pstatements, null);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteFlight(Flight flight) {

        try {
            Connection con = getConnection();
            String sql = "DELETE FROM flights WHERE id =?";
            PreparedStatement pstatement = con.prepareStatement(sql);
            pstatement.setInt(1, flight.getID());
            executeUpdate(pstatement);

            //Tidy up connection
            cons.add(con);
            pstatements.add(pstatement);
        } catch (SQLException ex) {
            printSQLException(ex);
        } finally {
            closeConnection(cons, pstatements, null);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Airplane getAirplane(int airplaneID) {

        try {
            Connection con = getConnection();
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
            printSQLException(ex);
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
    /**
     * {@inheritDoc}
     *
     */
    @Override
    public ArrayList<Flight> getFlights(Boolean freeSeatsOnly) {

        flights = null;
        flights = new ArrayList();

        try {
            Connection con = getConnection();
            String sql;
            ResultSet results;
            PreparedStatement pstatement;
            if (freeSeatsOnly == true) {
                sql = "SELECT * FROM flights WHERE isfull = 0 AND departuretime > DATE_ADD(SYSDATE(), INTERVAL 2 HOUR) ORDER BY departuretime ";
                pstatement = con.prepareStatement(sql);
            } else {
                sql = "SELECT * FROM flights WHERE departuretime > DATE_ADD(SYSDATE(), INTERVAL 2 HOUR) ORDER BY departuretime";
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
            printSQLException(ex);
        } finally {
            closeConnection(cons, pstatements, resultsets);
        }
        return flights;
    }

    /**
     * {@inheritDoc}
     *
     */
    @Override
    public ArrayList<Flight> getFilteredFlights(String[] filters, String comparer) {
        String sql = "SELECT f.id FROM flights f, airplanes a WHERE f.airplane_id = a.id AND";
        try {
            Connection con = getConnection();
            int i = 0;
            String[] filterArguments = new String[filters.length];
            for (String filter : filters) {
                System.out.println(filters.length);
                System.out.println(filter);
                String[] filterSplit = filter.split("\\.");

                switch (filterSplit[0].toString()) {

                    case "FirstClass":
                        sql += " a.firstseats > 0 " + comparer;
                        break;
                    case "BusinessClass":
                        sql += " a.businessseats > 0 " + comparer;
                        break;
                    case "EconomyClass":
                        sql += " a.economyseats > 0 " + comparer;
                        break;
                    case "Today":
                        sql += " CAST(f.departuretime AS DATE) + 0 = CAST( SYSDATE( ) AS DATE ) + 0 " + comparer;
                        break;
                    case "Tomorrow":
                        sql += " CAST(f.departuretime AS DATE) + 0 = CAST( SYSDATE( ) AS DATE ) + 1 " + comparer;
                        break;
                    case "Airplane":
                        sql += " a.name=? " + comparer;
                        i++;
                        break;
                    case "Date":
                        sql += " CAST(f.departuretime AS DATE) + 0 = CAST( ? AS DATE) " + comparer;
                        i++;
                        String[] filterDate = filterSplit[1].split("/");
                        filterSplit[1] = filterDate[2] + filterDate[1] + filterDate[0];
                        break;
                    case "From":
                        sql += " f.departureplace = ? " + comparer;
                        i++;
                        break;
                    case "To":
                        sql += " f.arrivalplace = ? " + comparer;
                        i++;
                        break;

                }
                if (filterSplit.length > 1 && filterSplit[1].contains("Class") != true) {
                    filterArguments[i - 1] = filterSplit[1];

                }

            }
            sql = sql.substring(0, sql.length() - comparer.length() - 1);
            PreparedStatement pstatement = con.prepareStatement(sql);
            for (int u = 0; u < i; u++) {
                pstatement.setString(u + 1, filterArguments[u]);
            }
            System.out.println(sql);
            ResultSet results = executeQuery(pstatement);
            flights.clear();
            flights = new ArrayList();

            while (results.next()) {
                flight = null;
                flight = getFlight(results.getInt(1));
                //System.out.println(flight.getID());
                flights.add(flight);
            }
        } catch (SQLException ex) {
            printSQLException(ex);
        } finally {
            closeConnection(cons, pstatements, resultsets);
        }

        return flights;
    }
}
