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
    }
    
    public static void addSeat(int seat)
    {
        // TODO: event!!
        if (!seats.contains(seat))
            seats.add(seat);
        
        updated();
    }
    
    public static void removeSeat(Integer seat)
    {
        // TODO: event!!
        //if (seats.contains(seat))
            seats.remove(seat);
        
        updated();
    }
    
    public static ArrayList<Integer> getSeats()
    {
        Collections.sort(seats);
        return seats;
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
