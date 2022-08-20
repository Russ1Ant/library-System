package studentsPackage;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateDetails implements Initializable {

    @FXML
    private TextField regno11;
    @FXML
    private Label editstudentdetails;
    @FXML
    private TextField firstname11;
    @FXML
    private TextField middlename11;
    @FXML
    private TextField lastname11;
    @FXML
    private TextField emailaddress11;
    @FXML
    private TextField phoneno11;
    @FXML
    private TextField course11;
    @FXML
    private TextField department11;
    @FXML
    private TextField faculty11;
    @FXML
    private TextField yearofstudy11;
    @FXML
    private ComboBox<String> gender11;
    
    Controller cc = new Controller();

    private Students selectedStudent;
    public void buttonbackscene(ActionEvent actionEvent) throws IOException {
        Node n =(Node) actionEvent.getSource();
        Stage stage = (Stage) n.getScene().getWindow();
        stage.close();
    }
    public void initStudentsdetails(Students students){
        selectedStudent = students;
        regno11.setText(selectedStudent.getRegNo());
        firstname11.setText(selectedStudent.getFirstName());
        middlename11.setText(selectedStudent.getMiddleName());
        lastname11.setText(selectedStudent.getLastName());
        emailaddress11.setText(selectedStudent.getEmailAddress());
        phoneno11.setText(Integer.toString(selectedStudent.getPhoneNo()));
        gender11.setValue(selectedStudent.getGender());
        course11.setText(selectedStudent.getGender());
        department11.setText(selectedStudent.getDepartment());
        faculty11.setText(selectedStudent.getFaculty());
        yearofstudy11.setText(Integer.toString(selectedStudent.getYearOfStudy()));



    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> genderList = FXCollections.observableArrayList();
        genderList.addAll("male", "female");
        gender11.setItems(genderList);

    }
    private boolean verifyRegNo(TextField regNoInput) {
    	String regNoregex = "^[A-Z]{1}\\D{2}/\\D{5}/\\D{2}";
    	Pattern pattern = Pattern.compile(regNoregex, Pattern.UNICODE_CASE);
    	Matcher matcher = pattern.matcher(regNoInput.getText());
    	return matcher.find();
	}


	private boolean isInt(TextField value) {
        try {
            int count = Integer.parseInt(value.getText());
            return true;

        } catch (NumberFormatException e) {
            return false;
        }
    }

    void alertUser(TextField ale) {
        JOptionPane.showMessageDialog(null, ale.getText() + " is an invalid value for this " + ale.getPromptText() + " field");
        ale.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));

    }

    public void updatescene(ActionEvent actionEvent) throws SQLException {
    	if (regno11.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "reg_no cannot be null ");
        }else if(!(cc.verifyRegNo(regno11))) {
            	  JOptionPane.showMessageDialog(null, "Incorrect registration number");
            	  } else if (firstname11.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "First name cannot be null ");


        } else if (middlename11.getText().isEmpty()) {
            JOptionPane.showConfirmDialog(null, "middle name cannot be null...");

        } else if (lastname11.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "last name cannot be null...");
        } else if (emailaddress11.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Email address cannot be null...");
        } else if (phoneno11.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Phone no cannot be null...");
        } else if (gender11 == null) {
            JOptionPane.showMessageDialog(null, "Please select a gender.");
        } else if (course11.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "course cannot be null...");
        } else if (department11.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Department cannot be null...");
        } else if (faculty11.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "faculty cannot be null...");
        } else if (yearofstudy11.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "year of study cannot be null...");
        } /*else if (balance.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "balance cannot be null...");
        }*/ else if (!isInt(phoneno11)) {
            alertUser(phoneno11);
            //JOptionPane.showMessageDialog(null, "Incorrect phone number");
            //phoneno.setBackground(new Background(new BackgroundFill(Color.RED,null ,null)));

        } else {
    	
       Database db = new Database();
        Connection conn = db.getConnection();
        String Reg_No = selectedStudent.getRegNo();
        String sql = "update Students set reg_no = '"+regno11.getText()+"',first_name = '"+firstname11.getText()+"',middle_name = '"+middlename11.getText()+"',last_name = '"+lastname11.getText()+"',email_address = '"+emailaddress11.getText()+"',phone_no = '"+phoneno11.getText()+"',gender = '"+gender11.getSelectionModel().getSelectedItem()+"',course = '"+course11.getText()+"',department = '"+department11.getText()+"',faculty = '"+faculty11.getText()+"',year_of_study = '"+yearofstudy11.getText()+"' where Reg_No ='"+Reg_No+"'";

        try {
            conn.createStatement().execute(sql);
            JOptionPane.showMessageDialog(null, "student details updated successfully");

            return;
        }catch (Exception e) {
            e.printStackTrace();
        }
    }}
}

