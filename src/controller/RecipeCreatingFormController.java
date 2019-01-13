package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class RecipeCreatingFormController implements Initializable {

	/* FXML elements */
	@FXML
	private TextField nameRecipe;
	@FXML
	private ComboBox<String> courseCategory;
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

	@FXML
    private TextArea descStep1;
    @FXML
    private TextField nameStep1;
    @FXML
    private TextArea descStep2;
    @FXML
    private TextField nameStep2;
    @FXML
    private TextArea descStep3;
    @FXML
    private TextField nameStep3;
    @FXML
    private TextArea descStep4;
    @FXML
    private TextField nameStep4;
    @FXML
    private TextArea descStep5;
    @FXML
    private TextField nameStep5;
    
	public void setCourseCategory() {

		HashMap<Integer, String> courses = facade.findAllCourseCategory();
		ObservableList<String> choices = FXCollections.observableArrayList(courses.values());
		courseCategory.setItems(choices);
	}

	private RecipeFacade facade = RecipeFacade.getInstance();

	/* initialize the spinners with integers */
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
	void submitForm(Event event) {

		if (nameRecipe.getText().equals("")) {
			displayErrorNameRecipeMissing(event);
		}

		else if (courseCategory.getValue() == null) {
			displayErrorCourseCategoryMissing(event);
		}
		
		else {
			this.createRecipe(event);
		}
	}

	@FXML
	void createRecipe(Event event) {

		Recipe recipe = new Recipe();

		// Set recipe information
		recipe.setNameRecipe(nameRecipe.getText());

		if (!preparationTime.getText().equals("")) {
			recipe.setPreparationTime(Integer.parseInt(preparationTime.getText()));
		}

		recipe.setNbPersRecipe((Integer) numberPeople.getValue());
		recipe.setDifficulty((Integer) difficulty.getValue());
		recipe.setImage(image.getText());

		// Get id of course category selected and set it in the recipe created

		if (getIdCourseByCourseNameSelected() != 0) {
			recipe.setIdCourse(getIdCourseByCourseNameSelected());
		}

		// Add in DB the cooking steps of the recipe created
		facade.createRecipe(recipe, getListCookingStepEntered());

		// Display confirmation message if recipe is created and added
		this.displayConfirmationCreation(event);

	}
	
	private ArrayList<CookingStep> getListCookingStepEntered() {
		ArrayList<CookingStep> listNewSteps = new ArrayList<CookingStep>();

		CookingStep newStep1 = new CookingStep(nameStep1.getText(), descStep1.getText());
		CookingStep newStep2 = new CookingStep(nameStep2.getText(), descStep2.getText());
		CookingStep newStep3 = new CookingStep(nameStep3.getText(), descStep3.getText());
		CookingStep newStep4 = new CookingStep(nameStep4.getText(), descStep4.getText());
		CookingStep newStep5 = new CookingStep(nameStep5.getText(), descStep5.getText());

		listNewSteps.add(newStep1);
		listNewSteps.add(newStep2);
		listNewSteps.add(newStep3);
		listNewSteps.add(newStep4);
		listNewSteps.add(newStep5);

//		for (int i = 0; i < listNewSteps.size(); i++) {
//
//			if (listNewSteps.get(i).getDescription().equals("") && listNewSteps.get(i).getDescription().equals("")) {
//				listNewSteps.remove(i);
//			}
//		}
//		
		return listNewSteps;
	}

	private void displayConfirmationCreation(Event event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Your recipe " + nameRecipe.getText() + " has been created and added to your list ! ");
		alert.showAndWait();

		// Come back to myRecipes page with the new recipe created
		this.redirectToMyRecipes(event);
	}
	
	private void displayErrorNameRecipeMissing(Event event) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Hey Stop there");
		alert.setHeaderText("Recipe name is missing. Enter one !");
		alert.showAndWait();
	}

	private void displayErrorCourseCategoryMissing(Event event) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Hey Stop there");
		alert.setHeaderText("Course category is missing. Choose one !");
		alert.showAndWait();
	}

	private int getIdCourseByCourseNameSelected() {

		HashMap<Integer, String> allCourses = facade.findAllCourseCategory();

		int idCourseSelected = 0;

		for (Object id : allCourses.keySet()) {
			if (allCourses.get(id).equals(courseCategory.getValue())) {
				idCourseSelected = (int) id;
			}
		}

		return idCourseSelected;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initSpinner();
		setCourseCategory();
	}
}
