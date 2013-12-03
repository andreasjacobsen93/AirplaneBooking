/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airplanebooking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/**
 *
 * @author Alex
 */
public class DatabaseHandler implements DatabaseInterface {
    Connection con = null;
    Statement statement = null;
    ResultSet results = null;
    Customer customer = null;
    ArrayList<Customer> customers = new ArrayList<>();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    //empty on purpose

    }
 

      
    private void getConnection() {
        try {
            //Connect with data described below
            con = DriverManager.getConnection(
                    "jdbc:mysql://mysql.itu.dk:3306/Airplanebooking",
                    "aljon",
                    "Jegeradministratorher123");
          //catch errors
        }
        catch (SQLException ex) {
            //throw error if present.
            throw new RuntimeException(
                    "Could not connect to database", ex);
        }

    }

    private void closeConnection() {
        //check to see if connection is established.
        if (con != null) {
            try {
                //close connection
                con.close();
              //catch errors.
            } catch (SQLException IGNORE) {
                //intentionally left empty.
            }
        }
    }

    private void executeUpdate(String sql){
        //first we establish DB connection.
        getConnection();

        //establish statement handler.
        try {
            statement = con.createStatement();
             //pass statement to statement handler -> db.
            statement.executeUpdate(sql);

        } catch (SQLException e) {

            System.out.println(e);
        } finally {
            //remember to close connection
            closeConnection();
        }

    }
    
    private ResultSet executeQuery(String sql){
        //first we establish DB connection.
        getConnection();

        //establish statement handler.
        try {
            statement = con.createStatement();
            //pass statement to statement handler -> db.
            results = statement.executeQuery(sql);
            
            //Debug code ----       while(results.next()){System.out.println(results.getString("firstname"));}
            
            
        } catch (SQLException e) {

            System.out.println(e);
        } 
        return results;
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
                    + "addresszip = "+ addressZip + ", "
                    + "addresscity = '" + addressCity + "', "
                    + "addresscountry = '"+ addressCountry + "', "
                    + "email = '"+ email + "', "
                    + "phonenumber = "+ phonenumber 
                    + "WHERE id = "+customerID;
            
            System.out.println("Customer " + firstname + " " + lastname + " edited.");
            
            //execute statement
            executeUpdate(sql);
    }

    @Override
    public void deleteCustomer(int customerID) {
                //create string (sql statement), which we'd like to pass to the statement handler.
            String sql = "DELETE FROM customers WHERE id = "+customerID;
            
            System.out.println("Customer with customerID:"+customerID+" deleted.");
            
            //execute statement
            executeUpdate(sql);
    }
    
    @Override
    public Customer getCustomer(int customerID) {
         String sql = "SELECT * FROM customers WHERE id = "+customerID;
            //pass query to query handler -> db. REMEMBER THAT THIS DOESN'T CLOSE DB CONNECTION, CLOSING IS PARAMOUNT!
          executeQuery(sql);
          
          try {
              while (results.next())
              {
              String maritalstatus = results.getString("maritalstatus");
              String firstname = results.getString("firstname");
              String lastname = results.getString("lastname");
              String address = results.getString("addressstreet") + " " + results.getInt("addresszip") + " " + results.getString("addresscity") + " " + results.getString("addresscountry");
              int phonenumber = results.getInt("phonenumber");
              String email = results.getString("email");
             
              customer = new Customer(maritalstatus,firstname,lastname,address,phonenumber,email);
              }
              
              return customer;
    } catch (SQLException ex) {
     throw new RuntimeException("Something went wrong while getting the customer:",ex);
            
    } finally {

            //remember to close connection (since this isn't a statement, but a quesry, executeQuery() cannot close connection before we extract the data.
            closeConnection();
              
          }
    }
    
 
 /*   public ArrayList getCustomers() {
        
    return customers;
    }*/
    
    @Override
    public ArrayList<Customer> getCustomers(int q) {
    
        String sql = "SELECT * FROM customers WHERE addresszip ="+q+" OR phonenumber ="+q;
        
        executeQuery(sql);
        
        try {
            
            results.beforeFirst();
            
             while (results.next())
              {
              String maritalstatus = results.getString("maritalstatus");
              String firstname = results.getString("firstname");
              String lastname = results.getString("lastname");
              String address = results.getString("addressstreet") + " " + results.getInt("addresszip") + " " + results.getString("addresscity") + " " + results.getString("addresscountry");
              int phonenumber = results.getInt("phonenumber");
              String email = results.getString("email");
              customer = new Customer(maritalstatus,firstname,lastname,address,phonenumber,email);
              customers.add(customer);
              }
             return customers;
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong in getting your customers",ex);
        } finally {
         closeConnection();  
        }
    }

    @Override
    public ArrayList<Customer> getCustomers(String q) {
        String sql = "SELECT * FROM customers WHERE firstname =\""+q+"\" OR lastname =\""+q+"\" OR addressstreet =\""+q+"\" OR email =\""+q+"\"";
        
        executeQuery(sql);
        
        try {
            results.beforeFirst();
             while (results.next())
              {
              String maritalstatus = results.getString("maritalstatus");
              String firstname = results.getString("firstname");
              String lastname = results.getString("lastname");
              String address = results.getString("addressstreet") + " " + results.getInt("addresszip") + " " + results.getString("addresscity") + " " + results.getString("addresscountry");
              int phonenumber = results.getInt("phonenumber");
              String email = results.getString("email");
              customer = new Customer(maritalstatus,firstname,lastname,address,phonenumber,email);
              customers.add(customer);
              }
             return customers;
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong in getting your customers",ex);
        } finally {
         closeConnection();  
        }
    
    }


   //Below are unimplemented methods.
    @Override
    public void createReservation(int customerID, String flightID, int seats) {
    }
    

    @Override
    public void editReservation(int customerID, String flightID, int seats) {
    }

    @Override
    public void deleteReservation(int reservationID) {
    }

    @Override
    public void getReservation(int reservationID) {
    }

}
