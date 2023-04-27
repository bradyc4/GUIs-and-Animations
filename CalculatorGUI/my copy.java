import java.util.*;

/*So here is what I am assuming

this has been a long and arduous time, certainly I might be able to use this as one of my challenges, the popping ( story

all thats left is syntax errors and forming numbers
3 + 3 */
class my{
    static Stack<Character> stack_operators = new Stack<Character>();
    static Stack<Double> stack_numbers = new Stack<Double>();
    static char charAt_i;
    static String e = "(3 + 3)^(1 + 1)";

    public static void main(String[] args){
        for(int i=0; i<e.length(); i++){
            charAt_i = e.charAt(i);

            if(charAt_i == '('){
                if(e.charAt(i+1)=='-'){
                    
                }
                stack_operators.push(charAt_i);
            }
            else if(charAt_i == ')' ){
                evalDown();
            }
            else if(charAt_i>='0' && charAt_i<='9'){
                stack_numbers.push(charAt_i-'0'+0.0);
            }
            else if(charAt_i=='+'||charAt_i=='-'||charAt_i=='*'||charAt_i=='/'||charAt_i=='^'){
                if(!stack_operators.empty() && !isHigherTier()){
                    evaluate();
                }
                stack_operators.push(charAt_i);
            }
        }
        while(!stack_operators.empty()){
            evaluate();
        }
        double x = stack_numbers.pop();
        System.out.println(x);
    }

    public static boolean isHigherTier(){
        char topOp = stack_operators.peek(); // add a condition for detecting (
        if(topOp == '(' || (   (charAt_i == '*'||charAt_i == '/') && (topOp == '+'||topOp == '-')   )||(   charAt_i == '^'&&(topOp=='*'||topOp=='/'||topOp=='+'||topOp=='-')   )){
            return true;
        }
        else if((   (topOp == '*'||topOp == '/')&&(charAt_i == '+'||charAt_i == '-')   )||(   topOp == '^'&&(charAt_i=='*'||charAt_i=='/'||charAt_i=='+'||charAt_i=='-')  )){
            return false;
        } else {
            return false; //--------------------
        }
    }

    public static void evaluate(){
        char char_operator = stack_operators.pop();
        double double_value = 0.0;
        double opnd2 = stack_numbers.pop();
        double opnd1; 
        if(!stack_numbers.empty())
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
                double_value = opnd1 / opnd2;
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

    //static String e = "2 + 2 ^ (2 * 3)";

    private static void evalDown(){
        do{
            evaluate();
        }while(stack_operators.peek() != '(');

        stack_operators.pop();
    }

    public static void showStack(){
        p("showing stack:");
        char char_temp;
        Stack<Character> stack_temp = new Stack<Character>();
        while(!stack_operators.empty()){
            char_temp = stack_operators.pop();
            p(char_temp);
            stack_temp.push(char_temp);
        }
        while(!stack_temp.empty()){
            stack_operators.push(stack_temp.pop());
        }
        p("end of showStack");
    }

    public static void p(Object s){
        System.out.println(s);
    }
}