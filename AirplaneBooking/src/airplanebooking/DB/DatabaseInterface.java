/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airplanebooking.DB;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author Alex
 */
public interface DatabaseInterface {
    
    /*
    //Below are all customer related DB method declarations.
    */
    
    public void createCustomer(String maritalstatus, String firstname, 
            String lastname, String addressStreet, int addressZip, 
            String addressCity, String addressCountry, String email, int phonenumber);

    public void editCustomer(int customerID, String maritalstatus,  String firstname, 
            String lastname, String addressStreet, int addressZip, String addressCity, 
            String addressCountry, String email, int phonenumber);

    public void deleteCustomer(int customerID);

    public Customer getCustomer(int customerID);
    
    public ArrayList<Customer> getCustomers(String q);
    
    public ArrayList<Customer> getCustomers(int q);
    
    public ArrayList<Customer> getCustomers(String firstName, String lastName, String Email, Integer Phone);
    
    public boolean customerExists(Customer customer);

    /*
    //Below are all reservation related DB method declarations.
    */
    
    public void createReservation(Customer currentCustomer, Flight flight, ArrayList<Seat> seats, int food, int cost);

    public void editReservation(int reservationID, int customerID, String flightID, ArrayList<Seat> seats, int food, int cost);

    public void deleteReservation(int reservationID);

    public Booking getReservation(int reservationID);
    
    public ArrayList<Booking> getCustomerReservations(int customerID);
    
    public Booking getReservation(int seatID, int flightID);
    
    public boolean seatsExist(ArrayList<Seat> seats, Flight flight);
    
    /*
    //Below are all flight related DB method declarations.
    */
    
    public void createFlight(int airplaneID, int firstCost, int businessCost, int economyCost, 
            String departurePlace, Timestamp departureTime, String arrivalPlace, Timestamp arrivalTime); 
    
   public void editFlight(int flightID, int airplaneID, int firstCost, int businessCost, int economyCost, 
           String departurePlace, Timestamp departureTime, String arrivalPlace, Timestamp arrivalTime);
    
    public void deleteFlight(int flightID);
    
    public Flight getFlight(int flightID);
    
    public ArrayList<Flight> getFlights(Boolean freeSeatsOnly);
    
    public ArrayList<Seat> getFlightBookedSeats(int flightID);
    
    //Below are all airplane related DB method declarations.
    
    public Airplane getAirplane(int airplaneID);
}
