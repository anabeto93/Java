package traffic;


public class TrafficLogic {
	Light light = new Light();
	
	Thread n1 = new Thread(new Runnable(){
		public void run()
		{
			try {
				light.N1move();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	});
	
	Thread s1 = new Thread(new Runnable(){
		public void run()
		{
			try {
				light.S1move();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	});
	
	Thread e1 = new Thread(new Runnable()
	{
		public void run()
		{
			try {
			light.E1move();
			} catch (InterruptedException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	});
	
	Thread w1 = new Thread(new Runnable(){
		public void run()
		{
			try {
				light.W1move();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	});
	
	public TrafficLogic()
	{
		n1.start();
		s1.start();
		e1.start();
		w1.start();
	}
	
}
