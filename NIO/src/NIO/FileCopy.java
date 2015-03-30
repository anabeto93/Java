package NIO;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class FileCopy{
	public static void main(String args[]) throws IOException {
		File sourceFile = new File(args[0]); //the file you want to copy
		File destinationFile = new File(args[1]);

		if(!destinationFile.exists()){
			destinationFile.createNewFile();
		}
		FileChannel sourceChannel =null;
		FileChannel destinationChannel =null;

		try{
			sourceChannel = new FileInputStream(sourceFile).getChannel();
			destinationChannel = new FileOutputStream(destinationFile).getChannel();

			destinationChannel.transferFrom(sourceChannel,0,sourceChannel.size());//start from 0 bytes to the size of the file
		}
		finally{
			if(sourceChannel != null){
				sourceChannel.close();
			}
			if(destinationChannel !=null){
				destinationChannel.close();
			}
		}
	}
}