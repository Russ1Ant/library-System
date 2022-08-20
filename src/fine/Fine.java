package fine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import book.dbConnection.ConnectDB;

public class Fine {
	
	private static ConnectDB db = new ConnectDB();
	
	
	public static void updateStudent(String id) {
		double fine = 0.00;
		int days = 0;
		try {
			Connection conn = db.connect();
			String upd = " update circulation set days = datediff(now(), issued_date)";
				
			String sql = "select * from circulation where student_id = '"+id+"' and book_status = 'issued'";
			Statement stm = conn.createStatement();
			stm.execute(upd);
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				
				System.out.println("Days updated");
				days = rs.getInt("days");
				System.out.println("days is set");
				
			
					if(days > 7) {
						days = days - 7;
						fine = days * 5;
						PreparedStatement ps1 = conn.prepareStatement("Update circulation set fine = ? where student_id = ?");
						ps1.setDouble(1, fine);
						ps1.setString(2, id);
						ps1.executeUpdate();
						System.out.println("Circulation updated");
						
						PreparedStatement ps2 = conn.prepareStatement("update students set balance = ? where reg_no = ?");
						ps2.setDouble(1, fine);
						ps2.setString(2, id);
						ps2.executeUpdate();
						
						System.out.println("Circulation and Students updated");
					}else {
						System.out.println("Days not greater than 7");
					}
			}
		}catch(Exception e) {
			System.out.println(e);
		}
		
		
	}
	public static void addToCirculation() throws ClassNotFoundException, SQLException {
		Connection conn = db.connect();
		String sql = "insert into circulation";
		}

	
}

