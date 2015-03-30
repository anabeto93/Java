package ExceptionHandling;

import java.io.*;
import java.util.*;

public class TypeError{
	public static void main(String args[]){
		String file = args[0];//from commandline

		try{
			BufferedReader input = new BufferedReader(new FileReader(file));

		}catch(FileNotFoundException e){
			System.out.println("File not found. Please try again!");
		}
	}
}