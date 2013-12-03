/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airplanebooking;

import java.util.ArrayList;

/**
 *
 * @author Alex
 */
public interface DatabaseInterface {

    //Below are all customer related DB method declarations.
    public void createCustomer(String maritalstatus, String firstname, String lastname, String addressStreet, int addressZip, String addressCity, String addressCountry, String email, int phonenumber);

    public void editCustomer(int customerID, String maritalstatus,  String firstname, String lastname, String addressStreet, int addressZip, String addressCity, String addressCountry, String email, int phonenumber);

    public void deleteCustomer(int customerID);

    public Customer getCustomer(int customerID);
    
    public ArrayList getCustomers(String q);

    //Below are all reservation related DB method declarations.
    public void createReservation(int customerID, String flightID, int seats);

    public void editReservation(int customerID, String flightID, int seats);

    public void deleteReservation(int reservationID);

    public void getReservation(int reservationID);
}
