package airplanebooking.DB;

import java.sql.Array;
import java.util.ArrayList;

/**
 *
 * @author Alex
 */
public class Booking {
    
    private final Integer id;
    private final Integer customerID;
    private final Integer flightID;
    private final Integer food;
    private final ArrayList<Seat> seats;
    private final Integer price;
    
    public Booking(int id, int customerID, int flightID, ArrayList<Seat> seats, int food, int price)
    {
        this.id = id;
        this.customerID = customerID;
        this.flightID = flightID;
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
        return customerID;
    }
    
    public int getFlightID(){
        return flightID;
    }
    
    public int getFood(){
        return food;
    }
    
    public ArrayList<Seat> getSeats(){
        return seats;
    }
}
