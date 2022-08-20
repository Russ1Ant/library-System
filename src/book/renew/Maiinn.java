package book.renew;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Maiinn extends Application {
	@Override
	public void start(Stage primaryStage)  {
		try {
			//welcome
	Parent root = FXMLLoader.load(getClass().getResource("renewBook.fxml"));		
	//AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("welcome.fxml"));
	Scene scene = new Scene(root,1200,720);
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