package airplanebooking;

/**
 *
 * @author Andreas
 */
public class Customer {
    
    private String maritialStatus;
    private String firstName;
    private String lastName;
    private String address;
    private Integer phone;
    private String mail;
    
    public Customer(String maritialStatus, String firstName, String lastName, String address, Integer phone, String mail)
    {
        this.maritialStatus = maritialStatus;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.mail = mail;
    }
    
}
