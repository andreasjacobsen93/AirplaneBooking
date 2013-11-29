/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package airplanebooking;

/**
 *
 * @author Andreas
 */
public interface IBooker {
    
    String[][] getList(String sortBy);
    
    void Book(int flight, int[] seats, Customer customer);
    
    String[] getDetails(int flight, int[] seats);
    
    
}
