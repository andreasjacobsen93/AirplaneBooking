package airplanebooking.DB;

/**
 * Customer class is used to store all data about a customer.
 * @author Andreas Jacobsen & Aleksandar Jonovic
 */
public class Customer {
    
    // The identification number of the customer
    private final Integer id;
    
    // The maritial status of the customer
    private final String maritialStatus;
    
    // The customers first name
    private final String firstName;
    
    // The customers last name
    private final String lastName;
    
    // The street of the customers address
    private final String addressStreet;
    
    // The zip code of the customers address
    private final Integer addressZip; 
    
    // The city of the customers address
    private final String addressCity;
    
    // The country of the customers address
    private final String addressCountry;
    
    // The phone number of the customer
    private final Integer phone;
    
    // The email address of the customer
    private final String email;

    /**
     * Constructor is used to create a customer object with data.
     * @param id Identification number of the customer as integer.
     * @param maritialStatus Maritial status of the customer as string.
     * @param firstName First name of the customer as string.
     * @param lastName Last name of the customer as string.
     * @param addressStreet Street of customers address as string.
     * @param addressZip Zip code of customers address as integer.
     * @param addressCity City of customers address as string.
     * @param addressCountry Country of customers address as string
     * @param phone Phone number of customer as integer.
     * @param email Email address of customer as string.
     */
    public Customer(int id, String maritialStatus, String firstName, String lastName, String addressStreet, int addressZip, String addressCity, String addressCountry, int phone, String email)
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
        this.email = email;
    }
    
    /**
     *  Overloaded constructor for methods which require this object to insert into the database. <br>
     * For JavaDoc please refer to:<p>
     * {@link #Customer(int, String, String, String, String, int, String, String, int, String) Customer constructor - JavaDoc}
     * 
     * @param maritialStatus
     * @param firstName
     * @param lastName
     * @param addressStreet
     * @param addressZip
     * @param addressCity
     * @param addressCountry
     * @param phone
     * @param email
     */
    public Customer(String maritialStatus, String firstName, String lastName, String addressStreet, int addressZip, String addressCity, String addressCountry, int phone, String email)
    {
        this.id = 0;
        this.maritialStatus = maritialStatus;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressStreet = addressStreet;
        this.addressZip = addressZip;
        this.addressCity = addressCity;
        this.addressCountry = addressCountry;
        this.phone = phone;
        this.email = email;
    }
    
    /**
     * This method gets the identification number of the customer.
     * @return Identification number of customer as integer.
     */
    public int getID(){
        return id;
    }
    
    /**
     * This method gets the maritial status of the customer.
     * @return Maritial status of customer as string.
     */
    public String getMaritalStatus(){
        return maritialStatus;
    }
    
    /**
     * This method gets the first name of the customer.
     * @return First name of customer as string.
     */
    public String getFirstName(){
        return firstName;
    }
    
    /**
     * This method gets the last name of the customer.
     * @return Last name of customers as string.
     */
    public String getLastName(){
        return lastName;
    }

    /**
     * This method gets the street of the customer address.
     * @return Street of customers address as string.
     */
    public String getAddressStreet(){
        return addressStreet;
    }
    
    /**
     * This method gets the zip code of the customer address.
     * @return Zip code of customers address as integer.
     */
    public int getAddressZip(){
        return addressZip;
    } 

    /**
     * This method gets the city of the customer address.
     * @return City of customers address as string.
     */
    public String getAddressCity(){
        return addressCity;
    }

    /**
     * This method gets the country of the customer address.
     * @return Country of customers address as string.
     */
    public String getAddressCountry(){
        return addressCountry;
    }
    
    /**
     * This method gets the phone number of the customer.
     * @return Phone number of customer as integer.
     */
    public int getPhone(){
        return phone;
    }
    
    /**
     * This method gets the email address of the customer.
     * @return Email address of customer as string.
     */
    public String getEmail(){
        return email;
    }
}
