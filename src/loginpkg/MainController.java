package loginpkg;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import connectivitypkg.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController implements Initializable{
	  @FXML
	    private PasswordField txtPassword;

	    @FXML
	    private TextField txtUsername,txtUsername1;
	    
	    

	    @FXML
	    private ComboBox<String> loginCombo;


    @FXML
    private Button buttonLogAdmin;
    
    @FXML
    private Label label;
    
    private Database db;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		db = new Database();
		ObservableList<String> logInOptions = FXCollections.observableArrayList();
		
		logInOptions.add("As admin");
		logInOptions.add("As Librarian");
	
	    loginCombo.setItems(logInOptions);
	    //loginCombo.setVisibleRowCount(1);
	
	}
	 public void comboSelectionM(ActionEvent event) throws IOException {   
		    String s = loginCombo.getSelectionModel().getSelectedItem().toString();

		    if(s=="As admin") {
		    	AnchorPane root = FXMLLoader.load(getClass().getResource("welcomeAdmin.fxml"));
		    	Scene scene = new Scene(root,500,500);
		    	Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		    	window.setScene(scene);
		    	window.show();
		    	//logInAsAdmin(event);
		    	
		    	
		    }else if(s=="As Librarian"){
		    	//logInAsLibrarian(event);
		    	AnchorPane root = FXMLLoader.load(getClass().getResource("welcomeLibrarian.fxml"));
		    	Scene scene = new Scene(root,500,500);
		    	Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		    	window.setScene(scene);
		    	window.show();
		    	
		    }
		    else {
		    	JOptionPane.showMessageDialog(null, "command not found...");
		    }
			// TODO Auto-generated method stub
		 }
	
	public void logInAsAdmin(ActionEvent event) throws IOException {
		try {
			
			  if(txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty()) {
	        	   label.setText("username or password must not be null ");
	        	   label.setBackground(new Background(new BackgroundFill(Color.RED,
	       				null, null)));
	        	   
	        	   JOptionPane.showMessageDialog(null,"username or password must not be null ");
	        	   }else {
//			Connection conn = db.getConnecton();
			 Connection conn = db.getConnection();
	            String sql = "SELECT *FROM ADMIN WHERE USERNAME = '" + txtUsername.getText() + "' AND PASSWORD ='" + txtPassword.getText() + "'";
	         
	           
	            ResultSet resultSet =conn.prepareStatement(sql).executeQuery();

	             if (resultSet.next()) {

		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("loginAsAdmin.fxml"));
		Parent root = loader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("Admin");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();}else {
			label.setText("cant log in you are not an administrator");
			label.setBackground(new Background(new BackgroundFill(Color.RED,null ,null)));
		}
	        	   }
		}catch (SQLException e1) {
			e1.printStackTrace();
		}
		txtPassword.setText("");
	}

	public void logInAsLibrarian(ActionEvent event) throws IOException {
		if(txtUsername.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Username must not be null.");
			
		}else if(txtPassword.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Password must not be null.");
		
		}else if((txtUsername.getText().length()) <10){
			JOptionPane.showMessageDialog(null, "username too short it must be between 10 and 13 characters ");
		}else if(txtUsername.getText().length() > 13) {
			JOptionPane.showMessageDialog(null, "username too long it must be between 10 and 13 characters ");
		}else {
		
			try {
			Connection conn = db.getConnection();
			String sql = "SELECT staff_no, PASSWORD FROM LIBRARIAN WHERE staff_no = '"+txtUsername.getText()+"'AND PASSWORD = '"+txtPassword.getText()+"'";
			ResultSet rs = conn.prepareStatement(sql).executeQuery();
			if(rs.next()) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("loginAsLibrarian.fxml"));
		Parent root = loader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.setTitle("librarian");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();
			}else {
				JOptionPane.showMessageDialog(null, "sorry Acess denied. Check with your admin");
			}
			}catch(SQLException e) {
				e.printStackTrace();
				
				JOptionPane.showMessageDialog(null, "sorry Acess denied. Check with your admin");
			}
	}
	

	txtPassword.setText("");}
}

