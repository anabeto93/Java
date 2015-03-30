package ExceptionHandling;

import java.io.*;
import java.util.*;

public class MultipleCatch{
	public static void main(String args[]){
		String file = args[0]; String line="";

		try{
		BufferedReader inFile = new BufferedReader(new FileReader(file));
			while((line=inFile.readLine())!=null){
				System.out.println(line);
			}
		}catch(FileNotFoundException e){
			System.out.println("The file is not found. Please try again!");
		}catch(IOException e){
			//maybe right protected
			System.out.println("Problem reading from file!!!");
		}

	}
}