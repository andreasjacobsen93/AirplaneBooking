package airplanebooking;

import airplanebooking.DB.Airplane;
import airplanebooking.DB.Booking;
import airplanebooking.DB.Flight;
import airplanebooking.DB.Customer;
import airplanebooking.DB.Database;
import airplanebooking.DB.Seat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JOptionPane;

/**
 *
 * @author Andreas
 */
public class CurrentBooking {

    // Seats which are booked in the current booking
    private static ArrayList<Seat> bookedSeats;
    // Seats which are already booked and cannot be booked
    private static ArrayList<Seat> blockedSeats;
    // Seats which are currently booked but is allowed to be booked again in edit reservation
    private static ArrayList<Seat> allowedSeats;

    // The customer to book for
    private static Customer customer;
    // The flight to book on
    private static Flight flight;
    // The airplane to fly with
    private static Airplane airplane;
    
    private static int id;

    // Economy Class
    private static Boolean EconomyClass;
    private static int EconomySeats;
    private static int EseatGroups;
    private static int EseatLength;
    private static int ErowSeats;
    private static int EseatPrice;

    // Business Class
    private static Boolean BusinessClass;
    private static int BusinessSeats;
    private static int BseatGroups;
    private static int BseatLength;
    private static int BrowSeats;
    private static int BseatPrice;

    // First Class
    private static Boolean FirstClass;
    private static int FirstSeats;
    private static int FseatGroups;
    private static int FseatLength;
    private static int FrowSeats;
    private static int FseatPrice;
    
    private static Boolean lunch;
    
    private static int totalCost;
    
    // List of event subscribers
    private static final ArrayList<BookingListener> listeners = new ArrayList<>();

    public CurrentBooking() {
        customer = null;
        flight = null;
        airplane = null;
        bookedSeats = null;
        blockedSeats = null;
        allowedSeats = null;
        EconomyClass = false;
        BusinessClass = false;
        FirstClass = false;
        EconomySeats = 0;
        BusinessSeats = 0;
        FirstSeats = 0;
        EseatGroups = 0;
        EseatLength = 0;
        ErowSeats = 0;
        BseatGroups = 0;
        BseatLength = 0;
        BrowSeats = 0;
        FseatGroups = 0;
        FseatLength = 0;
        FrowSeats = 0;
        blockedSeats = null;
    }
    
    public static void saveBooking(Boolean update)
    {
        if(update) Database.db().editReservation(new Booking(id, customer, flight, bookedSeats, lunch, totalCost));
        else Database.db().createReservation(customer, flight, bookedSeats, lunch, totalCost);
        CurrentFlight.setFlight(Database.db().getFlight(flight.getID()));
        CurrentFlight.updateFlights();
        reset();
    }

    public static void addCustomer(Customer c)
    {
        customer = c;
    }
    
    public static Customer getCustomer()
    {
        return customer;
    }

    public static void addFlight(Flight f) 
    {
        flight = f;
        
        EseatPrice = f.getEconomyClassSeatCost();
        BseatPrice = f.getBusinessClassSeatCost();
        FseatPrice = f.getFirstClassSeatCost();

        airplane = flight.getAirplane();
        
        String[] ec = airplane.getECSeatFormation().split(":");
        String[] bc = airplane.getBCSeatFormation().split(":");
        String[] fc = airplane.getFCSeatFormation().split(":");

        EconomySeats = airplane.getEconomySeats();
        BusinessSeats = airplane.getBusinessSeats();
        FirstSeats = airplane.getFirstSeats();

        // Economy Class
        EseatGroups = Integer.parseInt(ec[1]);
        EseatLength = Integer.parseInt(ec[0]);
        ErowSeats = Integer.parseInt(ec[2]);
        
        // Business Class
        BseatGroups = Integer.parseInt(bc[1]);
        BseatLength = Integer.parseInt(bc[0]);
        BrowSeats = Integer.parseInt(bc[2]);
        
        // First Class
        FseatGroups = Integer.parseInt(fc[1]);
        FseatLength = Integer.parseInt(fc[0]);
        FrowSeats = Integer.parseInt(fc[2]);
        
        blockedSeats = Database.db().getFlightBookedSeats(flight.getID());
        bookedSeats = new ArrayList<>();
        
        lunch = true;
        
        update();
    }
    
