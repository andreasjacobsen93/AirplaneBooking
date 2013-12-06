package airplanebooking.DB;

import java.sql.Array;

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
    
    public Customer(int customerID)
    {
        // database get customer from database
        this.id = 0;
        this.maritialStatus = "";
        this.firstName = "";
        this.lastName = "";
        this.addressStreet = "";
        this.addressZip = 0;
        this.addressCity = "";
        this.addressCountry = "";
        this.phone = 0;
        this.mail = "";
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
    
    public Object[] getCustomerDataList()
    {
        Object [] list = new Array[10];
        list[0] = id;
        list[1] = maritialStatus;
        list[2] = firstName;
        list[3] = lastName;
        list[4] = addressStreet;
        list[5] = addressZip;
        list[6] = addressCity;
        list[7] = addressCountry;
        list[8] = phone;
        list[9] = mail;
        return list;
    }
}
