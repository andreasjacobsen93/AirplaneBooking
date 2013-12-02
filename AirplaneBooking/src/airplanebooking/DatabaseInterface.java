/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airplanebooking;

/**
 *
 * @author Alex
 */
public interface DatabaseInterface {

    //Below are all DB connection related method declarations.
    public void getConnection();

    public void closeConnection();

    //Below are all customer related DB method declarations.
    public void createCustomer(String firstname, String lastname, String addressStreet, int addressZip, String adressCity, String addressCountry, String email, int phonenumber);

    public void editCustomer(int customerID, String firstname, String lastname, String addressStreet, int addressZip, String adressCity, String email, int phonenumber);

    public void deleteCustomer(int customerID);

    public void getCustomer(int customerID);

    //Below are all reservation related DB method declarations.
    public void createReservation(int customerID, String flightID, int seats);

    public void editReservation(int customerID, String flightID, int seats);

    public void deleteReservation(int reservationID);

    public void getReservation(int reservationID);
}
