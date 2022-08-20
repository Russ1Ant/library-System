package settings;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
public class MainSettings extends Application{
	@Override
	public void start(Stage primaryStage) {
		try {
		   Parent root = FXMLLoader.load(getClass().getResource("settings.fxml"));
			Scene scene = new Scene(root,600,400);
		//	scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			Preferences.initConfig();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
