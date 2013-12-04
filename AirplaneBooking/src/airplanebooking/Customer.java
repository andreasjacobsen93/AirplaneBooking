package airplanebooking;

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
    private final String phone;
    private final String mail;
    
    public Customer(Integer id, String maritialStatus, String firstName, String lastName, String address, Integer phone, String mail)
    {
        this.id = id;
        this.maritialStatus = maritialStatus;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone.toString();
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
        this.phone = "";
        this.mail = "";
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
    
    public String getPhone(){
        String number = phone.toString();
        return number;
    }
    public String getEmail(){
        return mail;
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
}
