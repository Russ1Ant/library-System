package lecturers2;

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
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;
//import java.awt.*;
import java.awt.MenuItem;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AddLecturerController implements Initializable {


    @FXML
    private Label lecturerdetails;
    @FXML
    private TextField lecturerID1;
    @FXML
    private TextField nat_ID1;
    @FXML
    private TextField firstname1;
    @FXML
    private TextField middlename1;
    @FXML
    private TextField lastname1;
    @FXML
    private TextField emailaddress1;
    @FXML
    private TextField phoneNo1;
    @FXML
    private TextField department1;
    @FXML
    private TextField faculty1;
    @FXML
    private TextField balance;
    @FXML
    private Button back;
    @FXML
    private Button addLecturer;
    @FXML
    private ComboBox<String> gender1;


    private Database db;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = new Database();
        ObservableList<String> genderlist = FXCollections.observableArrayList();
        genderlist.addAll("male", "female");
        gender1.setItems(genderlist);
    }
    boolean verifyLecturerID(TextField lecId) {
    	String regex = "^[A-Z]{2}\\d{2}/\\d{5}/\\d{2}";
    	Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CASE);
    	Matcher matcher = pattern.matcher(lecId.getText());
    	return matcher.find();
	}


    public void addLecturer(ActionEvent actionEvent) {
        ComboBox<String> genderComboBox = null;
        
        //   System.out.println(genderComboBox.getSelectionModel().getSelectedItemsCount());
        String gender = (gender1.getSelectionModel().getSelectedItem());
        System.out.println(gender);

        if (lecturerID1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "lecturer_ID cannot be null ");
        }else if(!verifyLecturerID(lecturerID1)) {
        	JOptionPane.showMessageDialog(null, "Invalid lecturer ID");
        


        } else if (firstname1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "First name cannot be null ");


        } else if (middlename1.getText().isEmpty()) {
            JOptionPane.showConfirmDialog(null, "middle name cannot be null...");

        } else if (lastname1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "last name cannot be null...");
        } else if (emailaddress1.getText().isEmpty() | !verifyEmail(emailaddress1)) {
            JOptionPane.showMessageDialog(null, "Invalid email, kindly enter a valid email");
        } else if (phoneNo1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Phone no cannot be null...");
        } else if (gender == null) {
            JOptionPane.showMessageDialog(null, "Please select a gender.");
        } else if (department1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Department cannot be null...");
        } else if (faculty1.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "faculty cannot be null...");
        } else if (!isInt(phoneNo1)) {
            alertUser(phoneNo1);
            //JOptionPane.showMessageDialog(null, "Incorrect phone number");
            //phoneno.setBackground(new Background(new BackgroundFill(Color.RED,null ,null)));

        } else {
            //	String gender = genderComboBox.getSelectionModel().getSelectedItem()


            Connection conn = db.getConnection();
            String sql = "insert into lecturers() values(?,?,?,?,?,?,?,?,?,?,?,?)";


            try {


                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, lecturerID1.getText());
                pst.setString(2, nat_ID1.getText());
                pst.setString(3, firstname1.getText());
                pst.setString(4, middlename1.getText());
                pst.setString(5, lastname1.getText());
                pst.setString(6, faculty1.getText());
                pst.setString(7, department1.getText());
                pst.setString(8, gender);
                pst.setString(9, emailaddress1.getText());
                pst.setString(10, phoneNo1.getText());
                pst.setDouble(11, 0.00);

                pst.setString(12, "working");



                pst.execute();
                JOptionPane.showMessageDialog(null, "successfully added");


            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);

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

    public void backbuttonscene(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("View.fxml"));
        Scene scene = new Scene(root,1280, 720);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
