package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StaffController implements Initializable{
    @FXML
    private BorderPane borderPane;
	      
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

	    @FXML
	    private TextField searchTxt;
	    
	    

	    @FXML
	    private TextField staffID;

	    @FXML
	    private TextField nationalID;

	    @FXML
	    private TextField firstName;

	    @FXML
	    private TextField middleName;

	    @FXML
	    private TextField lastName;

	    @FXML
	    private TextField email;

	    @FXML
	    private TextField phone;

	    @FXML
	    private ComboBox<String> genderComboBox;
	    
	    
	    
	    @FXML
	    private Button back;

	    @FXML
	    void backToStaffPage(ActionEvent event) throws IOException {
	    	borderPane.setCenter(tableViewStaff);
			/*
			 * Parent root = FXMLLoader.load(getClass().getResource("staff.fxml")); Scene
			 * scene = new Scene(root,1280, 720); Stage stage = (Stage) ((Node)
			 * event.getSource()).getScene().getWindow(); stage.setScene(scene);
			 * stage.show();
			 */
	    }
	    
	    private Database db;
	   private ObservableList<LibraryStaff> staffList;
	   
	    @FXML
	    void addStaffScene(ActionEvent event) throws IOException {
	    	Parent root = FXMLLoader.load(getClass().getResource("addStaff1.fxml"));
	    	Scene scene = new Scene(root,1280, 720);
	    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    	stage.setScene(scene);
	    	stage.show();

	    }
	    @FXML
	    void contextMenu(ContextMenuEvent event) {
	    //	MenuItem  mn = new MenuItem("delete");
	    	//ObservableList<String> cmen = FXCollections.observableArrayList("how","delete");
	    	//tableViewStaff.setContextMenu(mn);
	    	//JOptionPane.showMessageDialog(null, "how is that even possible ");
	    	

	    }

	    @FXML
	    void backPage(ActionEvent event) throws IOException {
	    	 Parent root = FXMLLoader.load(getClass().getResource("/bookIssiueAndRenewal/bookIssueAndRenewal.fxml")); 
			  Scene scene = new Scene(root,1280, 720);
			  Stage stage = (Stage) ((Node)
			  event.getSource()).getScene().getWindow(); 
			  stage.setScene(scene);
			  stage.show();

	    }

	    @FXML
	    void deleteStaffScene(ActionEvent event) {

	    	 
	    	if(searchTxt.getText().isEmpty()) {
	    		JOptionPane.showMessageDialog(null, "Please enter staff to delete");
	    	}else {
	    
             Connection conn = db.getConnection();
             String fired = "FIRED";
             String sqlconf = "select working_status from library_staff where staff_id= '"+searchTxt.getText()+"'";
             //String sql = "update books set copies_count =  '" + new_value +"' where isbn= '" +isbnTxt.getText()+"' ";
             
             String sql = "update library_staff set working_Status = '"+fired+"'where staff_id= '"+searchTxt.getText()+"'";
   try {
	   conn.createStatement().execute(sql);
	   JOptionPane.showMessageDialog(null, "successfully deleted");
   }catch(SQLException e) {
	   e.printStackTrace();
   }}
	    }

	    @FXML
	    void searchStaff(ActionEvent event) {
	    	ObservableList<LibraryStaff> searchStaffList =FXCollections.observableArrayList() ;
	    	if(searchTxt.getText().isEmpty()) {
	    		JOptionPane.showMessageDialog(null, "please enter staff number to search");
	    	}
	    	else {

	    	try {
	    		Connection conn = db.getConnection();
		    	String sql = "select * from Library_staff where staff_id = '"+searchTxt.getText()+"'";
				ResultSet rs = conn.prepareStatement(sql).executeQuery();
		
				while(rs.next()) {
					    searchStaffList.add(new LibraryStaff(rs.getString("staff_id"),rs.getInt("national_id"),rs.getString("first_name"),rs.getString("middle_name"),rs.getString("last_name"),rs.getString("email"),rs.getInt("phone"),rs.getString("password"),rs.getString("gender"),rs.getString("department"),rs.getString(11)));
						tableViewStaff.setItems(null);
						tableViewStaff.setItems(searchStaffList);
						
				//break;	 
				}
				if(searchStaffList.isEmpty()) {
					JOptionPane.showMessageDialog(null, searchTxt.getText()+" not found");
				}
				//JOptionPane.showMessageDialog(null, "not found");
				rs.close();	
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e);
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
	    @FXML
	    private void edit(ActionEvent e) {
	    	LibraryStaff staffToEdit = tableViewStaff.getSelectionModel().getSelectedItem();
	    	if(staffToEdit== null) {
	    		JOptionPane.showMessageDialog(null, "please select Staff to edit");
	    		return;
	    	}
	    	try {
				/*
				 * Parent root = FXMLLoader.load(getClass().getResource("editStaff.fxml"));
				 * Scene scene = new Scene(root,1280, 720); Stage stage = (Stage) ((Node)
				 * e.getSource()).getScene().getWindow(); stage.setScene(scene); stage.show();
				 */
	    		//FxmlLoader obj = new FxmlLoader();
	        	//Pane pane = obj.getPage("editStaff");
	        	 
	        	//borderPane.setCenter(pane);
	    		
	        	FXMLLoader loader = new FXMLLoader();
	        	loader.setLocation(getClass().getResource("editStaff.fxml"));
	        	Parent staffView = loader.load();
	        	
	        	Scene scene = new Scene(staffView);
	        	
	        	EditController controller = loader.getController();
	        	controller.initLibStaffData(tableViewStaff.getSelectionModel().getSelectedItem());
	        	//Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
	        	Stage window = new Stage();
	        	
	    		window.setTitle("Updating...");
	    		window.initModality(Modality.APPLICATION_MODAL);
	        			
	        	window.setScene(scene);
	        	window.show();
	        	
	    		
	    		
	    	}catch(Exception e1) {
	    		e1.printStackTrace();
	    	}
	    	
	    	
	    	
	    }
	    
	    @FXML
	    private void deleteSelectedStaff(ActionEvent event){
			/*
			 * tableViewStaff.setOnContextMenuRequested(e->{
			 * 
			 * ContextMenu cm = new ContextMenu(new MenuItem("delete"));
			 * //cm.getItems().add(new MenuItem("trial")); //Controller controller;
			 * tableViewStaff.setContextMenu(cm);
			 * System.out.println("You right clicked here"); });
			 * 
			 * LibraryStaff staffToDelete =
			 * tableViewStaff.getSelectionModel().getSelectedItem(); if(staffToDelete ==
			 * null) { //AlertMaker.showErrorMessage("","");
			 * JOptionPane.showMessageDialog(null, "no person is selected"); }
			 * 
			 * Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			 * alert.setTitle("deleting...");
			 * alert.setContentText("are you sure to delete this person");
			 */
	    	LibraryStaff staffToDelete = tableViewStaff.getSelectionModel().getSelectedItem();
	    	if(staffToDelete == null) {
	    		JOptionPane.showMessageDialog(null, "no staff is selected");
	    		return;
	    	}
	    	
	    	
	    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	    	alert.setTitle("Deleting library staff...");
	    	alert.setContentText("are you sure you want to delete the library Staff"+ staffToDelete.getStaffID()+ "?");
	    	Optional<ButtonType> answer = alert.showAndWait();
	    	if(answer.get()==ButtonType.OK) {
	    		
	    		Boolean result = deleteLibraryStaff(staffToDelete);
	    		if(result) {
	    			JOptionPane.showMessageDialog(null, staffToDelete.getStaffID()+" deleted successfully");
	    			refresh();
	    			 
	    		}
	    		
	    	}else {
	    		JOptionPane.showMessageDialog(null, "Cancelled");
	    	}
	    }

	private Boolean deleteLibraryStaff(LibraryStaff staffToDelete) {
	try {
		
	
	Connection conn = db.getConnection();
	String fired = "FIRED";
	String sql = "update library_staff set working_Status = '"+fired+"'where staff_id= ?";
	PreparedStatement stmt = conn.prepareStatement(sql);
	stmt.setString(1, staffToDelete.getStaffID());
	stmt.executeUpdate();
	
	
	return true;
	}catch(Exception e) {
		e.printStackTrace();
	}
	return false;
		}
	  @FXML
	    void viewDeletedStaff(ActionEvent event) throws IOException {
			/*
			 * try { tableViewStaff.setItems(null);
			 * 
			 * Connection conn = db.getConnection(); String working = "Fired"; String sql =
			 * "select * from Library_staff where working_status = '"+working+"'"; ResultSet
			 * rs = conn.prepareStatement(sql).executeQuery();
			 * 
			 * while(rs.next()) { staffList.add(new
			 * LibraryStaff(rs.getString("staff_id"),rs.getInt("national_id"),rs.getString(
			 * "first_name"),rs.getString("middle_name"),rs.getString("last_name"),rs.
			 * getString("email"),rs.getInt("phone"),rs.getString("password"),rs.getString(
			 * "gender"),rs.getString("department"),rs.getString(11)));
			 * //tableViewStaff.setItems(null); tableViewStaff.setItems(staffList); }
			 * }catch(SQLException e) { e.printStackTrace(); }
			 */
		  AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("deletedStaffTable.fxml"));
		  Scene scene = new Scene(root);
		  Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		  window.setScene(scene);
		  window.setTitle("Deleted library Staff");
		  window.show();
			}
	  
	  public void refresh() {
		  tableViewStaff.setItems(null);
		  try {
			  staffList.clear();
			  tableViewStaff.setItems(null);
				Connection conn = db.getConnection();
				String working = "WORKING";
				String sql = "select * from Library_staff where working_status = '"+working+"'";
				ResultSet rs = conn.prepareStatement(sql).executeQuery();
				
				while(rs.next()) {
					staffList.add(new LibraryStaff(rs.getString("staff_id"),rs.getInt("national_id"),rs.getString("first_name"),rs.getString("middle_name"),rs.getString("last_name"),rs.getString("email"),rs.getInt("phone"),rs.getString("password"),rs.getString("gender"),rs.getString("department"),rs.getString(11)));
					
					tableViewStaff.setItems(staffList);
				}
				
			}catch(SQLException e) {
				JOptionPane.showMessageDialog(null, e);
				e.printStackTrace();
			}

			
		  
	  }
	  
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		// TODO Auto-generated method stub
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
		String working = "WORKING";
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

	
	
	}}
