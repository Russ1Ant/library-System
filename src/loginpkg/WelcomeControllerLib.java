package loginpkg;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import connectivitypkg.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WelcomeControllerLib implements Initializable{

	
	private connectivitypkg.Database db;
	
    @FXML
    private TextField txtUsername1;

    @FXML
    private PasswordField txtPassword1;

    @FXML
    void logInAsLibrarian1(ActionEvent event) throws IOException{
		   if(txtUsername1.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Username must not be null.");
				
			}else if(txtPassword1.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Password must not be null.");
			
			}else if((txtUsername1.getText().length()) <10){
				JOptionPane.showMessageDialog(null, "username too short it must be between 10 and 13 characters ");
			}else if(txtUsername1.getText().length() > 13) {
				JOptionPane.showMessageDialog(null, "username too long it must be between 10 and 13 characters ");
			}else {
			
				try {
				Connection conn = db.getConnection();
				String sql = "SELECT staff_ID, PASSWORD FROM LIBRARY_STAFF WHERE staff_ID = '"+txtUsername1.getText()+"'AND PASSWORD = '"+txtPassword1.getText()+"'";
				ResultSet rs = conn.prepareStatement(sql).executeQuery();
				if(rs.next()) {
					
					  FXMLLoader loader = new FXMLLoader(getClass().getResource("/bookIssiueAndRenewal/bookIssueAndRenewalLibStaff.fxml"));
					  Parent root = loader.load();
					  Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					  stage.setScene(new Scene(root));
					  
					  stage.setTitle("librarian"); 
					
					//  stage.initModality(Modality.APPLICATION_MODAL);
					  stage.show();
					 
					/*
					 * Parent root = FXMLLoader.load(getClass().getResource(
					 * "/bookIssiueAndRenewal/bookIssueAndRenewal.fxml")); Scene scene = new
					 * Scene(root,1280,720); Stage window = (Stage) ((Node)
					 * event.getSource()).getScene().getWindow(); window.setScene(scene);
					 * window.show();
					 */
					
				}else {
					JOptionPane.showMessageDialog(null, "sorry Acess denied. Check with your admin");
				}
				}catch(SQLException e) {
					e.printStackTrace();
					
					JOptionPane.showMessageDialog(null, e);
				}}
		}
    
    public void changePassword(ActionEvent event) throws IOException {
    	System.out.println("ni uriku uyu");
    	AnchorPane root = FXMLLoader.load(getClass().getResource("changePassword.fxml"));
    	Scene scene = new Scene(root);
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	stage.setScene(scene);
    	stage.show();
    	
    	
    }


    @FXML
    void back(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("welcome.fxml"));
    	Scene scene = new Scene(root);
    	Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	window.setScene(scene);
    	window.show();
    	

    }
		
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	db = new Database();	
	}

}
