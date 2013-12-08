package airplanebooking.swing;

import airplanebooking.CurrentBooking; 
import airplanebooking.BookingListener;
import airplanebooking.CurrentFlight;
import airplanebooking.DB.Airplane;
import airplanebooking.DB.DatabaseHandler;
import airplanebooking.DB.DatabaseInterface;
import airplanebooking.DB.Flight;
import airplanebooking.SeatListener;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Andreas
 */
public final class AirplaneCanvas extends javax.swing.JComponent implements BookingListener {
    
    private Flight flight;
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
    
    private int iniX;
    private int iniY;
    
    private int[][] seats;
    private int seat;
    
    public int seatNumber;
    public String seatClass;
    private int seatsCount;
    public Boolean bookable;
    
    public int x;
    public int y;
    
    // List of event subscribers
    private static final ArrayList<SeatListener> listeners = new ArrayList<>();
    
    public AirplaneCanvas()
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
        seats = new int[0][0];
        seat = 0;
    }
    
    public void setAirplaneCanvas(final Boolean bookable, Flight flight)
    {
        CurrentBooking.reset();
        this.bookable = bookable;
        
        this.flight = flight;
        DatabaseInterface db = new DatabaseHandler();
        airplane = flight.getAirplane();
        
        String[] ec = airplane.getECSeatFormation().split(":");
        String[] bc = airplane.getBCSeatFormation().split(":");
        String[] fc = airplane.getFCSeatFormation().split(":");
        
        // Economy Class
        EseatGroups = Integer.parseInt(ec[1]);
        EseatLength = Integer.parseInt(ec[0]);
        ErowSeats = Integer.parseInt(ec[2]);
        EseatSize = 10;
        EClass = EseatGroups > 0 && EseatLength > 0 && ErowSeats > 0;
        
        // Business Class
        BseatGroups = Integer.parseInt(bc[1]);
        BseatLength = Integer.parseInt(bc[0]);
        BrowSeats = Integer.parseInt(bc[2]);
        BseatSize = 12;
        BClass = BseatGroups > 0 && BseatLength > 0 && BrowSeats > 0;
        BtotalSeats = BseatGroups*BseatLength*BrowSeats;
        
        // First Class
        FseatGroups = Integer.parseInt(fc[1]);
        FseatLength = Integer.parseInt(fc[0]);
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
        for (int i = 0; i < seatsCount; i++)
        {
            seats[i][0] = i+1;
        }
        
        CurrentBooking.addFlight(flight);
        
        // Event for mouse movement
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                x = e.getX(); y = e.getY();

                for (int i = 0; i < seatsCount; i++)
                {
                    if (x > seats[i][2] && y > seats[i][3] && x < seats[i][4] && y < seats[i][5])
                    {
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
                    else
                    {
                        // If nothing is found reset
                        seatNumber = 0;
                        seatClass = "";
                    }
                }
                
                repaint();
            }
        });

        addToBookingListeners();
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                x = e.getX(); y = e.getY();

                for (int i = 0; i < seatsCount; i++)
                {
                    if (x > seats[i][2] && y > seats[i][3] && x < seats[i][4] && y < seats[i][5])
                    {
                        if (seats[i][1] == 0)
                        {
                            // Red
                            CurrentFlight.setSeat(i);
                            updated();
                        }
                        
                        if (bookable == true)
                        {
                            if (seats[i][1] == 2)
                            {
                                // Blue
                                // Remove booking
                                // seats[i][1] = 1;
                                CurrentBooking.removeSeat(i+1);
                            }
                            else
                            {
                                // Green    
                                // Add booking
                                // seats[i][1] = 2;
                                CurrentBooking.addSeat(i+1);
                            }
                        }
                        
                    }
                }

                repaint();
            }
        });
        
        repaint();
    }
    
    public static void updated() {
        // Notify everybody that may be interested.
        for (SeatListener sl : listeners) {
            sl.seatChanged();
        }
    }
    
    private void addToBookingListeners()
    {
        CurrentBooking.addListener(this);
    }
    
    public void clickSeat(int i)
    {
        if (seats[i][1] == 0)
        {
            // Red
            CurrentFlight.setSeat(i);
            updated();
        }
        else if (seats[i][1] == 2)
        {
            // Blue
            // Remove booking
            seats[i][1] = 1;
            CurrentBooking.removeSeat(i+1);
        }
        else
        {
            // Green    
            // Add booking
            seats[i][1] = 2;
            CurrentBooking.addSeat(i+1);
        }
        repaint();
    }
    
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
                        g.setColor(Color.red);
                    }
                    else if (seats[seat][1] == 2)
                    {
                        g.setColor(Color.blue);
                    }
                    else
                    {
                        g.setColor(Color.green);
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
    
    // Get the highest (in pixel) class.
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
    
    // Get the height of the class in pixel
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
    
    // Get the length of all the seats in pixel
    private int getAirplaneLength()
    {
        int l = 0;
        
        if (FClass) l += ((FseatLength * (FseatSize+5))-5) + 30;
        if (BClass) l += ((BseatLength * (BseatSize+5))-5) + 30;
        if (EClass) l += ((EseatLength * (EseatSize+5))-5);
        
        return l;
    }
    
    private int offsetX()
    {
        return (getWidth() - getAirplaneLength()) / 2;
    }
    
    private int offsetY()
    {
        return (getHeight() - getClassHeight(getBiggestClass())) / 2;
    }

    @Override
    public void bookingChanged() {

        for (int i = 0; i < seatsCount; i++)
        { 
            if (CurrentBooking.isBlocked(i+1)) seats[i][1] = 0;
            if (CurrentBooking.isBooked(i+1) && bookable == true) seats[i][1] = 2;
            else seats[i][1] = 1;
        }

        repaint();
    }
    
    public void addListener(SeatListener listener) {
        listeners.add(listener);
    }
}
