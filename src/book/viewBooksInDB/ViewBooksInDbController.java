package book.viewBooksInDB;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;


import book.Main;
import book.dbConnection.ConnectDB;
import book.mainView.MainViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ViewBooksInDbController implements Initializable {

	 @FXML
	    private TableView<Book> tableViewBooks;
	
 	 @FXML
	    private TableColumn<Book, String> title;

	    @FXML
	    private TableColumn<Book, String> author;

	    @FXML
	    private TableColumn<Book, String> isbn;

	    @FXML
	    private TableColumn<Book, Integer> quantity;

	    @FXML
	    private TableColumn<Book, Integer> issued;

	    @FXML
	    private TableColumn<Book, Date> issueDate;

	    @FXML
	    private TableColumn<Book, String> publisher;
	    
	    @FXML
	    private TextField searchTxt;
	    
	    private ConnectDB db;
		private ObservableList<Book> bookList;
		private Main main = new Main();   
		
		
		   
		 public void goBack(ActionEvent e) throws IOException {
			 Parent root = FXMLLoader.load(getClass().getResource("/bookIssiueAndRenewal/bookIssueAndRenewal.fxml"));
		    	Scene scene = new Scene(root);
		    	Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		    	stage.setScene(scene);
		    	stage.show();
		 }  
		 
		 
		public void searchBook(ActionEvent event) throws ClassNotFoundException {
		    	ObservableList<Book> searchBookList =FXCollections.observableArrayList() ;
		    	if(searchTxt.getText().isEmpty()) {
		    		JOptionPane.showMessageDialog(null, "please enter staff number to search");
		    	}
		    	else {

		    	try {
		    		db = new ConnectDB();
		    		Connection conn = db.connect();
			    	String sql = "select * from books where title = '"+searchTxt.getText()+"'";
					ResultSet rs = conn.prepareStatement(sql).executeQuery();
			
					while(rs.next()) {
						searchBookList.add(new Book(rs.getString("title"),rs.getString("author"),rs.getString("isbn"),rs.getInt("quantity"),rs.getString("publisher"),rs.getInt("issued"),rs.getDate("issue_date")));
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
		   
		
		
		
		
		 @FXML
		    private void deleteSelectedBook(ActionEvent event) throws ClassNotFoundException, SQLException{
				
			 ObservableList<Book> newBookList =FXCollections.observableArrayList() ;
				
		    	Book bookToDelete = tableViewBooks.getSelectionModel().getSelectedItem();
		    	if(bookToDelete == null) {
		    		JOptionPane.showMessageDialog(null, "no book is selected");
		    		return;
		    	}
		    	
		    	
		    	
		    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		    	alert.setTitle("Deleting book...");
		    	alert.setContentText("are you sure you want to delete the book"+ bookToDelete.getTitle()+ "?");
		    	Optional<ButtonType> answer = alert.showAndWait();
		    	if(answer.get()==ButtonType.OK) {
		    		
		    		Boolean result = deleteBookInDB(bookToDelete);
		    		if(result) {
		    			JOptionPane.showMessageDialog(null, bookToDelete.getTitle()+" deleted successfully");
		    			 
		    			db = new ConnectDB();
			    		Connection conn = db.connect();
				    	String sql = "select * from books where title = '"+searchTxt.getText()+"'";
						ResultSet rs = conn.prepareStatement(sql).executeQuery();
				
						while(rs.next()) {
							newBookList.add(new Book(rs.getString("title"),rs.getString("author"),rs.getString("isbn"),rs.getInt("quantity"),rs.getString("publisher"),rs.getInt("issued"),rs.getDate("issue_date")));
								tableViewBooks.setItems(null);
								tableViewBooks.setItems(newBookList);
		    			
						}
		    		
		    	}}
		    		else {
		    		JOptionPane.showMessageDialog(null, "Cancelled");
		    		}
		    		}
		    	
		    

		private Boolean deleteBookInDB(Book bookToDelete) {
		try {
			
		
		Connection conn = db.connect();
		
		String sql = "delete from books where title = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, bookToDelete.getTitle());
		stmt.executeUpdate();
		
		
		return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
			}
		
		
		 @FXML
		    private void editBook(ActionEvent e) {
		    	Book bookToEdit = tableViewBooks.getSelectionModel().getSelectedItem();
		    	if(bookToEdit== null) {
		    		JOptionPane.showMessageDialog(null, "please select book to edit");
		    		return;
		    	}
		    	try {
					
		        	FXMLLoader loader = new FXMLLoader();
		        	loader.setLocation(getClass().getResource("editBook.fxml"));
		        	Parent staffView = loader.load();
		        	
		        	Scene scene = new Scene(staffView);
		        	
		        	EditBookController controller = loader.getController();
		        	controller.initBookData(tableViewBooks.getSelectionModel().getSelectedItem());
		        	//Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
		        	Stage window = new Stage();
		        	
		    		window.setTitle("Updating...");
		    		window.initModality(Modality.APPLICATION_MODAL);
		        			
		        	window.setScene(scene);
		        	window.show();
		        	
		    		
		    		
		    	}catch(Exception e1) {
		    		e1.printStackTrace();
		    	}
		    	
		    	
		    	
		    }
		 
		 @FXML
			public void addBook() throws IOException {
				main.showAddBookView();
			}
		 
		 @FXML
			public void issueBook() throws IOException {
				main.showIssueBookView();
			}
			
			@FXML
			public void returnBook() throws IOException {
				main.showReturnBookView();
			}
			
			@FXML
			public void viewIssuedBooks() throws IOException {
				main.showViewIssuedView();
			}
			
			@FXML
			public void viewAllBooks() throws IOException {
				main.showViewBooksInDbView();
			}
			
			@FXML
			public void viewDeletedBooks() throws IOException {
				main.showDeletedBooksView();
			}
			@FXML
			public void placeBookOnHold() throws IOException {
				main.showBooksOnHoldView();
			}
			public void showBooksOnHold() throws IOException {
				main.showViewBooksOnHoldView();
			}
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			// TODO Auto-generated method stub
			db = new ConnectDB();
			bookList = FXCollections.observableArrayList();
			
			title.setCellValueFactory(new PropertyValueFactory<>("title"));
			author.setCellValueFactory(new PropertyValueFactory<>("author"));
			isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
			quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
			publisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
			issued.setCellValueFactory(new PropertyValueFactory<>("issued"));;
			issueDate.setCellValueFactory(new PropertyValueFactory<>("issueDate"));;
			
			tableViewBooks.setItems(null);
			tableViewBooks.setItems(bookList);
			
			try {
				
			Connection conn = db.connect();
			String sql = "select * from books";
			ResultSet rs = conn.prepareStatement(sql).executeQuery();
			
			while(rs.next()) {
				bookList.add(new Book(rs.getString("title"),rs.getString("author"),rs.getString("isbn"),rs.getInt("quantity"),rs.getString("publisher"),rs.getInt("issued"),rs.getDate("issue_date")));
				tableViewBooks.setItems(null);
				tableViewBooks.setItems(bookList);
			}
			
		}catch(SQLException | ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}

		
		}
	
}
