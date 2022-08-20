package studentsPackage2;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewDeletedStudents implements Initializable {

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

    
    public void backbuttonscene1(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("View.fxml"));
        Scene scene = new Scene(root,1280, 720);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Database db = new Database();


        columnregno.setCellValueFactory(new PropertyValueFactory<>("regNo"));
        columnfirstname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        columnmiddlename.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        columnlastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        columnemailaddress.setCellValueFactory(new PropertyValueFactory<>("emailAddress"));
        columnphoneno.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
        
        columngender.setCellValueFactory(new PropertyValueFactory<>("gender"));
       
        columncourse.setCellValueFactory(new PropertyValueFactory<>("course"));
        columndepartment.setCellValueFactory(new PropertyValueFactory<>("department"));
        columnfaculty.setCellValueFactory(new PropertyValueFactory<>("faculty"));
        columnyearofstudy.setCellValueFactory(new PropertyValueFactory<>("yearOfStudy"));
        columnbalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        columnstudentstatus.setCellValueFactory(new PropertyValueFactory<>("studentStatus"));


        try {
            Connection conn = db.getConnection();


            String out_of_session = "outofsession";
            String sql = "select * from Students where student_status = '"+out_of_session+"'";
            ResultSet rs = conn.prepareStatement(sql).executeQuery();

            while (rs.next()) {
                ObservableList<Students> students = FXCollections.observableArrayList();
                students.add(new Students(rs.getString("reg_no"), rs.getString("first_name"), rs.getString("middle_name"), rs.getString("last_name"), rs.getString("email_address"), rs.getInt("phone_no"), rs.getString("gender"), rs.getString("course"), rs.getString("department"), rs.getString("faculty"), rs.getInt("year_of_study"), rs.getFloat("balance"),rs.getString("student_status")));
                tableview.setItems(students);

            }
        }catch (SQLException e){
            e.printStackTrace();
        }


    }
}
