package bookIssueAndRenewal2;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import application.LibraryStaff;
import book.Main;
import book.issue.IssiueBooksController;
import book.viewBooksInDB.Book;
import connectivitypkg.Database;
import javafx.beans.binding.Bindings;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import studentsPackage.Students;

public class Controller implements Initializable{
	
	  private connectivitypkg.Database db;
	    ObservableList<Book> bookList;
	    ObservableList<Students> studentList;
	    
	    
	/** Student**/
	    @FXML
	    private TableView<Students> borrowerTableView;
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
	    private TextField borrowerTextField;

	    
	    
	    /** books **/
	    @FXML
	    private TableView<Book> tableViewBooks;

		 @FXML
	    private TableColumn<Book, String> title;

	    @FXML
	    private TableColumn<Book, String> author;

	    @FXML
	    private TableColumn<Book, String> isbn;

	    @FXML
	    private TableColumn<Book, Integer> quantity;

	    @FXML
	    private TableColumn<Book, Integer> issued;

	    @FXML
	    private TableColumn<Book, Date> issueDate;

	    @FXML
	    private TableColumn<Book, String> publisher;
	    @FXML
	    private TextField bookSearchTextField;
	    @FXML
	    Main main = new Main();
	
	
   IssiueBooksController ibs = new IssiueBooksController();
	LibraryStaff lb;
	@FXML
    private Button staffButton;
	
	 @FXML
	    private TextField studeRegNoTF;
	
	 @FXML
	    private ListView<String> issueDataListView;
	  @FXML
	    private TextField bookID;
	 
	  	
	    @FXML
	    void reportsScene(ActionEvent event) throws IOException {
	    	Parent root = FXMLLoader.load(getClass().getResource("/reports/reports.fxml"));
	    	Scene scene = new Scene(root,1200,720);
	    	
	    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    stage.setScene(scene);
	    stage.show();
	     
	    }
	  	
	    @FXML
	    void loadBookInfo(ActionEvent event) {
	    	System.out.println("printing book info");

	    	ObservableList<String> issueData = FXCollections.observableArrayList();
	    	Connection conn = db.getConnection();
             String id = bookID.getText();
             String iss = "issued";
             String query = "select * from circulation where ISBN = '"+bookID.getText()+"' and book_status = '"+iss+"'";
             try {
             ResultSet rs = conn.prepareStatement(query).executeQuery(query);
             
             if(!(rs.next())) {
            	 JOptionPane.showMessageDialog(null, "Book not found");
            	 return;
             }
             
             while(rs.next()) {
            	 int bookId = rs.getInt("ISBN");
            	 String stude = rs.getString("student_id");
            	 System.out.println(stude);
            	 Timestamp issueTime = rs.getTimestamp("issued_date");
            	 
            	 issueData.add("Issue date and time: "+issueTime.toGMTString());
            	 
            	 issueData.add("Book Information: ");
            	 query = "select * from books where ISBN = '"+bookId+"'";
            	 ResultSet rs1 = conn.prepareStatement(query).executeQuery();
            	 while(rs1.next()) {
            		 issueData.add("BookName: "+rs1.getString("title"));
            		 issueData.add("Author: " +rs1.getString("author"));
            		// issueData.add("edition : "+rs1.getInt("edition"));
            		 //issueData.add("Year published: "+rs1.getString("yearOfPublication"));
            	 
            	 }
            	 
           	 
         	 query = "select * from students where reg_no = '"+stude+"'";
                	 ResultSet rs2 = conn.prepareStatement(query).executeQuery();
             issueData.add("Student information");
                	 while(rs2.next()) {
                		 issueData.add("Student registration Number: "+stude);
                		 issueData.add("Student Name: "+rs2.getString("first_name"));
                		 issueData.add("Phone number: "+rs2.getInt("phone_no"));
                	     issueData.add("Email: "+rs2.getString("email_address"));
                	     issueData.add("FAculty: "+ rs2.getString("faculty"));
            	 }
                	 issueData.add("********************************************");
            	 
            	 
             }
             }catch(Exception e) {
            	 e.printStackTrace();
             }
             issueData.add("********************************************");
             issueDataListView.getItems().setAll(issueData);
             
	    }
	    
