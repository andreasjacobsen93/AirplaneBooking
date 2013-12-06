package airplanebooking;

import java.sql.Array;
import java.util.ArrayList;

/**
 *
 * @author Alex
 */
public class Booking {
    
    private final Integer id;
    private final Integer customerID;
    private final String flightID;
    private final Integer food;
    private final ArrayList<Seat> seats;
    
    
    public Booking(int id, int customerID, String flightID, ArrayList<Seat> seats, int food)
    {
        this.id = id;
        this.customerID = customerID;
        this.flightID = flightID;
        this.seats = new ArrayList();
        this.food = food;

    }
    
    public Booking(int customerID)
    {
        // database get reservation from database
        this.id = 0;
        this.customerID = 0;
        this.flightID = "";
        this.food = 0;
        this.seats = new ArrayList();
    }
    
    public int getID(){
        return id;
    }
    
    public int getCustomerID(){
        return customerID;
    }
    
    public String getFlightID(){
        return flightID;
    }
    
    public int getFood(){
        return food;
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
