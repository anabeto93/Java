package Networking;

import java.net.*;
import java.io.*;

public class EchoClient{
	public static void main(String []args) throws IOException{
		Socket sock = null;
		PrintWriter output = null;
		BufferedReader input =null;

		try{
			sock = new Socket("127.0.0.1",10007);
			output = new PrintWriter(sock.getOutputStream(),true);
			input = new BufferedReader(new InputStreamReader(
				sock.getInputStream()
				));
		}
		catch(UnknownHostException ue){
			System.out.println("Unkown Host");
		}
		catch(IOException e){
			System.out.println("Cannot connect!");
		}

		BufferedReader stdIn = new BufferedReader(
			new InputStreamReader(System.in)
			);

		String userInput;
		while((userInput=stdIn.readLine())!=null){
			output.println(userInput);
			System.out.println("echo: "+input.readLine());
		}

		output.close(); input.close();
		stdIn.close(); sock.close();
	} 
}