/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airplanebooking;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alex
 */
public class DatabaseHandler implements DatabaseInterface {

    Connection con = null;
    Statement statement = null;
    ResultSet results = null;
    Customer customer = null;
    Booking reservation = null;
    ArrayList<Booking> reservations = new ArrayList();
    ArrayList<Seat> seats = new ArrayList();
    ArrayList<Customer> customers = new ArrayList();
    ComboPooledDataSource cpds = new ComboPooledDataSource();

    public DatabaseHandler() {
        //contructor method to initiate (combo)DataSource for pooled connections on spawning an object.
        initiateDataSource();

    }

    private void initiateDataSource() {

        cpds.setJdbcUrl("jdbc:mysql://mysql.itu.dk/Airplanebooking");
        cpds.setUser("aljon");
        cpds.setPassword("Jegeradministratorher123");
        cpds.setMinPoolSize(1);
        cpds.setAcquireIncrement(3);
        cpds.setMaxPoolSize(10);

    }

    private void executeUpdate(String sql) {

        try {
            //Query DataSource for connection, and establish statement handler
            con = cpds.getConnection();
            statement = con.createStatement();
            //pass statement to statement handler -> db.
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private ResultSet executeQuery(String sql) {
        //first we establish DB connection.

        //establish statement handler.
        try {
            con = cpds.getConnection();
            statement = con.createStatement();

            //pass statement to statement handler -> db and save ResultSet.
            results = statement.executeQuery(sql);

        } catch (SQLException e) {

            System.out.println(e);
        }
        return results;
    }

    private void closeConnection() {
        try {
            statement.close();
            //
            results.close();
            // results = null;
            con.close();
            // con = null;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void createCustomer(String maritalstatus, String firstname, String lastname, String addressStreet, int addressZip, String addressCity, String addressCountry, String email, int phonenumber) {

        //create string (sql statement), which we'd like to pass to the statement handler.
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
        System.out.println("Customer " + firstname + " " + lastname + " created.");
        //execute the statement
        executeUpdate(sql);

    }

    @Override
    public void editCustomer(int customerID, String maritalstatus, String firstname, String lastname, String addressStreet, int addressZip, String addressCity, String addressCountry, String email, int phonenumber) {

        //create string (sql statement), which we'd like to pass to the statement handler.
        String sql = "UPDATE customers SET "
                + "maritalstatus = '" + maritalstatus + "', "
                + "firstname = '" + firstname + "', "
                + "lastname = '" + lastname + "', "
                + "addressstreet = '" + addressStreet + "', "
                + "addresszip = " + addressZip + ", "
                + "addresscity = '" + addressCity + "', "
                + "addresscountry = '" + addressCountry + "', "
                + "email = '" + email + "', "
                + "phonenumber = " + phonenumber
                + "WHERE id = " + customerID;

        System.out.println("Customer " + firstname + " " + lastname + " edited.");

        //execute statement
        executeUpdate(sql);
    }

    @Override
    public void deleteCustomer(int customerID) {

        //create string (sql statement), which we'd like to pass to the statement handler.
        String sql = "DELETE FROM customers WHERE id = " + customerID;

        System.out.println("Customer with customerID:" + customerID + " deleted.");

        //execute statement
        executeUpdate(sql);

    }

    @Override
    public Customer getCustomer(int customerID) {
        try {
            String sql = "SELECT * FROM customers WHERE id = " + customerID;
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

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return customer;
    }

    /*   public ArrayList getCustomers() {
        
     return customers;
     }*/
    @Override
    public ArrayList<Customer> getCustomers(int q) {

        String sql = "SELECT * FROM customers WHERE addresszip =" + q + " OR phonenumber =" + q;

        try {
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

    @Override
    public ArrayList<Customer> getCustomers(String q) {
        String sql = "SELECT * FROM customers WHERE firstname =\"" + q + "\" OR lastname =\"" + q + "\" OR addressstreet =\"" + q + "\" OR email =\"" + q + "\"";

        try {
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

    public ArrayList<Customer> getCustomers(String firstName, String lastName, String Email, Integer Phone) {

        String sql = "SELECT * FROM customers WHERE ";

        int i = 0;
        if (firstName != null) {
            sql += "firstname = \"" + firstName + "\"";
            i++;
        }

        if (lastName != null) {
            if (i > 0) {
                sql += " AND ";
            }
            sql += "lastname = \"" + lastName + "\"";
            i++;
        }

        if (Email != null) {
            if (i > 0) {
                sql += " AND ";
            }
            sql += "email = \"" + Email + "\"";
            i++;
        }

        if (Phone != null) {
            if (i > 0) {
                sql += " AND ";
            }
            sql += "phonenumber = \"" + Phone + "\"";
        }

        try {
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
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // closeConnection();
        }

        return exists;

    }

    @Override
    public void createReservation(Customer currentCustomer, String flightID, ArrayList<Seat> seats, int food) {
        try {
            if (customerExists(currentCustomer)) {
                customer = currentCustomer;
                int customerID = customer.getID();

                String sql = "INSERT INTO reservations "
                        + "VALUES (null, "
                        + "" + customerID + ", "
                        + "'" + flightID + "', "
                        + "" + food + ")";

                int[] key = new int[1];
                key[0] = 1;
                statement.executeUpdate(sql, key[0]);

                ResultSet rs = statement.getGeneratedKeys();
                rs.first();
                int reservationID = rs.getInt(1);
                System.out.println(reservationID);
                closeConnection();
                for (Seat seat : seats) {
                    System.out.println(seat.getIndex());
                    sql = "INSERT INTO reservation2seat VALUES (" + reservationID + ", " + seat.getIndex() + ")";

                    executeUpdate(sql);

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
                System.out.println("Customer " + firstname + " " + lastname + " created.");

                statement.executeUpdate(sql);
                customer = getCustomer(statement.executeUpdate(sql, 1));
                createReservation(customer, flightID, seats, food);

            }

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while creating your reservation", ex);
        } finally {
            closeConnection();
        }

    }

    @Override
    public void editReservation(int reservationID, int customerID, String flightID, ArrayList<Seat> seats, int food) {

        String sql = "UPDATE reservations SET "
                + "customer_id = " + customerID + ", "
                + "flightid = '" + flightID + "', "
                + "food = " + food + " "
                + "WHERE id = " + reservationID;

        executeUpdate(sql);
        if (seats.isEmpty()) {
            sql = "DELETE FROM reservation2seat WHERE reservation_id=" + reservationID;
            executeUpdate(sql);
        } else {
            for (Seat seat : seats) {
                sql = "UPDATE reservation2seat SET "
                        + "reservation_id = " + reservationID + ", "
                        + "seat_id= " + seat.getIndex();

                executeUpdate(sql);
            }
        }

    }

    @Override
    public void deleteReservation(int reservationID) {

        String sql = "DELETE FROM reservations WHERE id = " + reservationID;

        executeUpdate(sql);

    }

    @Override
    public Booking getReservation(int reservationID) {

        String sql = "SELECT * FROM reservations WHERE id = " + reservationID;
        //pass query to query handler -> db. REMEMBER THAT THIS METHOD DOESN'T CLOSE STATEMENTS , CLOSING IS PARAMOUNT!

        try {
            executeQuery(sql);
            while (results.next()) {
                int id = results.getInt("id");
                int customerid = results.getInt("customer_id");
                String flightid = results.getString("flightid");
                int food = results.getInt("food");
                String getSeats = "SELECT r2s.seat_id "
                        + "FROM `reservation2seat` r2s "
                        + "INNER JOIN reservations rs "
                        + "ON r2s.reservation_id ="+ id +""
                        + "WHERE r2s.reservation_id = rs.id ";

                ResultSet seatResults = statement.executeQuery(getSeats);
                while (seatResults.next()) {
                    Seat seat = new Seat(seatResults.getInt("seat_id"));
                    seats.add(seat);
                }

                reservation = new Booking(id, customerid, flightid, seats, food);

            }
            statement.close();
            return reservation;

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while getting the reservation:", ex);

        } catch (NullPointerException e) {
            throw new RuntimeException("You didn't supply a valid reservation ID", e);
        }

    }
    //Below are unimplemented methods.

    @Override
    public void createFlight(int flightID, int firstSeats, int businessSeats, int economySeats, int totalSeats, String departureTime, String arrivalTime) {
        String sql = "INSERT INTO flights "
                + "VALUES (null, "
                + "" + firstSeats + ", "
                + "" + businessSeats + ", "
                + "" + economySeats + ", "
                + "" + totalSeats + ", "
                + "'" + departureTime + "', "
                + "'" + arrivalTime + "')";

        executeUpdate(sql);

    }

    @Override
    public void editFlight(int flightID, int firstSeats, int businessSeats, int economySeats, int totalSeats, String departureTime, String arrivalTime) {

    }

    @Override
    public void deleteFlight(int flightID) {

    }

    @Override
    public void getFlight(int flightID) {

    }

    //  @Override
    public ArrayList<Booking> getCustomerReservations(int customerID) {

        String sql = "SELECT rsv.id "
                + "FROM `reservations` rsv "
                + "INNER JOIN customers cs "
                + "ON rsv.id = cs.id "
                + "WHERE rsv.id = "+customerID;
        //pass query to query handler -> db. REMEMBER THAT THIS METHOD DOESN'T CLOSE STATEMENTS , CLOSING IS PARAMOUNT!

        try {
            executeQuery(sql);
            while (results.next()) {
                Booking currentBooking = getReservation(results.getInt(1));
                reservations.add(currentBooking);

            }
            statement.close();
            return reservations;

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while getting the reservation:", ex);

        } catch (NullPointerException e) {
            throw new RuntimeException("You didn't supply a valid reservation ID", e);
        }

    }

    public void deleteSeats(int i) {

    }

}
