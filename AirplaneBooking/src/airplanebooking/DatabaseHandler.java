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
    ArrayList<Customer> customers = new ArrayList<>();
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

    private void executeUpdate(String sql) throws SQLException {

        try {
            //Query DataSource for connection, and establish statement handler
            con = cpds.getConnection();
            statement = con.createStatement();
            //pass statement to statement handler -> db.
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            //remember to close statment and release resources back to DB.
            statement.close();
            con.close();
        }

    }

    private ResultSet executeQuery(String sql) throws SQLException {
        //first we establish DB connection.

        //establish statement handler.
        try {
            con = cpds.getConnection();
            statement = con.createStatement();

            //pass statement to statement handler -> db and save ResultSet.
            results = statement.executeQuery(sql);

        } catch (SQLException e) {

            System.out.println(e);
        } finally {
            con.close();
        }
        return results;
    }

    @Override
    public void createCustomer(String maritalstatus, String firstname, String lastname, String addressStreet, int addressZip, String addressCity, String addressCountry, String email, int phonenumber) {
        try {
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
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void editCustomer(int customerID, String maritalstatus, String firstname, String lastname, String addressStreet, int addressZip, String addressCity, String addressCountry, String email, int phonenumber) {
        try {
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
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void deleteCustomer(int customerID) {
        try {
            //create string (sql statement), which we'd like to pass to the statement handler.
            String sql = "DELETE FROM customers WHERE id = " + customerID;

            System.out.println("Customer with customerID:" + customerID + " deleted.");

            //execute statement
            executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Customer getCustomer(int customerID) {
        String sql = "SELECT * FROM customers WHERE id = " + customerID;
        //pass query to query handler -> db. REMEMBER THAT THIS DOESN'T CLOSE DB CONNECTION, CLOSING IS PARAMOUNT!

        try {
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
            statement.close();
            return customer;

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while getting the customer:", ex);

        } catch (NullPointerException e) {
            throw new RuntimeException("You didn't supply a valid customer ID", e);
        }
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
            statement.close();
            return customers;
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong in getting your customers", ex);
        } finally {

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
            statement.close();
            return customers;
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong in getting your customers", ex);
        } finally {

        }

    }

    public boolean customerExists(Customer customer) {
       
            String firstname = customer.getFirstName();
            String lastname = customer.getLastName();
            String email = customer.getEmail();
            boolean exists = false;
            
            String sql = "SELECT COUNT(*) FROM customers WHERE firstname="+firstname+" OR lastname ="+ lastname+ "OR email = "+email;
        try {
            executeQuery(sql);
            exists = 0 != results.getInt(1);
        
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
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

                statement.executeUpdate(sql);
                int reservationID = statement.executeUpdate(sql, 1);

                for (Seat seat : seats) {
                    sql = "UPDATE reservation2seat SET "
                            + "reservation_id = " + reservationID + ", "
                            + "seat_id= " + seat.getIndex();

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
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while creating your reservation", ex);
        }

    }

    @Override
    public void editReservation(int reservationID, int customerID, String flightID, ArrayList<Seat> seats, int food) {
        try {
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
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while editing your reservation", ex);
        }
    }

    @Override
    public void deleteReservation(int reservationID) {

        String sql = "DELETE FROM reservations WHERE id = " + reservationID;

        try {
            executeUpdate(sql);
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while deleting your reservation", ex);
        }

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

                reservation = new Booking(id, customerid, flightid, food);

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
        try {
            executeUpdate(sql);
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while creating your reservation", ex);
        }
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

    public void deleteSeats(int i) {
        String sql = "DELETE FROM seats WHERE seat_id= " + i;
        try {
            executeUpdate(sql);
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while creating your seats", ex);
        }
    }

}
