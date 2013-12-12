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
 * There can only be one of this class.
 * Used to inform all listening classes of the current booking,
 * to create and update bookings.
 * @author Andreas Jacobsen
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
    
    // The id of the booking if it is already existing
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
    
    // Lunch on-board or not
    private static Boolean lunch;
    
    // The total cost of the booking
    private static int totalCost;
    
    // List of event subscribers
    private static final ArrayList<BookingListener> listeners = new ArrayList<>();
    
    /**
     * This method saves the current booking,
     * as either a new reservation or an update
     * of an existing one.
     * @param update Whether to update an existing reservation or not.
     */
    public static void saveBooking(Boolean update)
    {
        if(update) Database.db().editReservation(new Booking(id, customer, flight, bookedSeats, lunch, totalCost));
        else Database.db().createReservation(customer, flight, bookedSeats, lunch, totalCost);
        CurrentFlight.setFlight(Database.db().getFlight(flight.getID()));
        CurrentFlight.updateFlights();
        reset();
    }

    /**
     * This method adds a customer to current booking. 
     * @param c Customer object with data.
     * @see Customer
     */
    public static void addCustomer(Customer c)
    {
        customer = c;
    }
    
    /**
     * This method returns the Customer object
     * currently related to the current booking.
     * @return Customer object with data.
     * @see Customer
     */
    public static Customer getCustomer()
    {
        return customer;
    }

    /**
     * This method adds a flight to the current booking.
     * @param f Flight object with data
     * @see Flight
     */
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
    
    /**
     * This method adds an existing booking to the current booking.
     * @param b Booking object with data
     * @see Booking
     */
    public static void addBooking(Booking b)
    {
        addCustomer(b.getCustomer());
        addFlight(b.getFlight());
        setLunch(b.getFood());
        id = b.getID();
        totalCost = b.getPrice();
        addSeats(b.getSeats());
        allowedSeats = b.getSeats();
    }
    
    /**
     * This method removes all seats from
     * the bookedSeats array list.
     */
    public static void clearBookedSeats()
    {
        bookedSeats.clear();
        update();
    }
    
    /**
     * This method sets whether the customer
     * wants lunch on-board or not.
     * @param state True or false
     */
    public static void setLunch(Boolean state)
    {
        lunch = state;
        update();
    }

    /**
     * This method resets the whole current booking,
     * with standard values for all fields.
     */
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

    /**
     * This method update boolean fields
     * for whether the three classes are
     * representet in the booking or not.
     * Also sends an update-event to BookingListeners.
     */
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

    /**
     * This method adds a seat to the current booking.
     * @param seat Seat object with seat number.
     * @see Seat
     */
    public static void addSeat(Seat seat) 
    {
        // Add seat to booking
        Boolean isUnique = true;
        
        // Check if seat is already in the list
        for (int i = 0; i < bookedSeats.size(); i++)
        {
            if (bookedSeats.get(i).getSeatID() == seat.getSeatID())
                isUnique = false;
        }
        
        // If seat is the last free seat on flight, mark it as last seat
        if (isLastSeat()) seat.setFinalSeat();
        
        // If seat is not in the list already add it
        if (isUnique) bookedSeats.add(seat);

        // Update
        update();
    }
    
    /**
     * This method checks whether next seat is last free seat or not.
     * @return True if seat is last, false if not.
     */
    private static Boolean isLastSeat()
    {
        return blockedSeats.size() + bookedSeats.size() + 1 == FirstSeats + BusinessSeats + EconomySeats;
    }

    /**
     * This method removes a seat from the list of the currently booked seats.
     * @param seat Seat number as integer for seat to remove.
     */
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
    
    /**
     * This method gets the list of blocked seats.
     * @return Array list of blocked seats.
     * @see Seat
     */
    public static ArrayList<Seat> getBlockedSeats()
    {
        return blockedSeats;
    }

    /**
     * This method gets the list of booked seats.
     * @return Array list of currently booked seats.
     */
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
    
    /**
     * This method gets the list of allowed seats,
     * which are already booked but can be booked again
     * because the booking is being edited.
     * @return Array list of allowed seats.
     */
    public static ArrayList<Seat> getAllowedSeats()
    {
        return allowedSeats;
    }

    /**
     * This method gets if current booking is on first class.
     * @return True if first class, false if not.
     */
    public static Boolean isFirstClass() 
    {
        return FirstClass;
    }

    /**
     * This method gets if current booking is on business class.
     * @return True if business class, false if not.
     */
    public static Boolean isBusinessClass() 
    {
        return BusinessClass;
    }

    /**
     * This method gets if current booking is on economy class.
     * @return True if economy class, false if not.
     */
    public static Boolean isEconomyClass() 
    {
        return EconomyClass;
    }

    /**
     * This method gets the seat groups on economy class
     * @return Seat groups on economy class as integer.
     */
    public static int economySeatGroups() 
    {
        return EseatGroups;
    }

    /**
     * This method gets the vertical number of seats on economy class
     * @return Seat groups on economy class as integer.
     */
    public static int economySeatLength() 
    {
        return EseatLength;
    }

    /**
     * This method gets the horizontal number of seats in a group on economy class
     * @return Seat groups on economy class as integer.
     */
    public static int economyRowSeats() 
    {
        return ErowSeats;
    }

    /**
     * This method gets the seat groups on business class
     * @return Seat groups on business class as integer.
     */
    public static int businessSeatGroups() 
    {
        return BseatGroups;
    }

    /**
     * This method gets the vertical number of seats on business class
     * @return Seat groups on business class as integer.
     */
    public static int businessSeatLength() 
    {
        return BseatLength;
    }

    /**
     * This method gets the horizontal number of seats in a group on business class
     * @return Seat groups on business class as integer.
     */
    public static int businessRowSeats() 
    {
        return BrowSeats;
    }

    /**
     * This method gets the seat groups on first class
     * @return Seat groups on first class as integer.
     */
    public static int firstSeatGroups() 
    {
        return FseatGroups;
    }

    /**
     * This method gets the vertical number of seats on first class
     * @return Seat groups on first class as integer.
     */
    public static int firstSeatLength() 
    {
        return FseatLength;
    }

    /**
     * This method gets the horizontal number of seats in a group on first class
     * @return Seat groups on first class as integer.
     */
    public static int firstRowSeats()
    {
        return FrowSeats;
    }
    
    /**
     * This method gets the total cost of the current booking.
     * @return Total booking cost as integer.
     */
    public static int getTotalCost()
    {
        return totalCost;
    }

    /**
     * This method send an update to all listeners.
     */
    public static void updated()
    {
        // Notify everybody that may be interested.
        for (BookingListener bl : listeners) {
            bl.bookingChanged();
        }
    }

    /**
     * This method adds a new listener to the list.
     * @param listener Listener as BookingListener.
     * @see BookingListener
     */
    public static void addListener(BookingListener listener)
    {
        listeners.add(listener);
    }

    /**
     * This method finds the best seats on the flight
     * by using an algorithm to search through all the seats.
     * @param amount The amount of seats to search for.
     */
    public static void findBestSeats(int amount)
    {
        bookedSeats.clear();
        
        // ECONOMY CLASS IS THE ONLY ONE WORKING!!
        findSeatsAlgorithm(amount);
    }

    /**
     * This method adds a list of seats to the current booking.
     * @param list List of seats to add.
     * @see Seat
     */
    public static void addSeats(ArrayList<Seat> list) {
        for (Seat s : list) {
            addSeat(s);
        }
    }
    
    /**
     * This method check if the list of blocked seats contains
     * the given integer.
     * @param i Integer to check for.
     * @return True if integer exists in list of blocked seats, false if not.
     */
    private static Boolean blockedSeatsContains(Integer i)
    {
        for (int a = 0; a < blockedSeats.size(); a++)
        {
            if (blockedSeats.get(a).getSeatID() == i)
                return true;
        }
        return false;
    }

    /**
     * This method searches for the best seats on the flight
     * @param amount The amount of seats to search for.
     */
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
        ArrayList<BestSeats> list = new ArrayList<>();

        // Så kører vi alle sæderne i gennem
        for (int i = 0; i < EseatGroups * ErowSeats; i++) {
            for (int j = 0; j < EseatLength; j++) {
                
                // Create new combination holder
                BestSeats bs = new BestSeats();
                
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
        Collections.sort(list, new Comparator<BestSeats>() {
            @Override
            public int compare(BestSeats o1, BestSeats o2) {
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
    
    /**
     * This method searches vertically for best seats.
     * @param i The current y-coordinate to continue from.
     * @param j The current x-coordinate to keep.
     * @param bs The current BestSeats object to continue on.
     * @param amount The amount of seats to find.
     * @param seatsArray The 2d-array of seats to find seat numbers in.
     * @see BestSeats
     * @see Seats
     */
    private static void vAlgorithmSearch(int i, int j, BestSeats bs, int amount, int[][] seatsArray)
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
    
    /**
     * This method sorts whether the horizontal search should be left (-) or right (+).
     * @param j The current x-coordinate to continue on.
     * @param a Whether the search should be left or right. String as "+" or "-".
     * @param bs The current BestSeats object to continue on.
     * @param amount The amount of seats to find.
     * @param seatsArray The 2d-array of seats to find seat numbers in.
     * @see BestSeats
     * @see Seats
     */
    private static void hAlgorithmSearch(int j, String a, BestSeats bs, int amount, int[][] seatsArray)
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
    
    /**
     * This method searches horizontally for best seats.
     * It takes a list of y-coordinates, and if a seat is free
     * it puts the y-coordinate in a new list.
     * @param jj The x-coordinate to keep.
     * @param bs The current BestSeats object to continue on.
     * @param amount The amount of seats to find.
     * @param seatsArray The 2d-array of seats to find seat numbers in.
     * @param tempSeatsFirst The list of y-coordinates to search in.
     * @param tempSeatsSecond A new list of y-coordinates to fill in.
     * @return tempSeatsSecond as a new list of y-coordinates to search for next time.
     * @see BestSeats
     * @see Seats
     */
    private static ArrayList<Integer> hAlgorithmSearchDo(int jj, BestSeats bs, int amount, int[][] seatsArray, ArrayList<Integer> tempSeatsFirst, ArrayList<Integer> tempSeatsSecond)
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
