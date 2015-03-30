package JDBC;

import java.sql.*;

public class ConnectDB {
	public static void main(String[] args){
		Connection conn = null;
		try{
			String userName = "root";
			String pass = "toor";
			String url = "jdbc:mysql://localhost:3306/test";
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url,userName,pass);
			
			System.out.println("System connected!");
		}catch(Exception e){
			System.out.println("Could not connect!");
		}
	}
}
