package airplanebooking;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Andreas
 */
public class CurrentBooking {
    
    private static final ArrayList<Integer> seats = new ArrayList<>();
    private static ArrayList<Integer> bookedSeats = new ArrayList<>();
    private static Customer customer;
    private static int flight;
    
    // Economy Class
    private static Boolean EconomyClass;
    private static int EconomySeats;
    private static int EseatGroups;
    private static int EseatLength;
    private static int ErowSeats;
    
    // Business Class
    private static Boolean BusinessClass;
    private static int BusinessSeats;
    private static int BseatGroups;
    private static int BseatLength;
    private static int BrowSeats;
    
    // First Class
    private static Boolean FirstClass;
    private static int FirstSeats;
    private static int FseatGroups;
    private static int FseatLength;
    private static int FrowSeats;
    
    // TODO: add event for when seats are changed
    private static final ArrayList<BookingListener> listeners = new ArrayList<>();
    
    public CurrentBooking()
    {
        
    }
    
    public static void addCustomer(Customer c)
    {
        customer = c;
    }
    
    public static void reset()
    {
        seats.clear();
        bookedSeats.clear();
        EconomyClass = false;
        BusinessClass = false;
        FirstClass = false;
        EconomySeats = 2*20*4;//0;
        BusinessSeats = 2*5*2;//0;
        FirstSeats = 2*2*2;//0;
        // Economy Class
        EseatGroups = 2;
        EseatLength = 20;
        ErowSeats = 4;
        
        // Business Class
        BseatGroups = 2;
        BseatLength = 5;
        BrowSeats = 2;
        
        // First Class
        FseatGroups = 2;
        FseatLength = 2;
        FrowSeats = 2;
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
    
    public static void addSeat(int seat)
    {
        // TODO: event!!
        if (!seats.contains(seat))
            seats.add(seat);
        
        update();
        
        updated();
    }
    
    public static void removeSeat(Integer seat)
    {
        // TODO: event!!
        if (seats.contains(seat))
            seats.remove(seat);
        
        update();
        
        updated();
    }
    
    public static void setBookedSeats(ArrayList<Integer> list)
    {
        bookedSeats = list;
    }
    
    public static void bookedSeat(Integer seat)
    {
        bookedSeats.add(seat);
    }
    
    public static Boolean isBooked(Integer seat)
    {
        return seats.contains(seat);
    }
    
    public static Boolean isBlocked(Integer seat)
    {
        if(bookedSeats.contains(seat)) {
            System.out.println("return true");
            return true;
        }
        System.out.println("return false");
        return false;
    }
    
    public static ArrayList<Integer> getSeats()
    {
        Collections.sort(seats);
        return seats;
    }
    
    public static ArrayList<Integer> getBookedSeats()
    {
        Collections.sort(bookedSeats);
        return bookedSeats;
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
    
    public static int economySeatGroups()
    {
        return EseatGroups;
    }
    
    public static int economySeatLength()
    {
        return EseatLength;
    }
    
    public static int economyRowSeats()
    {
        return ErowSeats;
    }
    
    public static int businessSeatGroups()
    {
        return BseatGroups;
    }
    
    public static int businessSeatLength()
    {
        return BseatLength;
    }
    
    public static int businessRowSeats()
    {
        return BrowSeats;
    }
    
    public static int firstSeatGroups()
    {
        return FseatGroups;
    }
    
    public static int firstSeatLength()
    {
        return FseatLength;
    }
    
    public static int firstRowSeats()
    {
        return FrowSeats;
    }
    
    public static void updated() {
        // Notify everybody that may be interested.
        for (BookingListener bl : listeners)
            bl.bookingChanged();
    }
    
    public static void addListener(BookingListener listener) {
        listeners.add(listener);
    }
    
    public static void findBestSeats(int amount, String travelClass)
    {
        seats.clear();
        updated();
        
        ArrayList<Integer> list = new ArrayList<>();
        
        switch(travelClass)
        {
            case "Economy Class":
                
                if (amount <= ErowSeats)
                {
                    int s = FirstSeats + BusinessSeats;
                    for (int i = 0; i < EseatGroups * EseatLength; i++)
                    {
                        int sp = 0;
                        int seatsInRow = 0;
                        for (int j = 0; j < ErowSeats; j++)
                        {
                            s++;
                            
                            
                            if (!bookedSeats.contains(s)) {
                                sp++;
                                seatsInRow++;
                                list.add(s);
                            }
                            else seatsInRow = 0;
                            
                            if (sp == amount && seatsInRow == amount) {
                                addSeats(list);
                                System.out.println("found in first try");
                                return;
                            }
                        }
                        list.clear();
                    }
                    
                    s = FirstSeats + BusinessSeats;
                    for (int i = 0; i < EseatGroups * EseatLength; i++)
                    {
                        int sp = 0;
                        for (int j = 0; j < ErowSeats; j++)
                        {
                            s++;
                            
                            if (!bookedSeats.contains(s)) {
                                sp++;
                                list.add(s);
                            }
                            
                            if (sp == amount) {
                                addSeats(list);
                                System.out.println("found in second try");
                                return;
                            }
                        }
                        list.clear();
                    }
                }
                
                if (amount <= ErowSeats * EseatGroups){
                    int s = FirstSeats + BusinessSeats;
                    for (int i = 0; i < EseatLength; i++)
                    {
                        int sp = 0;
                        int seatsInRow = 0;
                        for (int j = 0; j < EseatGroups * ErowSeats; j++)
                        {
                            s++;

                            if (!bookedSeats.contains(s)) {
                                sp++;
                                seatsInRow++;
                                list.add(s);
                            }
                            else seatsInRow = 0;

                            if (sp == amount && seatsInRow == amount) {
                                addSeats(list);
                                System.out.println("found in third try");
                                return;
                            }
                        }
                        list.clear();
                    }
                }
                    
                break;
            case "Business Class":
                break;
            case "First Class":
                break;
        }
        
        System.out.println("nothing found!");
    }
    
    public static void addSeats(ArrayList<Integer> list)
    {
        for (int s : list)
        {
            addSeat(s);
        }
    }
}
