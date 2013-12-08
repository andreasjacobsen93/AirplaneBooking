package airplanebooking.DB;

import java.util.ArrayList;

/**
 *
 * @author Alex
 */
public class Booking {
    
    private final Integer id;
    private final Customer customer;
    private final Flight flight;
    private final Integer food;
    private final ArrayList<Seat> seats;
    private final Integer price;
    
    
    public Booking(int id, Customer customer, Flight flight, ArrayList<Seat> seats, int food, int price)
    {
        this.id = id;
        this.customer = customer;
        this.flight = flight;
        this.seats = seats;
        this.food = food;
        this.price = price;
    }
    
    public int getID(){
        return id;
    }
    
    public int getPrice(){
        return price;
    }
    
    public int getCustomerID(){
        int customerID = customer.getID();
        return customerID;
    }
    
    public int getFlightID(){
        int flightID = flight.getID();
        return flightID;
    }
    
    public int getFood(){
        return food;
    }
    
    public ArrayList<Seat> getSeats(){
        return seats;
    }
    
    public Customer getCustomer(){
        return customer;
    }
    
    public Flight getFlight(){
        return flight;
    }
}
