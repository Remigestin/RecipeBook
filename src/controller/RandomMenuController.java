package controller;

import java.net.URL;
import java.util.ResourceBundle;

import businessLogic.Recipe;
import businessLogic.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RandomMenuController implements Initializable {
	@FXML
	private Button changeStarter;
	@FXML
	private Label nameStarter;
	@FXML
	private Label timeStarter;
	@FXML
	private Label lvlStarter;
	@FXML
	private ImageView imageStarter;
	@FXML
	private Button changeMain;
	@FXML
	private Button changeDessert;
	@Override
	
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		User session = User.getSession();
		Recipe starter = session.getRandomStarter();

		this.timeStarter.setText(Integer.toString(starter.getPreparationTime()) + " min");
		this.nameStarter.setText(starter.getNameRecipe());
		Image starterImg = new Image(starter.getImage());
		this.imageStarter.setImage(starterImg);
		this.lvlStarter.setText(Integer.toString(starter.getDifficulty()) + "/5");
		
		

	}

}
