package connectivitypkg;

import java.sql.Connection;
import java.sql.DriverManager;


public class Database {
	public Connection connection;
	
	public Connection getConnection() {
		String dbName = "library_db";
		String userName = "root";
		String password = "q123";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName,userName,password);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return connection;
		
	}
	

}
