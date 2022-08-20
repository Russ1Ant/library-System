package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class DeletedStaffTableController implements Initializable{
	  @FXML
	    void backPage(ActionEvent event) throws IOException {
		  Parent root = FXMLLoader.load(getClass().getResource("staff.fxml"));
	    	Scene scene = new Scene(root);
	    	Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    	window.setScene(scene);
	    	window.show();

	    }

	@FXML
    private TableView<LibraryStaff> tableViewStaff;

	 @FXML
    private TableColumn<LibraryStaff, String> colStaffID;

    @FXML
    private TableColumn<LibraryStaff, Integer> colNatID;

    @FXML
    private TableColumn<LibraryStaff, String> colFname;

    @FXML
    private TableColumn<LibraryStaff, String> colMidName;

    @FXML
    private TableColumn<LibraryStaff, String> colLastName;

    @FXML
    private TableColumn<LibraryStaff, String> colEmail;

    @FXML
    private TableColumn<LibraryStaff, Integer> colPhone;

    @FXML
    private TableColumn<LibraryStaff, String> colPassword;

    @FXML
    private TableColumn<LibraryStaff, String> colGender;

    @FXML
    private TableColumn<LibraryStaff, String> colDept;

    @FXML
    private TableColumn<LibraryStaff, String> colWorkingStatus;
	
    private Database db;
    ObservableList<LibraryStaff> staffList;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		db = new Database();
staffList = FXCollections.observableArrayList();
		
		colStaffID.setCellValueFactory(new PropertyValueFactory<>("staffID"));
		colNatID.setCellValueFactory(new PropertyValueFactory<>("nationalID"));
		colFname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		colMidName.setCellValueFactory(new PropertyValueFactory<>("middleName"));
		colLastName.setCellValueFactory(new PropertyValueFactory<>	("lastName"));
		colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));;
		colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));;
		colPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
		colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		colDept.setCellValueFactory(new PropertyValueFactory<>("department"));
		colWorkingStatus.setCellValueFactory(new PropertyValueFactory<>("workingStatus"));
		
		tableViewStaff.setItems(null);
		tableViewStaff.setItems(staffList);
		
		try {
			
		Connection conn = db.getConnection();
		String working = "FIRED";
		String sql = "select * from Library_staff where working_status = '"+working+"'";
		ResultSet rs = conn.prepareStatement(sql).executeQuery();
		
		while(rs.next()) {
			staffList.add(new LibraryStaff(rs.getString("staff_id"),rs.getInt("national_id"),rs.getString("first_name"),rs.getString("middle_name"),rs.getString("last_name"),rs.getString("email"),rs.getInt("phone"),rs.getString("password"),rs.getString("gender"),rs.getString("department"),rs.getString(11)));
			tableViewStaff.setItems(null);
			tableViewStaff.setItems(staffList);
		}
		
	}catch(SQLException e) {
		JOptionPane.showMessageDialog(null, e);
		e.printStackTrace();
	}

	
	
		
	}

}
