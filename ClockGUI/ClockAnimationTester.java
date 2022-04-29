import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;

//This is the ClockAnimationTester class which holds the main method.
//This class initializes a timer t. With every second that goes by, a new frame of Animation is made using the repaint method.

public class ClockAnimationTester
{
   public static void main(String[] args)
   {
     
     JFrame frame = new JFrame(); //declare a frame for the animation
     ClockShape shape = new ClockShape(0,0,100,0,0,0);
     
     ShapeIcon icon = new ShapeIcon(shape, 600, 600);   //The "shape" is a parameter to the ShapeIcon class, which will provide
                                                        //a mechanism for painting the shape "icon" (image).
                                                        //ShapeIcon is a class that implements the Java library interface Icon,
                                                        //which requires methods getIconWidth(), getIconHeight(), paintIcon.
      final JLabel label = new JLabel(icon); //a JLabel is created to hold the icon
      label.setBackground(new Color(51, 204,255)); //a light blue background color for the frame
      label.setOpaque(true);
      frame.setLayout(new BorderLayout()); //use FlowLayout for the components of the frame (but there is actually only one component)
      frame.add(label, BorderLayout.NORTH); //add the label component to the frame- it will fill the frame

      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      JPanel buttonPanel = new JPanel();
      buttonPanel.setBackground(Color.GREEN);
      buttonPanel.setLayout(new FlowLayout()); //buttons will "flow" accross the panel, left to right

        //create the start button component 

      JTextField hoursText = new JTextField("Enter Hours");
      buttonPanel.add(hoursText);
      JTextField minutesText = new JTextField("Enter Mins");
      buttonPanel.add(minutesText); 
      JButton setButton = new JButton("Set");
      buttonPanel.add(setButton);
      JButton resetButton = new JButton("Reset");
      buttonPanel.add(resetButton);
      
      frame.add(buttonPanel, BorderLayout.SOUTH);
      frame.pack();
      frame.setVisible(true); 
      
      final int DELAY = 1000; //the timer will tick second
      Timer t = new Timer(DELAY, new     //construct timer t and define its event handler anonymously
       ActionListener()
       {
         public void actionPerformed(ActionEvent event)
         {
           shape.translate(1,0); //these parameters are required by the definition of the Moveable interface, 
           label.repaint(); //with each tick of the timer, we repaint the frame, producing animation.
         }
       });

      ActionListener resetActionListener = new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          shape.resetClock();
          t.stop();
          t.start();
          label.repaint();
        }
      };
      resetButton.addActionListener(resetActionListener);

      ActionListener setActionListener = new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          try
          {
            int hours = Integer.parseInt(hoursText.getText());
            int mins = Integer.parseInt(minutesText.getText());
            shape.setClock(hours,mins);
            t.stop();
            t.start();
            label.repaint();
          }
          catch(Exception p)
          {

          }
        }
      };
      setButton.addActionListener(setActionListener);
     
     t.start(); //start the animation
   }

   private static final int SpecialWidth = 100;
}