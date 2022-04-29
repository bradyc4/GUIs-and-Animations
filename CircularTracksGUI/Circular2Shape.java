import java.awt.*;
import java.util.*;
import java.awt.Color;
import java.awt.geom.*;
import javax.swing.*;

public class Circular2Shape implements Moveable
{
  private int xx;
  private int yy;
  private int width;
  int n;

  //variables for the position of each train 
  private  double[] x = new double[20]; //at most 20 trains
  private  double[] y = new double[20];
  private double[] theta = new double[20];
  private double[] theta2 = new double[20];
  private  double[] x2 = new double[20]; //at most 20 trains
  private  double[] y2 = new double[20];
  private double[] speed = new double[20];

 //variables for the track limits (the center of the track on the right, bottom, left, and top, based on 40 pixel track width)
  int[] xmax = new int[20];
  int[] ymax = new int[20];
  int[] xmin = new int[20];
  int[] ymin = new int[20];

//colors for tracks (hexadecimal format)
 Color[] cc = {Color.decode("#ADFFFA"), Color.decode("#FFADE1"), Color.decode("#F2FFAD"), Color.decode("#FFE0AD"),
               Color.decode("#ADB4FF"), Color.decode("#ADFFB0"), Color.decode("#FFA998"), Color.decode("#A9FA6D"),
               Color.decode("#ADFFFA"), Color.decode("#FFADE1"), Color.decode("#F2FFAD"), Color.decode("#FFE0AD"),
               Color.decode("#ADB4FF"), Color.decode("#ADFFB0"), Color.decode("#FFA998"), Color.decode("#E9FA6D")};
 
//constructor
  public Circular2Shape(int x0, int y0, int w)
  {
    xx = x0;
    yy = y0;
    width = w;
    n = width/40;  //number of tracks, each 40 pixels wide    

// trackLimits
// for train on track i, give coordinates of the center of the track (and of the train)
// when train is on right, left, top, or bottom section of track i
 
 for(int i= 0; i<= n-1; i++){
  xmax[i]= width-20-20*i; //x-coordinate center of train on right
  xmin[i]=  20*i; //x-coordinate of center on left
  ymax[i] = xmax[i]; //y-coordinate of center at bottom
  ymin[i] = xmin[i]; //y-coordinate of center at top
  theta[i]=0;
  theta2[i]=0;
  if(i%2==1)
    speed[i]=1;
  else 
    speed[i]=-1;
 }

  
  //each train starts from the lower left corner of its track
  for(int i=0; i<= n-1; i=i+1){
   x[i] = 580-i*20;
   y[i] = 290;
 }

}//end constructor

public void changeSpeed(int i, double s)
{
  i--;
  if(speed[i]<0)
    speed[i]=-s;
  else
    speed[i]=s;
}
public void changeDirection(int i)
{
  i--;
  speed[i]=-speed[i];
}


//implementation of Moveable

public void translate(int dx, int dy)
{
 int delx = dx; //horizontal increment or decrement (depending on current position)
 int dely = dy; //vertical displacement

 //position counter-clockwise trains
  for (int i=0; i<= n-1; i= i+1){
    theta[i] = theta[i]+(0.01+0.05*i*Math.PI/300)*speed[i];
    theta2[i] = theta[i]+2*Math.asin(10.0/((xmax[i]-xmin[i])/2));
    x[i]=290+((xmax[i]-xmin[i])/2)*Math.cos(theta[i]);
    y[i]=290+((ymax[i]-ymin[i])/2)*Math.sin(theta[i]);
    x2[i]=290+((xmax[i]-xmin[i])/2)*Math.cos(theta2[i]);
    y2[i]=290+((ymax[i]-ymin[i])/2)*Math.sin(theta2[i]);
  }
}



 
  public void draw(Graphics2D g2)
  {   
   //paint tracks via superimposed shrinking rectangles
     for(int i= 0; i<=n-1; i++){
      Ellipse2D.Double rect = new Ellipse2D.Double(20*i, 20*i,width-(40*i),width-(40*i));
      g2.setColor(cc[i]);
      g2.fill(rect);
    }

    Random gen = new Random();
    
    //paint trains
     for (int i=0; i<= n-1; i= i+1){
      Color c = new Color(Math.abs(gen.nextInt()) % 255, Math.abs(gen.nextInt()) % 255, Math.abs(gen.nextInt()) % 255);
      g2.setColor(c);
      Ellipse2D.Double ball = new Ellipse2D.Double(x[i], y[i], 20, 20);
      Ellipse2D.Double car = new Ellipse2D.Double(x2[i], y2[i], 20, 20);
      g2.fill(ball);     
      g2.draw(ball);
      g2.fill(car);     
      g2.draw(car);
      g2.setColor(new Color(255,255,255));
      g2.drawString((i+1)+"",(int)x[i]+4,(int)y[i]+15);
     }

  } //end draw

}






