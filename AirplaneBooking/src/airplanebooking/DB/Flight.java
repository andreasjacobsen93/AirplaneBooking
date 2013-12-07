package airplanebooking.DB;

import java.sql.Array;
import java.sql.Date;
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
    private final Integer airplaneID;
    private final Integer firstCost;
    private final Integer businessCost;
    private final Integer economyCost;
    private final ArrayList<Seat> bookedSeats;
    private final Timestamp departureTime;
    private final Timestamp arrivalTime;
    private final String departurePlace;
    private final String arrivalPlace;

    private final DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public Flight(int id, int airplaneID, int firstCost, int businessCost, int economyCost, String departurePlace, Timestamp departureTime, String arrivalPlace, Timestamp arrivalTime) {
        this.id = id;
        this.airplaneID = airplaneID;
        this.firstCost = firstCost;
        this.businessCost = businessCost;
        this.economyCost = economyCost;
        this.bookedSeats = new ArrayList();
        this.departurePlace = departurePlace;
        this.departureTime = departureTime;
        this.arrivalPlace = arrivalPlace;
        this.arrivalTime = arrivalTime;
    }

    public int getID() {
        return id;
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

    public String getDeparturePlace() {
        return departurePlace;
    }

    public String getArrivalPlace() {
        return arrivalPlace;
    }

    public Object[] getCustomerDataList() {
        Object[] list = new Array[9];
        list[0] = id;
        list[1] = airplaneID;
        list[1] = firstCost;
        list[2] = businessCost;
        list[3] = economyCost;
        list[4] = bookedSeats;
        list[5] = departurePlace;
        list[6] = departureTime;
        list[7] = arrivalPlace;
        list[8] = arrivalTime;
        return list;
    }
}