    public static void addBooking(Booking b)
    {
        addCustomer(b.getCustomer());
        addFlight(b.getFlight());
        setLunch(b.getFood());
        totalCost = b.getPrice();
        addSeats(b.getSeats());
        allowedSeats = b.getSeats();
    }
    
    public static void clearBookedSeats()
    {
        bookedSeats.clear();
        update();
    }
    
    public static void setLunch(Boolean state)
    {
        lunch = state;
        update();
    }

    public static void reset() 
    {
        customer = null;
        flight = null;
        airplane = null;
        bookedSeats = new ArrayList<>();
        blockedSeats = new ArrayList<>();
        allowedSeats = new ArrayList<>();
        EconomyClass = false;
        BusinessClass = false;
        FirstClass = false;
        EconomySeats = 0;
        BusinessSeats = 0;
        FirstSeats = 0;
        EseatGroups = 0;
        EseatLength = 0;
        ErowSeats = 0;
        BseatGroups = 0;
        BseatLength = 0;
        BrowSeats = 0;
        FseatGroups = 0;
        FseatLength = 0;
        FrowSeats = 0;
        blockedSeats = null;
    }

    public static void update() 
    {
        EconomyClass = false;
        BusinessClass = false;
        FirstClass = false;
        
        totalCost = 0;

        for (Seat s : bookedSeats) {
            // Current seat is First Class
            if (FirstSeats > s.getSeatID()) {
                FirstClass = true;
                totalCost += FseatPrice;
                if (lunch) totalCost += 30;
            } 
            // Current seat is Business Class
            else if (FirstSeats + BusinessSeats > s.getSeatID()) {
                BusinessClass = true;
                totalCost += BseatPrice;
                if (lunch) totalCost += 20;
            } 
            // Current seat is Economy Class
            else {
                EconomyClass = true;
                totalCost += EseatPrice;
                if (lunch) totalCost += 10;
            }
        }

        updated();
    }

    public static void addSeat(Seat seat) 
    {
        // Add seat to booking
        Boolean isUnique = true;
        for (int i = 0; i < bookedSeats.size(); i++)
        {
            if (bookedSeats.get(i).getSeatID() == seat.getSeatID())
                isUnique = false;
        }
        
        if (isLastSeat()) seat.setFinalSeat();
        
        if (isUnique) bookedSeats.add(seat);

        // Update
        update();
    }
    
    private static Boolean isLastSeat()
    {
        if (blockedSeats.size() + bookedSeats.size() + 1 == FirstSeats + BusinessSeats + EconomySeats) return true;
        return false;
    }

    public static void removeSeat(int seat) 
    {
        // Remove seat from booking
        for (int i = 0; i < bookedSeats.size(); i++)
        {
            if (bookedSeats.get(i).getSeatID() == seat)
                bookedSeats.remove(i);
        }

        // Update
        update();
    }
    
    public static ArrayList<Seat> getBlockedSeats()
    {
        return blockedSeats;
    }

    public static ArrayList<Seat> getBookedSeats() 
    {
        // Sorter alle kombinationerne efter højeste id nummer
        Collections.sort(bookedSeats, new Comparator<Seat>() {
            @Override
            public int compare(Seat o1, Seat o2) {
                return o1.getSeatID() - o2.getSeatID();
            }
        });
        return bookedSeats;
    }
    
