package loginpkg;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import bookIssiueAndRenewal.Controller;
import connectivitypkg.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WelcomeController implements Initializable{
	
	
MainController ctrl = new MainController();


    @FXML
    private Label label;

    @FXML
    private TextField txtUsername, txtUsername1;

    @FXML
    private PasswordField txtPassword, txtPassword1;
    
    @FXML
    private Database db;
	
     
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		db = new Database();
		// TODO Auto-generated method stub
		
	}
	
	


    @FXML
    void back1(ActionEvent event) throws IOException {
    	WelcomeControllerLib lb = new WelcomeControllerLib();
    	lb.back(event);
    }
	
	   @FXML
		public void logInAsAdmin1(ActionEvent event) throws IOException {
		   
			try {
				
				  if(txtUsername.getText().equals("") || txtPassword.getText().equals("")) {
		        	   label.setText("username or password must not be null ");
		        	   label.setBackground(new Background(new BackgroundFill(Color.RED,
		       				null, null)));
		        	   
		        	   JOptionPane.showMessageDialog(null,"username or password must not be null ");
		        	   }else {
          
				    Connection conn = db.getConnection();
		            String sql = "SELECT *FROM ADMIN WHERE USERNAME = '" + txtUsername.getText() + "' AND PASSWORD ='" + txtPassword.getText() + "'";
		         
		           
		            ResultSet resultSet =conn.prepareStatement(sql).executeQuery();

		             if (resultSet.next()) {

			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/bookIssiueAndRenewal/bookIssueAndRenewal.fxml"));
			Parent root = loader.load();
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			
			
			stage.setScene(new Scene(root));
			stage.setTitle("Admin");
			//stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
			
		        	   }else {
				label.setText("cant log in you are not an administrator");
				label.setBackground(new Background(new BackgroundFill(Color.RED,null ,null)));
			}
		        	   }
		}catch (SQLException e1) {
				//e1.printStackTrace();
			}
		}
	   
	   
	   void logInAsLibrarian1(ActionEvent event) throws IOException{
		   if(txtUsername1.getText().equals("")) {
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
				String sql = "SELECT staff_no, PASSWORD FROM LIBRARIAN WHERE staff_no = '"+txtUsername1.getText()+"'AND PASSWORD = '"+txtPassword1.getText()+"'";
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
		

		   
		   
	   }
}
