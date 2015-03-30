package JDBC;

import java.sql.*;

public class Connect {
	public static void main(String[] args){
		try{
			String host="jdbc:mysql://localhost:3306/test";
			String username="root";
			String password="toor";
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			Connection conn = DriverManager.getConnection(host,username,password);
			
			System.out.println("Connected to the test database!");
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}
