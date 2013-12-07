package airplanebooking.DB;
import java.sql.Array;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author Andreas
 */
public class Flight {
    
    private final Integer id;
    private final Integer firstSeats;
    private final Integer businessSeats;
    private final Integer economySeats;
    private final Integer totalSeats;
    private final Seat[] seats;
    private final Timestamp departureTime;
    private final Timestamp arrivalTime;
    private final String departurePlace;
    private final String arrivalPlace;
    private final DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    
    public Flight(int id, int firstSeats, int businessSeats, int economySeats, String departurePlace, Timestamp departureTime, String arrivalPlace, Timestamp arrivalTime)
    {
        this.id = id;
        this.firstSeats = firstSeats;
        this.businessSeats = businessSeats;
        this.economySeats = economySeats;
        this.totalSeats = firstSeats+businessSeats+economySeats;
        this.seats = new Seat[totalSeats];
        this.departurePlace = departurePlace;
        this.departureTime = departureTime;
        this.arrivalPlace = arrivalPlace;
        this.arrivalTime = arrivalTime;
    }
    
    public Flight(int flightID)
    {
        // database get flight from database
        this.id = 0;
        this.firstSeats = 0;
        this.businessSeats = 0;
        this.economySeats = 0;
        this.totalSeats = 0;
        this.seats = new Seat[totalSeats];
        this.departurePlace = "";
        this.departureTime = null;
        this.arrivalPlace = "";
        this.arrivalTime = null;  
    }
    
    public int getID(){
        return id;
    }
    
    public int getFirstSeats(){
        return firstSeats;
    }
    
    public int getBusinessSeats(){
        return businessSeats;
    }
    
    public int getEconomySeats(){
        return economySeats;
    }
    
    public int getTotalSeats(){
        return totalSeats;
    }
    
    public Seat[] getSeats(){        
        return seats;
    }
    
    public String getDepartureTime(){
        String returnTime = df.format(departureTime);
        return returnTime;
    }
    public String getArrivalTime(){
        String returnTime = df.format(arrivalTime);
        return returnTime;
    }
    
    public String getDeparturePlace(){
        return departurePlace;
    }
    
    public String getArrivalPlace(){
        return arrivalPlace;
    }
    
    public Object[] getCustomerDataList()
    {
        Object [] list = new Array[9];
        list[0] = id;
        list[1] = firstSeats;
        list[2] = businessSeats;
        list[3] = economySeats;
        list[4] = totalSeats;
        list[5] = seats;
        list[6] = departurePlace;
        list[7] = departureTime;
        list[8] = arrivalPlace;
        list[9] = arrivalTime;
        return list;
    }
}
