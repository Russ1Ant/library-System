package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class AddStaff1Controller implements Initializable{

	
	
	
	
	 @FXML
	    private TextField staffID;

	    @FXML
	    private TextField nationalID;

	    @FXML
	    private TextField firstName;

	    @FXML
	    private TextField middleName;

	    @FXML
	    private TextField lastName;

	    @FXML
	    private TextField email;

	    @FXML
	    private TextField phone;

	    @FXML
	    private ComboBox<String> genderComboBox;

	    @FXML
	    private ComboBox<String> deptComboBox;

	    @FXML
	    private Button back;
	    
	    private Database db;
	    
	    
	     boolean isInt(TextField tfield) {
			try {
				int count = Integer.parseInt(tfield.getText());
				return true;
				
			}catch(NumberFormatException e) {
				//JOptionPane.showMessageDialog(null, "input "+message+" is not an integer");
				return false;
			}
			
		}
	     boolean verifyStaffNumber(TextField regNoInput) {
	     	String regNoregex = "^[A-Z]{2}\\d{2}/\\d{4}/\\d{2}";
	     	Pattern pattern = Pattern.compile(regNoregex, Pattern.UNICODE_CASE);
	     	Matcher matcher = pattern.matcher(regNoInput.getText());
	     	return matcher.find();
	 	}

	     
	    public boolean verifyEmail(TextField tf) {
	    	String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
	    	Pattern emailPat = Pattern.compile(emailRegex,Pattern.CASE_INSENSITIVE);
	    	Matcher matcher = emailPat.matcher(tf.getText());
	    	return matcher.find();
	    	
	    }
	    
	    

	    @FXML
	    void addNewStaff(ActionEvent event) {
	    	//System.out.println(genderComboBox.getSelectionModel().getSelectedItem());
	    	String gender = (genderComboBox.getSelectionModel().getSelectedItem());
	    	System.out.println(gender);
	    	String department = deptComboBox.getSelectionModel().getSelectedItem();
	    	if(staffID.getText().isEmpty()) {
	    			JOptionPane.showMessageDialog(null, "Staff id cannot be null ");
	    			
	    	}else if(verifyStaffNumber(staffID)==false) {
    			alertUser(staffID);
	    			
	    		}else if(nationalID.getText().isEmpty()) {
	    			JOptionPane.showMessageDialog(null, "Please Enter national ID number");
	    			
	    			
	    			
	    		}else if(firstName.getText().isEmpty()) {
	    			JOptionPane.showMessageDialog(null, "First name cannot be null ");

	    		}
	    		
	    		else if (middleName.getText().isEmpty()) {
	    			JOptionPane.showConfirmDialog(null, "middle name cannot be null...");
	    			
	    		}
	    		else if (lastName.getText().isEmpty()) {
	    			JOptionPane.showMessageDialog(null, "last name cannot be null...");
	    		}
	    		else if (email.getText().isEmpty()) {
	    			JOptionPane.showMessageDialog(null, "Email cannot be null...");
	    		}
	    		else if(verifyEmail(email)== false) {
	    			//JOptionPane.showMessageDialog(null, "Incorrect email ");
	    			alertUser(email);
	    			
	    		}
	    		else if (phone.getText().isEmpty() || phone.getText().length()<10 || phone.getText().length()>10) {
	    			JOptionPane.showMessageDialog(null, "Phone number must be 10 Digits");
	    		}
	    		else if(gender==null) {
	    			JOptionPane.showMessageDialog(null, "Please select a gender.");
	    		}
	    		
	    		else if(department==null) {
	    			JOptionPane.showMessageDialog(null, "Please select the department .");
	    		}
	    		else if (!isInt(nationalID)) {
	    			alertUser(nationalID);
	    			//JOptionPane.showMessageDialog(null, "Incorrect National ID number");
	    			//nationalID.setBackground(new Background(new BackgroundFill(Color.RED,null ,null)));
	    		}
	    		else if (!isInt(phone)) {
	    			alertUser(phone);
	    			//JOptionPane.showMessageDialog(null, "Check Phone number");
	    			//nationalID.setBackground(new Background(new BackgroundFill(Color.RED,null ,null)));
	    			}	    		

	    		else {
	    //	String gender = genderComboBox.getSelectionModel().getSelectedItem();
	    	// String department = deptComboBox.getSelectionModel().getSelectedItem();
	    	
	    	PreparedStatement pst = null;
    		
    		Connection conn = db.getConnection();
    		String sql = "insert into library_staff() values(?,?,?,?,?,?,?,?,?,?,?)";
    		
    		
    	
	    	try {
	    		
	    	pst= conn.prepareStatement(sql);
	    	pst.setString(1, staffID.getText());
	    	pst.setString(2, nationalID.getText());
	    	pst.setString(3, firstName.getText());
	    	pst.setString(4, middleName.getText());
	    	pst.setString(5, lastName.getText());
	    	pst.setString(6, email.getText());
	    	pst.setString(7, phone.getText());
	    	pst.setString(8, staffID.getText());
	    	pst.setString(9, gender);
	    	pst.setString(10, department);
	    	pst.setString(11, "working");
	    	
	    	pst.execute();
	    	JOptionPane.showMessageDialog(null, "successfully added");
	    		    		
	    		
	    	}catch(SQLException e) {
	    		JOptionPane.showMessageDialog(null, e);
	    		
	    	}

	    }}
	    
	    void alertUser(TextField ale){
	    	JOptionPane.showMessageDialog(null, ale.getText()+" is an invalid value for this "+ale.getPromptText()+" field");
	    	ale.setBackground(new Background(new BackgroundFill(Color.RED,null ,null)));
	    	
	    }
	    
	    @FXML
	    void backToStaffPage(ActionEvent event) throws IOException {

	    	Parent root = FXMLLoader.load(getClass().getResource("staff.fxml"));
	    	Scene scene = new Scene(root,1200,720);
	    	Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    	window.setScene(scene);
	    	window.show();
	    	
	    }

	    
	    @FXML
	    void selectDepartment(ActionEvent event) {
	    	String s = deptComboBox.getSelectionModel().getSelectedItem();
	    	System.out.println(s);

	    }

	    @FXML
	    void selectGender(ActionEvent event) {
	    	String s = genderComboBox.getSelectionModel().getSelectedItem();
	    	System.out.println(s);

	    }


		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			staffID.setOnContextMenuRequested(e->{
				
				ContextMenu cm = new ContextMenu(new MenuItem("delete"));
				cm.getItems().add(new MenuItem("trial"));
				//Controller controller;
			staffID.setContextMenu(cm);
				System.out.println("You right clicked here");
			});
			//staffID.setHover();
			ObservableList<String> genderlist = FXCollections.observableArrayList();
			genderlist.addAll("male", "female", "other");
			genderComboBox.setItems(genderlist);
			
			ObservableList<String> deptList = FXCollections.observableArrayList();
			deptList.addAll("Archive","circulation","it");
			deptComboBox.setItems(deptList);
			
			
			db = new Database();
			
			
			// TODO Auto-generated method stub
			
		}
}
