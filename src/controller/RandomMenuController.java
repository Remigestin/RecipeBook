package controller;

import java.net.URL;
import java.util.ResourceBundle;

import businessLogic.Recipe;
import businessLogic.User;
import facade.RandomFacade;
import javafx.event.Event;
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
	private Label nameMain;
	@FXML
	private Label timeMain;
	@FXML
	private Label lvlMain;
	@FXML
	private ImageView imageMain;
	
	@FXML
	private Button changeDessert;
	
	@FXML
	private Label nameDessert;
	@FXML
	private Label timeDessert;
	@FXML
	private Label lvlDessert;
	@FXML
	private ImageView imageDessert;
	
	private RandomFacade randomFacade=  RandomFacade.getInstance();
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		User session = User.getSession();
		Recipe starter = session.getRandomStarter();

		this.timeStarter.setText(Integer.toString(starter.getPreparationTime()) + " min");
		this.nameStarter.setText(starter.getNameRecipe());
		Image starterImg = new Image(starter.getImage());
		this.imageStarter.setImage(starterImg);
		this.lvlStarter.setText(Integer.toString(starter.getDifficulty()) + "/5");
		
		Recipe main = session.getRandomMain();

		this.timeMain.setText(Integer.toString(main.getPreparationTime()) + " min");
		this.nameMain.setText(main.getNameRecipe());
		Image mainImg = new Image(main.getImage());
		this.imageMain.setImage(mainImg);
		this.lvlMain.setText(Integer.toString(main.getDifficulty()) + "/5");
		
		Recipe dessert = session.getRandomDessert();

		this.timeDessert.setText(Integer.toString(dessert.getPreparationTime()) + " min");
		this.nameDessert.setText(dessert.getNameRecipe());
		Image dessertImg = new Image(dessert.getImage());
		this.imageDessert.setImage(dessertImg);
		this.lvlDessert.setText(Integer.toString(dessert.getDifficulty()) + "/5");
		
		

	}
	

    @FXML
    public void changeAll(Event event) {
    	randomFacade.changeRandomStarter();
    	randomFacade.changeRandomMain();
    	randomFacade.changeRandomDessert();
    	initialize(null,null);
    }
    
    @FXML
    void changeRandomStarter(Event event) {
    	randomFacade.changeRandomStarter();
    	initialize(null,null);
    }
    
    @FXML
    void changeRandomMain(Event event) {
    	randomFacade.changeRandomMain();
    	initialize(null,null);
    }
    
    @FXML
    void changeRandomDessert(Event event) {
    	randomFacade.changeRandomDessert();
    	initialize(null,null);
    }

}
