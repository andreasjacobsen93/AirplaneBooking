package airplanebooking.DB;

import java.util.ArrayList;

/**
 * Booking class used to store all data about a booking.
 * @author Andreas Jacobsen & Aleksandar Jonovic
 */
public class Booking 
{
    // Identification number of booking
    private final Integer id;
    
    // Customer who booked
    private final Customer customer;
    
    // Flight booked on
    private final Flight flight;
    
    // Whether customers wants lunch on-board or not
    private final Boolean food;
    
    // List of seats booked
    private final ArrayList<Seat> seats;
    
    // Booking price
    private final Integer price;
    
    /**
     * Constructor used to create booking object with all data.
     * @param id Identification number of booking.
     * @param customer Booking customer.
     * @param flight Flight booked on.
     * @param seats Seats booked.
     * @param food Lunch on-board or not.
     * @param price Total booking price.
     * @see Customer
     * @see Flight
     * @see Seat
     */
    public Booking(int id, Customer customer, Flight flight, ArrayList<Seat> seats, Boolean food, int price)
    {
        this.id = id;
        this.customer = customer;
        this.flight = flight;
        this.seats = seats;
        this.food = food;
        this.price = price;
    }
    
    /**
     * Overloaded constructor for methods which insert into the database. <br>For
     * JavaDoc please refer to:<p>
     * {@link #Booking(int, Customer, Flight, ArrayList, Boolean, int) Booking constructor - JavaDoc}
     * @param customer N/A
     * @param flight N/A
     * @param seats N/A
     * @param food N/A
     * @param price N/A
     */
    public Booking(Customer customer, Flight flight, ArrayList<Seat> seats, Boolean food, int price)
    {
        this.id = 0;
        this.customer = customer;
        this.flight = flight;
        this.seats = seats;
        this.food = food;
        this.price = price;
    }
    
    /**
     * This method gets the identification number of the booking.
     * @return Identification number as integer.
     */
    public int getID(){
        return id;
    }
    
    /**
     * This method gets the total price of the booking.
     * @return Total booking price as integer.
     */
    public int getPrice(){
        return price;
    }
    
    /**
     * This method gets the customer who booked.
     * @return Customer as customer object.
     * @see Customer
     */
    public Customer getCustomer(){
        return customer;
    }
    
    /**
     * This method gets the flight booked on.
     * @return Flight as flight object.
     * @see Flight
     */
    public Flight getFlight(){
        return flight;
    }
    
    /**
     * This method gets whether the booking is with lunch or not.
     * @return True if lunch on-board, false if not.
     */
    public Boolean getFood(){
        return food;
    }
    
    /**
     * This method gets all the seats booked.
     * @return List of seats.
     * @see Seat
     */
    public ArrayList<Seat> getSeats(){
        return seats;
    }
}
