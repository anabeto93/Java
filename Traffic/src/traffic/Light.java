package traffic;

import java.awt.*;

public class Light {
	private volatile boolean[] canMove;
	public Light()
	{
		canMove = new boolean[4];
		canMove[0]=true;
	}
	public void N1move() throws InterruptedException
	{
		//synchronized(this)
		//{
			while(true)
			{
				System.out.println("N1 has started");
				Thread.sleep(5000);
				canMove[0] = false;
				System.out.println("N1 has stopped");
				Thread.sleep(15000);
				canMove[0]=true;
			//}
		}
		
	}
	public void S1move() throws InterruptedException
	{
		//synchronized(this)
		//{
			while(true)
			{	
				Thread.sleep(5000);
				canMove[1] = true;
				System.out.println("S1 has started");
				Thread.sleep(5000);
				canMove[1] = false;
				System.out.println("S1 has stopped");
				Thread.sleep(10000);
			}
		//}
		
	}
	public void E1move() throws InterruptedException
	{
		//synchronized(this)
		//{
			while(true)
			{
				Thread.sleep(10000);
				canMove[2] = true;
				System.out.println("E1 has started");
				Thread.sleep(5000);
				canMove[2] = false;
				System.out.println("E1 has stopped");
				Thread.sleep(5000);
			}
		//}
		
	}
	public void W1move() throws InterruptedException
	{
		//synchronized(this)
		//{
			while(true)
			{
				Thread.sleep(15000);
				canMove[3] = true;
				System.out.println("W1 has started");
				Thread.sleep(5000);
				canMove[3] = false;
				System.out.println("W1 has stopped");
			}
		//}
		
	}
	public synchronized boolean getN1Move()
	{
		return canMove[0];
	}
	public synchronized boolean getS1Move()
	{
		return canMove[1];
	}
	public synchronized boolean getE1Move()
	{
		return canMove[2];
	}
	public synchronized boolean getW1Move()
	{
		return canMove[3];
	}
	public void paintLight(Graphics g, int index) //index specifies which one is green all others are red by default
	{
		Graphics2D brush = (Graphics2D) g;
		brush.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		Color go = Color.GREEN;
		Color ready = Color.YELLOW;
		Color stop = Color.RED;
		
		//Graphics hmm = getGraphics();
		
		if(index==1)//1 stands for N1
		{
			brush.setColor(go);
			brush.fillRect(300-45,295-50,40,10);//cordinates for right rect on N1
			brush.setColor(stop);
			brush.fillRect(300-55,295+10, 10, 40);//W1
			brush.fillRect(300+45, 295-40, 10, 40);//E1
			brush.fillRect(300+5,295+50,40,10);//S1
		}else if(index==2)//stands for S1
		{
			brush.setColor(go);
			brush.fillRect(300+5,295+50,40,10);
			brush.setColor(stop);
			brush.fillRect(300-45,295-50,40,10);
		}else if(index==3)//stands for E1
		{
			brush.setColor(go);
		}else if(index==4)
		{
			brush.setColor(go);
		}
	}
}
