import java.util.Stack;

public class ExprEvaluator
{
    private static Stack<Character> stack_operators = new Stack<Character>();
    private static Stack<Double> stack_numbers = new Stack<Double>();
    private static char charAt_i;
    private static String e;
    private static int i, parCount;
    private static boolean wasNumber, wasOperator, wasLeft, wasRight;

    public static void p(Object s){
        System.out.println(s);
    }
    
    public static void eval(){
        char char_operator = stack_operators.pop();
        double double_value = 0.0;
        double opnd2 = stack_numbers.pop();
        double opnd1; 
        if(!stack_numbers.empty()){
            opnd1 = stack_numbers.pop();
        }
        else{
            opnd1 = 0;
        }
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
                double_value = opnd1 / opnd2;
                break;
            case '^':
                double_value = Math.pow(opnd1,opnd2);
                break;
            case '(':
                double_value = opnd2;
                stack_numbers.push(opnd1);
                stack_operators.push(char_operator);
                break;
        }
        stack_numbers.push(double_value);
    }

    public static void evalDown(){
        if(parCount<=0){
            p(1);
            throw new NumberFormatException();
        }
        do{
            eval();
        }while(stack_operators.peek() != '(');
        parCount--;
        stack_operators.pop();
    }

    public static boolean prec(){
        char topOp = stack_operators.peek(); // add a condition for detecting (
        if(topOp == '(' || (   (charAt_i == '*'||charAt_i == '/') && (topOp == '+'||topOp == '-')   )||(   charAt_i == '^'&&(topOp=='*'||topOp=='/'||topOp=='+'||topOp=='-')   )){
            return true;
        }
        else{
            return false;
        }
    }

    public ExprEvaluator()
    {
        i = 0;
        parCount = 0;
        wasNumber=false;
        wasOperator=false;
        wasLeft=false;
        wasRight=false;
    }

    public ExprEvaluator(String ee)
    {
        e = ee;
        i = 0;
        parCount = 0;
        wasNumber=false;
        wasOperator=false;
        wasLeft=false;
        wasRight=false;
    }

    public void setExpression(String ee)
    {
        e = ee;
    }
    public String getExpression()
    {
        return e;
    }

    public static double formNum(boolean isPositive){
        if(wasNumber){
            p(2);
            throw new NumberFormatException();
        }
        wasOperator = wasLeft = wasRight = false;
        wasNumber = true;
        double total = 0.0;
        double mult = 1.0;
        int count = 0;
        boolean decimal_flag = false;

        do{
            if(charAt_i == '.'){
                decimal_flag = true;
            }
            else{
                total = (total*10)+(charAt_i-'0');
                if(decimal_flag){
                    count++;
                }
            }
            i++;
            if(i<e.length()){
                charAt_i = e.charAt(i);
            }
        } while(i<e.length() && (( charAt_i<='9' && charAt_i>='0' )|| charAt_i=='.'));

        if(e.charAt(i-1)=='.'){
            p(3);
            throw new NumberFormatException();
        }
        if(decimal_flag){
            total = total/Math.pow(10.0,count*1.0);
        }
         // i has to be decremented so that it's back in place to be incremented in the main method
        if(isPositive){
            i--;
            return total;
        }
        else{
            if(e.charAt(i)==')'){
                return -total;
            } else {
                p(4);
                throw new NumberFormatException();
            }
        }
    }
    
    public double evaluator()
    {
        while(i<e.length()){
            charAt_i = e.charAt(i);

            if(charAt_i == '('){
                if(e.charAt(i+1)=='-'){
                    i = i+2;
                    charAt_i = e.charAt(i);
                    stack_numbers.push(formNum(false));
                } else if(wasNumber || (i>0 && wasRight) || i>=e.length()-2){
                    p(5);
                    throw new NumberFormatException();
                } else {
                    stack_operators.push(charAt_i); // This is pushing the '(' operator
                    parCount++;
                    wasLeft = true;
                    wasRight = wasNumber = wasOperator = false;
                }
            }
            else if(charAt_i == ')' ){
                if(i<=1 || wasOperator || wasLeft){
                    p(6);
                    throw new NumberFormatException();
                }
                evalDown();
            }
            else if(charAt_i>='0' && charAt_i<='9'){
                stack_numbers.push(formNum(true));
            }
            else if(charAt_i=='+'||charAt_i=='-'||charAt_i=='*'||charAt_i=='/'||charAt_i=='^'){
                if(wasOperator || i==0 || wasLeft){
                    p(7);
                    throw new NumberFormatException();
                } 
                else{
                    while(!stack_operators.empty() && !prec()){
                        eval();
                    }
                }
                stack_operators.push(charAt_i); // This is pushing actual operators
                wasNumber = wasLeft = wasRight = false;
                wasOperator = true;
            } else if(charAt_i!=' '){
                p(8);
                throw new NumberFormatException();
            }
            i++;
        }
        if(parCount>0){
            p(9);
            throw new NumberFormatException();
        }
        while(!stack_operators.empty()){
            eval();
        }
        double x = stack_numbers.pop();
        p(x);
        return x;
    }
}
