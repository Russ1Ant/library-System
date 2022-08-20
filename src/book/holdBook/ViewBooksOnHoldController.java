package book.holdBook;

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
import book.viewIssued.IssuedBooks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewBooksOnHoldController implements Initializable{
	 @FXML
	    private TableView<BooksOnHold> tableViewBooksOnHold;
	
	 @FXML
	    private TableColumn<BooksOnHold, String> isbn;

	    @FXML
	    private TableColumn<BooksOnHold, String> bookTitle;

	    @FXML
	    private TableColumn<BooksOnHold, String> studentId;

	    @FXML
	    private TableColumn<BooksOnHold, String> studentName;

	    
	    
	    private ConnectDB db;
		private ObservableList<BooksOnHold> onHoldList;
		private Main main = new Main();
		@FXML
		public void goBack(ActionEvent e) throws IOException {
			main.showViewBooksInDbView();
		}
		
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			// TODO Auto-generated method stub
			db = new ConnectDB();
			onHoldList = FXCollections.observableArrayList();
			
			isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
			bookTitle.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
			studentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
			studentName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
			
			
			tableViewBooksOnHold.setItems(null);
			tableViewBooksOnHold.setItems(onHoldList);
			
			try {
				
			Connection conn = db.connect();
			//String sql = "select isbn, book_title, student_id, student_name,issued_date from circulation where return_date = 0";
			
			String sql = "select isbn, book_title, student_id, student_name from books_on_hold ";
			ResultSet rs = conn.prepareStatement(sql).executeQuery();
			
			while(rs.next()) {
				onHoldList.add(new BooksOnHold(rs.getString("isbn"),rs.getString("book_title"),rs.getString("student_id"),rs.getString("student_name")));
				tableViewBooksOnHold.setItems(null);
				tableViewBooksOnHold.setItems(onHoldList);
			}
			
		}catch(SQLException | ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}

		
		
		}
}
