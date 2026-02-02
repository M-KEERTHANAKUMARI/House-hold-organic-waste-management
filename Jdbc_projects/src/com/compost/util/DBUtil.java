package com.compost.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
public static Connection getDBConnection() {
	 try {
		Class.forName("oracle.jdbc.OracleDriver");
		String url="jdbc:oracle:thin:@localhost:1521:xe";
        String user="test";
        String password="test123";
        Connection connection;
		
			connection = DriverManager.getConnection(url,user,password);
			return connection;
	 } catch (ClassNotFoundException |SQLException e) {
		e.printStackTrace();
		return null;
	 }
      }
}
