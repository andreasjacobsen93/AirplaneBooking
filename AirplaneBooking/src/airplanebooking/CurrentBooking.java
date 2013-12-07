package airplanebooking;

import airplanebooking.DB.Flight;
import airplanebooking.DB.Customer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author Andreas
 */
public class CurrentBooking {

    // Seats which are booked in the current booking
    private static ArrayList<Integer> bookedSeats;
    // Seats which are already booked and cannot be booked
    private static ArrayList<Integer> blockedSeats;

    // The customer to book for
    private static Customer customer;
    // The flight to book on
    private static Flight flight;

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
    
    // List of event subscribers
    private static final ArrayList<BookingListener> listeners = new ArrayList<>();

    public CurrentBooking() {

    }

    public static void addCustomer(Customer c) {
        customer = c;
    }
    
    public static Customer getCustomer()
    {
        return customer;
    }

    public static void addFlight(Flight f) {
        flight = f;
    }
    
    public static void clearBookedSeats()
    {
        bookedSeats.clear();
        update();
    }

    public static void reset() {
        bookedSeats = new ArrayList<>();//null;
        blockedSeats = null;
        EconomyClass = false;
        BusinessClass = false;
        FirstClass = false;
        EconomySeats = 2 * 20 * 4;//0;
        BusinessSeats = 2 * 5 * 2;//0;
        FirstSeats = 2 * 2 * 2;//0;
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

        blockedSeats = new ArrayList<>();
        for (int i = 0; i < FirstSeats + BusinessSeats + EconomySeats; i++) {
            Random rand = new Random();
            int r = rand.nextInt(2);
            if (r < 1) {
                blockedSeats.add(i + 1);
            }
        }
        update();
    }

    public static void update() {
        EconomyClass = false;
        BusinessClass = false;
        FirstClass = false;

        for (int i : bookedSeats) {
            // Current seat is First Class
            if (FirstSeats > i) {
                FirstClass = true;
            } 
            // Current seat is Business Class
            else if (FirstSeats + BusinessSeats > i) {
                BusinessClass = true;
            } 
            // Current seat is Economy Class
            else {
                EconomyClass = true;
            }
        }

        updated();
    }

    public static void addSeat(int seat) {
        // Add seat to booking
        if (!bookedSeats.contains(seat)) {
            bookedSeats.add(seat);
        }

        // Update
        update();
    }

    public static void removeSeat(Integer seat) {
        // Remove seat from booking
        if (bookedSeats.contains(seat)) {
            bookedSeats.remove(seat);
        }

        // Update
        update();
    }

    public static Boolean isBooked(Integer seat) {
        return bookedSeats.contains(seat);
    }

    public static Boolean isBlocked(Integer seat) {
        return blockedSeats.contains(seat);
    }

    public static ArrayList<Integer> getSeats() {
        Collections.sort(bookedSeats);
        return bookedSeats;
    }

    public static ArrayList<Integer> getBookedSeats() {
        Collections.sort(blockedSeats);
        return blockedSeats;
    }

    public static Boolean isFirstClass() {
        return FirstClass;
    }

    public static Boolean isBusinessClass() {
        return BusinessClass;
    }

    public static Boolean isEconomyClass() {
        return EconomyClass;
    }

    public static int economySeatGroups() {
        return EseatGroups;
    }

    public static int economySeatLength() {
        return EseatLength;
    }

    public static int economyRowSeats() {
        return ErowSeats;
    }

    public static int businessSeatGroups() {
        return BseatGroups;
    }

    public static int businessSeatLength() {
        return BseatLength;
    }

    public static int businessRowSeats() {
        return BrowSeats;
    }

    public static int firstSeatGroups() {
        return FseatGroups;
    }

    public static int firstSeatLength() {
        return FseatLength;
    }

    public static int firstRowSeats() {
        return FrowSeats;
    }

    public static void updated() {
        // Notify everybody that may be interested.
        for (BookingListener bl : listeners) {
            bl.bookingChanged();
        }
    }

    public static void addListener(BookingListener listener) {
        listeners.add(listener);
    }

    public static void findBestSeats(int amount) {
        bookedSeats.clear();
        //update();
        
        // ECONOMY CLASS IS THE ONLY ONE WORKING!!
        findSeatsAlgorithm(amount);
    }

    public static void addSeats(ArrayList<Integer> list) {
        for (int s : list) {
            addSeat(s);
        }
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

                if (blockedSeats.contains(seatsArray[i][j])) {
                    continue;
                }

                bs.score++;

                bs.tempSeats.add(i);
                bs.seats.add(seatsArray[i][j]);

                vAlgorithmSearch(i, j, bs, amount, seatsArray);
                
                hAlgorithmSearch(j+1, "+", bs, amount, seatsArray);
                hAlgorithmSearch(j-1, "-", bs, amount, seatsArray);

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

            if (blockedSeats.contains(seatsArray[ii][j])) {
                //System.out.println("blocked 2");
                //stopped = true;
                bs.score--;
                break;
            }

            bs.score++;
            bs.tempSeats.add(ii); //System.out.println("2. saved " + ii + " and seat " + seatsArray[ii][j]);
            bs.seats.add(seatsArray[ii][j]);
        }
    }
    
    private static void hAlgorithmSearch(int j, String a, bestSeats bs, int amount, int[][] seatsArray) 
    {
        ArrayList<Integer> tempSeatsFirst = bs.tempSeats;
        ArrayList<Integer> tempSeatsSecond = new ArrayList<>();
        
        switch(a)
        {
            case "+":
                    for (int jj = j; jj < EseatLength; j++) {
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
                    for (int jj = j; jj >= 0; jj--) {
                        
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
            //System.out.println(jj + ":" + ii);

            if (bs.seats.size() >= amount) {
                break;
            }

            if (!blockedSeats.contains(seatsArray[ii][jj])) {
                bs.score++;

                tempSeatsSecond.add(ii);
                //System.out.println("3. saved " + ii + " and seat " + seatsArray[ii][jj]);
                bs.seats.add(seatsArray[ii][jj]);
            } else {
                bs.score--;
            }
        }
        return tempSeatsSecond;
    }
}
