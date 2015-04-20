
package TrafficLight;

import java.awt.*;
import java.awt.geom.*;

/**
 *
 * @author Africana
 */
public class TrafficLight {
    
    int trafficStop; //sleepTime
    Color red,yellow,green,
    activeRed,activeYellow,activeGreen,
            redShade,yellowShade,greenShade;
    boolean stop, go, slow;
    double xPos, yPos, xOrient, yOrient;
    double orb;
    double padd = 2;
    
    TrafficLight()
    {
        red = new Color(30,3,2); //not far from black
        yellow = new Color(32,31,0); //dark version of yellow
        green = new Color(2,17,0); //same as this
        
        activeRed = new Color(251,34,55); //a little light red
        activeYellow = new Color(251,247,77); //same as for this
        activeGreen = new Color(74,251,55);  //and this
        
        redShade = red; //light should be dim
        yellowShade = yellow; //all dim
        greenShade = green;
    }
    
    void station(Graphics2D waterPaint,double xLoc,double yLoc,int xDim , int yDim)
    {
        xPos = xLoc;
        yPos = yLoc;
        xOrient = xDim;
        yOrient  = yDim;
        waterPaint.setColor(Color.BLACK);
        waterPaint.fill(new Rectangle2D.Double(xLoc,yLoc,xDim,yDim));
        redlight(waterPaint, xLoc,yLoc);
        yellowlight(waterPaint,xLoc,yLoc);
        greenlight(waterPaint,xLoc,yLoc);
    }
    
    void redlight(Graphics2D waterSplash,double xLoc,double yLoc)
    {
        
        if(xOrient < yOrient) // x --> and  y |
        //*Vertical							  v
        {
         orb = xOrient;
         xLoc+=1;
         yLoc+=padd;
        }else
        //*Horizontal
        {
         orb = yOrient;
         xLoc+=padd;
         yLoc+=1;
        }
        
        waterSplash.setColor(redColor());
        waterSplash.fill(new Ellipse2D.Double(xLoc,yLoc,orb-padd,orb-padd));
    }
    void yellowlight(Graphics2D waterSplash,double xLoc,double yLoc)
    {
        
        if(xOrient < yOrient)
        //*Vertical
        {
         orb = xOrient;
         xLoc+=1;
         yLoc+=(padd + orb);
        }else
        //*Horizontal
        {
         orb = yOrient;
         xLoc+=(padd + orb);
         yLoc+=1;
        }
        
        waterSplash.setColor(yellowColor());
        waterSplash.fill(new Ellipse2D.Double(xLoc,yLoc,orb-padd,orb-padd));
    }
    
    void greenlight(Graphics2D waterSplash,double xLoc,double yLoc)
    {
         if(xOrient < yOrient)
        //*Vertical
        {
         orb = xOrient;
         xLoc+=1;
         yLoc+=(padd + (orb*2));
        }else
        //*Horizontal
        {
         orb = yOrient;
         xLoc+=(padd + (orb*2));
         yLoc+=1;
        }
        waterSplash.setColor(greenColor());
        waterSplash.fill(new Ellipse2D.Double(xLoc,yLoc,orb-padd,orb-padd));
    }
    
    Color redColor(){return redShade;}
     Color yellowColor(){return yellowShade;}
      Color greenColor(){return greenShade;}
    
      void activeRed(){redShade = activeRed;}
     void activeYellow(){yellowShade = activeYellow;}
      void activeGreen(){greenShade = activeGreen;}
      
      void dimRed(){redShade = red;}
     void dimYellow(){yellowShade = yellow;}
      void dimGreen(){greenShade = green;}
}
