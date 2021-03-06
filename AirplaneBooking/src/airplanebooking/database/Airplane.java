package airplanebooking.database;

/**
 * Airplane class. Used to store data about an airplane.
 *
 * @author Andreas Jacobsen & Aleksandar Jonovic
 */
public class Airplane {

    // The identification number of the airplane
    private final Integer id;

    // The name of the airplane
    private final String name;

    // The amount of first class seats on airplane
    private final Integer firstSeats;

    // The amount of business class seats on airplane
    private final Integer businessSeats;

    // The amount of economy class seats on airplane
    private final Integer economySeats;

    // The total amount of seats on airplane
    private final Integer totalSeats;

    // The seat formation used on first class
    // Seat formation is 3 numbers with ":" in between them.
    private final String fcSeatFormation;

    // The seat formation used on business class
    // Seat formation is 3 numbers with ":" in between them.
    private final String bcSeatFormation;

    // The seat formation used on economy class
    // Seat formation is 3 numbers with ":" in between them.
    private final String ecSeatFormation;

    /**
     * This constructor create the airplane object with all the data.
     *
     * @param id Identification number of the airplane, as integer.
     * @param name Name of the the airplane, as string.
     * @param firstSeats Amount of first class seats on airplane, as integer.
     * @param businessSeats Amount of business class seats on airplane, as
     * integer.
     * @param economySeats Amount of economy class seats on airplane, as
     * integer.
     * @param fcSeatFormation Formation of first class seats on airplane, as
     * string. Seat formation is 3 numbers with ":" in between them.
     * @param bcSeatFormation Formation of business class seats on airplane, as
     * string. Seat formation is 3 numbers with ":" in between them.
     * @param ecSeatFormation Formation of economy class seats on airplane, as
     * string. Seat formation is 3 numbers with ":" in between them.
     */
    public Airplane(int id, String name, int firstSeats, int businessSeats, int economySeats, String fcSeatFormation, String bcSeatFormation, String ecSeatFormation) {
        this.id = id;
        this.name = name;
        this.firstSeats = firstSeats;
        this.businessSeats = businessSeats;
        this.economySeats = economySeats;
        this.totalSeats = firstSeats + businessSeats + economySeats;
        this.fcSeatFormation = fcSeatFormation;
        this.bcSeatFormation = bcSeatFormation;
        this.ecSeatFormation = ecSeatFormation;
    }

    /**
     * Overloaded constructor for methods which insert into the database. <br>For
     * JavaDoc please refer to:<p>
     * {@link #Airplane(int, String, int, int, int, String, String, String) Airplane constructor - JavaDoc}
     *
     * @param name N/A
     * @param firstSeats N/A
     * @param businessSeats N/A
     * @param economySeats N/A
     * @param fcSeatFormation N/A
     * @param bcSeatFormation N/A
     * @param ecSeatFormation N/A
     */
    public Airplane(String name, int firstSeats, int businessSeats, int economySeats, String fcSeatFormation, String bcSeatFormation, String ecSeatFormation) {
        this.id = 0;
        this.name = name;
        this.firstSeats = firstSeats;
        this.businessSeats = businessSeats;
        this.economySeats = economySeats;
        this.totalSeats = firstSeats + businessSeats + economySeats;
        this.fcSeatFormation = fcSeatFormation;
        this.bcSeatFormation = bcSeatFormation;
        this.ecSeatFormation = ecSeatFormation;
    }

    /**
     * This method gets the identification number of the airplane.
     *
     * @return Identification number as integer.
     */
    public int getID() {
        return id;
    }

    /**
     * This method gets the name of the airplane
     *
     * @return Name as string.
     */
    public String getName() {
        return name;
    }

    /**
     * This method gets the amount of first class seats on airplane.
     *
     * @return Amount of first class seats as integer.
     */
    public int getFirstSeats() {
        return firstSeats;
    }

    /**
     * This method gets the amount of business class seats on airplane.
     *
     * @return Amount of business class seats as integer.
     */
    public int getBusinessSeats() {
        return businessSeats;
    }

    /**
     * This method gets the amount of economy class seats on airplane.
     *
     * @return Amount of economy class seats as integer.
     */
    public int getEconomySeats() {
        return economySeats;
    }

    /**
     * This method gets the total amount of seats on airplane.
     *
     * @return Total amount of seats as integer.
     */
    public int getTotalSeats() {
        return totalSeats;
    }

    /**
     * This method gets the formation of first class seats on airplane. Seat
     * formation is 3 numbers with ":" in between them.
     *
     * @return Seat formation as string.
     */
    public String getFCSeatFormation() {
        return fcSeatFormation;
    }

    /**
     * This method gets the formation of business class seats on airplane. Seat
     * formation is 3 numbers with ":" in between them.
     *
     * @return Seat formation as string.
     */
    public String getBCSeatFormation() {
        return bcSeatFormation;
    }

    /**
     * This method gets the formation of economy class seats on airplane. Seat
     * formation is 3 numbers with ":" in between them.
     *
     * @return Seat formation as string.
     */
    public String getECSeatFormation() {
        return ecSeatFormation;
    }
}
