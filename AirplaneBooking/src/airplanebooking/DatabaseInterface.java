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
    
    public ArrayList<Customer> getCustomers(String q);
    
    public ArrayList<Customer> getCustomers(int q);

    
    //Below are all reservation related DB method declarations.
    public void createReservation(int customerID, String flightID, int seats, int food);

    public void editReservation(int reservationID, int customerID, String flightID, int seats, int food);

    public void deleteReservation(int reservationID);

    public Booking getReservation(int reservationID);
    
    //Below are all flight related DB method declarations.
    
    public void createFlight(int flightID, int firstSeats, int businessSeats, int economySeats, int totalSeats, String departureTime, String arrivalTime); 
    
    public void editFlight(int flightID, int firstSeats, int businessSeats, int economySeats, int totalSeats, String departureTime, String arrivalTime);
    
    public void deleteFlight(int flightID);
    
    public void getFlight(int flightID);
    
    
}
