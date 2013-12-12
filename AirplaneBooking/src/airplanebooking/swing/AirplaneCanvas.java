package airplanebooking.swing;

import airplanebooking.CurrentBooking; 
import airplanebooking.BookingListener;
import airplanebooking.CurrentFlight;
import airplanebooking.DB.Airplane;
import airplanebooking.DB.Database;
import airplanebooking.DB.Flight;
import airplanebooking.DB.Seat;
import airplanebooking.SeatListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Cursor;
import java.util.ArrayList;

/**
 * AirplaneCanvas is used to draw the airplane on a JComponent.
 * @author Andreas Jacobsen
 */
public final class AirplaneCanvas extends javax.swing.JComponent implements BookingListener 
{ 
    // The flight to mark the seats for.
    private Flight flight;
    
    // The airplane to draw.
    private Airplane airplane;
    
    // Economy Class
    private Boolean EClass;
    private int EseatGroups;
    private int EseatLength;
    private int ErowSeats;
    private int EseatSize;
    
    // Business Class
    private Boolean BClass;
    private int BseatGroups;
    private int BseatLength;
    private int BrowSeats;
    private int BtotalSeats;
    private int BseatSize;
    
    // First Class
    private Boolean FClass;
    private int FseatGroups;
    private int FseatLength;
    private int FrowSeats;
    private int FtotalSeats;
    private int FseatSize;
    
    // Initial space in x- and y-direction
    private int iniX;
    private int iniY;
    
    // array of seats and current seat.
    private int[][] seats;
    private int seat;
    
    // 
    public int seatNumber;
    public String seatClass;
    private int seatsCount;
    public Boolean bookable;
    
    // X- and y-coordinate for the mouse.
    public int x;
    public int y;
    
    // List of event subscribers
    private static final ArrayList<SeatListener> listeners = new ArrayList<>();
    
    /**
     * Constructor used to initialze everything.
     */
    public AirplaneCanvas()
    {
        CurrentBooking.reset();
        reset();
    }
    
    private void reset()
    {
        bookable = false;
        
        flight = null;
        airplane = null;
        
        // Economy Class
        EseatGroups = 0;
        EseatLength = 0;
        ErowSeats = 0;
        EseatSize = 0;
        EClass = EseatGroups > 0 && EseatLength > 0 && ErowSeats > 0;
        
        // Business Class
        BseatGroups = 0;
        BseatLength = 0;
        BrowSeats = 0;
        BseatSize = 0;
        BClass = BseatGroups > 0 && BseatLength > 0 && BrowSeats > 0;
        BtotalSeats = BseatGroups*BseatLength*BrowSeats;
        
        // First Class
        FseatGroups = 0;
        FseatLength = 0;
        FrowSeats = 0;
        FseatSize = 0;
        FClass = FseatGroups > 0 && FseatLength > 0 && FrowSeats > 0;
        FtotalSeats = FseatGroups*FseatLength*FrowSeats;
        
        // Reset current seat values
        seatNumber = 0;
        seatClass = "";
        
        // Drawing start offset
        iniX = 0;
        iniY = 0;
        
        // All seats available
        seatsCount = 0;
        seats = new int[10][6];
        seat = 0;
    }
    
