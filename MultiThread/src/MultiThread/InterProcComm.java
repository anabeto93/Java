package MultiThread;

class Market{
	int n;
	synchronized int get(){
		System.out.println("Got: "+n);
		return n;
	}

	synchronized void put(int num){
		this.n = num;
		System.out.println("Put: "+n);
	}
}

class Producer implements Runnable{
	Market m;

	public Producer(Market here){
		this.m = here;
		new Thread(this,"Producer").start();

	}
	public void run(){
		int i=0; 
		while(true){
			m.put(i++);
		}
	}
}

class Consumer implements Runnable{
	Market m;

	public Consumer(Market there){
		this.m = there;
		new Thread(this,"consumer").start();
	}
	public void run(){
		int i=0; 
		while(true){
			m.get();
		}
	}
}

public class InterProcComm{
	public static void main(String args[]){
		Market today = new Market();
		new Producer(today);
		new Consumer(today);

		System.out.println("Stop the processes(threads) by pressing Ctrl+C");
	}
}