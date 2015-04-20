package TrafficLight;

import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
//import java.util.concurrent.CopyOnWriteArrayList;

public class AppSim extends JApplet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4083326984200422168L;
	//import the various cars
    Image[] northCars, southCars, eastCars, westCars; //* Car image array
    String[] northCarNames, southCarNames, eastCarNames, westCarNames;
    
    int firstMove=0; //meant for generate
    int eastTurn = 0;
    //*Buffer Graphics
    BufferedImage backBuff;
    Graphics2D brush = null;
    //replacement cars
    int northEast[],westSouth[],southWest[],eastNorth[];
    //New Graphics Objects
    Intersection intersection;
    static TrafficLight[] sideLights;
    static TrafficLight[] roadLights;
    ArrayList<SmartCar> nRCars = new ArrayList<>(), nLCars = new ArrayList<>(), sRCars = new ArrayList<>(), sLCars = new ArrayList<>(),
    		eRCars = new ArrayList<>(), eLCars = new ArrayList<>(), wRCars = new ArrayList<>(), wLCars = new ArrayList<>(),
    		/*this part is for the replacement cars*/
    		northEastCars = new ArrayList<>(), westSouthCars=new ArrayList<>(), southWestCars=new ArrayList<>(), eastNorthCars=new ArrayList<>();
    MediaTracker media;

    //*Number of cars in a giving direction
    int nNLCars, nNRCars, nSRCars, nSLCars, nERCars, nELCars, nWRCars, nWLCars; //where n is number of; S=south, etc ...
    volatile int NBCars,NBCars2, WBCars, WBCars2, SBCars, SBCars2, EBCars, EBCars2;

    //Starting Positions of cars
    int NSP, SSP, ESP, WSP;

    //*Lane Positions
    int NL, SL, EL, WL; //NL = north left
    int NR, SR, ER, WR; //NR = north right
    int north, south, east, west;

    //* Frame Resolution
    public static final int FrameX = 600;
    public static final int FrameY = FrameX;
    Container pro = getContentPane();

    @Override
    public void init() {

        setSize(FrameX, FrameY);
        intersection = new Intersection();
        backBuff = new BufferedImage(FrameX, FrameY, BufferedImage.TYPE_INT_RGB);

        //*Load Car Images
        media = new MediaTracker(this);
        new Thread(new Runnable() {
            public void run() {
                carImage();
            }
        }).start();

        try {
            media.waitForAll();
           
        } catch (InterruptedException e) {
        }
        //*Start Light
        new Thread(new Runnable() {
            public void run() {
                Graphics g = getGraphics();
                try {
                    while (true) {
                        intersection.innerNSMove();
                        Thread.sleep(4500);//(4500); //reduced by 1500 becuase of outer ns move
                        intersection.innerNSRoll();
                        Thread.sleep(1500);
                        intersection.outerNSMove();
                        Thread.sleep(4500);
                        intersection.outerNSRoll();
                        Thread.sleep(1500);
                        intersection.innerEWMove();
                        Thread.sleep(4500);
                        intersection.innerEWRoll();
                        Thread.sleep(1500);
                        intersection.outerEWMove();
                        Thread.sleep(4500);
                        intersection.outerEWRoll();
                        Thread.sleep(1500);
                        //light.setEastMove(false);//stop east cars
                    }
                    //g.dispose();
                } catch (InterruptedException e) {
                }
            }
        }).start();

        //*Generate Cars
        carThread.start();

        System.out.println("Init");
    }//end init()

    @Override
    public synchronized void paint(Graphics paintBrush) throws NullPointerException {
        //*Draw Images to backBuffer
        if (brush == null || pro.getSize().width != 0 || pro.getSize().height != 0) {
            brush = (Graphics2D) backBuff.getGraphics();
        }

        //*Add rendering hints to brush
        brush = rendering(brush);

        //*Draw Graphics
        brush.setColor(new Color(35, 41, 42));
        brush.fillRect(0, 0, FrameX, FrameY);
        intersection.drawIntersection(brush);
        drive();
        intersection.streetLights(brush);

        //*Draw Buffered Image Back to Screen
        paintBrush.drawImage(backBuff, 0, 0, pro);
    }//end paint()

    @Override
    public void update(Graphics g) throws NullPointerException {
        paint(g);
    }

    void drive() throws NullPointerException {
        int slowDown = 11;
        int bump = 9;
        int roll = 1;
        int vel = 8;

        driveInnerNorth(slowDown, roll, vel, bump);
        driveOuterNorth(slowDown, roll, vel, bump);
        driveInnerSouth(slowDown, roll, vel, bump);
        driveOuterSouth(slowDown, roll, vel, bump);
        driveInnerEast(slowDown, roll, vel, bump);
        driveOuterEast(slowDown, roll, vel, bump);
        driveInnerWest(slowDown, roll, vel, bump);
        driveOuterWest(slowDown, roll, vel, bump);
    }//end drive

    synchronized void driveInnerEast(int slowDown, int roll, int vel, int bump) throws IndexOutOfBoundsException {
        for (int e = 0; e < EBCars; e++) {
            boolean noCollision = true;
            boolean clearLane = true;
            int speed = vel;
            for (int c = 0; c < e; c++) { 
                if (clearLane && (eLCars.get(c).loc.y == eLCars.get(e).loc.y)) { //all cars on the inner lane
                    clearLane = false;
                } else if (clearLane && (eLCars.get(c).loc.y != eLCars.get(e).loc.y)) {
                    clearLane = true;
                }

                if ((eLCars.get(c).loc.y == eLCars.get(e).loc.y) && ((eLCars.get(c).loc.x + SmartCar.carLength) + bump < eLCars.get(e).loc.x)) {
                    noCollision = true;
                } else if ((eLCars.get(c).loc.y == eLCars.get(e).loc.y) && (eLCars.get(c).loc.x + SmartCar.carLength) + bump >= (eLCars.get(e).loc.x)) {
                    noCollision = false;
                }
            }

            if ((e == 0) || (e > 0 && (noCollision || clearLane))) {
                if (intersection.getInnerEWMove() || (eLCars.get(e).loc.x > Intersection.intersectionSE + slowDown) || 
                		(eLCars.get(e).loc.x < Intersection.intersectionSE)) {
                    if (eLCars.get(e).loc.x <= Intersection.intersectionNW) {
                        speed = vel;
                        eLCars.get(e).accl = 1; //reset acceleration
                    }
                    if ((!intersection.getInnerEWMove() || intersection.getInnerEWRoll()) && (eLCars.get(e).loc.x < Intersection.intersectionSE 
                    		&& eLCars.get(e).loc.x > Intersection.intersectionNW)) {
                        speed = eLCars.get(e).accl(vel);
                        System.out.println("East POWER!!!");
                    }
                    eLCars.get(e).loc.x -= speed;
                } else if ((!intersection.getInnerEWMove() || intersection.getInnerEWRoll()) && 
                		(eLCars.get(e).loc.x <= Intersection.intersectionSE + slowDown && eLCars.get(e).loc.x > Intersection.intersectionSE)) {
                    eLCars.get(e).loc.x -= roll;
                } else {
                    eLCars.get(e).loc.x -= 0;
                } //Stand still
            } else {
                eLCars.get(e).loc.x -= 0;
            } //Stand still
            eLCars.get(e).drawCar(brush);
        }
    }//end driveEast
    
    synchronized void driveOuterEast(int slowDown, int roll, int vel, int bump) throws IndexOutOfBoundsException
    {
    	for(int e=0; e<EBCars2; e++)
    	{
    		//collision and clear lane check
    		boolean noCollision = true;
            boolean clearLane = true;
            int speed = vel;
            for (int c = 0; c < e; c++) 
            { 
                if (clearLane && (eRCars.get(c).loc.y == eRCars.get(e).loc.y)) { //all cars on the inner lane
                    clearLane = false;
                } else if (clearLane && (eRCars.get(c).loc.y != eRCars.get(e).loc.y)) {
                    clearLane = true;
                }

                if ((eRCars.get(c).loc.y == eRCars.get(e).loc.y) && ((eRCars.get(c).loc.x + SmartCar.carLength) + bump < eRCars.get(e).loc.x)) {
                    noCollision = true;
                } else if ((eRCars.get(c).loc.y == eRCars.get(e).loc.y) && (eRCars.get(c).loc.x + SmartCar.carLength) + bump >= (eRCars.get(e).loc.x)) {
                    noCollision = false;
                }
            }
            
            if ((e == 0) || (e > 0 && (noCollision || clearLane))) {
                if (intersection.getOuterEWMove() || (eRCars.get(e).loc.x > Intersection.intersectionSE + slowDown) || 
                		(eRCars.get(e).loc.x < Intersection.intersectionSE)) {
                    if (eRCars.get(e).loc.x <= Intersection.intersectionNW) {
                        speed = vel;
                        eRCars.get(e).accl = 1; //reset acceleration
                    }
                    if ((!intersection.getOuterEWMove() || intersection.getOuterEWRoll()) && (eRCars.get(e).loc.x < Intersection.intersectionSE 
                    		&& eRCars.get(e).loc.x > Intersection.intersectionNW)) {
                        speed = eRCars.get(e).accl(vel);
                        System.out.println("East POWER!!!");
                    }
                    if(eRCars.get(e).loc.x>=365)
                    	eRCars.get(e).loc.x -= speed;
                    else
                    {
                    	eRCars.get(e).loc.y -=speed;
                    }
                } else if ((!intersection.getOuterEWMove() || intersection.getOuterEWRoll()) && 
                		(eRCars.get(e).loc.x <= Intersection.intersectionSE + slowDown && eRCars.get(e).loc.x > Intersection.intersectionSE)) {
                    //eRCars.get(e).loc.x -= roll;
                	if(eRCars.get(e).loc.x>=365)
                		eRCars.get(e).loc.x -= roll;
                	else{
                		eRCars.get(e).loc.x -=0;
                		eRCars.get(e).loc.y -=speed;
                	}
                    //System.out.println(eRCars.get(e).loc.x); //just want to know when it stops
                } else {
                	if(eRCars.get(e).loc.x>=365)
                		eRCars.get(e).loc.x -= 0;
                	else
                		eRCars.get(e).loc.y -=speed;
                    //System.out.println(eRCars.get(e).loc.x); //just want to know when it stops
                } //Stand still
            } else {
            	if(eRCars.get(e).loc.x>=365)
            		eRCars.get(e).loc.x -= 0;
            	else
            		eRCars.get(e).loc.y -=speed;
                System.out.println(eRCars.get(e).loc.x); //just want to know when it stops
            } //Stand still
            
            if(eRCars.get(e).loc.x>=365)
            {
                eRCars.get(e).setTurn(false);
                eRCars.get(e).drawCar(brush);
            }else
            {
            	eRCars.get(e).setTurn(true);
            	eRCars.get(e).rotateNorth(); //set it into rotation
            	//eRCars.get(e).turnCar(brush);
            	if(intersection.canMove[3])
            		eRCars.get(e).turnCarNorth(brush);
            }
            //eRCars.get(e).drawCar(brush);
            
    	}
    	
    }
    synchronized void driveInnerSouth(int slowDown, int roll, int vel, int bump) throws IndexOutOfBoundsException {
        for (int s = 0; s < SBCars; s++) {
            boolean noCollision = true;
            boolean clearLane = true;
            int speed = vel;
            for (int c = 0; c < s; c++) {
                if (clearLane && (sLCars.get(c).loc.x == sLCars.get(s).loc.x)) {
                    clearLane = false;
                } else if (clearLane && (sLCars.get(c).loc.x != sLCars.get(s).loc.x)) {
                    clearLane = true;
                }

                if ((sLCars.get(c).loc.x == sLCars.get(s).loc.x) && ((sLCars.get(c).loc.y + SmartCar.carLength) + bump 
                		< sLCars.get(s).loc.y)) {
                    noCollision = true;
                } else if ((sLCars.get(c).loc.x == sLCars.get(s).loc.x) && (sLCars.get(c).loc.y + SmartCar.carLength) + bump >= (sLCars.get(s).loc.y)) {
                    noCollision = false;
                }
            }//end collision logic 

            if ((s == 0) || (s > 0 && (noCollision || clearLane))) {
                if (intersection.getInnerNSMove() || (sLCars.get(s).loc.y > Intersection.intersectionSE + slowDown) 
                		|| (sLCars.get(s).loc.y < Intersection.intersectionSE)) {
                    if (sLCars.get(s).loc.y <= Intersection.intersectionNW) {
                        speed = vel;
                        sLCars.get(s).accl = 1; //reset acceleration
                    }
                    if ((!intersection.getInnerNSMove() || intersection.getInnerNSRoll()) && 
                    		(sLCars.get(s).loc.y < Intersection.intersectionSE && sLCars.get(s).loc.y > Intersection.intersectionNW)) {
                        speed = sLCars.get(s).accl(vel);
                        System.out.println("South POWER!!!");
                    }
                    sLCars.get(s).loc.y -= speed;
                } else if ((!intersection.getInnerNSMove() || intersection.getInnerNSRoll()) && 
                		(sLCars.get(s).loc.y <= Intersection.intersectionSE + slowDown 
                		&& sLCars.get(s).loc.y > Intersection.intersectionSE)) {
                    sLCars.get(s).loc.y -= roll;
                } else {
                    sLCars.get(s).loc.y -= 0;
                } //Stand still
            } else {
                sLCars.get(s).loc.y -= 0;
            } //Stand still
            sLCars.get(s).drawCar(brush);
        }
    }//end driveSouth
    
    synchronized void driveOuterSouth(int slowDown, int roll, int vel, int bump) throws IndexOutOfBoundsException
    {
    	for (int s = 0; s < SBCars2; s++) {
            boolean noCollision = true;
            boolean clearLane = true;
            int speed = vel;
            for (int c = 0; c < s; c++) {
                if (clearLane && (sRCars.get(c).loc.x == sRCars.get(s).loc.x)) {
                    clearLane = false;
                } else if (clearLane && (sRCars.get(c).loc.x != sRCars.get(s).loc.x)) {
                    clearLane = true;
                }

                if ((sRCars.get(c).loc.x == sRCars.get(s).loc.x) && ((sRCars.get(c).loc.y + SmartCar.carLength) + bump 
                		< sRCars.get(s).loc.y)) {
                    noCollision = true;
                } else if ((sRCars.get(c).loc.x == sRCars.get(s).loc.x) && (sRCars.get(c).loc.y + SmartCar.carLength) + bump >= (sRCars.get(s).loc.y)) {
                    noCollision = false;
                }
            }//end collision logic 

            if ((s == 0) || (s > 0 && (noCollision || clearLane))) {
                if (intersection.getOuterNSMove() || (sRCars.get(s).loc.y > Intersection.intersectionSE + slowDown) 
                		|| (sRCars.get(s).loc.y < Intersection.intersectionSE)) {
                    if (sRCars.get(s).loc.y <= Intersection.intersectionNW) {
                        speed = vel;
                        sRCars.get(s).accl = 1; //reset acceleration
                    }
                    if ((!intersection.getOuterNSMove() || intersection.getInnerNSRoll()) && 
                    		(sRCars.get(s).loc.y < Intersection.intersectionSE && sRCars.get(s).loc.y > Intersection.intersectionNW)) {
                        speed = sRCars.get(s).accl(vel);
                        System.out.println("South2 POWER!!!");
                    }

                	/*if(sRCars.get(s).loc.y!=409){
                		//at this distance, if the light is green, it should rotate
                		sRCars.get(s).loc.y -= speed;
                	}else{
                		//TODO: flip rotate the car to join the SE lane
                		//wRCars.get(s).drawCar(brush);
                		sRCars.get(s).loc.y -=speed;
                		sRCars.get(s).loc.x +=2*speed;
                		//sRCars.get(s).sTurnRight(brush);
                		return;
                	}*/ 
                    if(!(sRCars.get(s).loc.y<=389))
                		sRCars.get(s).loc.y -= speed;
                	else
                		sRCars.get(s).loc.x +=speed;
                    
                } else if ((!intersection.getOuterNSMove() || intersection.getInnerNSRoll()) && 
                		(sRCars.get(s).loc.y <= Intersection.intersectionSE + slowDown 
                		&& sRCars.get(s).loc.y > Intersection.intersectionSE)) {
                	System.out.println("South right y cordinate is "+ sRCars.get(s).loc.y);
                	if(!(sRCars.get(s).loc.y<=389))
                		sRCars.get(s).loc.y -= roll;
                	else
                		sRCars.get(s).loc.x +=roll;
                } else {
                	if(!(sRCars.get(s).loc.y<=389))
                    sRCars.get(s).loc.y -= 0;
                	else
                		sRCars.get(s).loc.x +=roll;
                } //Stand still
            } else {
            	if(!(sRCars.get(s).loc.y<=389))
            		sRCars.get(s).loc.y -= 0;
            } //Stand still
            if(!(sRCars.get(s).loc.y<=389)){
            	sRCars.get(s).drawCar(brush);
            }else{
            	sRCars.get(s).setTurn(true);
            	sRCars.get(s).rotateEast();
            	if(intersection.canMove[2])
            	sRCars.get(s).turnCarEast(brush);
            }
        }
    }
    synchronized void driveInnerWest(int slowDown, int roll, int vel, int bump) throws IndexOutOfBoundsException {
        for (int w = 0; w < WBCars; w++) {
            boolean noCollision = true;
            boolean clearLane = true;
            int speed = vel;
            for (int c = 0; c < w; c++) {
                if (clearLane && (wLCars.get(c).loc.y == wLCars.get(w).loc.y)) {
                    clearLane = false;
                } else if (clearLane && (wLCars.get(c).loc.y != wLCars.get(w).loc.y)) {
                    clearLane = true;
                }

                if ((wLCars.get(c).loc.y == wLCars.get(w).loc.y) && (wLCars.get(c).loc.x - bump 
                		> wLCars.get(w).loc.x + SmartCar.carLength)) {
                    noCollision = true;
                } else if ((wLCars.get(c).loc.y == wLCars.get(w).loc.y) && 
                		(wLCars.get(c).loc.x - bump <= wLCars.get(w).loc.x + SmartCar.carLength)) {
                    noCollision = false;
                }
            }//end collision logic

            if ((w == 0) || (w > 0 && (noCollision || clearLane))) {
                if (intersection.getInnerEWMove() || (wLCars.get(w).loc.x + SmartCar.carLength  
                		< Intersection.intersectionNW - slowDown) || (wLCars.get(w).loc.x+SmartCar.carLength > Intersection.intersectionNW )) {
                    if (wLCars.get(w).loc.x+SmartCar.carLength >= Intersection.intersectionSE) {
                        speed = vel;
                        wLCars.get(w).accl = 1; //reset acceleration
                    }
                    if ((!intersection.getInnerEWMove() || intersection.getInnerEWRoll()) && 
                    		(wLCars.get(w).loc.x + SmartCar.carLength > Intersection.intersectionNW && 
                    				wLCars.get(w).loc.x < Intersection.intersectionSE)) {
                        speed = wLCars.get(w).accl(vel);
                        System.out.println("West POWER!!!");
                    }
                    
                } else if ((!intersection.getInnerEWMove() || intersection.getInnerEWRoll()) && 
                		(wLCars.get(w).loc.x >= (Intersection.intersectionNW - SmartCar.carLength) - slowDown 
                		&& wLCars.get(w).loc.x < (Intersection.intersectionNW - SmartCar.carLength))) {
                    speed = roll;
                } else {
                    speed = 0;
                } //Stand still 
            } else {
                speed = 0;
            } //Stand still
            
            for(int h = 0; h<NBCars && h<SBCars; h++)
            //*Head On Collision 
            {
                if(wLCars.get(w).loc.x + SmartCar.carLength > Intersection.intersectionNW 
                		&& wLCars.get(w).loc.x + SmartCar.carLength < Intersection.intersectionSE)
                {
                    if(h<NBCars &&(nLCars.get(h).loc.y + SmartCar.carLength >= wLCars.get(w).loc.y 
                    		&& nLCars.get(h).loc.y <= wLCars.get(w).loc.y+SmartCar.carWidth))
                        //*Lane Potential
                    {System.out.println("WN: Potential Collision");
                        if(wLCars.get(w).loc.x+SmartCar.carLength >= nLCars.get(h).loc.x && wLCars.get(w).loc.x+SmartCar.carLength <= 
                        		nLCars.get(h).loc.x+SmartCar.carWidth)
                        //*Collision 
                        {System.out.println("WN: Collision");
                            speed = 0;
                        }
                    }
                    
                    if(h<SBCars &&(sLCars.get(h).loc.y <= wLCars.get(w).loc.y+SmartCar.carWidth && sLCars.get(h).loc.y+SmartCar.carLength >= wLCars.get(w).loc.y))
                    //*WS Lane Potential
                    {System.out.println("WS: Lane Potential");
                        if(wLCars.get(w).loc.x+SmartCar.carLength >= sLCars.get(h).loc.x && wLCars.get(w).loc.x+SmartCar.carLength 
                        		<= sLCars.get(h).loc.x+SmartCar.carWidth )
                        //*Collision
                        {System.out.println("WS: Collision");
                            speed = 0;
                        }
                    }
                }
            }//end for head on collision
            wLCars.get(w).loc.x += speed;
            wLCars.get(w).drawCar(brush);
        }
    }//end driveWest
    synchronized void driveOuterWest(int slowDown, int roll, int vel, int bump) throws IndexOutOfBoundsException
    {
    	for (int w = 0; w < WBCars2; w++) {
            boolean noCollision = true;
            boolean clearLane = true;
            int speed = vel;
            for (int c = 0; c < w; c++) {
                if (clearLane && (wRCars.get(c).loc.y == wRCars.get(w).loc.y)) {
                    clearLane = false;
                } else if (clearLane && (wRCars.get(c).loc.y != wRCars.get(w).loc.y)) {
                    clearLane = true;
                }

                if ((wLCars.get(c).loc.y == wRCars.get(w).loc.y) && (wRCars.get(c).loc.x - bump 
                		> wLCars.get(w).loc.x + SmartCar.carLength)) {
                    noCollision = true;
                } else if ((wRCars.get(c).loc.y == wRCars.get(w).loc.y) && 
                		(wRCars.get(c).loc.x - bump <= wRCars.get(w).loc.x + SmartCar.carLength)) {
                    noCollision = false;
                }
            }//end collision logic

            if ((w == 0) || (w > 0 && (noCollision || clearLane))) {
                if (intersection.getOuterEWMove() || (wRCars.get(w).loc.x + SmartCar.carLength  
                		< Intersection.intersectionNW - slowDown) || (wRCars.get(w).loc.x+SmartCar.carLength > Intersection.intersectionNW )) {
                    if (wRCars.get(w).loc.x+SmartCar.carLength >= Intersection.intersectionSE) {
                        speed = vel;
                        wRCars.get(w).accl = 1; //reset acceleration
                    }
                    if ((!intersection.getOuterEWMove() || intersection.getOuterEWRoll()) && 
                    		(wRCars.get(w).loc.x + SmartCar.carLength > Intersection.intersectionNW && 
                    				wRCars.get(w).loc.x < Intersection.intersectionSE)) {
                        speed = wRCars.get(w).accl(vel);
                        System.out.println("West2 POWER!!!");
                    }
                    
                } else if ((!intersection.getOuterEWMove() || intersection.getOuterEWRoll()) && 
                		(wRCars.get(w).loc.x >= (Intersection.intersectionNW - SmartCar.carLength) - slowDown 
                		&& wRCars.get(w).loc.x < (Intersection.intersectionNW - SmartCar.carLength))) {
                    speed = roll;
                } else {
                    speed = 0;
                } //Stand still 
            } else {
                speed = 0;
            } //Stand still
            
            for(int h = 0; h<NBCars2 && h<SBCars2; h++)
            //*Head On Collision 
            {
                if(wRCars.get(w).loc.x + SmartCar.carLength > Intersection.intersectionNW 
                		&& wRCars.get(w).loc.x + SmartCar.carLength < Intersection.intersectionSE)
                {
                    if(h<NBCars2 &&(nRCars.get(h).loc.y + SmartCar.carLength >= wRCars.get(w).loc.y 
                    		&& nRCars.get(h).loc.y <= wRCars.get(w).loc.y+SmartCar.carWidth))
                        //*Lane Potential
                    {System.out.println("WN: Potential Collision");
                        if(wRCars.get(w).loc.x+SmartCar.carLength >= nRCars.get(h).loc.x && wRCars.get(w).loc.x+SmartCar.carLength <= 
                        		nRCars.get(h).loc.x+SmartCar.carWidth)
                        //*Collision 
                        {System.out.println("WN: Collision");
                            speed = 0;
                        }
                    }
                    
                    if(h<SBCars2 &&(sRCars.get(h).loc.y <= wRCars.get(w).loc.y+SmartCar.carWidth && sLCars.get(h).loc.y+SmartCar.carLength >= wRCars.get(w).loc.y))
                    //*WS Lane Potential
                    {System.out.println("WS: Lane Potential");
                        if(wRCars.get(w).loc.x+SmartCar.carLength >= sRCars.get(h).loc.x && wRCars.get(w).loc.x+SmartCar.carLength 
                        		<= sRCars.get(h).loc.x+SmartCar.carWidth )
                        //*Collision
                        {System.out.println("WS: Collision");
                            speed = 0;
                        }
                    }
                }
            }//end for head on collision
            if(wRCars.get(w).loc.x <= 148){
            wRCars.get(w).loc.x += speed;
            wRCars.get(w).drawCar(brush);
            }else
            {
            	wRCars.get(w).loc.y+=speed;
            	wRCars.get(w).setTurn(true);
            	wRCars.get(w).rotateSouth();
            	if(intersection.canMove[3])
            	wRCars.get(w).turnCarSouth(brush);
            }
            
        }

    }
    synchronized void driveInnerNorth(int slowDown, int roll, int vel, int bump) throws IndexOutOfBoundsException {
        for (int n = 0; n < NBCars; n++) {
            boolean noCollision = true;
            boolean clearLane = true;
            int speed = vel;
            //Collision Avoidance Logic
            for (int c = 0; c < n; c++) {
                if (clearLane && (nLCars.get(c).loc.x == nLCars.get(n).loc.x)) {
                    clearLane = false;
                } else if (clearLane && (nLCars.get(c).loc.x != nLCars.get(n).loc.x)) {
                    clearLane = true;
                }

                if ((nLCars.get(c).loc.x == nLCars.get(n).loc.x) && (nLCars.get(c).loc.y - bump > nLCars.get(n).loc.y + SmartCar.carLength)) {
                    noCollision = true;
                } else if ((nLCars.get(c).loc.x == nLCars.get(n).loc.x) && (nLCars.get(c).loc.y - bump <= nLCars.get(n).loc.y + SmartCar.carLength)) {
                    noCollision = false;
                }
            }//end Collision Logic
            
            //Traffic Logic
            if ((n == 0) || (n > 0 && (noCollision || clearLane))) {
                if (intersection.getInnerNSMove() || (nLCars.get(n).loc.y + SmartCar.carLength < Intersection.intersectionNW - slowDown) 
                		|| (nLCars.get(n).loc.y + SmartCar.carLength > Intersection.intersectionNW)) {
                    if (nLCars.get(n).loc.y+SmartCar.carLength >= Intersection.intersectionSE) {
                        speed = vel;
                        nLCars.get(n).accl = 1; //reset acceleration
                    }
                    else if ((!intersection.getInnerNSMove() || intersection.getInnerNSRoll()) && (nLCars.get(n).loc.y + SmartCar.carLength 
                    		> Intersection.intersectionNW && nLCars.get(n).loc.y < Intersection.intersectionSE)) {
                        speed = nLCars.get(n).accl(vel);
                        System.out.println("North POWER!!!");
                    }
                    //nCars.get(n).loc.y += speed;
                } else if ((!intersection.getInnerNSMove() || intersection.getInnerNSRoll()) && (nLCars.get(n).loc.y + SmartCar.carLength 
                		>= Intersection.intersectionNW - slowDown && nLCars.get(n).loc.y + SmartCar.carLength < Intersection.intersectionNW)) {
                    //nCars.get(n).loc.y += roll;
                    speed = roll;
                } else {
                   // nCars.get(n).loc.y += 0;
                    speed = 0;
                } //Stand still
                
                //Check for head on Collision
                for(int h = 0; h<EBCars&&h<WBCars; h++){
                    if((nLCars.get(n).loc.y + SmartCar.carLength > Intersection.intersectionNW && nLCars.get(n).loc.y < Intersection.intersectionSE)){
                if(h<EBCars && (eLCars.get(h).loc.x <= nLCars.get(n).loc.x+SmartCar.carWidth && eLCars.get(h).loc.x+SmartCar.carLength 
                		>= nLCars.get(n).loc.x))
                //Same Lane Potential
                {System.out.println("NE: Potential Collision");
                    if(nLCars.get(n).loc.y+SmartCar.carLength >= eLCars.get(h).loc.y && nLCars.get(n).loc.y+SmartCar.carLength 
                    		< eLCars.get(h).loc.y+SmartCar.carWidth)
                    //Collision
                    {System.out.println("NE: Collision");
                        //*Action Performed 
                        speed = 0;
                    }//else if locations are equal yellow light runs
                }
                
                if(h<WBCars && (wLCars.get(h).loc.x+SmartCar.carLength >= nLCars.get(n).loc.x && wLCars.get(h).loc.x 
                		<= nLCars.get(n).loc.x+SmartCar.carWidth))
                //Same Lane Potential
                {System.out.println("NW: Potential Collision");
                    if(nLCars.get(n).loc.y+SmartCar.carLength >= wLCars.get(h).loc.y && nLCars.get(n).loc.y+SmartCar.carLength < wLCars.get(h).loc.y+SmartCar.carWidth)
                    //
                    {System.out.println("NW: Collision");
                        speed = 0;
                    }
                }
               }//end intersection box condition   
            }//end head on Collition
                
                nLCars.get(n).loc.y += speed;
            } else {
                nLCars.get(n).loc.y += 0;
            } //Stand still
           
            nLCars.get(n).drawCar(brush);
        }
    }//end driveNorth
    synchronized void driveOuterNorth(int slowDown, int roll, int vel, int bump) throws IndexOutOfBoundsException
    {
    	for (int n = 0; n < NBCars2; n++) {
            boolean noCollision = true;
            boolean clearLane = true;
            int speed = vel;
            //Collision Avoidance Logic
            for (int c = 0; c < n; c++) {
                if (clearLane && (nRCars.get(c).loc.x == nRCars.get(n).loc.x)) {
                    clearLane = false;
                } else if (clearLane && (nRCars.get(c).loc.x != nRCars.get(n).loc.x)) {
                    clearLane = true;
                }

                if ((nRCars.get(c).loc.x == nRCars.get(n).loc.x) && (nRCars.get(c).loc.y - bump > nRCars.get(n).loc.y + SmartCar.carLength)) {
                    noCollision = true;
                } else if ((nRCars.get(c).loc.x == nRCars.get(n).loc.x) && (nRCars.get(c).loc.y - bump <= nRCars.get(n).loc.y + SmartCar.carLength)) {
                    noCollision = false;
                }
            }//end Collision Logic
            
            //Traffic Logic
            if ((n == 0) || (n > 0 && (noCollision || clearLane))) {
                if (intersection.getOuterNSMove() || (nRCars.get(n).loc.y + SmartCar.carLength < Intersection.intersectionNW - slowDown) 
                		|| (nRCars.get(n).loc.y + SmartCar.carLength > Intersection.intersectionNW)) {
                    if (nRCars.get(n).loc.y+SmartCar.carLength >= Intersection.intersectionSE) {
                        speed = vel;
                        nRCars.get(n).accl = 1; //reset acceleration
                    }
                    else if ((!intersection.getOuterNSMove() || intersection.getOuterNSRoll()) && (nRCars.get(n).loc.y + SmartCar.carLength 
                    		> Intersection.intersectionNW && nRCars.get(n).loc.y < Intersection.intersectionSE)) {
                        speed = nRCars.get(n).accl(vel);
                        System.out.println("North POWER!!!");
                    }
                    //nCars.get(n).loc.y += speed;
                } else if ((!intersection.getOuterNSMove() || intersection.getOuterNSRoll()) && (nRCars.get(n).loc.y + SmartCar.carLength 
                		>= Intersection.intersectionNW - slowDown && nRCars.get(n).loc.y + SmartCar.carLength < Intersection.intersectionNW)) {
                    //nCars.get(n).loc.y += roll;
                    speed = roll;
                } else {
                   // nCars.get(n).loc.y += 0;
                    speed = 0;
                } //Stand still
                
                //Check for head on Collision
                for(int h = 0; h<EBCars2&&h<WBCars2; h++){
                    if((nRCars.get(n).loc.y + SmartCar.carLength > Intersection.intersectionNW && nRCars.get(n).loc.y < Intersection.intersectionSE)){
                if(h<EBCars2 && (eLCars.get(h).loc.x <= nLCars.get(n).loc.x+SmartCar.carWidth && eLCars.get(h).loc.x+SmartCar.carLength 
                		>= nRCars.get(n).loc.x))
                //Same Lane Potential
                {System.out.println("NE: Potential Collision");
                    if(nRCars.get(n).loc.y+SmartCar.carLength >= eLCars.get(h).loc.y && nRCars.get(n).loc.y+SmartCar.carLength 
                    		< eLCars.get(h).loc.y+SmartCar.carWidth)
                    //Collision
                    {System.out.println("NE: Collision");
                        //Action Performed 
                        speed = 0;
                    }//else if locations are equal yellow light runs
                }
                
                if(h<WBCars2 && (wLCars.get(h).loc.x+SmartCar.carLength >= nLCars.get(n).loc.x && wLCars.get(h).loc.x 
                		<= nRCars.get(n).loc.x+SmartCar.carWidth))
                //Same Lane Potential
                {System.out.println("NW: Potential Collision");
                    if(nRCars.get(n).loc.y+SmartCar.carLength >= wLCars.get(h).loc.y && nLCars.get(n).loc.y+SmartCar.carLength < wLCars.get(h).loc.y+SmartCar.carWidth)
                    //*
                    {System.out.println("NW: Collision");
                        speed = 0;
                    }
                }
               }//end intersection box condition   
            }//end head on Collition
                if(nRCars.get(n).loc.y<=148)
                	nRCars.get(n).loc.y += speed;
                else
                {
                	nRCars.get(n).loc.x -=speed;
                }
            } else {
                nRCars.get(n).loc.y += 0;
            } //Stand still
           
            if(nRCars.get(n).loc.y<=148)
            	nRCars.get(n).drawCar(brush);
            else{
            	nRCars.get(n).setTurn(true);
            	nRCars.get(n).rotateWest();
            	nRCars.get(n).turnCarWest(brush);
            }
        }
    }
    Thread carThread = new Thread(new Runnable() //*Generate and launch cars
    {
        public void run() {
            generate();
            try {

                boolean isRunning = true;
                int runup = 0;
                while (isRunning) //*While true:Game Loop extension
                {
                    //*Give the road time to refresh
                    if (nLCars.size() < nNLCars)//if theres's one more car to launch
                    {
                        launchNorth();
                    }
                    if (sLCars.size() < nSLCars) {
                        launchSouth();
                    }
                    if (eLCars.size() < nELCars) {
                        launchEast();
                    }
                    if (wLCars.size() < nWLCars) {
                        launchWest();
                        System.out.println();
                    }
                    if(nRCars.size() < nNRCars)
                    {
                    	//launchNorth(); //temp
                    }
                    if(sRCars.size() < nSRCars)
                    {
                    	//launchSouth();
                    }
                    if(wRCars.size() < nWRCars)
                    {
                    	//launchWest();
                    }
                    if(eRCars.size() < nERCars)
                    {
                    	//launchEast();
                    }
                    Thread.sleep(100);
                    repaint();
                    runup++;
                    if (runup > 170) {//just changed it from 170
                        runup = 0;
                        generate();
                    }
                }
            } catch (InterruptedException ex) {
            }
        }
    });//end Thread carThread

    public synchronized void generate() //*Generate a new set of cars when the simulation ends
    {
        System.out.println("generate");
        NBCars = SBCars = WBCars = EBCars = NBCars2 = SBCars2 = WBCars2 = EBCars2 = 1;

        nNLCars = randCar(); nNRCars = randCar();
        nSLCars = randCar(); nSRCars = randCar();
        nELCars = randCar(); nERCars = randCar();
        nWLCars = randCar(); nWRCars = randCar();//where is number of; S=south, etc ...
        //replacement car sizes
        northEast = new int[nNRCars];
        southWest = new int[nSRCars];
        eastNorth = new int[nERCars];
        westSouth = new int[nWRCars];
        //*Lane Positions
        int laneSpc = 7;
        NL = intersection.roadNSCen - (intersection.laneSize + SmartCar.carWidth + laneSpc);
        SL = intersection.roadNSCen + laneSpc;
        EL = intersection.roadEWCen - (intersection.laneSize) - SmartCar.carWidth - laneSpc;
        WL = intersection.roadEWCen + laneSpc/*308*/;

        SR = intersection.roadNSCen + (intersection.laneSize + laneSpc); //south right
        NR = intersection.roadNSCen - laneSpc - SmartCar.carWidth; //north right
        WR = intersection.roadEWCen + (intersection.laneSize + laneSpc); //west right
        ER = intersection.roadEWCen - laneSpc - SmartCar.carWidth; //east right

        //*Start Positions
        NSP = -SmartCar.carLength + 7;
        SSP = FrameY - 7;
        ESP = FrameX - 7;
        WSP = -SmartCar.carLength + 7;

        //*Launch first Vehicles for each lane
        if (!nLCars.isEmpty() || !sLCars.isEmpty() || !eLCars.isEmpty() || !wLCars.isEmpty()
        		|| !nRCars.isEmpty() || !sRCars.isEmpty() || !eRCars.isEmpty() || !wRCars.isEmpty()) {
            nLCars.clear(); nRCars.clear();
            sLCars.clear(); sRCars.clear();
            eLCars.clear(); eRCars.clear();
            wLCars.clear(); wRCars.clear();
        }
        nLCars.add(0, new SmartCar(0, NR, NSP, false));
        nLCars.get(0).carImage = northCars[randCol()];
        System.out.println("Good Launch North: " + NBCars + "/" + nNLCars);
        System.out.println("Good Launch North2: "+NBCars2+"/"+nNRCars);
        nRCars.add(0, new SmartCar(0, NL, NSP, false));
        northEast[0] = randCol();
        nRCars.get(0).carImage = northCars[northEast[0]];
        sLCars.add(0, new SmartCar(0, SL, SSP, false));
        sLCars.get(0).carImage = southCars[randCol()];
        System.out.println("Good Launch South: " + SBCars + "/" + nSLCars);
        System.out.println("Good Launch South2: "+SBCars2+"/"+nSRCars);
        southWest[0] = randCol();
        sRCars.add(0, new SmartCar(0, SR, SSP, false));
        sRCars.get(0).carImage = southCars[southWest[0]];
        eLCars.add(0, new SmartCar(false, 0, ESP, ER));
        eLCars.get(0).carImage = eastCars[randCol()];
        System.out.println("Good Launch East: " + EBCars + "/" + nELCars);
        System.out.println("Good Launch East2: "+EBCars2+"/"+nERCars);
        eastNorth[0] = randCol();
        eRCars.add(0, new SmartCar(0, EL, ESP, false));
        eRCars.get(0).carImage = eastCars[eastNorth[0]];
        //eastNorthCars.add(0, new SmartCar(false,375,0, SR));
        //eastNorthCars.get(0).carImage = eastCars[eastNorth[0]];
        wLCars.add(0, new SmartCar(false, 0, WSP, WL));
        wLCars.get(0).carImage = westCars[randCol()];
        System.out.println("Good Launch West: " + WBCars + "/" + nWLCars);
        System.out.println("Good Launch West2: "+WBCars2+"/"+nWRCars);
        westSouth[0] = randCol();
        wRCars.add(0, new SmartCar(false, 0, WSP, WR));
        wRCars.get(0).carImage = westCars[westSouth[0]];
        //westSouthCars.get(0).carImage = westCars[westSouth[0]];
        System.out.println();

        nLCars.trimToSize(); nRCars.trimToSize();
        sLCars.trimToSize(); sRCars.trimToSize();
        eLCars.trimToSize(); eRCars.trimToSize();
        wLCars.trimToSize(); wRCars.trimToSize();
        
      //first of all disable all cars ability to move
        if(firstMove==0){}
        else
    	for(int i=0; i<4; i++)
    	{
    		intersection.canMove[i] = false;
    	}firstMove++;
    }//end generate()

    synchronized void launchNorth() {
        //*Do While there are still cars to generate 
        if ((NBCars > 0 || NBCars2 >0)/*!nCars.isEmpty()*/ && (nLCars.get(NBCars - 1).loc.y >= (SmartCar.carLength - randBuffNW()))) // if preceding car far off enought get to start position
        //*Launch car only if it the lead if far off enough
        {
            /*north = NL;
            //*Lane picker
            if (randCar() % 2 == 0) {
                north = NL;
            } else {
                north = NR;
            }*/
            nLCars.add(NBCars, new SmartCar(NBCars, NR, NSP, false));
            nRCars.add(NBCars2, new SmartCar(NBCars2, NL, NSP, false));
            nLCars.get(NBCars).carImage = northCars[randCol()];//pick a random car color
            nRCars.get(NBCars2).carImage = northCars[randCol()];
            nLCars.trimToSize(); nRCars.trimToSize();
            NBCars = nLCars.size() ;//+ nRCars.size();//Number of cars on the road -- lazy programming adding nRCars.size()
            NBCars2 = nRCars.size();
            System.out.println("Good Launch North: " + NBCars + "/" + nNLCars);
            //System.out.println("Good launch North2: "+NBCars2+"/"+nNRCars);
        }
    }//end launchNorth

    synchronized void launchWest() {
        //*Do While there are still cars to generate 
        if ((WBCars > 0 || WBCars >0) /*!wLCars.isEmpty()*/ && (wLCars.get(WBCars - 1).loc.x >= (SmartCar.carLength - randBuffNW()))) // if precedding car far off enought get to start position
        //*Launch car only if it the lead if far off enough
        {
            /*west = WL;
            //*Lane picker
            if (randCar() % 2 == 0) {
                west = WL;
            } else {
                west = WR;
            }*/
            wLCars.add(WBCars, new SmartCar(false, 1, WSP, WL));
            wRCars.add(WBCars2, new SmartCar(false, 1, WSP, WR));
            wLCars.get(WBCars).carImage = westCars[randCol()];
            wRCars.get(WBCars2).carImage = westCars[randCol()];
            wLCars.trimToSize(); wRCars.trimToSize();
            WBCars = wLCars.size();// + wRCars.size();//Number of cars on the road
            WBCars2 = wRCars.size();
            System.out.println("Good Launch West: " + WBCars + "/" + WBCars);
            //System.out.println("Good Launch West 2: "+WBCars2+"/"+WBCars2);
        }
    }//end launchWest

    synchronized void launchSouth() {

        //*Do While there are still cars to generate 
        if ((SBCars > 0 || SBCars2 > 0)/*!sCars.isEmpty()*/ && (sLCars.get(SBCars - 1).loc.y <= (FrameY - SmartCar.carLength - randBuffSE()))) // if precedding car far off enought get to start position
        //*Launch car only if it the lead if far off enough
        {
            /*south = SL;
            if (randCar() % 2 == 0) {
                south = SL;
            } else {
                south = SR;
            }*/
            sLCars.add(SBCars, new SmartCar(SBCars, SL, SSP, false));
            sRCars.add(SBCars2, new SmartCar(SBCars2, SR, SSP, false));
            sLCars.get(SBCars).carImage = southCars[randCol()];//pick a random car color
            sRCars.get(SBCars2).carImage = southCars[randCol()];
            sLCars.trimToSize(); sRCars.trimToSize();
            SBCars = sLCars.size();// + sRCars.size();
            SBCars2 = sRCars.size();
            System.out.println("Good Launch South: " + SBCars + "/" + nSLCars);
            //System.out.println("Good Launch South2: "+SBCars2+"/"+nSRCars);
        }
    }//end launchSouth()

    synchronized void launchEast() {
        //*Do While there are still cars to generate 
        if ((EBCars > 0 || EBCars2 >0)/*!eCars.isEmpty()*/ && (eLCars.get(EBCars - 1).loc.x <= (FrameX - SmartCar.carLength - randBuffSE()))) // if preceding car far off enough get to start position
        //*Launch car only if it the lead if far off enough
        {
            /*east = EL;
            if (randCar() % 2 == 0) {
                east = EL;
            } else {
                east = ER;
            }*/
            eLCars.add(EBCars, new SmartCar(false, EBCars, ESP, ER));
            eRCars.add(EBCars2, new SmartCar(false, EBCars2, ESP, EL));
            eLCars.get(EBCars).carImage = eastCars[randCol()];//pick a random car color
            eRCars.get(EBCars2).carImage = eastCars[randCol()];
            eLCars.trimToSize(); eRCars.trimToSize();
            EBCars = eLCars.size() ;//+ eRCars.size();
            EBCars2 = eRCars.size();
            System.out.println("Good Launch East :" + EBCars + "/" + nELCars);
            //System.out.println("Good Launch East2 :"+EBCars2+"/"+nERCars);
        }
    }//end launchEast()

    int randBuffNW() {
        int buff = (int) (Math.random() * 11) + 7;
        return buff;
    }

    int randBuffSE() {
        int buff = (int) (Math.random() * 11) + SmartCar.carLength;
        return buff;
    }

    public int randCar() //random number of cars to place on a lane
    {//maximum of 3 cars per lane
        int temp;
        temp = (int) (Math.random() * 3) + 1;
        return temp;
    }//end randCar()

    public int randCol() //random type of car to choose
    {
        int temp;
        temp = (int) (Math.random() * 5) + 0;
        return temp;
    }//end randCol
    //choose to randomly turn or not to turn
    public boolean randTurn()
    {
    	int temp;
    	temp = (int)(Math.random()*1)+0;
    	
    	if(temp==0)
    		return true;
    	else
    		return false;
    }
    public Graphics2D rendering(Graphics2D brush) {
        brush.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        brush.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        brush.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        brush.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        brush.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        brush.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        brush.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        return brush;
    }//end rendering

    synchronized void carImage() {

        northCarNames = new String[]{"Cars/rich1C270.png", "Cars/rich1P270.png", "Cars/rich1S527.png",
            "Cars/rich2G270.png", "Cars/rich2R270.png", "Cars/rich2W270.png"};

        southCarNames = new String[]{"Cars/rich1C90.png", "Cars/rich1P90.png", "Cars/rich1S590.png",
            "Cars/rich2G90.png", "Cars/rich2R90.png", "Cars/rich2W90.png"};

        eastCarNames = new String[]{"Cars/rich1C180.png", "Cars/rich1P180.png", "Cars/rich1S518.png",
            "Cars/rich2G180.png", "Cars/rich2R180.png", "Cars/rich2W180.png"};

        westCarNames = new String[]{"Cars/rich1C0.png", "Cars/rich1P0.png", "Cars/rich1S500.png",
            "Cars/rich2G0.png", "Cars/rich2R0.png", "Cars/rich2W0.png"};

        int images = 6;
        northCars = new Image[images];
        southCars = new Image[images];
        eastCars = new Image[images];
        westCars = new Image[images];

        //fill image arrays with the car names
        int mt = 1;
        for (int i = 0; i < images; i++, mt++) {
            northCars[i] = getImage(getCodeBase(), northCarNames[i]);
            media.addImage(northCars[i], mt);
        }
        for (int i = 0; i < images; i++, mt++) {
            southCars[i] = getImage(getCodeBase(), southCarNames[i]);
            media.addImage(southCars[i], mt);
        }
        for (int i = 0; i < images; i++, mt++) {
            eastCars[i] = getImage(getCodeBase(), eastCarNames[i]);
            media.addImage(eastCars[i], mt);
        }
        for (int i = 0; i < images; i++, mt++) {
            westCars[i] = getImage(getCodeBase(), westCarNames[i]);
            media.addImage(westCars[i], mt);
        }
    }//end carImage()    
}//end Class