    public static ArrayList<Seat> getAllowedSeats()
    {
        return allowedSeats;
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
    
    public static int getTotalCost()
    {
        return totalCost;
    }

    public static void updated()
    {
        // Notify everybody that may be interested.
        for (BookingListener bl : listeners) {
            bl.bookingChanged();
        }
    }

    public static void addListener(BookingListener listener)
    {
        listeners.add(listener);
    }

    public static void findBestSeats(int amount)
    {
        bookedSeats.clear();
        
        // ECONOMY CLASS IS THE ONLY ONE WORKING!!
        findSeatsAlgorithm(amount);
    }

    public static void addSeats(ArrayList<Seat> list) {
        for (Seat s : list) {
            addSeat(s);
        }
    }
    
    private static Boolean blockedSeatsContains(Integer i)
    {
        for (int a = 0; a < blockedSeats.size(); a++)
        {
            if (blockedSeats.get(a).getSeatID() == i)
                return true;
        }
        return false;
    }

    private static void findSeatsAlgorithm(int amount) {

        // Først laver vi et int 2d array som holder alle sæde numrene
        int[][] seatsArray = new int[EseatGroups * ErowSeats][EseatLength];
        int s = FirstSeats + BusinessSeats; // Antallet af sæder som ikke er med i economy class
        for (int j = 0; j < EseatLength; j++) {
            for (int i = 0; i < EseatGroups * ErowSeats; i++) {
                s++;
                seatsArray[i][j] = s;
            }
        }

        // Vi laver så en liste over alle de mulige kombinationer vi kan få
        ArrayList<bestSeats> list = new ArrayList<>();

        // Så kører vi alle sæderne i gennem
        for (int i = 0; i < EseatGroups * ErowSeats; i++) {
            for (int j = 0; j < EseatLength; j++) {
                
                // Create new combination holder
                bestSeats bs = new bestSeats();
                
                if(blockedSeatsContains(seatsArray[i][j])) continue;

                bs.score++;

                bs.tempSeats.add(i);
                bs.seats.add(new Seat(seatsArray[i][j]));

                vAlgorithmSearch(i, j, bs, amount, seatsArray);
                
                hAlgorithmSearch(j, "+", bs, amount, seatsArray);
                hAlgorithmSearch(j, "-", bs, amount, seatsArray);

                if (bs.seats.size() == amount) {
                    bs.score += 10;
                }
                else
                {
                    bs.score -= (amount - bs.seats.size())*2;
                }

                bs.tempSeats.clear();
                list.add(bs);

            }
        }

        // Sorter alle kombinationerne efter højeste score
        Collections.sort(list, new Comparator<bestSeats>() {
            @Override
            public int compare(bestSeats o1, bestSeats o2) {
                return o2.score - o1.score;
            }
        });
        
        addSeats(list.get(0).seats);

        if (list.get(0).seats.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nothing found!");
        }
        if (list.get(0).seats.size() != amount) {
            JOptionPane.showMessageDialog(null, list.get(0).seats.size() + " seats were found!");
        }

    }
    
    private static void vAlgorithmSearch(int i, int j, bestSeats bs, int amount, int[][] seatsArray)
    {
        for (int ii = i + 1; ii < EseatGroups * ErowSeats; ii++) {
            if (bs.seats.size() >= amount) {
                break;
            }
            
            if(blockedSeatsContains(seatsArray[ii][j])){
                bs.score--;
                break;
            }

            bs.score+=2;
            bs.tempSeats.add(ii);
            bs.seats.add(new Seat(seatsArray[ii][j]));
        }
    }
    
    private static void hAlgorithmSearch(int j, String a, bestSeats bs, int amount, int[][] seatsArray)
    {
        ArrayList<Integer> tempSeatsFirst = bs.tempSeats;
        ArrayList<Integer> tempSeatsSecond = new ArrayList<>();
        
        switch(a)
        {
            case "+":
                    for (int jj = j+1; jj < EseatLength; jj++) {
                        tempSeatsFirst = hAlgorithmSearchDo(jj, bs, amount, seatsArray, tempSeatsFirst, tempSeatsSecond);
                        
                        if (tempSeatsFirst.isEmpty()) {
                            break;
                        }
                        
                        if (bs.seats.size() >= amount) {
                            break;
                        }
                        
                        tempSeatsSecond.clear();
                    }
                break;
            case "-":
                    for (int jj = j-1; jj >= 0; jj--) {
                        
                        if (bs.seats.size() >= amount) {
                            break;
                        }
                        
                        tempSeatsFirst = hAlgorithmSearchDo(jj, bs, amount, seatsArray, tempSeatsFirst, tempSeatsSecond);
                        
                        if (tempSeatsFirst.isEmpty()) {
                            break;
                        }
                        
                        tempSeatsSecond.clear();
                    }
                break;
        }
    }
    
    private static ArrayList<Integer> hAlgorithmSearchDo(int jj, bestSeats bs, int amount, int[][] seatsArray, ArrayList<Integer> tempSeatsFirst, ArrayList<Integer> tempSeatsSecond)
    {
        for (int ii : tempSeatsFirst) {

            if (bs.seats.size() >= amount) {
                break;
            }
            
            if(blockedSeatsContains(seatsArray[ii][jj])) continue;
                
            bs.score++;

            tempSeatsSecond.add(ii);
            bs.seats.add(new Seat(seatsArray[ii][jj]));
        }
        return tempSeatsSecond;
    }
}
