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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
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
    private TableView<Lecturers> tableview;
    @FXML
    private TableColumn<Lecturers, String> columnLecID;

    @FXML
    private TableColumn<Lecturers, Integer> columnNationalID;
    @FXML
    private TableColumn<Lecturers, String> columnfirstname;
    @FXML
    private TableColumn<Lecturers, String> columnmiddlename;
    @FXML
    private TableColumn<Lecturers, String> columnlastname;
    @FXML
    private TableColumn<Lecturers, String> columnemailaddress;
    @FXML
    private TableColumn<Lecturers, Integer> columnphoneno;
    @FXML
    private TableColumn<Lecturers, String> columngender;
    @FXML
    private TableColumn<Lecturers, String> columndepartment;
    @FXML
    private TableColumn<Lecturers, String> columnfaculty;
    @FXML
    private TableColumn<Lecturers, Float> columnbalance;
    @FXML
    private TableColumn<Lecturers, String> columnWorkingStatus;
    @FXML
    private Button searchLecturer;
    @FXML
    TextField searchTextField;
    private Database db;
    ObservableList<Lecturers> lecturers = FXCollections.observableArrayList();

    @FXML
    void searchLecturer(ActionEvent event) {
        ObservableList<Lecturers> searchLecturer = FXCollections.observableArrayList();
        if (searchTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "please enter lecturer ID to search");
        } else {

            try {
                Connection conn = db.getConnection();

                String sql1 = "select * from lecturers where lecturer_id = '" + searchTextField.getText() + "'";
                ResultSet rs = conn.prepareStatement(sql1).executeQuery();

                while (rs.next()) {
                    searchLecturer.add(new Lecturers(rs.getString("lecturer_id"), rs.getInt("national_id"), rs.getString("first_name"), rs.getString("middle_name"), rs.getString("last_name"), rs.getString("email"), rs.getInt("phone"), rs.getString("gender"), rs.getString("department"), rs.getString("faculty"), rs.getFloat("balance"), rs.getString("working_status")));
                    System.out.println(searchLecturer);
                    tableview.setItems(null);

                    tableview.setItems(searchLecturer);
                }
                if (searchLecturer.isEmpty()) {
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
    
    @FXML
    void back(ActionEvent event) throws IOException {
    	   Parent root = FXMLLoader.load(getClass().getResource("/bookIssiueAndRenewal/bookIssueAndRenewal.fxml"));
           Scene scene = new Scene(root,1280, 720);
           Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
           stage.setScene(scene);
           stage.show();

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = new Database();


        columnLecID.setCellValueFactory(new PropertyValueFactory<>("lecturerID"));
        columnNationalID.setCellValueFactory(new PropertyValueFactory<>("nationalID"));
        columnfirstname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnmiddlename.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        columnlastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnemailaddress.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
        columnphoneno.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
        ;
        columngender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        ;
        columndepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
        columnfaculty.setCellValueFactory(new PropertyValueFactory<>("faculty"));
        columnbalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        columnWorkingStatus.setCellValueFactory(new PropertyValueFactory<>("workingStatus"));


        try {
            Connection conn = db.getConnection();


            String working_status = "working";
            String sql = "select * from lecturers where working_status = '"+working_status+"'";
            ResultSet rs = conn.prepareStatement(sql).executeQuery();

            while (rs.next()) {
                lecturers.add(new Lecturers(rs.getString("lecturer_id"), rs.getInt("national_id"),rs.getString("first_name"), rs.getString("middle_name"), rs.getString("last_name"), rs.getString("email"), rs.getInt("phone"), rs.getString("gender"), rs.getString("department"), rs.getString("faculty"), rs.getFloat("balance"),rs.getString("working_status")));
                tableview.setItems(lecturers);

            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }


    public void deleteselectedlecturer(ActionEvent actionEvent) {
        Lecturers lecturerToDelete = tableview.getSelectionModel().getSelectedItem();
        if(lecturerToDelete == null) {
            JOptionPane.showMessageDialog(null, "no lecturer is selected");
            return;
        }


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting a lecturer...");
        alert.setContentText("are you sure you want to delete the lecturer"+ lecturerToDelete.getLecturerID()+ "?");
        Optional<ButtonType> answer = alert.showAndWait();
        if(answer.get()==ButtonType.OK) {

            Boolean result = deletelecturer(lecturerToDelete);
            if(result) {
                JOptionPane.showMessageDialog(null, lecturerToDelete.getLecturerID()+" deleted successfully");

            }

        }else {
            JOptionPane.showMessageDialog(null, "Cancelled");
        }
    }

    private Boolean deletelecturer(Lecturers lecturerToDelete) {
        try {


            Connection conn = db.getConnection();
            String working_status = "not working";
            String sql = "update lecturers set working_status = '"+working_status+"'where lecturer_id= ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, lecturerToDelete.getLecturerID());
            stmt.executeUpdate();


            return true;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void viewdeletedlecturersscene(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("viewDeletedLecturers.fxml"));
        Scene scene = new Scene(root,1280, 720);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void addNewLecturerScene(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("addLecturers.fxml"));
        Scene scene = new Scene(root,1280, 720);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }



    public void updateselectedlecturer(ActionEvent actionEvent) throws IOException {
        Lecturers studentToUpdate = tableview.getSelectionModel().getSelectedItem();
        if(studentToUpdate == null) {
            JOptionPane.showMessageDialog(null, "no lecturer is selected");
            return;
        }


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Updating lecturer details...");
        alert.setContentText("are you sure you want to update the lecturer details"+ studentToUpdate.getLecturerID()+ "?");
        Optional<ButtonType> answer = alert.showAndWait();
        if(answer.get()==ButtonType.OK) {
            FXMLLoader loader = new FXMLLoader();
            loader. setLocation(getClass().getResource("UpdateDetails.fxml"));
            Parent tablevi = loader.load();
            Scene scene = new Scene(tablevi,1280,720);
            UpdateDetails controller = loader.getController();
            controller.initLecturerDetails(tableview.getSelectionModel().getSelectedItem());

            Stage window = new Stage();
            window.setTitle("Updating....");
            window.initModality(Modality.APPLICATION_MODAL);

            window.setScene(scene);
            window.show();

        }

    }

}