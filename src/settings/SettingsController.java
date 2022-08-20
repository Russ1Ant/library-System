package settings;

import java.net.URL;
import java.util.EventObject;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SettingsController implements Initializable{
	   @FXML
	    private TextField noOfDWFine;

	    @FXML
	    private TextField finePerDay;

	    @FXML
	    private TextField userName;

	    @FXML
	    private PasswordField password;

	    @FXML
	    void handleCancelAction(ActionEvent event) {
	    	Stage s =((Stage)finePerDay.getScene().getWindow());
	    	s.close();

	    }

	    @FXML
	    void handleSaveAction(ActionEvent event) {
	    	int ndays = Integer.parseInt(noOfDWFine.getText());
	    	float fine = Float.parseFloat(finePerDay.getText());
	    	String uname = userName.getText();
	    	String pass = password.getText();
	    	
	    	Preferences preferences = Preferences.getPreferences();
	    	preferences.setDaysCountWithoutFine(ndays);
	    	preferences.setFinePerDay(fine);
	    	preferences.setUsername(uname);
	    	preferences.setPassword(pass);
	    	
	    	preferences.writePrefToFile(preferences);
	    	JOptionPane.showMessageDialog(null, "saved successfully");
	    }

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			// TODO Auto-generated method stub
			initDefaultValues();
		}
	
		private void initDefaultValues() {
			Preferences preferences =Preferences.getPreferences();
			
			noOfDWFine.setText(String.valueOf(preferences.getDaysCountWithoutFine()));
			finePerDay.setText(String.valueOf(preferences.getFinePerDay()));
			userName.setText(String.valueOf(preferences.getUsername()));
			password.setText(String.valueOf(preferences.getPassword()));
			
			
			// TODO Auto-generated method stub
			
		}

}
