import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Line2D;
import java.awt.Stroke;

//This is the ClockShape class that builds the image for the clock for each frame of the animation.
//The translate method incriments the angle of each hand of the clock.
//The draw method has the code that actually makes the clock image, using lines and circles to represent various parts of the clock.

public class ClockShape implements Moveable  //produce a randomly colored ball for animation
{
   private double theta;
   private double alpha;
   private double gamma;
   private int count;
   private int m;
   private int h;
   private int x;
   private int y;
   private int width;
   public ClockShape(int xx, int yy, int ww, int zz, int qq, int rr)
   {
     x = xx;
     y = yy;
     width = ww;
     theta = -Math.PI/2;
     alpha = -Math.PI/2;
     gamma = -Math.PI/2;
     count=zz;
     m=qq;
     h=rr;
   }
    public void setClock(int h, int m)
    {
        count=h*3600+m*60;
        theta = -Math.PI/2;
        alpha = -Math.PI/2+m*Math.PI/30;
        gamma = -Math.PI/2+h*Math.PI/6+m/12*Math.PI/30;
    }
    public void resetClock()
    {
        count=0;
        theta = -Math.PI/2;
        alpha = -Math.PI/2;
        gamma = -Math.PI/2;
    }
    int xchange = 1;

    public void translate(int dx, int dy)  
    {
        theta = theta+Math.PI/30;
        count++;
        if(count%60==0)
            alpha = alpha+Math.PI/30;
        if(count%720==0)
            gamma = gamma+Math.PI/30;
    }

    public void draw(Graphics2D g2)  //The Graphics2D object g2 will produce the picture
    {
        Random gen = new Random(); //We declare a random number generator to produce random colors.
        Color c = new Color(Math.abs(gen.nextInt()) % 255, Math.abs(gen.nextInt()) % 255, Math.abs(gen.nextInt()) % 255); //a random color //we set the color to be used in the current painting of the ball
        Point2D.Double endSecond = new Point2D.Double(300 + 110*Math.cos(theta), 300 + 110*Math.sin(theta));
        Point2D.Double endMinute = new Point2D.Double(300 + 150*Math.cos(alpha), 300 + 150*Math.sin(alpha));
        Point2D.Double endHour = new Point2D.Double(300 + 130*Math.cos(gamma), 300 + 130*Math.sin(gamma));
        Point2D.Double Origin = new Point2D.Double(300, 300);
        Shape seconds = new Line2D.Double(Origin, endSecond);
        Shape minutes = new Line2D.Double(Origin, endMinute);
        Shape hours = new Line2D.Double(Origin, endHour);
        Ellipse2D.Double ball = new Ellipse2D.Double(295,295,10,10);
        g2.setStroke(new BasicStroke(5));
        g2.setColor(new Color(0,0,150));
        g2.draw(minutes);
        g2.setStroke(new BasicStroke(7));
        g2.draw(hours);
        g2.setStroke(new BasicStroke(3));
        g2.setColor(c);
        g2.draw(seconds);
        g2.setStroke(new BasicStroke(0));
        for(int j=0; j<60; j++)
        {
            if(j%5==0)
            {
                Ellipse2D.Double poi = new Ellipse2D.Double(297+170*Math.cos(j*Math.PI/30), 297+170*Math.sin(j*Math.PI/30),8,8);
                g2.setColor(new Color(250,0,0));
                g2.fill(poi);
                g2.draw(poi);
            }
            else
            {
                Ellipse2D.Double poi = new Ellipse2D.Double(298+170*Math.cos(j*Math.PI/30), 298+170*Math.sin(j*Math.PI/30),4,4);
                g2.setColor(new Color(0,0,0));
                g2.fill(poi);
                g2.draw(poi);
            }
        }
        g2.setColor(new Color(0,0,0));
        g2.fill(ball);
        g2.draw(ball);
  }
}