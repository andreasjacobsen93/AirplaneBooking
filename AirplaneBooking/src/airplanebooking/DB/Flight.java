package airplanebooking.DB;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author Andreas
 */
public class Flight {

    private final Integer id;
    private final Airplane airplane;
    private final Integer airplaneID;
    private final Integer firstCost;
    private final Integer businessCost;
    private final Integer economyCost;
    private final ArrayList<Seat> bookedSeats;
    private final Timestamp departureTime;
    private final Timestamp arrivalTime;
    private final String departurePlace;
    private final String arrivalPlace;
    private final Boolean isFull;

    private final DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

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

    public int getID() {
        return id;
    }

    public Airplane getAirplane() {
        return airplane;
    }
    
    public int getAirplaneID() {
        return airplaneID;
    }

    public int getFirstClassSeatCost() {
        return firstCost;
    }

    public int getBusinessClassSeatCost() {
        return businessCost;
    }

    public int getEconomyClassSeatCost() {
        return economyCost;
    }

    public ArrayList<Seat> getSeats() {
        return bookedSeats;
    }

    public String getDepartureTime() {
        String returnTime = df.format(departureTime);
        return returnTime;
    }

    public String getArrivalTime() {
        String returnTime = df.format(arrivalTime);
        return returnTime;
    }
    public Timestamp getArrivalTimestamp(){
        return arrivalTime;
    }
    
    public Timestamp getDepartureTimestamp(){
        return departureTime;
    }

    public String getDeparturePlace() {
        return departurePlace;
    }

    public String getArrivalPlace() {
        return arrivalPlace;
    }

    public Boolean isFull() {
        return isFull;
    }
}
