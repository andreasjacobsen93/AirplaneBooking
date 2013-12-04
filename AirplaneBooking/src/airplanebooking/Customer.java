package airplanebooking;

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
    private final String address;
    private final Integer phone;
    private final String mail;
    
    public Customer(int id, String maritialStatus, String firstName, String lastName, String address, int phone, String mail)
    {
        this.id = id;
        this.maritialStatus = maritialStatus;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
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
        this.address = "";
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
    
    public String getAddress(){
        return address;
    }
    
    public int getPhone(){
        return phone;
    }
    public String getEmail(){
        return mail;
    }
    
    public Object[] getCustomerDataList()
    {
        Object [] list = new Array[7];
        list[0] = id;
        list[1] = maritialStatus;
        list[2] = firstName;
        list[3] = lastName;
        list[4] = address;
        list[5] = phone;
        list[6] = mail;
        return list;
    }
}
