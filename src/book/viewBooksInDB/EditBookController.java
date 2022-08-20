package book.viewBooksInDB;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JOptionPane;

import com.sun.jdi.connect.spi.Connection;

import book.Main;
import book.dbConnection.ConnectDB;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditBookController {
	
	
	
	 @FXML
	    private TextField title;

	    @FXML
	    private TextField author;

	    @FXML
	    private TextField isbn;

	    @FXML
	    private TextField quantity;

	   
	    @FXML
	    private TextField publisher;
	    
	    private javafx.scene.control.Button closeButton;
	    
	   
	    private Main main = new Main();
	    private ConnectDB db;
	    
	    private Book selectedBook;
	    
	    public void initBookData(Book book) {
	    	
	    	selectedBook = book;
	    	String isbnt = book.getIsbn();
	    	
	    	isbn.setText(selectedBook.getIsbn());
	    	title.setText(selectedBook.getTitle());
	    	author.setText(selectedBook.getAuthor());
	    	quantity.setText(Integer.toString(selectedBook.getQuantity()));
	    	publisher.setText(selectedBook.getPublisher());
	    	    	
	    }
	    
	    @FXML
		public void goBack(ActionEvent e) throws IOException {
	    	  Node n =(Node) e.getSource();
	          Stage stage = (Stage) n.getScene().getWindow();
	          stage.close();
		}
	    
	    @FXML
	    void updateBook(ActionEvent event) throws ClassNotFoundException, SQLException {
	 
	    	db = new ConnectDB();
	    	java.sql.Connection conn = db.connect();
	       String bookIsbn = selectedBook.getIsbn();
		String sql = "update books set isbn = '"+isbn.getText()+"',author = '"+author.getText()+"', title = '"+title.getText()+"',quantity = '"+quantity.getText()+"',publisher = '"+publisher.getText()+"' where isbn ='"+bookIsbn+"'";
		
		try {
		conn.createStatement().execute(sql);
		JOptionPane.showMessageDialog(null, "Book updated successsfully");
		isbn.setText(null);
		title.setText(null);
		author.setText(null);
		quantity.setText(null);
		publisher.setText(null);
		
		return;
		
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }}
	
}
