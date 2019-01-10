package controller;

import java.net.URL;
import java.util.ResourceBundle;

import facade.LoginPageFacade;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import javafx.scene.control.ListView;

import javafx.scene.control.TextArea;

import javafx.scene.image.ImageView;

import javafx.scene.control.Spinner;

public class RecipeController implements Initializable {
	@FXML
	private Label nameRecipe;
	@FXML
	private Label mainCourse;
	@FXML
	private TextArea ingredients;
	@FXML
	private ListView cookingSteps;
	@FXML
	private Label rating;
	@FXML
	private Label preparationTime;
	@FXML
	private Label difficulty;
	@FXML
	private Spinner numberPeople;
	@FXML
	private ImageView addToFavorite;
	@FXML
	private ImageView addToCart;
	
	//private RecipeFacade facade = RecipeFacade.getInstance(); 
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		cookingSteps.getItems().add("test");

		//disable selecting items from the list
		cookingSteps.setMouseTransparent( true ); 
		cookingSteps.setFocusTraversable( false );
		
	}

	
	
}
