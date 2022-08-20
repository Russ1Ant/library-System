package application;
import java.net.URL;

import javax.swing.JOptionPane;

import application.Main;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class FxmlLoader {

private Pane view;
	
	public Pane getPage(String filename) {
		
		try {
		      URL fileUrl = Main.class.getResource("/application/"+filename+".fxml");
			if(fileUrl== null) {
				//throw new java.io.FileNotFoundException("FXML file cannot be found");
				JOptionPane.showMessageDialog(null, "file name is null");
				throw new java.io.FileNotFoundException("FXML file cannot be found");
			}else
			new FXMLLoader();
			view = FXMLLoader.load(fileUrl);
			
		}catch(Exception e) {
			e.printStackTrace();
			String ss = "no page "+filename+".fxml check FXMLoader";
			System.out.println(ss);
			JOptionPane.showMessageDialog(null, ss);
		}
		return view;
	}


	
	
}
