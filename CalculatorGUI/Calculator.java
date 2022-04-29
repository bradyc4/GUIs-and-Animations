import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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

   private JTextField ioField;
   private JTextField answerField;
   private double answer = 0.0;

   public static void main(String[] args)
   {
     Calculator calc = new Calculator();  //declare a calculat object
     calc.setVisible(true);
   }
     //constructor for Calculator
     public Calculator()
     {
        setTitle("Expression Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout());

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new FlowLayout());
        ioField = new JTextField("Enter Expression", NUMBER_OF_DIGITS);
        ioField.setBackground(Color.YELLOW);
        textPanel.add(ioField); //add ioField as a component of the panel
        add(textPanel, BorderLayout.NORTH);

        JPanel instrPanel = new JPanel();
        instrPanel.setBounds(0,300,400,100);
        JTextArea instructions = new JTextArea(" This program is a Calculator that creates a GUI for the user to use.\n It has two buttons, \"Calculate\", and \"Reset.\" \n Once an Expression has been entered into the textbox,\n the user can hit Calculate to get a result.\n In order to reset the calculator back to 0.0,\n the user must hit the reset button.\n The Calculator uses an expression evaluator object from the ExprEvaluator Class\n which reads an expression from left to right.\n ");
        instrPanel.add(instructions);
        add(instrPanel, BorderLayout.WEST);

        answerField = new JTextField("Answer", NUMBER_OF_DIGITS);
        textPanel.add(answerField);

        //create  a JPanel for command buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.GREEN);
        buttonPanel.setLayout(new FlowLayout());

        //add button components to buttonPanel
        JButton addButton = new JButton("Calculate");
        addButton.addActionListener(this);
        buttonPanel.add(addButton);

        JButton reset = new JButton("Reset");
        reset.addActionListener(this);
        buttonPanel.add(reset);

        //include the button panel component
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
          answerField.setText(Integer.toString((int)answer));
         else
          answerField.setText(Double.toString(answer));
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
}  










