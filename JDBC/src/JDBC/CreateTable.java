package JDBC;

import java.sql.*;

public class CreateTable{
	public static void main(String[] args){
		Connection connection = null;

		try{

			String username = "root"; String password = "toor"; String url = "jdbc:mysql://localhost/canteen";

			Class.forName("com.mysql.jdbc.Driver").newInstance();

			connection = DriverManager.getConnection(url,username,password);

			Statement stmt = connection.createStatement();
			stmt.executeUpdate("drop table if exists javatable");
			stmt.executeUpdate(
				"create table javatable ("
					+"isbn char(10), title char(50),"
					+"author char(50), publisher char(50))"
				);

			connection.close();
			System.out.println("Table created successfully!");


		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}