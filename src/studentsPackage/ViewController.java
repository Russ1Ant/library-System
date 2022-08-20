package studentsPackage;

import connectivitypkg.Database;
import fine.Fine;
import javafx. collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;

import application.StaffController;

import java.io.FileWriter;
//import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;


public class ViewController implements Initializable {
    @FXML
    private TableView<Students> tableview;
    @FXML
    private TableColumn<Students, String> columnregno;
    @FXML
    private TableColumn<Students, String> columnfirstname;
    @FXML
    private TableColumn<Students, String> columnmiddlename;
    @FXML
    private TableColumn<Students, String> columnlastname;
    @FXML
    private TableColumn<Students, String> columnemailaddress;
    @FXML
    private TableColumn<Students, Integer> columnphoneno;
    @FXML
    private TableColumn<Students, String> columngender;
    @FXML
    private TableColumn<Students, String> columncourse;
    @FXML
    private TableColumn<Students, String> columndepartment;
    @FXML
    private TableColumn<Students, String> columnfaculty;
    @FXML
    private TableColumn<Students, Integer> columnyearofstudy;
    @FXML
    private TableColumn<Students, Float> columnbalance;
    @FXML
    private TableColumn<Students, String> columnstudentstatus;
    @FXML
    private Button searchstudent;
    @FXML
    TextField searchTextField;
    private static Database db;
    ObservableList<Students> students = FXCollections.observableArrayList();
    
    @FXML
    void backToDash(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/bookIssiueAndRenewal/bookIssueAndRenewal.fxml"));
    	Scene scene = new Scene(root);
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	stage.setScene(scene);
    	stage.show();

    }


    @FXML
    void searchStudentScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("View.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }


    @FXML
    void searchStudent(ActionEvent event) {
        ObservableList<Students> searchStudent = FXCollections.observableArrayList();
        if (searchTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "please enter reg no to search");
        }
        else {

        try {
            Connection conn = db.getConnection();

            String sql1 = "select * from Students where reg_no = '" + searchTextField.getText() + "'";
            ResultSet rs = conn.prepareStatement(sql1).executeQuery();

            while (rs.next()) {
                searchStudent.add(new Students(rs.getString("reg_no"), rs.getString("first_name"), rs.getString("middle_name"), rs.getString("last_name"), rs.getString("email_address"), rs.getInt("phone_no"), rs.getString("gender"), rs.getString("course"), rs.getString("department"), rs.getString("faculty"), rs.getInt("year_of_study"), rs.getFloat("balance"),rs.getString("student_status")));
                System.out.println(searchStudent);
                tableview.setItems(null);

                tableview.setItems(searchStudent);
            }
                if (searchStudent.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Student not found");

                }




            // TODO Auto-generated method stub

              /*  TableView<Students> tableView = new TableView<>();
                tableView.setItems(null);
                tableView.setItems(students);*/


        } catch (SQLException t) {
            t.printStackTrace();
        }
    }

}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	
        db = new Database();


        columnregno.setCellValueFactory(new PropertyValueFactory<>("regNo"));
        columnfirstname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnmiddlename.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        columnlastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnemailaddress.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
        columnphoneno.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
        ;
        columngender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        ;
        columncourse.setCellValueFactory(new PropertyValueFactory<>("course"));
        columndepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
        columnfaculty.setCellValueFactory(new PropertyValueFactory<>("faculty"));
        columnyearofstudy.setCellValueFactory(new PropertyValueFactory<>("yearOfStudy"));
        columnbalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        columnstudentstatus.setCellValueFactory(new PropertyValueFactory<>("studentStatus"));


