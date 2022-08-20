package studentsPackage2;

import com.sun.javafx.charts.Legend;
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


public class Controller implements Initializable {
    @FXML
    private TextField regno;
    @FXML
    private Label studentdetails;
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
    private TextField course;
    @FXML
    private TextField department;
    @FXML
    private TextField faculty;
    @FXML
    private TextField yearofstudy;
    @FXML
    private TextField balance;
    @FXML
    private Button back;
    @FXML
    private Button addsStudent;
    @FXML
    private ComboBox<String> gender;


    private Database db;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = new Database();
        ObservableList<String> genderlist = FXCollections.observableArrayList();
        genderlist.addAll("male", "female");
        gender.setItems(genderlist);

    }


    public void addsStudent(ActionEvent actionEvent) {
        ComboBox<String> genderComboBox = null;
        //   System.out.println(genderComboBox.getSelectionModel().getSelectedItemsCount());
        String gende = (gender.getSelectionModel().getSelectedItem());
        System.out.println(gende);

        if (regno.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "reg_no cannot be null ");
        }else if(!(verifyRegNo(regno))) {
            	  JOptionPane.showMessageDialog(null, "Incorrect registration number");}
        else if (firstname.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "First name cannot be null ");
        } else if (middlename.getText().isEmpty()) {
            JOptionPane.showConfirmDialog(null, "middle name cannot be null...");

        } else if (lastname.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "last name cannot be null...");
        } else if (emailaddress.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Email address cannot be null...");
        }else if(!verifyEmail(emailaddress)) {
        	JOptionPane.showMessageDialog(null, "incorrect Email format...");
        }
        else if (phoneno.getText().isEmpty() || phoneno.getText().length()<10) {
            JOptionPane.showMessageDialog(null, "Phone no cannot be null and cannot be less than ten digits");
        } else if (gende == null) {
            JOptionPane.showMessageDialog(null, "Please select a gender.");
        } else if (course.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "course cannot be null...");
        } else if (department.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Department cannot be null...");
        } else if (faculty.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "faculty cannot be null...");
        } else if (yearofstudy.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "year of study cannot be null...");
        } /*else if (balance.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "balance cannot be null...");
        }*/ else if (!isInt(phoneno)) {
            alertUser(phoneno);
            //JOptionPane.showMessageDialog(null, "Incorrect phone number");
            //phoneno.setBackground(new Background(new BackgroundFill(Color.RED,null ,null)));

        } else {
            //	String gender = genderComboBox.getSelectionModel().getSelectedItem()


            Connection conn = db.getConnection();
            String sql = "insert into students() values(?,?,?,?,?,?,?,?,?,?,?,?,?)";


            try {


                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, regno.getText());
                pst.setString(2, firstname.getText());
                pst.setString(3, middlename.getText());
                pst.setString(4, lastname.getText());
                pst.setString(5, emailaddress.getText());
                pst.setString(6, phoneno.getText());
                pst.setString(7, gende);
                pst.setString(8, course.getText());
                pst.setString(9, department.getText());
                pst.setString(10, faculty.getText());
                pst.setString(11, yearofstudy.getText());
                pst.setDouble(12, 0.00);

                pst.setString(13, "in_session");



                pst.execute();
                JOptionPane.showMessageDialog(null, "successfully added");


            } catch (SQLException e) {
            	e.printStackTrace();
                JOptionPane.showMessageDialog(null, e);

            }

        }
    }
    public boolean verifyEmail(TextField tf) {
    	String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
    	Pattern emailPat = Pattern.compile(emailRegex,Pattern.CASE_INSENSITIVE);
    	Matcher matcher = emailPat.matcher(tf.getText());
    	return matcher.find();
    	
    }
    

    private boolean verifyRegNo(TextField regNoInput) {
    	String regNoregex = "^[A-Z]{1}\\d{2}/\\d{5}/\\d{2}";
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

    @FXML
    void backToStudentsPage(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();


    }

    @FXML
    void selectGender(ActionEvent event) {

        String s = gender.getSelectionModel().getSelectedItem().toString();
        System.out.println(s);

    }

    public void backbuttonscene(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("View.fxml"));
        Scene scene = new Scene(root,1280, 720);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}


       /* @Override
        public void initialize(URL arg0, ResourceBundle arg1) {
            regno.setOnContextMenuRequested(e->{

                ContextMenu cm = new ContextMenu(new MenuItem("delete"));
                cm.getItems().add(new MenuItem("trial"));
                //Controller controller;
                regno.setContextMenu(cm);
                System.out.println("You right clicked here");
            });

            ObservableList<String> genderlist = FXCollections.observableArrayList();
            genderlist.addAll("male", "female");
            Legend genderComboBox;
            genderComboBox.setItems(genderlist);

*/






