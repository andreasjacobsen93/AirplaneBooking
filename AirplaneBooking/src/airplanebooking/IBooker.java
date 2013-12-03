package airplanebooking;

/**
 *
 * @author Andreas
 */
public interface IBooker {
    
    String[][] getFlightList(String[] searchFilters);
    
    void Book(int flight, int[] seats, Customer customer);
    
    int[] getReservedSeats(int flight);
    
    String[] getCustomer(int flight, int seat);
}
