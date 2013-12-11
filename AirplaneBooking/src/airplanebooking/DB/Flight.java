package airplanebooking.DB;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Flight class is used to store all data about a flight.
 * @author Andreas Jacobsen
 */
public class Flight 
{
    // The idenfication number of the flight
    private final Integer id;
    
    // The airplane which the flight is with
    private final Airplane airplane;
    
    // The idenfication number of the airplane
    private final Integer airplaneID;
    
    // The cost of a seat on first class
    private final Integer firstCost;
    
    // The cost of a seat on business class
    private final Integer businessCost;
    
    // The cost of a seat on economy class
    private final Integer economyCost;
    
    // List of booked seats on flight
    private final ArrayList<Seat> bookedSeats;
    
    // The time when the flight takes off
    private final Timestamp departureTime;
    
    // The time when the flight lands
    private final Timestamp arrivalTime;
    
    // The place where the flight takes off
    private final String departurePlace;
    
    // The place where the flight lands
    private final String arrivalPlace;
    
    // Is the flight filled or not
    private final Boolean isFull;

    // The format to use for times.
    private final DateFormat df = new SimpleDateFormat("dd/MM-yyyy HH:mm");

    /**
     * Constructor is used to create a flight with all the right data.
     * @param id Identification number of flight as integer.
     * @param airplane Airplane to fly with as airplane object.
     * @param firstCost The cost of a ticket on first class as integer.
     * @param businessCost The cost of a ticket on business class as integer.
     * @param economyCost The cost of a ticket on economy class as integer.
     * @param seats List of seats on flight as array list of seat objects.
     * @param departurePlace The place to fly from as string.
     * @param departureTime The time when the airplane takes off as timestamp.
     * @param arrivalPlace The place to fly to as string.
     * @param arrivalTime The time when the airplane arrives as timestamp.
     * @param isFull Whether the flight is fully booked or not as boolean.
     * @see Airplane
     * @see Seat
     */
    public Flight(int id, Airplane airplane, int firstCost, int businessCost, int economyCost, ArrayList<Seat> seats, String departurePlace, Timestamp departureTime, String arrivalPlace, Timestamp arrivalTime, Boolean isFull) {
        this.id = id;
        this.airplane = airplane;
        this.airplaneID = airplane.getID();
        this.firstCost = firstCost;
        this.businessCost = businessCost;
        this.economyCost = economyCost;
        this.bookedSeats = seats;
        this.departurePlace = departurePlace;
        this.departureTime = departureTime;
        this.arrivalPlace = arrivalPlace;
        this.arrivalTime = arrivalTime;
        this.isFull = isFull;
    }

    /**
     * This method gets the identification number of the flight.
     * @return Identification number of flight as integer.
     */
    public int getID() {
        return id;
    }

    /**
     * This method gets the airplane to fly with.
     * @return Airplane as airplane object.
     * @see Airplane
     */
    public Airplane getAirplane() {
        return airplane;
    }
    
    /**
     * This method gets the identification number of the airplane.
     * @return Airplane idenfication number as integer.
     */
    public int getAirplaneID() {
        return airplaneID;
    }

    /**
     * This method gets the cost of flying on first class.
     * @return Cost of a seat on first class as integer.
     */
    public int getFirstClassSeatCost() {
        return firstCost;
    }

    /**
     * This method gets the cost of flying on business class.
     * @return Cost of a seat on business class as integer.
     */
    public int getBusinessClassSeatCost() {
        return businessCost;
    }

    /**
     * This method gets the cost of flying on economy class.
     * @return Cost of a seat on economy class as integer.
     */
    public int getEconomyClassSeatCost() {
        return economyCost;
    }

    /**
     * This method gets a list of seats on flight.
     * @return List of seats as array list of seat objects.
     * @see Seat
     */
    public ArrayList<Seat> getSeats() {
        return bookedSeats;
    }

    /**
     * This method gets the time when the airplane takes off.
     * @return Take off time as string.
     */
    public String getDepartureTime() {
        String returnTime = df.format(departureTime);
        return returnTime;
    }

    /**
     * This method gets the time when the airplane arrives.
     * @return Arrival time as string.
     */
    public String getArrivalTime() {
        String returnTime = df.format(arrivalTime);
        return returnTime;
    }
    
    /**
     * This method gets the time when the airplane arrives.
     * @return Arrival time as timestamp.
     */
    public Timestamp getArrivalTimestamp(){
        return arrivalTime;
    }
    
    /**
     * This method gets the time when the airplane takes off.
     * @return Take off time as timestamp.
     */
    public Timestamp getDepartureTimestamp(){
        return departureTime;
    }

    /**
     * This method gets the place where the airplane takes off.
     * @return Take off place as string.
     */
    public String getDeparturePlace() {
        return departurePlace;
    }

    /**
     * This method gets the place where the airplane lands.
     * @return Landing place as string.
     */
    public String getArrivalPlace() {
        return arrivalPlace;
    }

    /**
     * This method gets whether the flight is fully booked or not.
     * @return Fully booked or not as boolean.
     */
    public Boolean isFull() {
        return isFull;
    }
}
