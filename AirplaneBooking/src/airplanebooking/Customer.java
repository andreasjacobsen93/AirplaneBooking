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
        String phone = this.phone.toString();
        return phone;
    }
    public String getEmail(){
        return mail;
    }
    
    
    

}
