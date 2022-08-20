package reports;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

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
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import studentsPackage.Students;

public class ReportsController implements Initializable{
           private ObservableList<String> repoOb;
           private Database db;
           
	  @FXML
	    private ListView<String> reportsListView;

	    @FXML
	    void backToMainPage(ActionEvent event) throws IOException {
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/bookIssiueAndRenewal/bookIssueAndRenewal.fxml"));
			Parent root = loader.load();
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			
			
			stage.setScene(new Scene(root,1200,720));
			//stage.setTitle("Admin");
			//stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
			

	    }
	    @FXML
	    void reportsOnBooks() throws SQLException, IOException{
	    	Connection conn = db.getConnection();
	    	
	    	String sqqq = "select * from circulation where days = 0";
			ResultSet rss1 = conn.prepareStatement(sqqq).executeQuery();
			while(rss1.next()) {
				repoOb.add(rss1.getString("book_title")+ " of ISBN: "+rss1.getString("ISBN")+ " was issued to: "+rss1.getString("student_name")+" of registration number "+rss1.getString("student_id"));
				FileWriter writer = new FileWriter("reports.txt");
			writer.write(rss1.getString("book_title")+" was issued on "+ rss1.getDate("issued_date").toString()+" and was issued to "+rss1.getString("student_name")+" of registration number "+rss1.getString("student_id"));
			//writer.write(rss1.getDate("issued_date").toString());
			System.out.println("Successfully written to file");
			writer.close();
			}
	    }

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			db = new Database();
			repoOb = FXCollections.observableArrayList();
			Connection conn = db.getConnection();
			//finding the number of books issued
			String sql = "select count(*) from circulation where book_status = 'issued';";
			//students with balance
			String ssql1 = "select * from students where balance > 0";
			try {
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery();
				while(rs.next()) {
					int count = rs.getInt(1);
					System.out.println(count);
					repoOb.add("number of books currently issued is : "+count);
					
				}
			rs = conn.prepareStatement(ssql1).executeQuery();
			repoOb.add("Students with fine are ...");
			while(rs.next()){
				
				ObservableList<Students> ob = FXCollections.observableArrayList();
				repoOb.add("student "+rs.getString("first_name")+ " reg number: "+rs.getString("Reg_No")+" has a fine of : " +Double.toString(rs.getDouble("balance")));
				
				
			}
			repoOb.add("****************************************************");
			repoOb.add("books borrowed today are as follows:");
			String sqqq = "select * from circulation where days = 0";
			ResultSet rss1 = conn.prepareStatement(sqqq).executeQuery();
			while(rss1.next()) {
				reportsOnBooks();
			}
			repoOb.add("**********************************");
			
			reportsListView.getItems().setAll(repoOb);
			
			
			
			
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			// TODO Auto-generated method stub
			
		}
	

}
