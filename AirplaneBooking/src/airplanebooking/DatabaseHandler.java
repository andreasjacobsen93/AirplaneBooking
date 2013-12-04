/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airplanebooking;

import com.mchange.v2.c3p0.DataSources;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

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
    DataSource ds_unpooled = null;
    DataSource ds_pooled = null;

    public DatabaseHandler() {
        //contructor method to initiate DataSource for pooled connections on spawning an object.
        initiateDataSource();
    }

    private void initiateDataSource() {
        try {
            //first we establish unpooled DB connection.
            ds_unpooled = DataSources.unpooledDataSource("jdbc:mysql://mysql.itu.dk/Airplanebooking",
                    "aljon",
                    "Jegeradministratorher123");
            //Use C3P0 to convert unpooled DataSource to pooled DataSource
            ds_pooled = DataSources.pooledDataSource(ds_unpooled);

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void executeUpdate(String sql) throws SQLException {
        
        
        try {
            //Query DataSource for connection, and establish statement handler
            con = ds_pooled.getConnection();
            statement = con.createStatement();
            //pass statement to statement handler -> db.
            statement.executeUpdate(sql);
            
        } catch (SQLException e) {
            System.out.println(e);            
        } finally {
            //remember to close statment and release resources back to DB.
            statement.close();
        }

    }

    private ResultSet executeQuery(String sql) {
        //first we establish DB connection.

        //establish statement handler.
        try {
            con = ds_pooled.getConnection();
            statement = con.createStatement();
            
            //pass statement to statement handler -> db and save ResultSet.
            results = statement.executeQuery(sql);
            
        } catch (SQLException e) {

            System.out.println(e);
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
        executeQuery(sql);

        try {

            while (results.next()) {
                int id = results.getInt("id");
                String maritalstatus = results.getString("maritalstatus");
                String firstname = results.getString("firstname");
                String lastname = results.getString("lastname");
                String address = results.getString("addressstreet") + " " + results.getInt("addresszip") + " " + results.getString("addresscity") + " " + results.getString("addresscountry");
                int phonenumber = results.getInt("phonenumber");
                String email = results.getString("email");

                customer = new Customer(id, maritalstatus, firstname, lastname, address, phonenumber, email);

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

        executeQuery(sql);

        try {

            results.beforeFirst();

            while (results.next()) {
                int id = results.getInt("id");
                String maritalstatus = results.getString("maritalstatus");
                String firstname = results.getString("firstname");
                String lastname = results.getString("lastname");
                String address = results.getString("addressstreet") + " " + results.getInt("addresszip") + " " + results.getString("addresscity") + " " + results.getString("addresscountry");
                int phonenumber = results.getInt("phonenumber");
                String email = results.getString("email");
                customer = new Customer(id, maritalstatus, firstname, lastname, address, phonenumber, email);
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

        executeQuery(sql);

        try {
            results.beforeFirst();
            while (results.next()) {
                int id = results.getInt("id");
                String maritalstatus = results.getString("maritalstatus");
                String firstname = results.getString("firstname");
                String lastname = results.getString("lastname");
                String address = results.getString("addressstreet") + " " + results.getInt("addresszip") + " " + results.getString("addresscity") + " " + results.getString("addresscountry");
                int phonenumber = results.getInt("phonenumber");
                String email = results.getString("email");
                customer = new Customer(id, maritalstatus, firstname, lastname, address, phonenumber, email);
                customers.add(customer);
            }
            statement.close();
            return customers;
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong in getting your customers", ex);
        } finally {

        }

    }

    //Below are unimplemented methods.
    @Override
    public void createReservation(int customerID, String flightID, int seats, int food) {
        
            String sql = "INSERT INTO reservations "
                    + "VALUES (null, "
                    + "" + customerID + ", "
                    + "'" + flightID + "', "
                    + "" + seats + ", "
                    + "" + food + ")";
        try 
        {   
            executeUpdate(sql);
        } 
        catch (SQLException ex) 
        {
            throw new RuntimeException("Something went wrong while creating your reservation",ex);
        }
        
    }

    @Override
    public void editReservation(int reservationID, int customerID, String flightID, int seats, int food) {
                
            String sql = "UPDATE reservations SET "
                    + "customer_id = " + customerID + ", "
                    + "flightid = '" + flightID + "', "
                    + "seats = " + seats + ", "
                    + "food = " + food+ " "
                    + "WHERE id = " + reservationID;
        try 
        {   
            executeUpdate(sql);
        } 
        catch (SQLException ex) 
        {
            throw new RuntimeException("Something went wrong while creating your reservation",ex);
        }
    }

    @Override
    public void deleteReservation(int reservationID) {
        
        String sql = "DELETE FROM reservations WHERE id = "+reservationID;
        
        try 
        {   
            executeUpdate(sql);
        } 
        catch (SQLException ex) 
        {
            throw new RuntimeException("Something went wrong while deleting your reservation",ex);
        }
        
    }

    @Override
    public Booking getReservation(int reservationID) {
        
        String sql = "SELECT * FROM reservations WHERE id = " + reservationID;
        //pass query to query handler -> db. REMEMBER THAT THIS DOESN'T CLOSE DB CONNECTION, CLOSING IS PARAMOUNT!
        executeQuery(sql);
        
        try {

            while (results.next()) {
                int id = results.getInt("id");
                int customerid = results.getInt("customerid");
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

}
