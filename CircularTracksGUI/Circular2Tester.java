import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Circular2Tester
{
  public static void main(String[] args)
  {
    JFrame frame = new JFrame();

    final Circular2Shape shape = new Circular2Shape(0, 0, 600);

    ShapeIcon icon = new ShapeIcon(shape,600,600);

    final JLabel label = new JLabel(icon);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(Color.GREEN);
    buttonPanel.setLayout(new FlowLayout());
    JTextField carText = new JTextField("Enter car #");
    JTextField speedText = new JTextField("Enter Speed Mod");
    JButton setButton = new JButton("Set Speed");
    JButton directionButton = new JButton("Change Direction");
    buttonPanel.add(carText);
    buttonPanel.add(speedText);
    buttonPanel.add(setButton);
    buttonPanel.add(directionButton);

    ActionListener setActionListener = new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          try
          {
            int carnum = Integer.parseInt(carText.getText());
            int carspeed = Integer.parseInt(speedText.getText());
            if(carnum>=0 && carnum<20)
            {
              shape.changeSpeed(carnum,carspeed);
            }
          }
          catch(Exception p)
          {

          }
        }
      };
      setButton.addActionListener(setActionListener);

      ActionListener changeDirActionListener = new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          try
          {
            int carnum = Integer.parseInt(carText.getText());
            if(carnum>=0 && carnum<20)
            {
              shape.changeDirection(carnum);
            }
          }
          catch(Exception p)
          {

          }
        }
      };
      directionButton.addActionListener(changeDirActionListener);


    label.setBackground(new Color(51, 204,255));
    label.setOpaque(true);

    frame.setLayout(new BorderLayout());
    frame.add(label, BorderLayout.NORTH);
    frame.add(buttonPanel, BorderLayout.SOUTH);

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  
    final int DELAY = 50;
    Timer t = new Timer(DELAY, new 
       ActionListener()
       {
         public void actionPerformed(ActionEvent event)
         {
           label.repaint();
           shape.translate(1,1);
         }
       });
     t.start();
   }

}




