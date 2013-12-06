package airplanebooking;

import airplanebooking.DB.Customer;

/**
 *
 * @author Andreas
 */
public interface IBooker {
    
    String[][] getFlightList(String sortBy);
    
    void Book(int flight, int[] seats, Customer customer);
    
    int[] getReservedSeats(int flight);
    
    String[] getCustomer(int customerId);
    
}