        try {
        	
        	
            Connection conn = db.getConnection();


            String in_session = "in_session";
            String sql = "select * from Students where student_status = '"+in_session+"'";
            ResultSet rs = conn.prepareStatement(sql).executeQuery();
            
            
            
            while (rs.next()) {
            	Fine.updateStudent(rs.getString("reg_no"));
            	students.add(new Students(rs.getString("reg_no"), rs.getString("first_name"), rs.getString("middle_name"), rs.getString("last_name"), rs.getString("email_address"), rs.getInt("phone_no"), rs.getString("gender"), rs.getString("course"), rs.getString("department"), rs.getString("faculty"), rs.getInt("year_of_study"), rs.getDouble("balance"),rs.getString("student_status")));
            	
            	tableview.setItems(students);

            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void searchStudent1(ActionEvent actionEvent) throws  Exception{
        ObservableList<Students> searchStudent = FXCollections.observableArrayList();
        Connection conn = db.getConnection();

        String sql1 = "select * from Students where reg_no = '" + searchTextField+"'";
        ResultSet rs = conn.prepareStatement(sql1).executeQuery();

        while (rs.next()){
            System.out.println("in the while loop");
            searchStudent.add(new Students(rs.getString("reg_no"),rs.getString( "first_name"),rs.getString("middle_name"),rs.getString("last_name"),rs.getString("email_address"),rs.getInt("phone_no"),rs.getString("gender"),rs.getString("course"),rs.getString("department"),rs.getString("faculty"),rs.getInt("year_of_study"),rs.getFloat("balance"),rs.getString("student_status")));
            System.out.println(searchStudent);
            tableview.setItems(null);

            tableview.setItems(searchStudent);

            if (searchStudent.isEmpty()){
                JOptionPane.showMessageDialog(null, "Student not found");
                
            }

        }


        // TODO Auto-generated method stub

              /*  TableView<Students> tableView = new TableView<>();
                tableView.setItems(null);
                tableView.setItems(students);*/


    }

    public void deleteselectedstudent(ActionEvent actionEvent) {
        Students studentToDelete = tableview.getSelectionModel().getSelectedItem();
        if(studentToDelete == null) {
            JOptionPane.showMessageDialog(null, "no student is selected");
            return;
        }


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting a student...");
        alert.setContentText("are you sure you want to delete the student"+ studentToDelete.getRegNo()+ "?");
        Optional<ButtonType> answer = alert.showAndWait();
        if(answer.get()==ButtonType.OK) {

            Boolean result = deletestudent(studentToDelete);
            if(result) {
            	
            	
                JOptionPane.showMessageDialog(null, studentToDelete.getRegNo()+" deleted successfully");
              

            }

        }else {
            JOptionPane.showMessageDialog(null, "Cancelled");
        }
    }



    private Boolean deletestudent(Students studentToDelete) {
        try {


            Connection conn = db.getConnection();
            String outofsession = "OUTOFSESSION";
            String sql = "update students set Student_Status = '"+outofsession+"'where reg_no= ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, studentToDelete.getRegNo());
            stmt.executeUpdate();


            return true;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addnewstudentscene1(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
        Scene scene = new Scene(root,1280, 720);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void viewdeletedstudentsscene(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ViewDeletedStudents.fxml"));
        Scene scene = new Scene(root,1280, 720);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void updateselectedstudent(ActionEvent actionEvent) throws IOException {
        Students studentToUpdate = tableview.getSelectionModel().getSelectedItem();
        if(studentToUpdate == null) {
            JOptionPane.showMessageDialog(null, "no student is selected");
            return;
        }


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Updating student details...");
        alert.setContentText("are you sure you want to update the student details"+ studentToUpdate.getRegNo()+ "?");
        Optional<ButtonType> answer = alert.showAndWait();
        if(answer.get()==ButtonType.OK) {
            FXMLLoader loader = new FXMLLoader();
            loader. setLocation(getClass().getResource("UpdateDetails.fxml"));
            Parent tablevi = loader.load();
            Scene scene = new Scene(tablevi,1280,720);
            UpdateDetails controller = loader.getController();
            controller.initStudentsdetails(tableview.getSelectionModel().getSelectedItem());

            Stage window = new Stage();
            window.setTitle("Updating....");
            window.initModality(Modality.APPLICATION_MODAL);

            window.setScene(scene);
            window.show();

        }else

         {
            JOptionPane.showMessageDialog(null, "Cancelled");
        }
    }
    
  
   
}