	    @FXML
	    void loadBookInfoRegNo(ActionEvent event) {
	    	
	    	if(studeRegNoTF.getText().isEmpty()) {
	    		JOptionPane.showMessageDialog(null, "please enter student reg no");
	    		return;
	    	}

	    	ObservableList<String> issueData = FXCollections.observableArrayList();
	    	Connection conn = db.getConnection();
             String id = studeRegNoTF.getText();
             String issued = "issued";
             String query = "select * from circulation where student_id = '"+id+"'";
             try {
             ResultSet rs = conn.prepareStatement(query).executeQuery(query);
             if(!(rs.next())) {
            	 JOptionPane.showMessageDialog(null, "Student not found");
            	 return;
             }
             
             while(rs.next()) {
            	 String bookId = rs.getString("ISBN");
            	 String stude = rs.getString("student_id");
            	 
            	 Timestamp issueTime = rs.getTimestamp("issued_date");
            	 
            	 issueData.add("Issue date and time: "+issueTime.toGMTString());
            	 
            	 issueData.add("Book Information: ");
            	 query = "select * from books where ISBN = '"+bookId+"'";
            	 ResultSet rs1 = conn.prepareStatement(query).executeQuery();
            	 while(rs1.next()) {
            		 issueData.add("BookName: "+rs1.getString("title"));
            		 issueData.add("Author: " +rs1.getString("author"));
            		 //issueData.add("edition : "+rs1.getInt("edition"));
            		// issueData.add("Year published: "+rs1.getString("yearOfPublication"));
            	 
            	 }
            	 
           	 
         	 query = "select * from students where reg_no = '"+stude+"'";
                	 ResultSet rs2 = conn.prepareStatement(query).executeQuery();
             issueData.add("Student information");
                	 while(rs2.next()) {
                		 issueData.add("Student registration Number: "+stude);
                		 issueData.add("Student Name: "+rs2.getString("first_name"));
                		 issueData.add("Phone number: "+rs2.getInt("phone_no"));
                	     issueData.add("Email: "+rs2.getString("email_address"));
                	     issueData.add("FAculty: "+ rs2.getString("faculty"));
            	 }
                	 issueData.add("********************************************");
            	 
            	 
             }
             }catch(Exception e) {
            	 e.printStackTrace();
             }
             issueData.add("********************************************");
             issueDataListView.getItems().setAll(issueData);
             
	    }

	    @FXML
	    void renewBook(ActionEvent event) throws SQLException {
	    	if(studeRegNoTF.getText().isBlank()) {
	    		JOptionPane.showMessageDialog(null, "student Reg No not entered");
	    		return; 
	    	}
	    	
	    	Connection conn = db.getConnection();
	    	String sqlCheck = "select * from circulation where student_id = '"+studeRegNoTF.getText()+"' and book_status = 'issued'";
	    	ResultSet rs = conn.prepareStatement(sqlCheck).executeQuery();
	    	if(rs.next()) {
	    	
	    	Alert alert = new Alert(AlertType.CONFIRMATION);
	    	alert.setContentText("Are you sure you want to renew this book ?");
	    	Optional<ButtonType> conff=alert.showAndWait();
	    	if(conff.get()== ButtonType.OK) {
	    		String sql = "update circulation set issued_date = CURRENT_TIMESTAMP WHERE student_id = '"+studeRegNoTF.getText()+"' and book_Status = 'issued'"; 
	    	conn.prepareStatement(sql).executeUpdate();
	    	JOptionPane.showMessageDialog(null, "book renewed successfully");
	    	
	    	}
	    	}else {
	    		JOptionPane.showMessageDialog(null, "Operation cancelled");
	    	}

	    }

	    @FXML
	    void returnBook(ActionEvent event) {

	    }
	    @FXML
	    void issueBook(ActionEvent event) {

	    }
	    
	    @FXML
	    void logOut(ActionEvent event) throws IOException {
	    
	    	Parent root = FXMLLoader.load(getClass().getResource("/loginpkg/welcome.fxml"));
	    	Scene scene = new Scene(root,1200,720);
	  // 	
	    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    stage.setScene(scene);
	    stage.show();
	    }
	    
	    @FXML
	    void staffMenu(ActionEvent event) throws IOException {
	    	Parent root = FXMLLoader.load(getClass().getResource("/application/staff.fxml"));
	    	Scene scene = new Scene(root,1200,720);
	    	
	    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    stage.setScene(scene);
	    stage.show();
	    
	    

	    }
	    
	    @FXML
	    void booksMenu(ActionEvent event) throws IOException {
			
	    	Parent root = FXMLLoader.load(getClass().getResource("/book.viewBooksInDB2/viewBooksInDBs.fxml"));
	    	Scene scene = new Scene(root,1200,720);
	    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    	stage.setScene(scene);
	    	stage.show();
	    	
	    	
	    }

