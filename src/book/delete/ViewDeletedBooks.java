package book.delete;

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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewDeletedBooks implements Initializable {
	
	@FXML
    private TableView<DeletedBook> tableViewBooks;

	 @FXML
    private TableColumn<DeletedBook, String> title;

    @FXML
    private TableColumn<DeletedBook, String> author;

    @FXML
    private TableColumn<DeletedBook, String> isbn;

    @FXML
    private TableColumn<DeletedBook, Integer> quantity;

    @FXML
    private TableColumn<DeletedBook, Integer> issued;

    @FXML
    private TableColumn<DeletedBook, Date> issueDate;

    @FXML
    private TableColumn<DeletedBook, String> publisher;
    
    @FXML
    private TextField searchTxt;
    
    private ConnectDB db;
	private ObservableList<DeletedBook> deletedBookList;
	private Main main = new Main();   
	   
	 public void goBack(ActionEvent e) throws IOException {
		 main.showViewBooksInDbView();
	 }  
	 
		public void searchBook(ActionEvent event) throws ClassNotFoundException {
	    	ObservableList<DeletedBook> searchBookList =FXCollections.observableArrayList() ;
	    	if(searchTxt.getText().isEmpty()) {
	    		JOptionPane.showMessageDialog(null, "please enter book title to search");
	    	}
	    	else {

	    	try {
	    		db = new ConnectDB();
	    		Connection conn = db.connect();
		    	String sql = "select * from deleted_books where title = '"+searchTxt.getText()+"'";
				ResultSet rs = conn.prepareStatement(sql).executeQuery();
		
				while(rs.next()) {
					searchBookList.add(new DeletedBook(rs.getString("title"),rs.getString("author"),rs.getString("isbn"),rs.getInt("quantity"),rs.getString("publisher"),rs.getInt("issued"),rs.getDate("issue_date")));
						tableViewBooks.setItems(null);
						tableViewBooks.setItems(searchBookList);
						
				//break;	 
				}
				if(searchBookList.isEmpty()) {
					JOptionPane.showMessageDialog(null, searchTxt.getText()+" not found");
				}
				//JOptionPane.showMessageDialog(null, "not found");
				
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	}
	    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		db = new ConnectDB();
		deletedBookList = FXCollections.observableArrayList();
		
		title.setCellValueFactory(new PropertyValueFactory<>("title"));
		author.setCellValueFactory(new PropertyValueFactory<>("author"));
		isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
		quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		publisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
		issued.setCellValueFactory(new PropertyValueFactory<>("issued"));;
		issueDate.setCellValueFactory(new PropertyValueFactory<>("issueDate"));;
		
		tableViewBooks.setItems(null);
		tableViewBooks.setItems(deletedBookList);
		
		try {
			
		Connection conn = db.connect();
		String sql = "select * from deleted_books";
		ResultSet rs = conn.prepareStatement(sql).executeQuery();
		
		while(rs.next()) {
			deletedBookList.add(new DeletedBook(rs.getString("title"),rs.getString("author"),rs.getString("isbn"),rs.getInt("quantity"),rs.getString("publisher"),rs.getInt("issued"),rs.getDate("issue_date")));
			tableViewBooks.setItems(null);
			tableViewBooks.setItems(deletedBookList);
		}
		
	}catch(SQLException | ClassNotFoundException e) {
		JOptionPane.showMessageDialog(null, e);
		e.printStackTrace();
	}

	
	}
	

}
