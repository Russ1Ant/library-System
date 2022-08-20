package book.dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
	
	public  Connection connect() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		System.out.println("Driver loaded");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/library_db", "root", "q123");
		System.out.println("Database connected");
		return conn;
	}
	
}