	    @FXML
	    void studentsMenu(ActionEvent event) throws IOException {

	        Parent root = FXMLLoader.load(getClass().getResource("/studentsPackage2/View.fxml"));
	 	    Scene scene = new Scene(root,1200,720);
	 	    	
	 	    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	 	    stage.setScene(scene);
	 	    stage.show();
	    }

	    @FXML
	    void lecturersMenu(ActionEvent event) throws IOException {

	    	 Parent root = FXMLLoader.load(getClass().getResource("/lecturers2/view.fxml"));
		 	    Scene scene = new Scene(root,1200,720);
		 	    	
		 	    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		 	    stage.setScene(scene);
		 	    stage.show();
	    }
	    
	    
	    @FXML
		public
	    void back(ActionEvent event) throws IOException {
	    	
	    	Parent root = FXMLLoader.load(getClass().getResource("/book.issue/bookIssueAndRenewalLibStaff.fxml"));
	    	Scene scene = new Scene(root,1200,720);
	    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    	stage.setScene(scene);
	    	stage.show();
	    	

	    }

	    @FXML
		public
	    void issiueBook(ActionEvent event) throws SQLException {
	    	String bookIsbn = bookSearchTextField.getText();
	    	String studentID = borrowerTextField.getText();
	    	Connection conn = db.getConnection();
	    	String sqlb = "select * from BOOKS where ISBN = '"+bookSearchTextField.getText()+"'";
	         String sqlS = "select * from students where reg_no = '"+studentID+"'";
			
			ResultSet rs = conn.prepareStatement(sqlb).executeQuery();
			ResultSet rs1 = conn.prepareStatement(sqlS).executeQuery();
	    
			if(bookIsbn.isEmpty()) {
	    		Alert alert = new Alert(Alert.AlertType.WARNING);
	    		alert.setContentText("book id is null");
	    		alert.setTitle("you cannot proceed ");
	    		alert.showAndWait();}
	    		else if(Bindings.isEmpty(borrowerTableView.getItems()) == null) {
	    			JOptionPane.showMessageDialog(null, "no student is selectred");
	    			
	    		
	    		
	    	}else if(studentID.isEmpty()) {
	    		Alert alert = new Alert(Alert.AlertType.WARNING);
	    		alert.setTitle("you cannot proceed ");
	    		alert.setContentText("studentID is null");
	    		alert.showAndWait();
	    		
	    	}else	if(!(rs.next())) {
	    		JOptionPane.showMessageDialog(null, "Book does not Exist");
	    	}else if(!(rs1.next())) {
	    		JOptionPane.showMessageDialog(null, "Student  not found");
	    	}else { 
	    	
	    		
	    	
	    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	    	alert.setContentText("issue "+studentID+" with book "+bookIsbn); 	
	    	Optional<ButtonType> bt = alert.showAndWait();
	    	if(bt.get()==ButtonType.OK) {
	    		try {

	    			Connection conn1 = db.getConnection();
	    			String checkSql = "select * from circulation where isbn = '"+bookIsbn+"' and student_id = '"+studentID+"'";

	    			ResultSet rs11 = conn1.prepareStatement(checkSql).executeQuery();
					
	    			while(rs11.next()) {
	    			//	JOptionPane.showMessageDialog(null, rs11.getDate("return_date"));
	    				System.out.println(rs11.getDate("return_date"));    				
	    				//	System.out.println(rs11.getDate("return_date"));
	    					if(rs11.getString("book_status").equals("issued")){
	    						JOptionPane.showMessageDialog(null, "cannot issue book to this student. please return the one you have tuitled '"+rs11.getString("book_title")+"'");
	    						return;
	    					
	    					
	    				}
	    			}
	    			PreparedStatement ps = null;  
	    		String sql = "Insert into circulation (isbn, book_title, student_id, student_name) values(?,?,?,?)";
	    		String sql2 = "update Books set quantity = quantity-1 ,issued = issued+1 where ISBN = '"+bookSearchTextField.getText()+"'";
	    		String sql3 = "select quantity from books where isbn = '"+bookIsbn+"'";
	    		
	    		ResultSet rsCop = conn1.prepareStatement(sql3).executeQuery(sql3);
	    		
	    		while(rsCop.next()) {
	    			int copyNumber = rs.getInt("quantity");
	    			int issued = rs.getInt("issued");
	    			if(copyNumber<1) {
	    				JOptionPane.showMessageDialog(null, bookIsbn+" is out of stock ");
	    				return;
	    			}else
	    			{
	    		
				
				  ps= conn1.prepareStatement(sql); 
				  ps.setString(1, bookIsbn);
				  ps.setString(2, rs.getString("TITLE"));
				  ps.setString(3,studentID); 
				  ps.setString(4, rs1.getString("FIRST_NAME"));
				  ps.execute();
				 
	    		//Boolean bi =  conn.prepareStatement(sql).execute();
	    		
	    	    conn1.createStatement().executeUpdate(sql2);
	    		
	    		//Statement statement = conn.createStatement();`
	    	//	statement.executeUpdate(sql);
	       		JOptionPane.showMessageDialog(null, "Book issiued successfully.");
	       	
	    			}}
	    		
	    		}catch(Exception e) {
	    			e.printStackTrace();
	    			JOptionPane.showMessageDialog(null, e.toString());		
	    		}
	    		
	    	}else {
	    		JOptionPane.showMessageDialog(null, "cancelled");
	    	}
	    	
	    	}
	    	

	    }
	    	
