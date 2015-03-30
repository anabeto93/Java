package JDBC;

import java.sql.*;

public class InsertData{
	public static void main(String args[]){
		Connection conn = null;

		try{

			String username = "root"; String password = "toor"; String url="jdbc:mysql://localhost/canteen";

			Class.forName("com.mysql.jdbc.Driver").newInstance();

			conn = DriverManager.getConnection(url,username,password);

			Statement query = conn.createStatement();
			query.executeUpdate(
				"insert into javatable(isbn,title,author,publisher)"
				+"values ('007160630','Java-The complete Reference','Herbert Schmidt','Oracle Press'),"
				+"('032135668','Effective Java','Joshua Bloch','Addison-Wesley')"
				);

			conn.close();//close connection

			System.out.println("Table updated with values!!!");

		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}