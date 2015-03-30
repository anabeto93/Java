package MultiThread;

class Market{
	int n; boolean valSet = false;
	synchronized int get(){
		if(!valSet){ //wait while the value is being set
			try{
			wait();
			}
			catch(InterruptedException e){
				System.out.println("Get interrupted!");
			}
		}
		System.out.println("Got: "+n);
		valSet = false;
		notify();
		return n;
	}

	synchronized void put(int num){
		if(valSet){
			try{
				wait();
			}
			catch(InterruptedException e){
				System.out.println("Put interrupted!");
			}
		}
		this.n = num;
		valSet = true;
		System.out.println("Put: "+n);
		notify();
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

public class SyncThread{
	public static void main(String args[]){
		Market today = new Market();
		new Producer(today);
		new Consumer(today);

		System.out.println("Stop the processes(threads) by pressing Ctrl+C");
	}
}