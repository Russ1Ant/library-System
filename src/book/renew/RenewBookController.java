package book.renew;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.LibraryStaff;
import connectivitypkg.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class RenewBookController implements Initializable{
	LibraryStaff lb;
	@FXML
    private Button staffButton;
	
	 @FXML
	    private TextField studeRegNoTF;
	
	 @FXML
	    private ListView<String> issueDataListView;
	  @FXML
	    private TextField bookID;
	  	private connectivitypkg.Database db;
	  	
	    @FXML
	    void loadBookInfo(ActionEvent event) {
	    ;

	    	ObservableList<String> issueData = FXCollections.observableArrayList();
	    	Connection conn = db.getConnection();
             String id = bookID.getText();
             String query = "select * from issued_books where ISBN = '"+bookID.getText()+"'";
             try {
             ResultSet rs = conn.prepareStatement(query).executeQuery(query);
             
             if(!(rs.next())) {
            	 JOptionPane.showMessageDialog(null, "Book not found");
            	 return;
             }
             
             while(rs.next()) {
            	 int bookId = rs.getInt("ISBN");
            	 String stude = rs.getString("reg_no");
            	 System.out.println(stude);
            	 Timestamp issueTime = rs.getTimestamp("issueTime");
            	 
            	 issueData.add("Issue date and time: "+issueTime.toGMTString());
            	 
            	 issueData.add("Book Information: ");
            	 query = "select * from circulation where ISBN = '"+bookId+"'";
            	 ResultSet rs1 = conn.prepareStatement(query).executeQuery();
            	 while(rs1.next()) {
            		 issueData.add("BookName: "+rs1.getString("title"));
            		 issueData.add("Author: " +rs1.getString("author"));
            		 issueData.add("edition : "+rs1.getInt("edition"));
            		 issueData.add("Year published: "+rs1.getString("yearOfPublication"));
            	 
            	 }
            	 
           	 
         	 query = "select * from student where reg_no = '"+stude+"'";
                	 ResultSet rs2 = conn.prepareStatement(query).executeQuery();
             issueData.add("Student information");
                	 while(rs2.next()) {
                		 issueData.add("Student registration Number: "+stude);
                		 issueData.add("Student Name: "+rs2.getString("name"));
                		 issueData.add("Phone number: "+rs2.getInt("phone_no"));
                	     issueData.add("Email: "+rs2.getString("email"));
                	     issueData.add("FAculty: "+ rs2.getString("faculty"));
            	 }
                	 issueData.add("********************************************");
            	 
            	 
             }
             }catch(Exception e) {
            	 e.printStackTrace();
             }
             issueData.add("********************************************");
             issueDataListView.getItems().setAll(issueData);
             
	    }
	    
	    @FXML
	    void loadBookInfoRegNo(ActionEvent event) {
	    	
	    	if(studeRegNoTF.getText().isEmpty()) {
	    		JOptionPane.showMessageDialog(null, "please enter student reg no");
	    		return;
	    	}

	    	ObservableList<String> issueData = FXCollections.observableArrayList();
	    	Connection conn = db.getConnection();
             String id = studeRegNoTF.getText();
             String query = "select * from circulation where reg_no = '"+id+"'";
             try {
             ResultSet rs = conn.prepareStatement(query).executeQuery(query);
             if(!(rs.next())) {
            	 JOptionPane.showMessageDialog(null, "Student not found");
            	 return;
             }
             
             while(rs.next()) {
            	 int bookId = rs.getInt("bookISBN");
            	 String stude = rs.getString("reg_no");
            	 
            	 Timestamp issueTime = rs.getTimestamp("issueTime");
            	 
            	 issueData.add("Issue date and time: "+issueTime.toGMTString());
            	 
            	 issueData.add("Book Information: ");
            	 query = "select * from books where ISBN = '"+bookId+"'";
            	 ResultSet rs1 = conn.prepareStatement(query).executeQuery();
            	 while(rs1.next()) {
            		 issueData.add("BookName: "+rs1.getString("title"));
            		 issueData.add("Author: " +rs1.getString("author"));
            		 issueData.add("edition : "+rs1.getInt("edition"));
            		 issueData.add("Year published: "+rs1.getString("yearOfPublication"));
            	 
            	 }
            	 
           	 
         	 query = "select * from student where reg_no = '"+stude+"'";
                	 ResultSet rs2 = conn.prepareStatement(query).executeQuery();
             issueData.add("Student information");
                	 while(rs2.next()) {
                		 issueData.add("Student registration Number: "+stude);
                		 issueData.add("Student Name: "+rs2.getString("name"));
                		 issueData.add("Phone number: "+rs2.getInt("phone_no"));
                	     issueData.add("Email: "+rs2.getString("email"));
                	     issueData.add("FAculty: "+ rs2.getString("faculty"));
            	 }
                	 issueData.add("********************************************");
            	 
            	 
             }
             }catch(Exception e) {
            	 e.printStackTrace();
             }
             issueData.add("********************************************");
             issueDataListView.getItems().setAll(issueData);
             
	    }
	    
	    @FXML
	    void renewBook(ActionEvent event) throws SQLException {
	    	System.out.println("issueing books page");
	    	if(bookID.getText().isBlank()) {
	    		JOptionPane.showMessageDialog(null, "book id not entered");
	    		return; 
	    	}
	    	
	    	Connection conn = db.getConnection();
	    	Alert alert = new Alert(AlertType.CONFIRMATION);
	    	alert.setContentText("Are you sure you want to renew this book ?");
	    	Optional<ButtonType> conff=alert.showAndWait();
	    	if(conff.get()== ButtonType.OK) {
	    		String sql = "update issued_books set issue_date = CURRENT_TIMESTAMP WHERE BOOKISBN = '"+bookID.getText()+"'"; 
	    	conn.prepareStatement(sql).executeUpdate();
	    	JOptionPane.showMessageDialog(null, "book renewed successfully");
	    	
	    	
	    	}else {
	    		JOptionPane.showMessageDialog(null, "Operation cancelled");
	    	}

	    }

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			db = new Database();
			// TODO Auto-generated method stub
			
		}

	    

}
