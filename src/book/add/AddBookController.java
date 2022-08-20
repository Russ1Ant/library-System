package book.add;
import java.sql.Connection;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import book.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddBookController {
	
	@FXML
	private TextField bookTitle;
	
	@FXML
	private TextField author;
	
	@FXML
	private TextField bookIsbn;
	
	@FXML
	private TextField bookQuantity;
	
	@FXML
	private TextField publisher;
	
	@FXML
	private Button addBook;
	
	@FXML
	private Button back;
	
	
	private Main main = new Main();
	
	@FXML
	public void addBook(ActionEvent e) throws ClassNotFoundException, SQLException {
		
		if(bookTitle.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Title cannot be null ");
		}else if(author.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Author cannot be null");
			
			
			
		}else if(bookIsbn.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Isbn cannot be null ");

		}
		
		else if(bookQuantity.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Quantity cannot be null ");
	

		}else if (publisher.getText().isEmpty()) {
			JOptionPane.showConfirmDialog(null, "publisher name cannot be null...");
		}
		else {
		
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/library_db", "root", "q123");
			System.out.println("Database connected");
			
			String sql = "Insert into books(title, author, isbn, quantity, publisher) values(?, ?, ?, ?, ?)";
			PreparedStatement pst;
    	try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, bookTitle.getText());
			pst.setString(2, author.getText());
			pst.setString(3, bookIsbn.getText());
			pst.setInt(4, Integer.parseInt(bookQuantity.getText()));
			pst.setString(5, publisher.getText());
			
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
