package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditController implements Initializable{
	

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
    private LibraryStaff selectedStaff;
    
    
    private Database db;
    
    AddStaff1Controller ad = new AddStaff1Controller();
    
    
    
    public void initLibStaffData(LibraryStaff staff) {
    	
    	selectedStaff = staff;
    	String staff_id = staff.getStaffID();
    	
    	staffID.setText(selectedStaff.getStaffID());
    	nationalID.setText(Integer.toString(selectedStaff.getNationalID()));
    	firstName.setText(selectedStaff.getFirstName());
    	middleName.setText(selectedStaff.getMiddleName());
    	lastName.setText(selectedStaff.getLastName());
    	phone.setText(Integer.toString(selectedStaff.getPhone()));
    	email.setText(selectedStaff.getEmail());
    	
    	deptComboBox.setValue(selectedStaff.getDepartment());
    	genderComboBox.setValue(selectedStaff.getGender());
    	
    	
    }

    @FXML
    void backToStaffView(ActionEvent event) throws IOException {
    	//Stage stage = (Stage) staffID.getScene().getWindow();
    	//stage.close();
    	//Parent root = FXMLLoader.load(getClass().getResource("staff.fxml"));
    	//Scene scene = new Scene(root,1280, 720);
    	//Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	//stage.setScene(scene);
    	//stage.show();
      Node n =(Node) event.getSource();
      Stage stage = (Stage) n.getScene().getWindow();
      stage.close();
      
    
    }
    
    @FXML
    void updateStaff(ActionEvent event) {
    	
     	String gender = (genderComboBox.getSelectionModel().getSelectedItem());
    	System.out.println(gender);
    	String department = deptComboBox.getSelectionModel().getSelectedItem();
    	
    	
    	
    	if(staffID.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Staff id cannot be null ");
    	}else if(ad.verifyStaffNumber(staffID)==false) {
			ad.alertUser(staffID);
    		
			
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
		else if(ad.verifyEmail(email)== false) {
			//JOptionPane.showMessageDialog(null, "Incorrect email ");
			ad.alertUser(email);
			
		}
		else if (phone.getText().isEmpty() || phone.getText().length()<10 || phone.getText().length()>10) {
			JOptionPane.showMessageDialog(null, "Phone number must be 10 digits");
		}		else if (!ad.isInt(nationalID)) {
			ad.alertUser(nationalID);
			//JOptionPane.showMessageDialog(null, "Incorrect National ID number");
			//nationalID.setBackground(new Background(new BackgroundFill(Color.RED,null ,null)));
		}
		else if (!ad.isInt(phone)) {
			ad.alertUser(phone);
			//JOptionPane.showMessageDialog(null, "Check Phone number");
			//nationalID.setBackground(new Background(new BackgroundFill(Color.RED,null ,null)));
			}	    		
		else {
 
    
   	Connection conn = db.getConnection();
    String staff_Id = selectedStaff.getStaffID();
	String sql = "update library_staff set staff_id = '"+staffID.getText()+"',national_id = '"+nationalID.getText()+"', first_name = '"+firstName.getText()+"',middle_name = '"+middleName.getText()+"',last_name = '"+lastName.getText()+"', phone = '"+phone.getText()+"',email = '"+email.getText()+"',gender = '"+gender+"',department = '"+department+"' where staff_id='"+staff_Id +"'";
	
	try {
	conn.createStatement().execute(sql);
	JOptionPane.showMessageDialog(null, "Staff updated successsfully");
	
	return;
	
    }catch(Exception e) {
    	e.printStackTrace();
    }}
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		db=new Database();
		
		
		ObservableList<String> genderlist = FXCollections.observableArrayList();
		genderlist.addAll("male", "female", "other");
		genderComboBox.setItems(genderlist);
		
		ObservableList<String> deptList = FXCollections.observableArrayList();
		deptList.addAll("Archive","circulation","it");
		deptComboBox.setItems(deptList);

		
	}

	
	
}
