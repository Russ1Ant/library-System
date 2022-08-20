package book.mainView;

import java.io.IOException;

import book.Main;
import book.viewBooksInDB.ViewBooksInDbController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainViewController  {
	
	private Main main = new Main();
	
	@FXML
	public void addBook() throws IOException {
		main.showAddBookView();
	}
	
	@FXML
	public void backToMain(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/bookIssiueAndRenewal/bookIssueAndRenewal.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		stage.setScene(scene);
		stage.show();
		
		
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
	
}
