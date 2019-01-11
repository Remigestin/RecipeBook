package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import businessLogic.CookingStep;
import facade.LoginPageFacade;
import facade.RecipeFacade;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import javafx.scene.control.ListView;

import javafx.scene.control.TextArea;

import javafx.scene.image.ImageView;

import javafx.scene.control.Spinner;

public class RecipeController implements Initializable {

	/* FXML elements */
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

	/* attributes */
	private RecipeFacade facade = RecipeFacade.getInstance();

	private int idRecipe;

	public void setIdRecipe(int idRecipe) {
		this.idRecipe = idRecipe;
	}

	public void setCookingSteps(ArrayList<CookingStep> cookingSteps) {

		int numberStep = 1;

		for (CookingStep c : cookingSteps) {

			if (c.getName() != null) {

				this.cookingSteps.getItems().add(numberStep + ". " + c.getName());
				this.cookingSteps.getItems().add(c.getDescription());

			} else {

				this.cookingSteps.getItems().add(numberStep + ". " + c.getDescription());

			}

			this.cookingSteps.getItems().add(""); // skip a line between each step
			numberStep++;
		}

	}

	public void consultRecipe() {

		/* find and set cooking steps */
		this.setCookingSteps(facade.findCookingSteps(1));

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

}
