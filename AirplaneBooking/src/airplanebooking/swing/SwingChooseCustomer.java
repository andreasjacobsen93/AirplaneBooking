package airplanebooking.swing;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import airplanebooking.GUI;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Andreas
 */
public class SwingChooseCustomer extends JFrame implements GUI, ActionListener {
    
    public SwingChooseCustomer()
    {
        setTitle("AirplaneBooking");
        setSize(300, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public void loadFrame()
    {
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        /*
         * if (e.getSource() == button1)
         * {
         *  label1.setText("button1 clicked!");
         * }
         */
    }
}
