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


public class ViewDeletedLecturersController implements Initializable{

    @FXML
    private TableView<Lecturers> tableview;
    @FXML
    private TableColumn<Lecturers, String> columnlecturerid;
    @FXML
    private TableColumn<Lecturers, String> columnnationalid;
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
    private TableColumn<Lecturers, String> columnworkingstatus;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Database db = new Database();


        columnlecturerid.setCellValueFactory(new PropertyValueFactory<>("lecturerID"));
        columnnationalid.setCellValueFactory(new PropertyValueFactory<>("nationalID"));
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
        columnworkingstatus.setCellValueFactory(new PropertyValueFactory<>("workingStatus"));


        try {
            Connection conn = db.getConnection();


            String working_status = "not working";
            String sql = "select * from lecturers where working_status = '"+working_status+"'";
            ResultSet rs = conn.prepareStatement(sql).executeQuery();

            while (rs.next()) {
                ObservableList<Lecturers> lecturers = FXCollections.observableArrayList();
                lecturers.add(new Lecturers(rs.getString("lecturer_id"), rs.getInt("national_id"),rs.getString("first_name"), rs.getString("middle_name"), rs.getString("last_name"), rs.getString("email"), rs.getInt("phone"), rs.getString("gender"), rs.getString("department"), rs.getString("faculty"), rs.getFloat("balance"),rs.getString("working_status")));
                tableview.setItems(lecturers);

            }
        }catch (SQLException e){
            e.printStackTrace();
        }


    }

    @FXML
    void backToLecturersPage(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("View.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();


    }
}

