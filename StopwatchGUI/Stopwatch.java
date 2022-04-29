import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JTextArea;


/* This is a stopwatch program, it keeps track of time passing in hundredths of a second and every 
measurement up to and including hours. It displays each number in a digital clock format:
(00:00:00:00:00) there are 3 buttons, start, stop, and reset. Start begins the stop watch. 
Stop pauses the stop watch. Reset resets the stop watch.*/

public class Stopwatch extends JFrame //since Stopwatch is a subclass of JFrame, all of the JFrame properties and
                                      //methods are available to a Stopwatch. In particular, the Stopwatch constructor
                                      //can use the JFrame "add" method, for adding components to a JFrame.
{
     public static final Color VERY_LIGHT_BLUE = new Color(51,204,255);

      //frame specifications
      final int FIELD_WIDTH =20;

      //member variables for a Stopwatch object
      private JTextField seconds;
      private JTextField minutes;
      private JTextField tenthsSeconds;
      private int hour;
      private int min;
      private int sec;
      private int tenth;
      private int hundredth;
      private String hourString;
      private String minString;
      private String secString;
      //constructor for a Stopwatch object
      public Stopwatch()
      {


        //frame settings
        setTitle("Stopwatch");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setSize(30, 30);
        setLayout(new BorderLayout());

        //textPanel with 6 components for the three time textFields and their descriptions
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(1,1));//using a grid layout with 3 rows, 2 columns

        //initialize the time variables
         hour = 0;
         min = 0;
         sec = 0;
         tenth = 0;
          hundredth = 0;
          secString = "0"+sec;
          minString = "0"+min;
          hourString = "0"+hour;

         //textPanel component 1,1 for minutes
         minutes = new JTextField(" "+hourString+" : "+minString+" : "+secString+" : 0"+tenth+" : 0"+hundredth, 24);
         minutes.setEditable(false);
         minutes.setBackground(Color.YELLOW);
         Font font = new Font("Courier", Font.BOLD, 40);
         minutes.setFont(font);
         textPanel.add(minutes); //add the textField to the textPanel grid

         add(textPanel, BorderLayout.NORTH); //add the entire panel to the frame from the top of the frame

        final int DELAY = 10; //delay constant is 100/1000 milliseconds = 1/10 second
        Timer t = new Timer(DELAY, new ActionListener()
        {
          public void actionPerformed(ActionEvent event)
          {
            hundredth = hundredth + 1;
            if(hundredth ==  10) //each new full second, increment the seconds counter and reset the tenths counter
            {
              hundredth = 0;
              tenth = tenth + 1;
              if(tenth ==  10) //each new full second, increment the seconds counter and reset the tenths counter
              {
                tenth = 0;
                sec = sec + 1;
                if(sec < 10)
                  secString = "0"+sec;
                else
                  secString = Integer.toString(sec);
                if(sec == 60) //each new full minute, increment the minutes counter and reset the seconds counter
                { 
                  secString = "00";
                  sec = 0;
                  min = min +1;
                  if(min < 10)
                    minString = "0"+min;
                  else
                    minString = Integer.toString(min);
                  if(min == 60)
                  {
                    minString = "00";
                    min = 0;
                    hour = hour + 1;
                    if(hour < 10)
                      hourString = "0"+hour;
                    else
                      hourString = Integer.toString(hour);
                  }
                }    
              } 
            } //each tenth of a second, increment the tenths counter mod 10
            
            minutes.setText(" "+hourString+" : "+minString+" : "+secString+" : 0"+tenth+" : 0"+hundredth); //display tenths
          }
        }); //Construct a timer object with delay 1/10 sec. 
                                              //The event handler "listener" will process
                                              //each timer update event every tenth of a second.
                                              //Because the code for this is a bit lengthy,
                                              //the detailed code for "listener" is given later,
                                              //so as not to clutter up the constructor.

        //create  a JPanel for command buttons for start, stop, reset
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.GREEN);
        buttonPanel.setLayout(new FlowLayout()); //buttons will "flow" accross the panel, left to right

        //create the start button component  
        JButton startButton = new JButton("Start");
        
        //The response to clicking the start button is to simply start the timer; since this action
        //can be coded so simply, the event handler is defined "anonymously", 
        //without declaring a named ActionListener interface object.
        //Instead, we simply provide the code for the method actionPerformed(ActionEvent e)
        //which the ActionListener interface requires, and we add the event handler to the button,   
        //using addActionListener, all in one step:

        ActionListener startActionListener = new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
           {            
            t.start();//start the timer
           } 
        };
  
        startButton.addActionListener(startActionListener);

        //now add the button to the panel
        buttonPanel.add(startButton);



        //the stop button is designed similarly:

        JButton stopButton = new JButton("Stop");

        ActionListener stopActionListener = new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {             
            t.stop();//stop the timer
          }
        };
        stopButton.addActionListener(stopActionListener);

        buttonPanel.add(stopButton);

         //to handler the "reset" button click, stop the timer, blank out the time fields,
         //and reset the time variables to 0:
        ActionListener resetActionListener = new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            t.stop();
            hour=0;
            min=0;
            sec=0;
            tenth=0;
            hundredth=0;
            secString = "0"+sec;
            minString = "0"+min;
            hourString = "0"+hour;
            minutes.setText(hourString+" : "+minString+" : "+secString+" : 0"+tenth+" : 0"+hundredth);
          }
        };
        
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(resetActionListener);

        buttonPanel.add(resetButton);

        //add the button panel component to the frame
        add(buttonPanel, BorderLayout.CENTER);
  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();  
 
     }//this concludes the constructor for a Stopwatch object

     //We still have to describe the event handler for the timer, 
     //to give the response whenever there is a timer event
     //corresponding to the passing of another 1/10 second time interval:
 
   public static void main(String[] args)
   {
     Stopwatch sw = new Stopwatch(); //create a new Stopwatch object
     sw.pack();
     sw.setVisible(true); //make the stopwatch visible to the user,
                          //who can then trigger button click events.
   }

}    
