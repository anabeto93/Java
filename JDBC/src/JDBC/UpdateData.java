package JDBC;

import java.sql.*;
import java.io.*;

public class UpdateData{
	public static void main(String args[]){
		Connection conn = null; BufferedReader input=null;

		try{

			String uname = "root", pass="toor",url="jdbc:mysql://localhost/canteen";

			Class.forName("com.mysql.jdbc.Driver").newInstance();

			conn = DriverManager.getConnection(url,uname,pass);

			Statement query = conn.createStatement();

			input = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Enter the record to update: ");
			String isbn = input.readLine();
			System.out.println("Enter field to update: ");
			String field = input.readLine();
			System.out.println("Enter data to update with: ");
			String data = input.readLine();

			String updateString = "update javatable set "+field+" =' "+data+"' "
								+" where isbn ='"+isbn+"'";

			query.executeUpdate(updateString);

			conn.close();
			System.out.println("Succesfully updated the table!");

		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}