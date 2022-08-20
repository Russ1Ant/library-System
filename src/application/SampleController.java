package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import connectivitypkg.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SampleController implements Initializable {
	@FXML
	private ComboBox<String> comboBox;

	@FXML
	private Label label;
	
	private Database db;

	/*
	 * @FXML void changePasswordScene(ActionEvent event){ try { BorderPane root =
	 * (BorderPane)FXMLLoader.load(getClass().getResource("resetPassword.fxml"));
	 * Scene scene = new Scene(root); Stage window = (Stage) ((Node)
	 * event.getSource()).getScene().getWindow(); window.setScene(scene);
	 * window.show(); }catch(Exception e) { e.printStackTrace(); } }
	 */ @FXML
	public void select(ActionEvent event) throws IOException {
		String s = comboBox.getSelectionModel().getSelectedItem().toString();

		label.setText(s);
		if (s == "as admin") {
		//	LoginAsAdmin(event);
			/*
			 * AnchorPane root =
			 * (AnchorPane)FXMLLoader.load(getClass().getResource("logIn.fxml")); Scene
			 * scene = new Scene(root); Stage window = (Stage) ((Node)
			 * event.getSource()).getScene().getWindow(); window.setScene(scene);
			 * window.show();
			 */

		}

		else if (s == "as librarian") {

			// AnchorPane root =
			// (AnchorPane)FXMLLoader.load(getClass().getResource("logIn.fxml"));
			// Scene scene = new Scene(root);
			// Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			// window.setScene(scene);
			// window.show();
			//logInAsLibrarian(event);

			// changePasswordScene(event);
		}

	}

	/*
	 * public void logInAsLibrarian(ActionEvent event1) { try{ AnchorPane root =
	 * (AnchorPane)FXMLLoader.load(getClass().getResource("logInLib.fxml"));
	 * 
	 * Scene scene = new Scene(root); Stage window = (Stage) ((Node)
	 * event1.getSource()).getScene().getWindow(); window.setScene(scene);
	 * window.show();
	 * 
	 * }catch(IOException e) { e.printStackTrace(); } // TODO Auto-generated method
	 * stub
	 * 
	 * }
	 * 
	 * 
	 * 
	 * public void LoginAsAdmin(ActionEvent event) throws IOException { AnchorPane
	 * root = (AnchorPane)FXMLLoader.load(getClass().getResource("logIn.fxml"));
	 * Scene scene = new Scene(root); Stage window = (Stage) ((Node)
	 * event.getSource()).getScene().getWindow(); window.setScene(scene);
	 * window.show();
	 * 
	 * 
	 * // TODO Auto-generated method stub
	 * 
	 * }
	 * 
	 */

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		db = new Database();

		ObservableList<String> list = FXCollections.observableArrayList();
		list.add("as admin");
		list.add("as librarian");
		// comboBox.setItems(null);
		comboBox.setItems(list);

		// TODO Auto-generated method stub

	}

}
