package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("/views/LoginPage.fxml"));
			
			Scene scene = new Scene(root,1920,1080);
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image("file:../../asset/image/logo.png"));
			primaryStage.setTitle("RecipeBook");
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}