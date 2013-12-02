/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airplanebooking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Alex
 */
public class DatabaseHandler implements DatabaseInterface {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    //empty on purpose

    }

    Connection con = null;
    Statement statement = null;

    @Override
    public void getConnection() {
        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://mysql.itu.dk:3306/Airplanebooking",
                    "aljon",
                    "Jegeradministratorher123");
        } catch (SQLException ex) {
            throw new RuntimeException(
                    "Could not connect to database", ex);
        }

    }

    @Override
    public void closeConnection() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException IGNORE) {
                //intentionally left empty.
            }
        }
    }

    @Override
    public void createCustomer(String firstname, String lastname, String addressStreet, int addressZip, String addressCity, String addressCountry, String email, int phonenumber) {
        //first we establish DB connection.
        getConnection();

        //establish statement handler.
        try {
            statement = con.createStatement();

            //create string (sql statement), which we'd like to pass to the statement handler.
            String sql = "INSERT INTO customers "
                    + "VALUES (null, '" + firstname + "', '" + lastname + "', '" + addressStreet + "', " + addressZip + ", '" + addressCity + "', '" + addressCountry + "', '" + email + "', " + phonenumber + ")";
            System.out.println("Customer " + firstname + " " + lastname + " created.");

        //pass statement to statement handler -> db.
            statement.executeUpdate(sql);

        } catch (SQLException e) {

            System.out.println(e);
        } finally {
            closeConnection();
        }

    }

    //Below are unimplemented methods.
    @Override
    public void editCustomer(int customerID, String firstname, String lastname, String addressStreet, int addressZip, String adressCity, String email, int phonenumber) {
    }

    @Override
    public void deleteCustomer(int customerID) {
    }

    @Override
    public void getCustomer(int customerID) {
    }

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