    /**
     * This method sets the airplane canvas with everything
     * used to draw the airplane.
     * @param isBookable Whether seat can be clicked for booking or not as boolean.
     * @param flight The flight to draw for as flight object.
     * @see Flight
     */
    public void setAirplaneCanvas(Boolean isBookable, Flight flight)
    {
        reset();
        CurrentBooking.reset();
        
        // Add flight to current booking
        CurrentBooking.addFlight(flight);
        
        // Set whether flight is bookable or not
        this.bookable = isBookable;
        
        // Set flight and airplane variables
        this.flight = flight;
        airplane = flight.getAirplane();
        
        // Get seat formations
        String[] ec = airplane.getECSeatFormation().split(":");
        String[] bc = airplane.getBCSeatFormation().split(":");
        String[] fc = airplane.getFCSeatFormation().split(":");
        
        // Economy Class
        EseatLength = Integer.parseInt(ec[0]);
        EseatGroups = Integer.parseInt(ec[1]);
        ErowSeats = Integer.parseInt(ec[2]);
        EseatSize = 10;
        EClass = EseatGroups > 0 && EseatLength > 0 && ErowSeats > 0;
        
        // Business Class
        BseatLength = Integer.parseInt(bc[0]);
        BseatGroups = Integer.parseInt(bc[1]);
        BrowSeats = Integer.parseInt(bc[2]);
        BseatSize = 12;
        BClass = BseatGroups > 0 && BseatLength > 0 && BrowSeats > 0;
        BtotalSeats = BseatGroups*BseatLength*BrowSeats;
        
        // First Class
        FseatLength = Integer.parseInt(fc[0]);
        FseatGroups = Integer.parseInt(fc[1]);
        FrowSeats = Integer.parseInt(fc[2]);
        FseatSize = 15;
        FClass = FseatGroups > 0 && FseatLength > 0 && FrowSeats > 0;
        FtotalSeats = FseatGroups*FseatLength*FrowSeats;
        
        // Reset current seat values
        seatNumber = 0;
        seatClass = "";
        
        // Drawing start offset
        iniX = 10;
        iniY = 10;
        
        // All seats available
        seatsCount = (EseatGroups*EseatLength*ErowSeats)+(BseatGroups*BseatLength*BrowSeats)+(FseatGroups*FseatLength*FrowSeats);
        seats = new int[seatsCount][6];
        seat = 0;
        
        // Create data for seats
        int i = 0;
        for (int[] s : seats) {
            s[0] = i++;
        }
        
        if (bookable == true)
        {
            // Go through all blocked seats
            for (Seat s : CurrentBooking.getBlockedSeats())
            {
                seats[s.getSeatID()-1][1] = 1;
            }
            
            // Go through all booked seats
            for (Seat s : CurrentBooking.getBookedSeats())
            {
                seats[s.getSeatID()-1][1] = 2;
            }
        }
        else
        {
            for (Seat s : Database.db().getFlightBookedSeats(flight.getID()))
            {
                seats[s.getSeatID()-1][1] = 1;
            }
            System.out.println(flight.getID() + ": " +Database.db().getFlightBookedSeats(flight.getID()));
        }
        
        
        // Event for mouse movement
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                x = e.getX(); y = e.getY();
                
                Boolean isInside = false;
                for (int i = 0; i < seatsCount; i++)
                {
                    // Check if mouse is inside a seat
                    if (x > seats[i][2] && y > seats[i][3] && x < seats[i][4] && y < seats[i][5])
                    {
                        isInside = true;
                        if (bookable == true)
                        {
                            if (seats[i][1] == 2)
                            {
                                // Blue
                                changeCursor(new Cursor(Cursor.HAND_CURSOR));
                            }
                            else if (seats[i][1] == 0)
                            {
                                // Green
                                changeCursor(new Cursor(Cursor.HAND_CURSOR));
                            }
                            else
                            {
                                changeCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                            }
                        }
                        else
                        {
                            if (seats[i][1] == 1)
                            {
                                // Red
                                changeCursor(new Cursor(Cursor.HAND_CURSOR));
                            }
                            else
                            {
                                changeCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                            }
                        }
                        
                        // Current seat
                        seatNumber = i+1;
                        
                        // Current seat is First Class
                        if (FClass && FtotalSeats > i)
                        {
                            seatClass = "First Class";
                        }
                        // Current seat is Business Class
                        else if (BClass && FtotalSeats + BtotalSeats > i)
                        {
                            seatClass = "Business Class";
                        }
                        // Current seat is Economy Class
                        else if (EClass && seatsCount > i)
                        {
                            seatClass = "Economy Class";
                        }
                        
                        break;
                    }
                }
                
                if (!isInside)
                {
                    // If nothing is found reset
                    seatNumber = 0;
                    seatClass = "";
                    changeCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
                
                repaint();
            }
        });
        
        // If bookable add AirplaneCanvas to booking listeners
        if(bookable == true) addToBookingListeners();
        
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                x = e.getX(); y = e.getY();

                for (int i = 0; i < seatsCount; i++)
                {
                    if (x > seats[i][2] && y > seats[i][3] && x < seats[i][4] && y < seats[i][5])
                    {
                        clickSeat(i);
                    }
                }
            }
        });
        
        repaint();
    }
    
    /**
     * This method changes the mouse cursor
     * @param c Cursor to change to
     * @see Cursor
     */
    public void changeCursor(Cursor c)
    {
        this.setCursor(c);
    }
    
    /**
     * This method checks if airplane if fully booked.
     * @return Whether airplane is fully booked or not as boolean.
     */
    public Boolean airplaneIsFull()
    {
        int c = 0;
        for (int i = 0; i < seatsCount; i++)
        {
            if (seats[i][1] == 1) c++;
        }
        return c >= seatsCount;
    }
    
    /**
     * This method send an update notify to all listeners.
     */
    public void updated() {
        // Notify everybody that may be interested.
        for (SeatListener sl : listeners) {
            sl.seatChanged();
        }
    }
    
    /**
     * This method adds AirplaneCanvas to CurrentBooking as listener.
     */
    private void addToBookingListeners()
    {
        CurrentBooking.addListener(this);
    }
    
    /**
     * This method make a virtual click on a list of seats.
     * @param list Array list of seat objects.
     * @see Seat
     */
    public void clickSeats(ArrayList<Seat> list)
    {
        for (int[] s : seats) {
            if(s[1] == 3) s[1] = 1;
        }
        
        for (Seat i : list)
        {
            if (bookable == false)
            {
                if (seats[i.getSeatID()-1][1] == 1)
                {
                    // Red
                    seats[i.getSeatID()-1][1] = 3;
                }
            }
        }
        
        repaint();
    }
    
    /**
     * This method makes a virtual click on a seat.
     * @param i The seat number to click on.
     */
    public void clickSeat(int i)
    {
        if (bookable == true)
        {
            if (seats[i][1] == 2)
            {
                // Blue
                // Remove booking
                CurrentBooking.removeSeat(i+1);
            }
            else if (seats[i][1] == 0)
            {
                // Green
                // Add booking
                CurrentBooking.addSeat(new Seat(i+1));
            }
        }
        else
        {
            if (seats[i][1] == 1)
            {
                for (int s = 0; s < seatsCount; s++)
                {
                    if (seats[s][1] == 3) seats[s][1] = 1;
                }
                // Red
                seats[i][1] = 3;
                CurrentFlight.setSeat(new Seat(i+1));
                updated();
            }
        }
        repaint();
    }
    
    /**
     * This method paints the airplane.
     * @param g
     * @see Graphics
     */
    @Override
    public void paint(Graphics g)
    {
        // Set default drawing color
        g.setColor(Color.black);
        // Draw rectangle around area
        g.drawRect(0, 0, getWidth()-1, getHeight()-1);
        // If a seat is hovered, show info
        if(seatNumber > 0) g.drawString(seatClass + " Seat: " + seatNumber, 4, 12);
        
        // Set drawing offset based on window size
        iniX = offsetX();
        iniY = offsetY();
        seat = 0;
        
        // Draw airplane for all classes
        if (FClass) drawClass(g, "first");
        if (BClass) drawClass(g, "business");
        if (EClass) drawClass(g, "economy");
    }
    
    /**
     * This method draws the correspondent class.
     * @param g
     * @see Graphics
     * @param cl Class to draw as string.
     */
    private void drawClass(Graphics g, String cl)
    {
        // Set default values
        int seatLength = 0;
        int seatGroups = 0;
        int rowSeats = 0;
        int seatSize = 0;
        int rowSpace = 0;
        int initialY = offsetY();
        
        // Check which class to draw now
        // and change variables to match class
        switch(cl)
        {
            case "first":
                seatLength = FseatLength;
                seatGroups = FseatGroups;
                rowSeats = FrowSeats;
                seatSize = FseatSize;
                break;
            case "business":
                seatLength = BseatLength;
                seatGroups = BseatGroups;
                rowSeats = BrowSeats;
                seatSize = BseatSize;
                break;
            case "economy":
                seatLength = EseatLength;
                seatGroups = EseatGroups;
                rowSeats = ErowSeats;
                seatSize = EseatSize;
                break;
        }
        
        iniY = initialY;
        
        // If there is only one group of seats
        if (seatGroups == 1)
        {
            // Place seat in middle
            iniY = initialY + (((getClassHeight(getBiggestClass())) - getClassHeight(cl)) / 2);
        }
        else
        {
            // Place seats closest to the windows
            if (cl.equals(getBiggestClass())) rowSpace = 15;
            else rowSpace = 15 + (((getClassHeight(getBiggestClass())) - getClassHeight(cl)) / (seatGroups - 1));
        }
        
        // column number
        int l = 0;
        // row number
        int r = 0;
        // seat number
        int s = 0;
        
        // go through each column
        for (int li = 0; li < seatLength; li++)
        {
            // go through each row
            for (int ri = 0; ri < seatGroups; ri++)
            {
                // go through each seat
                for (int si = 0; si < rowSeats; si++)
                {
                    // Ff seat is booked show red
                    // else show green
                    if (seats[seat][1] == 0)
                    {
                        g.setColor(Color.green);
                    }
                    else if (seats[seat][1] == 1)
                    {
                        g.setColor(Color.red);
                    }
                    else if (seats[seat][1] == 2)
                    {
                        g.setColor(Color.blue);
                    }
                    else if (seats[seat][1] == 3)
                    {
                        g.setColor(Color.orange);
                    }
                    g.fillRect(((l*(seatSize+5))+iniX), ((s*(seatSize+5))-5)+(r*rowSpace)+iniY, seatSize, seatSize);
                    
                    // Set seat coordinates
                    seats[seat][2] = (l*(seatSize+5))+iniX;
                    seats[seat][3] = ((s*(seatSize+5))-5)+(r*rowSpace)+iniY;
                    seats[seat][4] = ((l*(seatSize+5))+iniX)+seatSize;
                    seats[seat][5] = (((s*(seatSize+5))-5)+(r*rowSpace)+iniY)+seatSize;
                    
                    s++;
                    seat++;
                }
                r++;
            }
            s = 0;
            r = 0;
            l++;
        }
        
        // Set x-offset for next class-drawing
        iniX = iniX+(l*15)+30;
    }
    
    /**
     * This method gets the highest (in pixel) class.
     * @return The class as string (economy/business/first).
     */
    private String getBiggestClass()
    {
        int EconomyClassHeight = getClassHeight("economy");
        int BusinessClassHeight = getClassHeight("business");
        int FirstClassHeight = getClassHeight("first");
        
        // 
        if (EconomyClassHeight > FirstClassHeight)
        {
            if (EconomyClassHeight > BusinessClassHeight)
            {
                return "economy"; // return economy class as the highest class
            }
            else
            {
                return "business"; // return business class as the highest class
            }
        }
        else
        {
            if (FirstClassHeight > BusinessClassHeight)
            {
                return "first"; // return first class as the highest class
            }
            else
            {
                return "business"; // return business class as the highest class
            }
        }        
    }
    
    /**
     * This method gets the height of a class in pixel.
     * @param cl Class to get height of.
     * @return The height of the class as integer.
     */
    private int getClassHeight(String cl)
    {
        switch(cl)
        {
            case "first":
                return (FseatGroups * ((FrowSeats*(FseatSize+5)))) + (15*(FseatGroups - 1));
            case "business":
                return (BseatGroups * ((BrowSeats*(BseatSize+5)))) + (15*(BseatGroups - 1));
            case "economy":
                return (EseatGroups * ((ErowSeats*(EseatSize+5)))) + (15*(EseatGroups - 1));
        }
        return 0;
    }
    
    /**
     * This method gets the class for a given seat number.
     * @param s The seat number as integer.
     * @return The class as string (f/b/e).
     */
    public String getClassType(int s)
    {
        if (FClass && FtotalSeats > s)
        {
            return "f";
        }
        // Current seat is Business Class
        else if (BClass && FtotalSeats + BtotalSeats > s)
        {
            return "b";
        }
        // Current seat is Economy Class
        else if (EClass && seatsCount > s)
        {
            return "e";
        }
        return "error";
    }
    
    /**
     * This method gets the length in pixel of the airplane (all of the seats).
     * @return The length in pixel as integer.
     */
    private int getAirplaneLength()
    {
        int l = 0;
        
        if (FClass) l += ((FseatLength * (FseatSize+5))-5) + 30;
        if (BClass) l += ((BseatLength * (BseatSize+5))-5) + 30;
        if (EClass) l += ((EseatLength * (EseatSize+5))-5);
        
        return l;
    }
    
    /**
     * This method gets the x-offset to draw after.
     * @return Offest in integer.
     */
    private int offsetX()
    {
        return (getWidth() - getAirplaneLength()) / 2;
    }
    
    /**
     * This method gets y-offet to draw after.
     * @return Offset in integer.
     */
    private int offsetY()
    {
        return (getHeight() - getClassHeight(getBiggestClass())) / 2;
    }

    /**
     * This method is called when a booking changed event has been fired.
     */
    @Override
    public void bookingChanged() 
    {
        // Go through all seats and set them to free.
        for (int[] s : seats) {
            s[1] = 0;
        }

        if (bookable == true)
        {
            // Go through all blocked seats and mark them red.
            for (Seat s : CurrentBooking.getBlockedSeats())
            {
                seats[s.getSeatID()-1][1] = 1;
            }
            
            // Go through all seats which are blocked but allowed to be booked anyway.
            for (Seat s : CurrentBooking.getAllowedSeats())
            {
                seats[s.getSeatID()-1][1] = 0;
            }
            
            // Go through all booked seats and mark them blue.
            for (Seat s : CurrentBooking.getBookedSeats())
            {
                seats[s.getSeatID()-1][1] = 2;
            }
        }
        else
        {
            for (Seat s : Database.db().getFlightBookedSeats(flight.getID()))
            {
                seats[s.getSeatID()-1][1] = 1;
            }
        }

        repaint();
    }
    
    /**
     * This method adds a listener to the SeatListener list.
     * @param listener Listener as SeatListener.
     */
    public void addListener(SeatListener listener) {
        listeners.add(listener);
    }
}
