package com.wipro.sales.util;

import java.sql.*;

public class DBUtil {
	static String db_url = "jdbc:oracle:thin:@localhost:1521:xe";
	static Connection con = null;
	static String user = "username";
	static String pass = "password";
	public static Connection getDBConnection() {
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521", "scott", "tiger");
			return con;
		} catch (SQLException e) {
			System.out.println("Connection could not be estanlished");
			e.printStackTrace();
			return null;
		}
	}
}
