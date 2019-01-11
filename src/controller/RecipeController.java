package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import businessLogic.CookingStep;
import businessLogic.Recipe;
import facade.RecipeFacade;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

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

	/* Getters Setters */
	public void setIdRecipe(int idRecipe) {
		this.idRecipe = idRecipe;
	}

	public void setNameRecipe(String nameRecipe) {
		this.nameRecipe.setText(nameRecipe);
	}

	public void setMainCourse(String mainCourse) {
		this.mainCourse.setText(mainCourse);
	}

	public void setRating(String rating) {
		this.rating.setText(rating);
	}

	public void setPreparationTime(String preparationTime) {
		this.preparationTime.setText(preparationTime + "  min");
	}

	public void setDifficulty(String difficulty) {
		this.difficulty.setText(difficulty + " / 5");
	}

	public void setNumberPeople(int numberPeople) {
		this.numberPeople.getValueFactory().setValue(numberPeople);
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

	/* initialize the spinner of numberPeople with integers */
	private void initSpinner() {
		numberPeople.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
	}

	/* methods */
	public void consultRecipe(Event e) {

		/* find and set cooking steps */
		this.setCookingSteps(facade.findCookingSteps(idRecipe));

		/* find recipe details and set them */
		Recipe recipe = facade.findRecipe(idRecipe);

		this.setNumberPeople(recipe.getNbPersRecipe());
		this.setPreparationTime(Integer.toString(recipe.getPreparationTime()));
		// this.setDifficulty(recipe.getDifficulty());
		// this.setRating(recipe.getRating());

		/* find main course and set it */

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		initSpinner();
	}

}
