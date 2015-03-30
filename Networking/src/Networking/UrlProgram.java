package Networking;

import java.net.*;
import java.io.*;

public class UrlProgram{
	public static void main(String[] args) throws Exception {
		URL website = new URL("http://www.ug.edu.gh");
		System.out.println("Basic Information:\nProtocol: "+website.getProtocol());

		System.out.println("Port: "+website.getPort()+"\nHost: "+website.getHost());

		URLConnection theConn = website.openConnection();

		int contentLength = theConn.getContentLength();
		int c;

		if(contentLength!=0){
			System.out.println("Content: \n");
			InputStream urlInput = theConn.getInputStream();

			while(((c=urlInput.read())!=-1)){
				System.out.print((char) c);
			}

			urlInput.close();
		}else{
			System.out.println("Sorry no content!");
		}

	}
}