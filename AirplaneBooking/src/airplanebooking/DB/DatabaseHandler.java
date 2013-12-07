/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airplanebooking.DB;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alex
 */
public class DatabaseHandler implements DatabaseInterface {

    //DB & SQL fields
    Connection con = null;
    Statement statement = null;
    ResultSet results = null;
    SQLWarning warning = new SQLWarning();
    ComboPooledDataSource cpds = new ComboPooledDataSource();

    //Customer field
    Customer customer;
    ArrayList<Customer> customers = new ArrayList();

    //Booking field
    Booking reservation = null;
    ArrayList<Booking> reservations = new ArrayList();

    //Flight field
    Flight flight = null;

    //Seat field
    ArrayList<Seat> seats = new ArrayList();

    //Airplane field
    Airplane airplane = null;

    public DatabaseHandler() {
        //contructor method to initiate (combo)DataSource for pooled connections on spawning an object.
        initiateDataSource();

    }

    private void initiateDataSource() {

        cpds.setJdbcUrl("jdbc:mysql://mysql.itu.dk/Airplanebooking");
        cpds.setUser("aljon");
        cpds.setPassword("Jegeradministratorher123");
        cpds.setMinPoolSize(1);
        cpds.setAcquireIncrement(3);
        cpds.setMaxPoolSize(10);

    }

