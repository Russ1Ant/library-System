package book.returnB2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import book.Main;
import book.dbConnection.ConnectDB;
import connectivitypkg.Database;
import fine.Fine;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ReturnBookController {
	
	@FXML
	private TextField isbn;
	
	@FXML
	private TextField studentId;
	
	private Main main = new Main();
	
	ConnectDB db = new ConnectDB();
	
	@FXML
	public void goBack(ActionEvent e) throws IOException {
		main.showViewBooksInDbView();
	}
	boolean checkWhetherPresentInCirculation() {
		Database db = new Database();
		
		Connection conn = db.getConnection();
		String issued = "issued";
		String sql = "select * from circulation where isbn ='"+isbn.getText()+"'and student_id ='"+studentId.getText()+"' and book_status ='"+issued+"' ";
		try {
			ResultSet rss = conn.prepareStatement(sql).executeQuery();
			if(rss.next()) {
			}
			return true;
			
		}catch(SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "student not issued with book please check you input the values correctly ");
			return false;
		}
		
	}
	
	
	@FXML 
	void returnBook1(ActionEvent e) {
		if(isbn.getText().isBlank()) {
			JOptionPane.showMessageDialog(null, "Isbn cannot be null ");
		}else if(studentId.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "StudentId cannot be null");
			/*
			 * }else if(checkWhetherPresentInCirculation()) { return;
			 */
		}
		else {
			
			
			updateBookAndCirculation(isbn.getText());
		
		}
		}
		
		void updateBookAndCirculation(String isbnM){
			
			int quantity=0,issued=0;
			try{
				
				
				
				Connection con= db.connect();
				//update book
				PreparedStatement ps=con.prepareStatement("select quantity,issued from books where isbn=?");
				ps.setString(1,isbnM);
				ResultSet rs=ps.executeQuery();
				while(rs.next()){
					quantity=rs.getInt("quantity");
					issued=rs.getInt("issued");
					System.out.println("Quantity and Issued updated");
					System.out.println("valued of issued is "+issued);
				}
				
				if(issued>0){
					
				PreparedStatement ps2=con.prepareStatement("update books set quantity=?,issued=? where isbn=?");
				ps2.setInt(1,quantity+1);
				ps2.setInt(2,issued-1);
				ps2.setString(3,isbnM);
				
				
				ps2.executeUpdate();
				System.out.println("Book updated");
				
				//update circulation
				String returned = "returned";
				PreparedStatement ps1=con.prepareStatement("update circulation set return_date = CURRENT_TIMESTAMP, book_status = '"+returned+"' where isbn=? and student_id =? and book_status = ?");
				ps1.setString(1,isbnM);
				ps1.setString(2, studentId.getText());
				ps1.setString(3, "issued");
				ps1.executeUpdate();
				
				
				con.close();
				JOptionPane.showMessageDialog(null, "Book returned successfully!");
				isbn.setText(null);
				studentId.setText(null);

				}
				/*
				 * con.close(); JOptionPane.showMessageDialog(null,
				 * "Book returned successfully!"); isbn.setText(null); studentId.setText(null);
				 */				
			}catch(Exception e){
				e.printStackTrace();
				System.out.println(e);}
			
			
	}
	
}
