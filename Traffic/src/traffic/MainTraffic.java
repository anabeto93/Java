package traffic;

import javax.swing.*;

import java.awt.*;

public class MainTraffic extends JApplet
{
	Road road = new Road();
	Light network = new Light();
	//Light network1 = new Light();
	TrafficLogic lights;
	
	int positions[] = {0,0,0,0};//starting point of all cars
	 Image[] N1,S1,E1,W1;
	 String[] N1names = {"Cars/rich1C270.png","Cars/rich1P270.png","Cars/rich1S527.png","Cars/rich2G270.png","Cars/rich2W270.png"};
	 String[] S1names = {"Cars/rich1C90.png","Cars/rich1P90.png","Cars/rich1S590.png","Cars/rich2G90.png","Cars/rich2W90.png"};
	 String[] E1names = {"Cars/rich1C180.png","Cars/rich1P180.png","Cars/rich1S518.png","Cars/rich2G180.png","Cars/rich2W180.png"};
	 String[] W1names = {"Cars/rich1C0.png","Cars/rich1P0.png","Cars/rich1S500.png","Cars/rich2G0.png","Cars/rich2W0.png"};
	
	Thread draw = new Thread(new Runnable()
	{
		public void run()
		{
			moveCar();
		}
	});
	public void init()
	{
		N1 = new Image[N1names.length];
		S1 = new Image[S1names.length];
		E1 = new Image[E1names.length];
		W1 = new Image[W1names.length];
		
		for(int i=0; i<N1names.length; ++i)
		{
			N1[i] = getImage(getCodeBase(),N1names[i]);
			S1[i] = getImage(getCodeBase(), S1names[i]);
			E1[i] = getImage(getCodeBase(), E1names[i]);
			W1[i] = getImage(getCodeBase(), W1names[i]);
		}
		setSize(600,600);
		lights= new TrafficLogic();
		draw.start();
	}
	public void paint(Graphics brush)
	{
		road.paintRoad(brush);
	}
	
	public void moveCar()
	{
		Graphics brush = getGraphics();
		int i1,i2,i3,i4;
		i1 = num(); i2 = num(); i3=num(); i4=num();
		for(int i=0; i<4000; i++)
		{
			try
			{
				Thread.sleep(10);//controls the speed of the cars
				road.paintRoad(brush);
				drawCars(brush,i1,i2,i3,i4);
			}catch(InterruptedException e)
			{
				e.printStackTrace();
			}
			
		}
	}
	//generate random numbers
	public int num()
	{
		int temp;
		temp = (int)(Math.random()*5)+0;
		return temp;
	}		
	
	public void drawCars(Graphics g, int i1, int i2, int i3, int i4) //in where n=0...4 are just specific cars
	{
		
		//just testing rendering hints
				Graphics2D brush = (Graphics2D) g;
				brush.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
						RenderingHints.VALUE_INTERPOLATION_BICUBIC);
				
				//rendering ends here
		
		//draw cars on the various lanes
		brush.drawImage(N1[i1],295-40,positions[0],50,50,this);
		if(network.getN1Move())
		{
			positions[0]+=1;
			network.paintLight(brush, 1);
			if(positions[0]>600)
				positions[0]=0; //reset
		}
		brush.drawImage(S1[i2],305,570-positions[1],50,50,this);
		if(network.getS1Move())
		{
			positions[1]+=1;
			network.paintLight(brush, 2);
		}else if(positions[1]<210)
		{
				positions[1]+=1;
		}
		brush.drawImage(W1[i3],positions[2],295,50,50,this);
		if(network.getW1Move())
		{
			positions[2]+=1;
			network.paintLight(brush, 3);
		}else if(positions[2]<190)
		{
			positions[2]+=1;
		}
		brush.drawImage(E1[i4],570-positions[3],295-40,50,50,this);
		if(network.getE1Move())
		{
			positions[3]+=1;
			network.paintLight(brush, 4);
		}else if(positions[3]<190)
		{
			positions[3]+=1;
		}
	}			
			
}