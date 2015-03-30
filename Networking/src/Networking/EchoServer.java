package Networking;

import java.net.*;
import java.io.*;

public class EchoServer {
	public static void main(String []args) throws IOException{
		ServerSocket server = null;
		try{
			server = new ServerSocket(10007);
		}
		catch(IOException e){
			System.out.println("Can't listen on 10007");
		}

		Socket client = null;
		System.out.println("Listening for connection...");

		try{
			client = server.accept();
		}catch(IOException e){
			System.out.println("Accept failed!!!");
		}

		System.out.println("Connection Successful\nNow Listening for input....");

		PrintWriter output = new PrintWriter(client.getOutputStream(), true);

		BufferedReader input = new BufferedReader(new InputStreamReader(
			client.getInputStream()
			));
		String inputLine;

		while((inputLine = input.readLine()) !=null){
			System.out.println("Server: "+inputLine);
			output.println(inputLine);

			if(inputLine.equals("Bye")){
				break;
			}
			output.close();
			input.close();
			client.close();
			server.close();
		}
	}
}