	    @FXML
		public
	    void searchBookToIssiue(ActionEvent event) {
	    	
	    	if(bookSearchTextField.getText().isEmpty()) {
	    	JOptionPane.showMessageDialog(null, "Enter book isbn to search ");	
	    	}else {
	    //	JOptionPane.showMessageDialog(null, "searching...");	
	    	try {
	    		Connection conn = db.getConnection();
	    		String sql = "select * from BOOKS where ISBN = '"+bookSearchTextField.getText()+"'";

	    		
	    		ResultSet rs = conn.prepareStatement(sql).executeQuery();
	    	    
	    			
		    	
					    	if(rs.next()) {
					    		bookList = FXCollections.observableArrayList();
								bookList.add(new Book(rs.getString("title"),rs.getString("author"),rs.getString("isbn"),rs.getInt("quantity"),rs.getString("publisher"),rs.getInt("issued"),rs.getDate("issue_date")));
								tableViewBooks.setItems(bookList);
					    	}else {
					    		JOptionPane.showMessageDialog(null, "book not found");
					    	}
		    	
		    	    		
		  	    	
		    }catch(Exception e) {
	    		e.printStackTrace();
	    	}

	    }}

	    @FXML
		public
	    void searchBorrowerToIssiueBook(ActionEvent event) {
	    	studentList=FXCollections.observableArrayList();
	    	if(borrowerTextField.getText().isEmpty()) {
	    		JOptionPane.showMessageDialog(null,"Enter borrower ID to issue book. ");
	    	}else {
	    		try {
	    		Connection conn = db.getConnection();
	    		String sql = "Select * from students where reg_no = '" +borrowerTextField.getText()+"'";
	    		ResultSet rs = conn.prepareStatement(sql).executeQuery();
	    		if(rs.next()) {
	               studentList.add(new Students(rs.getString("reg_no"),rs.getString( "first_name"),rs.getString("middle_name"),rs.getString("last_name"),rs.getString("email_address"),rs.getInt("phone_no"),rs.getString("gender"),rs.getString("course"),rs.getString("department"),rs.getString("faculty"),rs.getInt("year_of_study"),rs.getFloat("balance"),rs.getString("student_status")));
	    			borrowerTableView.setItems(studentList);
	    			
	    		}else {
	    			JOptionPane.showMessageDialog(null, "Student not found");
	    		}
	    		}catch(Exception e){
	    			e.printStackTrace();
	    			
	    		}
	    		
	    	}

	    }


		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			//Stage stage = (Stage) tableViewBooks.getScene().getWindow();
			//if(stage.getTitle().equals("librarian")) {
				//staffButton.setVisible(false);
				//staffButton.setDisable(true);
				
			
			db = new connectivitypkg.Database();

			title.setCellValueFactory(new PropertyValueFactory<>("title"));
			author.setCellValueFactory(new PropertyValueFactory<>("author"));
			isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
			quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
			publisher.setCellValueFactory(new PropertyValueFactory<>("publisher"));
			issued.setCellValueFactory(new PropertyValueFactory<>("issued"));;
			issueDate.setCellValueFactory(new PropertyValueFactory<>("issueDate"));;
			
			tableViewBooks.setItems(null);
			
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
		        
		      borrowerTableView.setItems(null);

			db = new Database();
		//checkSessionHolder();
			}
			// TODO Auto-generated method stub
			
	//	}

		public void checkSessionHolder() {
			Stage stage = (Stage) studeRegNoTF.getScene().getWindow(); 
					if(stage.getTitle().equals("librarian")) {
		             
						staffButton.setDisable(true);
					}
			
		}}
