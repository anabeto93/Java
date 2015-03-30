package MultiThread;

import java.io.*;

public class TwoThreads{
	public static void main(String args[]) throws InterruptedException{
		Thread firstThread = new UserInteraction();
		firstThread.start();
		Thread secondThread = new ComputeLog();
		secondThread.start();

		firstThread.join();
		((ComputeLog) secondThread).end(); //secondThread ends when first does
	}
}

class UserInteraction extends Thread{
	public void run(){
		try{
			BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));

			System.out.println("Enter your name: ");
			String input = userIn.readLine();
			System.out.println("Hello "+input+", how are you? ");
			input = userIn.readLine();
			System.out.println("What are you doing today? ");
			input = userIn.readLine();
			System.out.println("It is a very good thing");
		}catch(IOException e){
			System.out.println("Caught some I/O Exception!!");
		}
	}
}

class ComputeLog extends Thread{
	private static boolean stop = false;
	public void run(){
		try{
			int i=1; double d =0;
			while(!stop){
				d = Math.log(i++);
				sleep(1);
			}
			System.out.println("The log of "+i+" is "+d);
		}catch(InterruptedException e){
			System.out.println("Thread Execution was Interrupted!");
		}
	}
	public void end(){
		stop = true;
	}
}