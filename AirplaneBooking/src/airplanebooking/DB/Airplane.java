package airplanebooking.DB;

import java.sql.Array;
import java.util.ArrayList;

/**
 *
 * @author Andreas
 */
public class Airplane {

    private final Integer id;
    private final Integer firstSeats;
    private final Integer businessSeats;
    private final Integer economySeats;
    private final Integer totalSeats;
    private final String fcSeatFormation;
    private final String bcSeatFormation;
    private final String ecSeatFormation;

    public Airplane(int id, int firstSeats, int businessSeats, int economySeats, String fcSeatFormation, String bcSeatFormation, String ecSeatFormation) {
        this.id = id;
        this.firstSeats = firstSeats;
        this.businessSeats = businessSeats;
        this.economySeats = economySeats;
        this.totalSeats = firstSeats + businessSeats + economySeats;
        this.fcSeatFormation = fcSeatFormation;
        this.bcSeatFormation = bcSeatFormation;
        this.ecSeatFormation = ecSeatFormation;

    }

    public Airplane(int flightID) {
        // database get flight from database
        this.id = 0;
        this.firstSeats = 0;
        this.businessSeats = 0;
        this.economySeats = 0;
        this.totalSeats = 0;
        this.fcSeatFormation = "";
        this.bcSeatFormation = "";
        this.ecSeatFormation = "";
    }

    public int getID() {
        return id;
    }

    public int getFirstSeats() {
        return firstSeats;
    }

    public int getBusinessSeats() {
        return businessSeats;
    }

    public int getEconomySeats() {
        return economySeats;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public String getFCSeatFormation() {
        return fcSeatFormation;
    }

    public String getBCSeatFormation() {
        return bcSeatFormation;
    }

    public String getECSeatFormation() {
        return ecSeatFormation;
    }

    public Object[] getCustomerDataList() {
        Object[] list = new Array[5];
        list[0] = id;
        list[1] = firstSeats;
        list[2] = businessSeats;
        list[3] = economySeats;
        list[4] = totalSeats;

        return list;
    }
}
