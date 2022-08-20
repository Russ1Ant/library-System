package lecturers;

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


public class UpdateDetails implements Initializable{

    @FXML
    private TextField lecturerid;
    @FXML
    private TextField nationalid;
    @FXML
    private Label updatelecturerdetails;
    @FXML
    private TextField firstname;
    @FXML
    private TextField middlename;
    @FXML
    private TextField lastname;
    @FXML
    private TextField emailaddress;
    @FXML
    private TextField phoneno;
    @FXML
    private TextField department;
    @FXML
    private TextField faculty;
    @FXML
    private ComboBox<String> gender;

    private Lecturers selectedLecturer;
    public void buttonbackscene(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("View.fxml"));
        Scene scene = new Scene(root,1280, 720);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void initLecturerDetails(Lecturers lecturers){
        selectedLecturer = lecturers;
        lecturerid.setText(selectedLecturer.getLecturerID());
        nationalid.setText(String.valueOf(selectedLecturer.getNationalID()));
        firstname.setText(selectedLecturer.getFirstName());
        middlename.setText(selectedLecturer.getMiddleName());
        lastname.setText(selectedLecturer.getLastName());
        emailaddress.setText(selectedLecturer.getEmailAddress());
        phoneno.setText(Integer.toString(selectedLecturer.getPhoneNo()));
        gender.setValue(selectedLecturer.getGender());
        department.setText(selectedLecturer.getDepartment());
        faculty.setText(selectedLecturer.getFaculty());



    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> genderList = FXCollections.observableArrayList();
        genderList.addAll("male", "female");
        gender.setItems(genderList);
    }

    public void updatescene(ActionEvent actionEvent) throws SQLException {
    	AddLecturerController aa = new AddLecturerController();
        if (lecturerid.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "lecturer_ID cannot be null ");
        }else if(!aa.verifyLecturerID(lecturerid)) {
        	JOptionPane.showMessageDialog(null, "Invalid lecturer ID");
        	

        } else if (firstname.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "First name cannot be null ");


        } else if (middlename.getText().isEmpty()) {
            JOptionPane.showConfirmDialog(null, "middle name cannot be null...");

        } else if (lastname.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "last name cannot be null...");
        } else if (emailaddress.getText().isEmpty() | !verifyEmail(emailaddress)) {
            JOptionPane.showMessageDialog(null, "Invalid email, kindly enter a valid email");
        } else if (phoneno.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Phone no cannot be null...");
        } else if (gender == null) {
            JOptionPane.showMessageDialog(null, "Please select a gender.");
        } else if (department.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Department cannot be null...");
        } else if (faculty.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "faculty cannot be null...");
        } else if (!isInt(phoneno)) {
            alertUser(phoneno);
            //JOptionPane.showMessageDialog(null, "Incorrect phone number");
            //phoneno.setBackground(new Background(new BackgroundFill(Color.RED,null ,null)));

        }
        else {
            Database db = new Database();
            Connection conn = db.getConnection();
            String lecturerID = selectedLecturer.getLecturerID();
            String sql = "update lecturers set lecturer_id = '"+lecturerid.getText()+"',national_id = '"+nationalid.getText()+"',first_name = '"+firstname.getText()+"',middle_name = '"+middlename.getText()+"',last_name = '"+lastname.getText()+"',email = '"+emailaddress.getText()+"',phone = '"+phoneno.getText()+"',gender = '"+gender.getSelectionModel().getSelectedItem()+"',department = '"+department.getText()+"',faculty = '"+faculty.getText()+"' where lecturer_id ='"+lecturerID+"'";

            try {
                conn.createStatement().execute(sql);
                JOptionPane.showMessageDialog(null, "lecturer details updated successfully");

                return;
            }catch (Exception e) {
                e.printStackTrace();
            }

        }

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

    public boolean verifyEmail(TextField tf) {
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPat.matcher(tf.getText());
        return  matcher.find();
    }
}
