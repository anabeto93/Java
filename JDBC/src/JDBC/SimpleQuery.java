package JDBC;

import java.sql.*;

public class SimpleQuery{
	public static void main(String[] args){
		Connection conn = null;
		try{
			String userName = "root";
			String passWord = "toor";
			String url = "jdbc:mysql://localhost/canteen";

			Class.forName("com.mysql.jdbc.Driver").newInstance();

			conn = DriverManager.getConnection(url,userName,passWord);
			Statement stmt;
			ResultSet result;

			stmt = conn.createStatement();
			result = stmt.executeQuery("select * from sales");

			while(result.next()){
				String ID = result.getString("id");
				String drinkName = result.getString("drinkName");
				String numSold = result.getString("numberSold");

				System.out.println(ID+" "+drinkName+" "+numSold+" .");
			}
			result.close();
			conn.close();

		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}