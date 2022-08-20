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
import javafx.stage.Stage;

public class ChangePasswordController implements Initializable{
	private connectivitypkg.Database db;
	
	@FXML
    private TextField username;

    @FXML
    private PasswordField passwordOld;

    @FXML
    private PasswordField passwordnew;

    @FXML
    private PasswordField passwordConfirm;

    @FXML
    void backPage(ActionEvent event) throws IOException {
    	AnchorPane root = FXMLLoader.load(getClass().getResource("welcomeLibrarian.fxml"));
    	Scene scene = new Scene(root);
    	Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	window.setScene(scene);
    	window.show();
    	
    	
    	

    }

    @FXML
    void submit(ActionEvent event) throws SQLException {
    	
    	String userName= username.getText();
    	if(userName.isBlank()) {
    		JOptionPane.showMessageDialog(null, "username cannot be null");
    		
    	}else if(passwordOld.getText().isBlank()) {
    		JOptionPane.showMessageDialog(null, "old password cannot be null");
    		
    	}
    	else if(passwordnew.getText().isBlank()) {
    		JOptionPane.showMessageDialog(null, "new password cannot be null");
    		
    	}
    	else if(passwordnew.getText().length()<8) {
    		JOptionPane.showMessageDialog(null, "new password must be atleast 8 characters");
    		
    	}
    	else if(passwordnew.getText().compareTo(passwordConfirm.getText() ) != 0) {
    		JOptionPane.showMessageDialog(null, "confirm password and new password do not match");
    		
    	}
    	
    	else if(passwordConfirm.getText().isBlank()) {
    		JOptionPane.showMessageDialog(null, "Confirm password cannot be null");
    		
    	}else {
    	Connection conn = db.getConnection();
    	String sqlString = "Select * from library_Staff where staff_Id = '"+userName+"' AND password = '"+passwordOld.getText()+"'";
    	ResultSet resultSet =conn.prepareStatement(sqlString).executeQuery();
    	if(!(resultSet.next())) {
    		JOptionPane.showMessageDialog(null, "User does not exist or incorrrect old password");
    		return;
    		
    	}
    	
    	String newPassword = passwordnew.getText();
    	String sql = "update library_staff set password = '"+newPassword+"' where staff_ID = '"+userName+"'";
    	try {
			conn.createStatement().execute(sql);
			JOptionPane.showMessageDialog(null, "password successfully changed");
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    	

    }}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		db = new Database();
		
		// TODO Auto-generated method stub
		
	}

}
