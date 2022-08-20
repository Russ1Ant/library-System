package application;

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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class AddStaffController implements Initializable{
	
	 @FXML
	    private ComboBox<String> roleComboBox;

	    @FXML
	    private ComboBox<String> genderComboBox;
	
	  @FXML
	    private TextField idtxt;

	    @FXML
	    private TextField natIdtxt;

	    @FXML
	    private TextField firstName;

	    @FXML
	    private TextField middkeName;

	    @FXML
	    private TextField lastName;

	    @FXML
	    private TextField email;

	    @FXML
	    private TextField phonetxt;

	    @FXML
	    private TextField facultyTxt;

	    @FXML
	    private TextField balanceTxt;

	    @FXML
	    private TextField passwordTxt;

	    @FXML
	    private RadioButton circulationRB;

	    @FXML
	    private RadioButton archiveRB;

	    @FXML
	    private RadioButton maleRB;

	    @FXML
	    private RadioButton femaleRB;

	    @FXML
	    private Button submitBtn;
	    
	    
	    private Database db;

	    @FXML
	    void addNewStaff(ActionEvent event) {
	    	if(verifyEmail(email)== false) {
	    	JOptionPane.showConfirmDialog(null, "incorrect email format please check");
	    	
	    	}else {
	    	PreparedStatement pst= null;
	    	Connection conn = db.getConnection();
	    	String role = roleComboBox.getSelectionModel().getSelectedItem().toString();
	    	String gender = genderComboBox.getSelectionModel().getSelectedItem().toString();
	    	
	    	//parametrized string.
	    	String sql = "Insert into staff() values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
	    	try {
				pst = conn.prepareStatement(sql);
				pst.setString(1, idtxt.getText());
				pst.setString(2, natIdtxt.getText());
				pst.setString(3, firstName.getText());
				pst.setString(4, middkeName.getText());
				pst.setString(5, lastName.getText());
				pst.setString(6, email.getText());
				pst.setString(7, phonetxt.getText());
				pst.setString(8, facultyTxt.getText());
				pst.setString(9, gender);
				pst.setString(10, passwordTxt.getText());
				pst.setString(11, balanceTxt.getText());
				pst.setString(12, role);
				pst.setString(13,"working");
				
				pst.execute();
				JOptionPane.showMessageDialog(null, "Successfullly added");
				pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	}

	    }
	    public boolean verifyEmail(TextField tf) {
	    	String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
	    	Pattern emailPat = Pattern.compile(emailRegex,Pattern.CASE_INSENSITIVE);
	    	Matcher matcher = emailPat.matcher(tf.getText());
	    	return matcher.find();
	    	
	    }

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			db = new Database();
			ObservableList<String> genderlist = FXCollections.observableArrayList();
			genderlist.addAll("male","female");
			genderComboBox.setItems(genderlist);
			
			ObservableList<String> rolelist = FXCollections.observableArrayList();
			rolelist.addAll("circulation","archive");
			roleComboBox.setItems(rolelist);
			
			// TODO Auto-generated method stub
			
		}

}
