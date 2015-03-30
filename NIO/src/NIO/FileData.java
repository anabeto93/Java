package NIO;

import java.io.*;
import java.nio.file.*;

public class FileData{
	public static void main(String[] args){
		Path filePath = Paths.get(args[0]);
		System.out.println("File name: "+filePath.getName(1));

		try{
			if(!Files.isHidden(filePath)){
				System.out.println("File is not hidden");
			}else{
				System.out.println("File is hidden");
			}

			if(Files.isWritable(filePath)){
				System.out.println("Can write to the file.");
			}else{
				System.out.println("File is write protected");
			}

			if(Files.isReadable(filePath)){
				System.out.println("Can read from the file");
			}else{
				System.out.println("Cannot read from the file!");
			}
		}catch(IOException e){
			System.out.println("Error checking file properties.");
		}
	}
}