    private void executeUpdate(String sql) {

        try {
            //Query DataSource for connection, and establish statement handler
            con = cpds.getConnection();
            statement = con.createStatement();
            //pass statement to statement handler -> db.
            statement.executeUpdate(sql);
            statement.getWarnings();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private ResultSet executeQuery(String sql) {
        //first we establish DB connection.

        //establish statement handler.
        try {
            con = cpds.getConnection();
            statement = con.createStatement();
            statement.getWarnings();
            //pass statement to statement handler -> db and save ResultSet.
            results = statement.executeQuery(sql);

        } catch (SQLException e) {

            System.out.println(e);
        }
        return results;
    }

    private String getWarnings() {
        String status = warning.getMessage();
        return status;
        /*        if (!status.isEmpty()) {
         return status;
         } else {
         status = "Completed without error.";
         return status;
         }
         */
    }

    private void closeConnection() {
        try {
            
            if (con != null){
            statement.close();
            //
            results.close();
            // results = null;
            con.close();
            // con = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void createCustomer(String maritalstatus, String firstname, String lastname, String addressStreet, int addressZip, String addressCity, String addressCountry, String email, int phonenumber) {

        //create string (sql statement), which we'd like to pass to the statement handler.
        String sql = "INSERT INTO customers "
                + "VALUES (null, "
                + "'" + maritalstatus + "', "
                + "'" + firstname + "', "
                + "'" + lastname + "', "
                + "'" + addressStreet + "', "
                + "" + addressZip + ", "
                + "'" + addressCity + "', "
                + "'" + addressCountry + "', "
                + "'" + email + "', "
                + "" + phonenumber + ")";
        System.out.println("Customer " + firstname + " " + lastname + " created.");
        //execute the statement
        executeUpdate(sql);

    }

    @Override
    public void editCustomer(int customerID, String maritalstatus, String firstname, String lastname, String addressStreet, int addressZip, String addressCity, String addressCountry, String email, int phonenumber) {

        //create string (sql statement), which we'd like to pass to the statement handler.
        String sql = "UPDATE customers SET "
                + "maritalstatus = '" + maritalstatus + "', "
                + "firstname = '" + firstname + "', "
                + "lastname = '" + lastname + "', "
                + "addressstreet = '" + addressStreet + "', "
                + "addresszip = " + addressZip + ", "
                + "addresscity = '" + addressCity + "', "
                + "addresscountry = '" + addressCountry + "', "
                + "email = '" + email + "', "
                + "phonenumber = " + phonenumber
                + "WHERE id = " + customerID;

        System.out.println("Customer " + firstname + " " + lastname + " edited.");

        //execute statement
        executeUpdate(sql);
    }

    @Override
    public void deleteCustomer(int customerID) {

        //create string (sql statement), which we'd like to pass to the statement handler.
        String sql = "DELETE FROM customers WHERE id = " + customerID;

        System.out.println("Customer with customerID:" + customerID + " deleted.");

        //execute statement
        executeUpdate(sql);

    }

    @Override
    public Customer getCustomer(int customerID) {
        try {
            if (customerExists(customerID)) {
                String sql = "SELECT * FROM customers WHERE id = " + customerID;
                //pass query to query handler -> db. REMEMBER THAT THIS DOESN'T CLOSE DB CONNECTION, CLOSING IS PARAMOUNT!

                executeQuery(sql);
                while (results.next()) {
                    int id = results.getInt("id");
                    String maritalstatus = results.getString("maritalstatus");
                    String firstname = results.getString("firstname");
                    String lastname = results.getString("lastname");
                    String addressStreet = results.getString("addressstreet");
                    int addressZip = results.getInt("addresszip");
                    String addressCity = results.getString("addresscity");
                    String addressCountry = results.getString("addresscountry");
                    int phonenumber = results.getInt("phonenumber");
                    String email = results.getString("email");

                    customer = new Customer(id, maritalstatus, firstname, lastname, addressStreet, addressZip, addressCity, addressCountry, phonenumber, email);
                    System.out.println(getWarnings());
                 
                }
            } else {
                                  
                }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return customer;
    }

    @Override
    public ArrayList<Customer> getCustomers(int q) {

        String sql = "SELECT * FROM customers WHERE addresszip =" + q + " OR phonenumber =" + q;

        try {
            executeQuery(sql);
            results.beforeFirst();

            while (results.next()) {
                int id = results.getInt("id");
                String maritalstatus = results.getString("maritalstatus");
                String firstname = results.getString("firstname");
                String lastname = results.getString("lastname");
                String addressStreet = results.getString("addressstreet");
                int addressZip = results.getInt("addresszip");
                String addressCity = results.getString("addresscity");
                String addressCountry = results.getString("addresscountry");
                int phonenumber = results.getInt("phonenumber");
                String email = results.getString("email");
                customer = new Customer(id, maritalstatus, firstname, lastname, addressStreet, addressZip, addressCity, addressCountry, phonenumber, email);
                customers.add(customer);
            }

            return customers;
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong in getting your customers", ex);
        } finally {
            closeConnection();
        }
    }

    @Override
    public ArrayList<Customer> getCustomers(String q) {
        String sql = "SELECT * FROM customers WHERE firstname =\"" + q + "\" OR lastname =\"" + q + "\" OR addressstreet =\"" + q + "\" OR email =\"" + q + "\"";

        try {
            executeQuery(sql);
            results.beforeFirst();
            while (results.next()) {
                int id = results.getInt("id");
                String maritalstatus = results.getString("maritalstatus");
                String firstname = results.getString("firstname");
                String lastname = results.getString("lastname");
                String addressStreet = results.getString("addressstreet");
                int addressZip = results.getInt("addresszip");
                String addressCity = results.getString("addresscity");
                String addressCountry = results.getString("addresscountry");
                int phonenumber = results.getInt("phonenumber");
                String email = results.getString("email");
                customer = new Customer(id, maritalstatus, firstname, lastname, addressStreet, addressZip, addressCity, addressCountry, phonenumber, email);
                customers.add(customer);
            }
            return customers;
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong in getting your customers", ex);
        } finally {
            closeConnection();
        }

    }

    @Override
    public ArrayList<Customer> getCustomers(String firstName, String lastName, String Email, Integer Phone) {

        String sql = "SELECT * FROM customers WHERE ";

        int i = 0;
        if (firstName != null) {
            sql += "firstname = \"" + firstName + "\"";
            i++;
        }

        if (lastName != null) {
            if (i > 0) {
                sql += " AND ";
            }
            sql += "lastname = \"" + lastName + "\"";
            i++;
        }

        if (Email != null) {
            if (i > 0) {
                sql += " AND ";
            }
            sql += "email = \"" + Email + "\"";
            i++;
        }

        if (Phone != null) {
            if (i > 0) {
                sql += " AND ";
            }
            sql += "phonenumber = \"" + Phone + "\"";
        }

        try {
            executeQuery(sql);
            results.beforeFirst();
            while (results.next()) {
                int id = results.getInt("id");
                String maritalstatus = results.getString("maritalstatus");
                String firstname = results.getString("firstname");
                String lastname = results.getString("lastname");
                String addressStreet = results.getString("addressstreet");
                int addressZip = results.getInt("addresszip");
                String addressCity = results.getString("addresscity");
                String addressCountry = results.getString("addresscountry");
                int phonenumber = results.getInt("phonenumber");
                String email = results.getString("email");
                customer = new Customer(id, maritalstatus, firstname, lastname, addressStreet, addressZip, addressCity, addressCountry, phonenumber, email);
                customers.add(customer);
            }
            return customers;
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong in getting your customers", ex);
        } finally {
            closeConnection();
        }

    }

    @Override
    public boolean customerExists(Customer customer) {

        String firstname = customer.getFirstName();
        String lastname = customer.getLastName();
        String email = customer.getEmail();
        boolean exists = false;

        String sql = "SELECT COUNT(*) FROM customers WHERE firstname='" + firstname + "' AND lastname = '" + lastname + "' AND email = '" + email + "' OR email = '" + email + "'";
        try {
            executeQuery(sql);
            results.first();
            exists = 0 != results.getInt(1);
            System.out.println(exists);
            //statement.close();
            //  results.close();

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // closeConnection();
        }

        return exists;

    }

    public boolean customerExists(int customerID) {
        String sql = "SELECT * FROM customers WHERE id="+customerID;
        executeQuery(sql);
        Boolean exists = false;
        try {
        results.first();
        if (results.wasNull()){
            exists = false;
        } else {
            exists = true;

        }
        
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
      return exists;

    }

    @Override
    public boolean seatsExist(ArrayList<Seat> seats, Flight flight) {
        boolean exists = false;
        for (Seat currentSeat : seats) {
            try {
                String sql = "SELECT f.id, r2s.seat_id FROM flights f, reservation2seat r2s WHERE f.id =" + flight.getID() + " AND r2s.seat_id =" + currentSeat;
                executeQuery(sql);
                if (results.getRow() == 0) {
                    exists = false;
                } else {
                    exists = true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return exists;
    }

    @Override
    public void createReservation(Customer currentCustomer, Flight flight, ArrayList<Seat> seats, int food) {
        try {
            if (customerExists(currentCustomer)) {
                if (!seatsExist(seats, flight)) {
                    customer = currentCustomer;
                    int customerID = customer.getID();
                    int flightID = flight.getID();
                    System.out.println(flightID);
                    String sql = "INSERT INTO reservations "
                            + "VALUES (null, "
                            + "" + customerID + ", "
                            + "'" + flightID + "', "
                            + "" + food + ")";

                    int[] key = new int[1];
                    key[0] = 1;
                    statement.executeUpdate(sql, key[0]);

                    ResultSet rs = statement.getGeneratedKeys();
                    rs.first();
                    int reservationID = rs.getInt(1);
                    System.out.println(reservationID);
                    //System.out.println(reservationID);

                    for (Seat seat : seats) {
                        System.out.println(seat.getIndex());
                        sql = "INSERT INTO reservation2seat VALUES (" + reservationID + ", " + seat.getIndex() + ")";

                        executeUpdate(sql);

                    }
                } else {
                    System.out.println("YOUR SEATS ALREADY EXIST, RESERVATION CREATION ABORTED!");
                }

            } else {

                String maritalstatus = currentCustomer.getMaritalStatus();
                String firstname = currentCustomer.getFirstName();
                String lastname = currentCustomer.getLastName();
                String addressStreet = currentCustomer.getAddressStreet();
                int addressZip = currentCustomer.getAddressZip();
                String addressCity = currentCustomer.getAddressCity();
                String addressCountry = currentCustomer.getAddressCountry();
                String email = currentCustomer.getEmail();
                int phonenumber = currentCustomer.getPhone();

                String sql = "INSERT INTO customers "
                        + "VALUES (null, "
                        + "'" + maritalstatus + "', "
                        + "'" + firstname + "', "
                        + "'" + lastname + "', "
                        + "'" + addressStreet + "', "
                        + "" + addressZip + ", "
                        + "'" + addressCity + "', "
                        + "'" + addressCountry + "', "
                        + "'" + email + "', "
                        + "" + phonenumber + ")";
                System.out.println("Customer " + firstname + " " + lastname + " created.");

                statement.executeUpdate(sql);
                customer = getCustomer(statement.executeUpdate(sql, 1));
                createReservation(customer, flight, seats, food);

            }

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while creating your reservation", ex);
        } finally {
            closeConnection();
        }

    }

    @Override
    public void editReservation(int reservationID, int customerID, String flightID, ArrayList<Seat> seats, int food) {

        String sql = "UPDATE reservations SET "
                + "customer_id = " + customerID + ", "
                + "flightid = '" + flightID + "', "
                + "food = " + food + " "
                + "WHERE id = " + reservationID;

        executeUpdate(sql);
        if (seats.isEmpty()) {
            sql = "DELETE FROM reservation2seat WHERE reservation_id=" + reservationID;
            executeUpdate(sql);
        } else {
            for (Seat seat : seats) {
                sql = "UPDATE reservation2seat SET "
                        + "reservation_id = " + reservationID + ", "
                        + "seat_id= " + seat.getIndex();

                executeUpdate(sql);
            }
        }

    }

    @Override
    public void deleteReservation(int reservationID) {

        String sql = "DELETE FROM reservations WHERE id = " + reservationID;

        executeUpdate(sql);

    }

    @Override
    public Booking getReservation(int reservationID) {

        String sql = "SELECT * FROM reservations WHERE id = " + reservationID;
        //pass query to query handler -> db. REMEMBER THAT THIS METHOD DOESN'T CLOSE STATEMENTS , CLOSING IS PARAMOUNT!

        try {
            executeQuery(sql);
            while (results.next()) {
                int id = results.getInt("id");
                int customerid = results.getInt("customer_id");
                int flightid = results.getInt("flightid");
                int food = results.getInt("food");

                String getSeats = "SELECT r2s.seat_id "
                        + "FROM `reservation2seat` r2s "
                        + "INNER JOIN reservations rs "
                        + "ON r2s.reservation_id = " + id + " "
                        + "WHERE r2s.reservation_id = rs.id ";

                ResultSet seatResults = executeQuery(getSeats);
                while (seatResults.next()) {
                    Seat seat = new Seat(seatResults.getInt("seat_id"));
                    seats.add(seat);
                }

                reservation = new Booking(id, customerid, flightid, seats, food);

            }
            //statement.close();
            return reservation;

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while getting the reservation:", ex);

        } catch (NullPointerException e) {
            throw new RuntimeException("You didn't supply a valid reservation ID", e);
        }

    }
    
     public Booking getReservation(int seatID, int flightID) {

        String sql = "SELECT reservation_id FROM reservation2seat WHERE seat_id ="+seatID+" AND flight_id="+flightID;
        //pass query to query handler -> db. REMEMBER THAT THIS METHOD DOESN'T CLOSE STATEMENTS , CLOSING IS PARAMOUNT!
        executeQuery(sql);
        try {
            results.first();
            int reservationID = results.getInt(1);
            sql = "SELECT * FROM reservations WHERE id = " + reservationID;
            executeQuery(sql);
           // results.first();
            while (results.next()) {
                int id = results.getInt("id");
                int customerid = results.getInt("customer_id");
                int flightid = results.getInt("flightid");
                int food = results.getInt("food");

                String getSeats = "SELECT r2s.seat_id "
                        + "FROM `reservation2seat` r2s "
                        + "INNER JOIN reservations rs "
                        + "ON r2s.reservation_id = " + id + " "
                        + "WHERE r2s.reservation_id = rs.id ";

                ResultSet seatResults = executeQuery(getSeats);
                
                while (seatResults.next()) {
                    Seat seat = new Seat(seatResults.getInt("seat_id"));
                    seats.add(seat);
                    System.out.println(seat.getIndex());
                }

                reservation = new Booking(id, customerid, flightid, seats, food);

            }
            //statement.close();
            return reservation;

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while getting the reservation:", ex);

        } catch (NullPointerException e) {
            throw new RuntimeException("You didn't supply a valid reservation ID", e);
        }

    }

    @Override
    public void createFlight(int airplaneID, int firstCost, int businessCost, int economyCost, String departurePlace, Timestamp departureTime, String arrivalPlace, Timestamp arrivalTime) {
        String sql = "INSERT INTO flights "
                + "VALUES (null, "
                + "" + airplaneID + ", "
                + "" + firstCost + ", "
                + "" + businessCost + ", "
                + "" + economyCost + ", "
                + "'" + departurePlace + "', "
                + "'" + departureTime + "', "
                + "'" + arrivalPlace + "', "
                + "'" + arrivalTime + "')";

        executeUpdate(sql);

    }

    @Override
    public Flight getFlight(int flightID) {
        try {
            String sql = "SELECT * FROM flights WHERE id = " + flightID;
            //pass query to query handler -> db. REMEMBER THAT THIS DOESN'T CLOSE DB CONNECTION, CLOSING IS PARAMOUNT!

            executeQuery(sql);
            while (results.next()) {
                int id = results.getInt("id");
                int airplaneID = results.getInt("airplane_id");
                int firstCost = results.getInt("firstcost");
                int businessCost = results.getInt("businesscost");
                int economyCost = results.getInt("economycost");
                String departurePlace = results.getString("departureplace");
                Timestamp departureTime = results.getTimestamp("departuretime");
                String arrivalPlace = results.getString("arrivalplace");
                Timestamp arrivalTime = results.getTimestamp("arrivaltime");

                flight = new Flight(id, airplaneID, firstCost, businessCost, economyCost, departurePlace, departureTime, arrivalPlace, arrivalTime);

            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return flight;
    }

    @Override
    public ArrayList<Booking> getCustomerReservations(int customerID) {

        String sql = "SELECT rsv.id "
                + "FROM `reservations` rsv "
                + "INNER JOIN customers cs "
                + "ON rsv.id = cs.id "
                + "WHERE rsv.id = " + customerID;
        //pass query to query handler -> db. REMEMBER THAT THIS METHOD DOESN'T CLOSE STATEMENTS , CLOSING IS PARAMOUNT!

        try {
            executeQuery(sql);
            while (results.next()) {
                Booking currentBooking = getReservation(results.getInt(1));
                reservations.add(currentBooking);

            }
            //statement.close();
            return reservations;

        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong while getting the reservation:", ex);

        } catch (NullPointerException e) {
            throw new RuntimeException("You didn't supply a valid reservation ID", e);
        }

    }

    @Override
    public ArrayList<Seat> getFlightBookedSeats(int flightID) {
        try {
            String sql = "SELECT s.seat_id FROM reservation2seat s, reservations r WHERE flightid =" + flightID + " AND  r.id = s.reservation_id";
            executeQuery(sql);
            while (results.next()) {
                Seat seat = new Seat(results.getInt(1));
                seats.add(seat);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return seats;
    }

    /*
     *   Below are unimplemented methods.
     *   Below are unimplemented methods.
     *   Below are unimplemented methods.
     *   Below are unimplemented methods.
     */
    @Override
    public void editFlight(int flightID, int airplaneID, int firstCost, int businessCost, int economyCost, String departurePlace, Timestamp departureTime, String arrivalPlace, Timestamp arrivalTime) {

        String sql = "UPDATE flights SET "
                + "airplane_id = " + airplaneID + ", "
                + "firstcost = " + firstCost + ", "
                + "businesscost = " + businessCost + ", "
                + "economycost = " + economyCost + ", "
                + "departuretime = '" + departurePlace + "', "
                + "departureplace = '" + departureTime + "', "
                + "arrivaltime = '" + arrivalTime + "', "
                + "arrivalplace = '" + arrivalPlace + "', "
                + "WHERE id = " + flightID;

        executeUpdate(sql);
    }

    @Override
    public void deleteFlight(int flightID) {
        String sql = "DELETE FROM flights WHERE id =" + flightID;
        executeUpdate(sql);
    }

    @Override
    public Airplane getAirplane(int airplaneID) {
        try {
            String sql = "SELECT * FROM airplanes WHERE id =" + airplaneID;
            executeQuery(sql);
            results.first();
            int id = results.getInt("id");
            int firstSeats = results.getInt("firstseats");
            int businessSeats = results.getInt("businessseats");
            int economySeats = results.getInt("economyseats");
            String fcSeatFormation = results.getString("fcseatformation");
            String bcSeatFormation = results.getString("bcseatformation");
            String ecSeatFormation = results.getString("ecseatformation");

            airplane = new Airplane(id, firstSeats, businessSeats, economySeats, fcSeatFormation, bcSeatFormation, ecSeatFormation);

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return airplane;
    }

}
