package airplanebooking.DB;

import airplanebooking.DB.Seat;
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
    
    public Booking(int id, int customerID, int flightID, ArrayList<Seat> seats, int food)
    {
        this.id = id;
        this.customerID = customerID;
        this.flightID = flightID;
        this.seats = seats;
        this.food = food;

    }
    
    public int getID(){
        return id;
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
        
    public Object[] getBookingDataList()
    {
        Object [] list = new Array[5];
        list[0] = id;
        list[1] = customerID;
        list[2] = flightID;
        list[3] = food;
        list[4] = seats;
        return list;
    }
}
