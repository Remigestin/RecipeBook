package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import businessLogic.CookingStep;
import businessLogic.Recipe;
import facade.RecipeFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RecipeCreatingFormController implements Initializable {

	/* FXML elements */
	@FXML
	private TextField nameRecipe;
	@FXML
	private ChoiceBox<String> courseCategory;
	@FXML
	private TextField image;
	@FXML
	private TextArea ingredients;
	@FXML
	private ListView cookingSteps;
	@FXML
	private TextField preparationTime;
	@FXML
	private Spinner difficulty;
	@FXML
	private Spinner numberPeople;
	@FXML
	private Button create;

	public void setCourseCategory() {

		ObservableList<String> choices = FXCollections.observableArrayList("apples", "oranges");
		courseCategory.setItems(choices);
	}

	private RecipeFacade facade = RecipeFacade.getInstance();

	/* initialize the spinner of numberPeople with integers */
	private void initSpinner() {
		numberPeople.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
		difficulty.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5));
	}

	@FXML
	void redirectToMyRecipes(Event event) {

		Parent root;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MyRecipes.fxml"));

			root = loader.load();

			Scene scene = new Scene(root, 1920, 1080);

			Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

			newStage.setScene(scene);
			newStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@FXML
	void createRecipe(Event event) {

		Recipe recipe = new Recipe();

		recipe.setNameRecipe(nameRecipe.getText());
		recipe.setPreparationTime(Integer.parseInt(preparationTime.getText()));
		recipe.setNbPersRecipe(Integer.parseInt((String) numberPeople.getValue()));
		recipe.setDifficulty(Integer.parseInt((String) difficulty.getValue()));
		recipe.setImage(image.getText());

		// recipe.setIdCourse(courseCategory);

		ArrayList<CookingStep> steps = new ArrayList<CookingStep>();
		facade.createRecipe(recipe, steps);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initSpinner();
		setCourseCategory();
	}

}
