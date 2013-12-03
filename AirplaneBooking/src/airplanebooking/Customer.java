package airplanebooking;

/**
 *
 * @author Andreas
 */
public class Customer {
    
    private final String maritialStatus;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String phone;
    private final String mail;
    
    public Customer(String maritialStatus, String firstName, String lastName, String address, String phone, String mail)
    {
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
        this.maritialStatus = "";
        this.firstName = "";
        this.lastName = "";
        this.address = "";
        this.phone = "";
        this.mail = "";
    }
    
    public String[] getCustomerDataList()
    {
        String[] list = new String[6];
        list[0] = maritialStatus;
        list[1] = firstName;
        list[2] = lastName;
        list[3] = address;
        list[4] = phone;
        list[5] = mail;
        return list;
    }
    
    public String getMaritialStatus()
    {
        return maritialStatus;
    }
    
    public String getFirstName()
    {
        return firstName;
    }
    
    public String getLastName()
    {
        return lastName;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public String getPhone()
    {
        return phone;
    }
    
    public String getMail()
    {
        return mail;
    }
}
