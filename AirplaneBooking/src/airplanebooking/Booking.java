package airplanebooking;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Andreas
 */
public class Booking {
    
    private static final ArrayList<Integer> seats = new ArrayList<>();
    public static Customer customer;
    
    private static int EconomySeats;
    private static int BusinessSeats;
    private static int FirstSeats;
    
    private static Boolean EconomyClass;
    private static Boolean BusinessClass;
    private static Boolean FirstClass;
    
    // TODO: add event for when seats are changed
    private static final ArrayList<BookingListener> listeners = new ArrayList<>();
    
    public Booking()
    {
       
    }
    
    public static void addCustomer(Customer c)
    {
        customer = c;
    }
    
    public static void reset()
    {
        seats.clear();
        EconomyClass = false;
        BusinessClass = false;
        FirstClass = false;
        EconomySeats = 2*20*4;//0;
        BusinessSeats = 2*5*2;//0;
        FirstSeats = 2*2*2;//0;
    }
    
    private static void update()
    {
        EconomyClass = false;
        BusinessClass = false;
        FirstClass = false;
    
        for (int i : seats)
        {
            if (FirstSeats > i)
            {
                FirstClass = true;
            }
            // Current seat is Business Class
            else if (FirstSeats + BusinessSeats > i)
            {
                BusinessClass = true;
            }
            // Current seat is Economy Class
            else if (FirstSeats + BusinessSeats + EconomySeats > i)
            {
                EconomyClass = true;
            }
        }
    }
    
    public static void addSeat(int seat, String c)
    {
        // TODO: event!!
        if (!seats.contains(seat))
            seats.add(seat);
        
        update();
        
        updated();
    }
    
    public static void removeSeat(Integer seat, String c)
    {
        // TODO: event!!
        if (seats.contains(seat))
            seats.remove(seat);
        
        update();
        
        updated();
    }
    
    public static ArrayList<Integer> getSeats()
    {
        Collections.sort(seats);
        return seats;
    }
    
    public static Boolean isFirstClass()
    {
        return FirstClass;
    }
    
    public static Boolean isBusinessClass()
    {
        return BusinessClass;
    }
    
    public static Boolean isEconomyClass()
    {
        return EconomyClass;
    }
    
    public static void updated() {
        // Notify everybody that may be interested.
        for (BookingListener bl : listeners)
            bl.bookingChanged();
    }
    
    public static void addListener(BookingListener listener) {
        listeners.add(listener);
    }
    
}
