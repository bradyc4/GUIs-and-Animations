import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/* This program is a Calculator that creates a GUI for the user to use.
It has two buttons, "Calculate", and "Reset." 
Once an Expression has been entered into the textbox, the user can hit Calculate to get a result.
In order to reset the calculator back to 0.0, the user must hit the reset button.
The Calculator uses an expression evaluator object from the ExprEvaluator Class which reads an expression from left to right.
*/

public class Calculator extends JFrame implements ActionListener  
{
   //class constants 
   public static final int WIDTH = 700;
   public static final int HEIGHT = 300;
   public static final int NUMBER_OF_DIGITS = 30;

   //initialize private variables
   private JTextField ioField;
   private JTextField answerField;
   private double answer = 0.0;

   //main method - declares the calculator object and makes it visible
   public static void main(String[] args)
   {
      Calculator  calc = new Calculator();  //declare a calculator object
                  calc.pack();
                  calc.setVisible(true);
   }
   
   //constructor for Calculator
   public Calculator()
   {
      //settings
      setTitle("Expression Calculator");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
      setSize(WIDTH, HEIGHT);
      setLayout(new BorderLayout());

      
      /* This JPanel holds the two textfields at the top of the window. 
      The first textfield is for entering an expression, and the second 
      is for desplaying the answer to the expression. */
      JPanel   textPanel = new JPanel();
               textPanel.setLayout(new FlowLayout());

                  //create the first textfield where the user is supposed to enter an expression
                  ioField = new JTextField("Enter Expression", NUMBER_OF_DIGITS);
                  ioField.setBackground(Color.YELLOW);

                  //create the second textfield that displays the answer to the user
                  answerField = new JTextField("Answer", NUMBER_OF_DIGITS);
               
               //add the textfields to the panel
               textPanel.add(ioField);
               textPanel.add(answerField);

      // This JPanel is for displaying the instructions to the user.
      JPanel   instrPanel = new JPanel();
               instrPanel.setBounds(0,300,400,100);
               
               JTextArea instructions = new JTextArea(" This program is a Calculator that creates a GUI for the user to use.\n It has two buttons, \"Calculate\", and \"Reset.\" \n Once an Expression has been entered into the textbox,\n the user can hit Calculate to get a result.\n In order to reset the calculator back to 0.0,\n the user must hit the reset button.\n The Calculator uses an expression evaluator object from the ExprEvaluator Class\n which reads an expression from left to right.\n ");
               
               instrPanel.add(instructions);
        
      

        

      /* This JPanel holds two buttons, Calculate and Reset. 
      The first calculates the answer and the second resets 
      the answer text field to blank. */
      JPanel   buttonPanel = new JPanel();
               buttonPanel.setBackground(Color.GREEN);
               buttonPanel.setLayout(new FlowLayout());

               // creates button for calculating the answer
               JButton  addButton = new JButton("Calculate");
                        addButton.addActionListener(this);

               // creates button for resetting the answer text field
               JButton  reset = new JButton("Reset");
                        reset.addActionListener(this);
               
               // add the buttons to the panel
               buttonPanel.add(addButton);
               buttonPanel.add(reset);

      // add the JPanels to the Calculator JFrame
      add(textPanel, BorderLayout.NORTH);
      add(instrPanel, BorderLayout.WEST);
      add(buttonPanel, BorderLayout.CENTER);
   }


     public void actionPerformed(ActionEvent e)
     {
        try
        {
           assumingCorrectNumberFormats(e);
        }
        catch (NumberFormatException e2)
        {
           ioField.setText("Error: reenter value");
        }
     }

     public void assumingCorrectNumberFormats(ActionEvent e)
     {
       ExprEvaluator eval;
       String actionCommand = e.getActionCommand();
       if(actionCommand.equals("Calculate"))
       {
         eval = new ExprEvaluator(ioField.getText()); 
         answer = eval.evaluator();
         if(answer == (double)Math.round(answer))
         {
          answerField.setText(Integer.toString((int)answer));
         }
         else
         {
          answerField.setText(Double.toString(answer));
         }
       }
       else if(actionCommand.equals("Reset"))
       {
          answer = 0.0;
          answerField.setText("0.0");
       }
       else
          answerField.setText("error");
     }

     private static double stringToDouble(String stringObject)
     {
        return Double.parseDouble(stringObject.trim());
     }
     
      public static void p(Object s){
         System.out.println(s);
      }
}