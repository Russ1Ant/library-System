package book.holdBook2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import book.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class HoldBookController {
	@FXML
	private TextField bookTitle;
	
	
	@FXML
	private TextField bookIsbn;
	
	@FXML
	private TextField studentName;
	
	@FXML
	private TextField studentId;
	
	@FXML
	private Button placeBookOnHold;
	
	@FXML
	private Button back;
	
	
	private Main main = new Main();
	
	@FXML
	public void placeBookOnHold(ActionEvent e) throws ClassNotFoundException, SQLException {
		
		if(bookIsbn.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Isbn cannot be null ");

		}
		
		else if(studentId.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "StudentId cannot be null ");
	

		}else {
			
			
		
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/library_db", "root", "q123");
			System.out.println("Database connected");
			
			String sqlb= "select * from books where isbn = '"+bookIsbn.getText()+"'";
			String sqls = "select * from students where reg_no = '"+studentId.getText()+"'";
			
			Statement stb = conn.createStatement();
			ResultSet rsb = stb.executeQuery(sqlb);
			
			Statement sts = conn.createStatement();
			ResultSet rss = sts.executeQuery(sqls);
			
			if(!rsb.next()) {
				JOptionPane.showMessageDialog(null, "Book does not exist");
				return;
			}
			if(!rss.next()) {
				JOptionPane.showMessageDialog(null, "Student does not exist");
				return;
			}
			
			String sql = "Insert into books_on_hold(book_title, isbn, student_id, student_name) values(?, ?, ?,  ?)";
			PreparedStatement pst;
    	try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, rsb.getString("title"));
			pst.setString(2, bookIsbn.getText());
			pst.setString(3, studentId.getText());
			pst.setString(4, rss.getString("first_name"));
			
			
			pst.execute();
			JOptionPane.showMessageDialog(null, "Successfullly added");
			pst.close();
		} catch (SQLException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
    	
		}
	}
	
	@FXML
	public void goBack(ActionEvent e) throws IOException {
		main.showViewBooksInDbView();
	}

}
