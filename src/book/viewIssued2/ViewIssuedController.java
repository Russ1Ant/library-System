package book.viewIssued2;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import book.Main;
import book.dbConnection.ConnectDB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewIssuedController implements Initializable {
	
	 @FXML
	    private TableView<IssuedBooks> tableViewIssuedBooks;
	
	 @FXML
	    private TableColumn<IssuedBooks, String> isbn;

	    @FXML
	    private TableColumn<IssuedBooks, String> bookTitle;

	    @FXML
	    private TableColumn<IssuedBooks, String> studentId;

	    @FXML
	    private TableColumn<IssuedBooks, String> studentName;

	    @FXML
	    private TableColumn<IssuedBooks, Date> issueDate;
	    
	    private ConnectDB db;
		private ObservableList<IssuedBooks> issuedList;
		private Main main = new Main();   
		   
		public void goBack(ActionEvent e) throws IOException {
			 main.showViewBooksInDbView();
		 }  
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		db = new ConnectDB();
		issuedList = FXCollections.observableArrayList();
		
		isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
		bookTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
		studentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
		studentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
		issueDate.setCellValueFactory(new PropertyValueFactory<>("issueDate"));;
		
		tableViewIssuedBooks.setItems(null);
		tableViewIssuedBooks.setItems(issuedList);
		
		try {
			
		Connection conn = db.connect();
		//String sql = "select isbn, book_title, student_id, student_name,issued_date from circulation where return_date = 0";
		String issued = "issued";
		String sql = "select isbn, book_title, student_id, student_name,issued_date from circulation where book_status = '"+issued+"'";
		ResultSet rs = conn.prepareStatement(sql).executeQuery();
		
		while(rs.next()) {
			issuedList.add(new IssuedBooks(rs.getString("isbn"),rs.getString("book_title"),rs.getString("student_id"),rs.getString("student_name"),rs.getDate("issued_date")));
			tableViewIssuedBooks.setItems(null);
			tableViewIssuedBooks.setItems(issuedList);
		}
		
	}catch(SQLException | ClassNotFoundException e) {
		JOptionPane.showMessageDialog(null, e);
		e.printStackTrace();
	}

	
	
	}

}
