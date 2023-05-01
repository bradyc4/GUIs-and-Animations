import java.util.Stack;

/*So here is what I am assuming

this has been a long and arduous time, certainly I might be able to use this as one of my challenges, the popping ( story

all thats left is syntax errors and forming numbers

for syntax:
there should be no spaces in the middle of forming numbers (check)
there can only be operators following numbers, no consecutive numbers. (check)
no consecutive actual operators (check)
no operators following ( (check)
no operators preceding ) (check)
3 + 3 */
class my{
    static Stack<Character> stack_operators = new Stack<Character>();
    static Stack<Double> stack_numbers = new Stack<Double>();
    static char charAt_i;
    static String e = "2+2-2*2^2+2";
    static int i = 0;
    static int parCount = 0;
    static boolean wasNumber=false, wasOperator=false, wasLeft=false, wasRight=false;

    public static void main(String[] args){
        while(i<e.length()){
            charAt_i = e.charAt(i);

            if(charAt_i == '('){
                if(e.charAt(i+1)=='-'){
                    i = i+2;
                    charAt_i = e.charAt(i);
                    stack_numbers.push(formNum(false));
                } else if(wasNumber || (i>0 && wasRight) || i>=e.length()-2){
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
                    throw new NumberFormatException();
                }
                evalDown();
            }
            else if(charAt_i>='0' && charAt_i<='9'){
                stack_numbers.push(formNum(true));
            }
            else if(charAt_i=='+'||charAt_i=='-'||charAt_i=='*'||charAt_i=='/'||charAt_i=='^'){
                if(wasOperator || i==0 || wasLeft){
                    throw new NumberFormatException();
                } 
                else{
                    while(!stack_operators.empty() && !isHigherTier()){
                        eval();
                    }
                }
                stack_operators.push(charAt_i); // This is pushing actual operators
                wasNumber = wasLeft = wasRight = false;
                wasOperator = true;
            } else if(charAt_i!=' '){
                throw new NumberFormatException();
            }
            i++;
        }
        if(parCount>0){
            throw new NumberFormatException();
        }
        while(!stack_operators.empty()){
            eval();
        }
        double x = stack_numbers.pop();
        System.out.println(x);
    }

    public static boolean isHigherTier(){
        char topOp = stack_operators.peek(); // add a condition for detecting (
        if(topOp == '(' || (   (charAt_i == '*'||charAt_i == '/') && (topOp == '+'||topOp == '-')   )||(   charAt_i == '^'&&(topOp=='*'||topOp=='/'||topOp=='+'||topOp=='-')   )){
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean isNumber(){
        if(charAt_i>='0' && charAt_i<='9'){
            return true;
        } else {
            return false;
        }
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

    public static double formNum(boolean isPositive){
        if(wasNumber){
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
                throw new NumberFormatException();
            }
        }
    }

    public static void evalDown(){
        if(parCount<=0){
            throw new NumberFormatException();
        }
        do{
            eval();
        }while(stack_operators.peek() != '(');
        parCount--;
        stack_operators.pop();
    }

    public static void showStack(){
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
    }

    public static void p(Object s){
        System.out.println(s);
    }
}