package airplanebooking.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 *
 * @author Andreas
 */
public class AirplaneCanvas extends javax.swing.JComponent {
    
    // Economy Class
    private final Boolean EClass;
    private final int EseatGroups;
    private final int EseatLength;
    private final int ErowSeats;
    private final int EseatSize;
    
    // Business Class
    private final Boolean BClass;
    private final int BseatGroups;
    private final int BseatLength;
    private final int BrowSeats;
    private final int BtotalSeats;
    private final int BseatSize;
    
    // First Class
    private final Boolean FClass;
    private final int FseatGroups;
    private final int FseatLength;
    private final int FrowSeats;
    private final int FtotalSeats;
    private final int FseatSize;
    
    private int iniX;
    private int iniY;
    
    private final int[][] seats;
    private int seat;
    
    public int seatNumber;
    public String seatClass;
    
    public int x;
    public int y;
    
    public AirplaneCanvas()
    {
        // Economy Class
        EseatGroups = 2;
        EseatLength = 20;
        ErowSeats = 3;
        EseatSize = 10;
        EClass = EseatGroups > 0 && EseatLength > 0 && ErowSeats > 0;
        
        // Business Class
        BseatGroups = 2;
        BseatLength = 5;
        BrowSeats = 2;
        BseatSize = 12;
        BClass = BseatGroups > 0 && BseatLength > 0 && BrowSeats > 0;
        BtotalSeats = BseatGroups*BseatLength*BrowSeats;
        
        // First Class
        FseatGroups = 2;
        FseatLength = 2;
        FrowSeats = 1;
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
        final int seatsCount = (EseatGroups*EseatLength*ErowSeats)+(BseatGroups*BseatLength*BrowSeats)+(FseatGroups*FseatLength*FrowSeats);
        seats = new int[seatsCount][6];
        seat = 0;
        
        // Create random data for seats
        for (int i = 0; i < seatsCount; i++)
        {
            Random rand = new Random();
            seats[i][0] = i+1;
            seats[i][1] = rand.nextInt(2);
        }
        
        // Event for mouse movement
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                x = e.getX(); y = e.getY();
                repaint();

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
            }
        });
        
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                
            }
        });
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
                        g.fillRect(((l*(seatSize+5))+iniX), ((s*(seatSize+5))-5)+(r*rowSpace)+iniY, seatSize, seatSize);
                    }
                    else
                    {
                        g.setColor(Color.green);
                        g.fillRect(((l*(seatSize+5))+iniX), ((s*(seatSize+5))-5)+(r*rowSpace)+iniY, seatSize, seatSize);
                    }
                    
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
    
    // Get the length of all the seats in pixel
    private int getAirplaneLength()
    {
        int l = 0;
        
        if (FClass) l = l + ((FseatLength * (FseatSize+5))-5) + 30;
        if (BClass) l = l + ((BseatLength * (BseatSize+5))-5) + 30;
        if (EClass) l = l + ((EseatLength * (EseatSize+5))-5);
        
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
}