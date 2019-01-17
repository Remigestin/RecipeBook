package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.Rating;

import businessLogic.Recipe;
import facade.RecipeFacade;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import mySQLDAO.MySQLRecipeDAO;

public class HomePageController implements Initializable {
	@FXML
	private Rating rating;
	@FXML
	private Label preparationTime;
	@FXML
	private Label difficulty;
	@FXML
	private Label nbPeople;
	@FXML
	private Label nameRecipe;
	@FXML
	private ImageView image;

	private RecipeFacade facade = RecipeFacade.getInstance();

	public void setRating(float rating) {
		this.rating.setRating(rating);
	}

	public void setPreparationTime(String preparationTime) {
		this.preparationTime.setText(preparationTime);
	}

	public void setDifficulty(String difficulty) {
		this.difficulty.setText(difficulty);
	}

	public void setNbPeople(String nbPeople) {
		this.nbPeople.setText(nbPeople);
	}

	public void setNameRecipe(String nameRecipe) {
		this.nameRecipe.setText(nameRecipe);
	}

	public void setImage(String image) {
		this.image.setImage(new Image(image));
	}


	private void setTop1() {
		
		Recipe top1 = facade.findTop1Recipe();
		
		setDifficulty(Integer.toString(top1.getDifficulty()) + " / 5");
		setNameRecipe(top1.getNameRecipe());
		setNbPeople(Integer.toString(top1.getNbPersRecipe()));
		setPreparationTime(Integer.toString(top1.getPreparationTime()) + " min");
		setRating(MySQLRecipeDAO.findRating(top1.getIdRecipe()));
		setImage(top1.getImage());
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		this.setTop1();
	}
}
