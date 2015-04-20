package TrafficLight;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 *
 * @author Africana and Anabeto
 */
public class SmartCar extends AppSim{

    class Vec {

        int x, y; boolean turn;

        Vec() {}

        Vec(int xVal, int yVal) //*Constructor
        {
            x = xVal;
            y = yVal;
            //turn = false;
        }//end Constructor

    }//end Vec Class

   
    //* Car Attributes
    Vec loc = new Vec();
    Image carImage;
    int accl=1;
    static int carWidth = 27; //x-axis dim of car
    static int carLength = 57; //y-axis dim of car
    int iD; //plate number to identify each car
    boolean stall;
    int carW, carL;
    double currentAngle; int turns=0; boolean turn;
    public void setTurn(boolean value){turn = value;}
    public boolean getTurn(){return turn;}
    public int getTurns(){return turns;}
    public void setTurns(int i){turns+=i;}
    
    SmartCar( int iDLabel ,int xPos, int yPos,boolean condition) //*Constructor:Each car object is created knowing its start position,lane, speed, iD
    //*Dimentions of the car are also set
    {
        loc.x = xPos;
        loc.y = yPos;
        iD = iDLabel;
        carW = carWidth;
        carL = carLength;
        stall = condition;
        turn=false;
    }

    SmartCar(boolean condition,int iDLabel ,int xPos, int yPos) 
    {
        loc.x = xPos;
        loc.y = yPos;
        iD = iDLabel;
        carW = carLength;
        carL = carWidth;
        stall = condition;
        turn=false;
    }
    
    //SmartCar(){}

    public void drawCar(Graphics2D brush) {
        //draw all the cars below (Resize images in photoShop)
   
        //brush.setColor(Color.red);
        //brush.fill(new Ellipse2D.Float(loc.x, loc.y, carW, carL));
    	if(!getTurn()){
    		brush.drawImage(carImage,loc.x, loc.y/*, carW, carL*/,this);}
    	else
    	{
    		/*System.out.println("About to rotate the damn car");
            AffineTransform origXform = brush.getTransform();
            AffineTransform newXform = (AffineTransform)(origXform.clone());
            //center of rotation is center of the panel
            int xRot = loc.x+50;//loc.x;//this.getWidth()/2;
            int yRot = loc.y - 20;//this.getHeight()/2;
            newXform.rotate(Math.toRadians(getAngle()), xRot, yRot);
            brush.setTransform(newXform);
            //draw image centered in panel
            int x = (getWidth() - carImage.getWidth(this))/2;
            int y = (getHeight() - carImage.getHeight(this))/2;
            brush.drawImage(carImage, loc.x, loc.y, this);
            brush.setTransform(origXform);*/		
    	}
    }
    
    public void turnCarNorth(Graphics2D brush)
    {
    	AffineTransform origXform = brush.getTransform();
        AffineTransform newXform = (AffineTransform)(origXform.clone());
        //center of rotation is center of the panel
        int xRot = loc.x+10;//loc.x;//this.getWidth()/2;
        int yRot = loc.y;//this.getHeight()/2;
        newXform.rotate(Math.toRadians(getAngle()), xRot, yRot);
        brush.setTransform(newXform);
        //draw image centered in panel
        int x = (getWidth() - carImage.getWidth(this))/2;
        int y = (getHeight() - carImage.getHeight(this))/2;
        brush.drawImage(carImage, loc.x, loc.y, this);
        brush.setTransform(origXform);
    }
    public void turnCarSouth(Graphics2D brush)
    {
    	AffineTransform origXform = brush.getTransform();
        AffineTransform newXform = (AffineTransform)(origXform.clone());
        //center of rotation is center of the panel
        int xRot = loc.x+45;//loc.x;//this.getWidth()/2;
        int yRot = loc.y + 50;//this.getHeight()/2;
        newXform.rotate(Math.toRadians(getAngle()), xRot, yRot);
        brush.setTransform(newXform);
        //draw image centered in panel
        int x = (getWidth() - carImage.getWidth(this))/2;
        int y = (getHeight() - carImage.getHeight(this))/2;
        brush.drawImage(carImage, loc.x, loc.y, this);
        brush.setTransform(origXform);
    }
    public void turnCarEast(Graphics2D brush)
    {
    	AffineTransform origXform = brush.getTransform();
        AffineTransform newXform = (AffineTransform)(origXform.clone());
        //center of rotation is center of the panel
        int xRot = loc.x+45;//loc.x;//this.getWidth()/2;
        int yRot = loc.y +3;//this.getHeight()/2;
        newXform.rotate(Math.toRadians(getAngle()), xRot, yRot);
        brush.setTransform(newXform);
        //draw image centered in panel
        int x = (getWidth() - carImage.getWidth(this))/2;
        int y = (getHeight() - carImage.getHeight(this))/2;
        brush.drawImage(carImage, loc.x, loc.y, this);
        brush.setTransform(origXform);
    }
    public void turnCarWest(Graphics2D brush)
    {
    	AffineTransform origXform = brush.getTransform();
        AffineTransform newXform = (AffineTransform)(origXform.clone());
        //center of rotation is center of the panel
        int xRot = loc.x-5;//loc.x;//this.getWidth()/2;
        int yRot = loc.y+60;//this.getHeight()/2;
        newXform.rotate(Math.toRadians(getAngle()), xRot, yRot);
        brush.setTransform(newXform);
        //draw image centered in panel
        int x = (getWidth() - carImage.getWidth(this))/2;
        int y = (getHeight() - carImage.getHeight(this))/2;
        brush.drawImage(carImage, loc.x, loc.y, this);
        brush.setTransform(origXform);
    }
   
    public synchronized void rotateNorth() {
        //rotate 5 degrees at a time
        /*for(int i=0; i<4; i++)
        {
        	setAngle(10);
        	//repaint();
        }*/
        if (getAngle() >= 90.0) {
          setAngle(0.0); //setTurns(-1); 
          //setTurn(false);
        }else if(!(getTurns()>=4)){
        	setAngle(22.5);
        }
        
      }
    public synchronized void rotateSouth()
    {
    	if(getAngle() >= 90)
    	{
    		setAngle(0.0);
    	}else if(!(getTurns()>=4))
    	{
    		setAngle(22.5);
    	}
    }
    public synchronized void rotateEast()
    {
    	if(getAngle() >= 90)
    	{
    		setAngle(0.0);
    	}else if(!(getTurns()>=4))
    	{
    		setAngle(22.5);
    	}
    }
    public synchronized void rotateWest()
    {
    	if(getAngle() >= 90)
    	{
    		setAngle(0.0);
    	}else if(!(getTurns()>=4))
    	{
    		setAngle(22.5);
    	}
    }
    public void setAngle(double value)
    {
    	currentAngle+= value;
    }
    public double getAngle(){return currentAngle;}
    public int accl(int vel)//vel is velocity
    {
        vel += accl; //accelerate
        accl++;
        return vel;
    }

}//end SmartCar class
