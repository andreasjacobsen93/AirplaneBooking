package airplanebooking.DB;

import java.sql.Array;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andreas
 */
public class Customer {
    
    private final Integer id;
    private final String maritialStatus;
    private final String firstName;
    private final String lastName;
    private final String addressStreet;
    private final Integer addressZip; 
    private final String addressCity;
    private final String addressCountry;
    private final Integer phone;
    private final String mail;

    
    public Customer(int id, String maritialStatus, String firstName, String lastName, String addressStreet, int addressZip, String addressCity, String addressCountry, int phone, String mail)
    {
        this.id = id;
        this.maritialStatus = maritialStatus;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressStreet = addressStreet;
        this.addressZip = addressZip;
        this.addressCity = addressCity;
        this.addressCountry = addressCountry;
        this.phone = phone;
        this.mail = mail;
    }
    
    public int getID(){
        return id;
    }
    
    public String getMaritalStatus(){
        return maritialStatus;
    }
    
    public String getFirstName(){
        return firstName;
    }
    
    public String getLastName(){
        return lastName;
    }

    public String getAddressStreet(){
        return addressStreet;
    }
    
    
    public int getAddressZip(){
        return addressZip;
    } 

    public String getAddressCity(){
        return addressCity;
    }

    public String getAddressCountry(){
        return addressCountry;
    }
    
    public int getPhone(){
        return phone;
    }
    
    public String getEmail(){
        return mail;
    }
}
