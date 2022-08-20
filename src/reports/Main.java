package reports;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage)  {
		try {
			//welcome
	//Parent root = FXMLLoader.load(getClass().getResource("issueBooks.fxml"));		
	AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("reports.fxml"));
	Scene scene = new Scene(root, 1200,720);
	primaryStage.setScene(scene);
	primaryStage.show();
	
	
	}catch(Exception e) {
		e.printStackTrace();	
	}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
