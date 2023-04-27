import java.util.Scanner;

public class ExprEvaluator2
{
    Scanner kb = new Scanner(System.in);
    private static Stack1gen<Character> stack_operators = new Stack1gen<Character>();
    private static Stack1gen<Double> stack_numbers = new Stack1gen<Double>();

    private static void eval()
    {
        char char_operator = stack_operators.pop();
        double double_value = 0.0;
        double opnd2 = stack_numbers.pop();
        double opnd1; 
        if(stack_numbers.getSize()>0)
            opnd1 = stack_numbers.pop();
        else
            opnd1 = 0;
        switch (char_operator)
        {
            case '+':
                double_value = opnd1 + opnd2;
                break;
            case '-':
                double_value = opnd1 - opnd2;
                break;
            case '*':
                double_value = opnd1 * opnd2;
                break;
            case '/':
                double_value = opnd1/opnd2;
                break;
            case '^':
                double_value = Math.pow(opnd1,opnd2);
                break;
            case '(':
                double_value = opnd2;
                break;
        }
        stack_numbers.push(double_value);
    }

    private static void evalDown()
    {
        do
        {
            eval();
        }while((stack_operators.getSize()>0) && (stack_operators.getTop() != '('));
        if((stack_operators.getSize()>0) && (stack_operators.getTop() == '('))
        {
            stack_operators.pop();
        }
    }

    private static boolean prec(char token, Stack1gen<Character> StackA)
    {
        char topOp = StackA.getTop();
        // checks if the current token in hand is a higher tier operator than the one at the top of the stack(which would be next in line)
        if((   ((token == '*')||(token == '/')) && ((topOp == '+')||(topOp == '-'))   )||(   (token == '^')&&((topOp=='*')||(topOp=='/')||(topOp=='+')||(topOp=='-'))   ))
        {
            return true;
        }
        else
        {
            if((((topOp == '*')||(topOp == '/'))&&((token == '+')||(token == '-')))||((topOp == '^')&&((token=='*')||(token=='/')||(token=='+')||(token=='-'))))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
    }

    private String e; //the expression as a string
    private int i; // pointer or index
    public ExprEvaluator2()
    {
        i = 0;
    }

    public ExprEvaluator2(String ee)
    {
        e = ee;
        i = 0;
    }

    public void setExpression(String ee)
    {
        e = ee;
    }
    public String getExpression()
    {
        return e;
    }

    public double formNum()
    {
        double total = 0.0;
        int count = 0;
        int decimal_flag = 0;
        double mult = 1.0;
        char charAt_i,d;
        boolean space = false;
        boolean neg = false;
        
        d = e.charAt(i);
        do
        {
            p(1);
            charAt_i = e.charAt(i);
            if(charAt_i=='(' && e.charAt(i+1)=='-'){
                p(2);
                neg = true;
            }
            else if(charAt_i==' '){
                p(3);
                space = true;
                if(d=='.'){
                    throw new NumberFormatException();
                }
            }
            else if(charAt_i == '.'){
                p(4);
                if(space==true){
                    p(5);
                    System.exit(1);
                }
                decimal_flag = 1;
            }
            else if((charAt_i >= '0') && (charAt_i<= '9')){
                p(7);
                if(space==true){
                    p(8);
                    System.exit(1);
                }
                p("total = "+total);
                p("total = ("+total+"*10)+("+charAt_i+"-'0');");
                /*we multiply the total by 10 because when we are adding something after the decimal place, 
                we need it to add in the ones place, otherwise adding a .4 to 3.0 would result in 7.0 instead of 3.4 */
                total = (total*10)+(charAt_i-'0');
                p("total = "+total);
                if(decimal_flag == 1){
                    p(9);
                    count++;
                }
            }
            i++;
            d = '?';
            if(i<e.length()){
                p(10);
                d = charAt_i;
            }
        } while((i<e.length()) && (((d<='9')&&(d>='0'))||(d=='.')));

        if(decimal_flag==1)
        {
            total = total/Math.pow(10.0,count*1.0);
        }
        if(neg)
        {
            p("total is");
            p(-total);
            return -total;
        }
        else
        {
            p("total is");
            p(total);
            return total;
        }
    }
    
    public double evaluator()
    {
        char token;                 //Initialize the token char variable
        do                          //start the do-while loop
        {
            token = e.charAt(i);    //take the char at i and assign it to the token variable
            if(token==' ')          //if the token at the index is space, advance the index forward
                i++;
            else if(token == '(')   //if the token is (, push the token onto the stack_operators character stack
            {
                stack_operators.push(token);
                i++;
            }
            else if(token == ')' )  //if the token is ), call the evalDown method which...
            
            {
                evalDown();
                i++;
            }
            else if(token=='+'||token=='-'||token=='*'||token=='/'||token=='^') // checks if the token is an operator
            {
                p("what in gods name?");
                /*This if statement checks if the stack_operators stack is empty or the operator in hand is a higher tier operator than the one at the top of the stack (next in line).
                This means that when we encounter 3 * 3 + 3, the program will push 3 to stack_numbers stack, * to stack_operators stack, 3 to stack_numbers stack, and when it encounters
                the + operator, it will evalulate, multiplying the two 3's together to make 9 which will then be added to the third 3.*/
                if((stack_operators.getSize() == 0) || (prec(token, stack_operators) == true))
                {
                    stack_operators.push(token);
                    i++;
                }
                else
                {
                    eval();
                }
            }
            /*This checks if the token is a number, if */
            else if(((token<='9')&&(token>='0'))||(token=='.')||(token=='('))
            {
                stack_numbers.push(formNum());
            }
            else{
                throw new NumberFormatException();
            }
        }while(i<e.length());

        while(stack_operators.getSize()>0)
        {
            eval();
        }

        double x = stack_numbers.pop();
        return x;
    }

    public void p(Object s){
        System.out.println(s);
    }
}
