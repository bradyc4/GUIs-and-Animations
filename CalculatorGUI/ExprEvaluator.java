import java.util.Scanner;


public class ExprEvaluator
{
  Scanner kb =  new Scanner(System.in);
  private static Stack1gen<Character> A = new Stack1gen<Character>(); 
  private static Stack1gen<Double> B = new Stack1gen<Double>(); 
  public static void expressionOutput(double x)
  {
    if(x == (double)Math.round(x)) 
    {
       int intAns = (int)x;
       //System.out.println("value = " + intAns + '\n');
    }
    else
    {
       //System.out.println("value = " + x + '\n');
    }
  }
  
  private static void eval()
  {
      char op = A.pop();
      double val = 0.0;
      double opnd2 = B.pop();
      double opnd1; 
      if(B.getSize()>0)
        opnd1 = B.pop();
      else
        opnd1 = 0;
      switch (op)
      {
        case '+':
          val = opnd1 + opnd2;
          break;
        case '-':
          val = opnd1 - opnd2;
          break;
        case '*':
          val = opnd1 * opnd2;
          break;
        case '/':
          val = opnd1/opnd2;
          break;
        case '^':
          val = Math.pow(opnd1,opnd2);
          break;
        case '(':
          val = opnd2;
          break;
      }
      B.push(val);
  }

  private static void evalDown()
  {
    do
    {
      eval();
    }while((A.getSize()>0) && (A.getTop() != '('));
    if((A.getSize()>0) && (A.getTop() == '('))
    {
      A.pop();
    }
  }

  private static boolean prec(char token, Stack1gen<Character> StackA)
  {
    char topOp = StackA.getTop();
    if((((token == '*')||(token == '/'))&&((topOp == '+')||(topOp == '-')))||((token == '^')&&((topOp=='*')||(topOp=='/')||(topOp=='+')||(topOp=='-'))))
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

  private String e;
  private int p; 
  public ExprEvaluator()
  {
    p = 0;      
  }

  public ExprEvaluator(String ee)
  {
    e = ee;
    p = 0;  
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
    int flag = 0;
    double mult = 1.0;
    char c,d;
    boolean space = false;
    boolean neg = false;
    do
    {
      c = e.charAt(p); 
      if(c=='(')
        if(e.charAt(p+1)=='-')
        {
          neg = true;
        }
      if(c==' ')
        space = true;
      if(c == '.')
      {
        if(space==true)
        System.exit(1);
        flag = 1;
      }
      else
      {
        if((c >= '0') && (c<= '9')) 
        {
          if(space==true)
            System.exit(1);
          total = (total*10)+(c-'0');
          if(flag == 1)
          {
            count++;
          }
        }
      }
      p++; 
      d = '?';
      if(p<e.length())
      {
        d = e.charAt(p);
      }
    }  
    while((p<e.length()) && (((d<='9')&&(d>='0'))||(d=='.')));
    if(flag==1)
    {
      total = total/Math.pow(10.0,count*1.0);
    }
    if(neg==true)
    {
      return -total;
    }
    else
    {
      return total;
    }
  }
  
  public double evaluator() 
  {
 
    char token;
    do
    {    
      token = e.charAt(p);
      if(token==' ')
      p++;
      if(token == '(')
      {
        A.push(token);
      }
      if(token == ')' )
      {
        evalDown();
        p++; 
      }   
      if((token=='+')||(token=='-')||(token=='*')||(token=='/')||(token=='^'))
      {          
        if((A.getSize() == 0) || (prec(token, A) == true))
        {
          A.push(token);
          p++;         
        }
        else
        {
          eval();
        }
      }
      if(((token<='9')&&(token>='0'))||(token=='.')||(token=='('))
      {   
         B.push(formNum());
      }
    }while(p<e.length());

    while(A.getSize()>0) 
    {
      eval();
    }
    
    double x = B.pop();
    expressionOutput((double)Math.round(x*10000000)/10000000.0); 
    return x;
  }
}
