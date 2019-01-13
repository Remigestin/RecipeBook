package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

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
import javafx.event.ActionEvent;
import javafx.event.Event;

import javafx.scene.control.TextArea;

import javafx.scene.control.ComboBox;

import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class RecipeEditingFormController implements Initializable {
	@FXML
	private TextArea ingredients;
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
	@FXML
	private TextField preparationTime;
	@FXML
	private Spinner difficulty;
	@FXML
	private Spinner numberPeople;
	@FXML
	private TextField nameRecipe;
	@FXML
	private Button create;
	@FXML
	private TextField image;
	@FXML
	private Button cancel;
	@FXML
	private ComboBox courseCategory;

	private int idRecipe;
	
	private CookingStep step1 = new CookingStep();
	private CookingStep step2 = new CookingStep();
	private CookingStep step3 = new CookingStep();
	private CookingStep step4 = new CookingStep();
	private CookingStep step5 = new CookingStep();

	private RecipeFacade facade = RecipeFacade.getInstance();

	public void setCourseCategory() {

		HashMap<Integer, String> courses = facade.findAllCourseCategory();
		ObservableList<String> choices = FXCollections.observableArrayList(courses.values());
		courseCategory.setItems(choices);
	}

	public void setIdRecipe(int idRecipe) {
		this.idRecipe = idRecipe;
	}

	public void setNameRecipe(String nameRecipe) {
		this.nameRecipe.setText(nameRecipe);
	}

	public void setIngredients(String ingredients) {
		this.ingredients.setText(ingredients);
	}

	public void setDescStep1(String descStep1) {
		this.descStep1.setText(descStep1);
	}

	public void setNameStep1(String nameStep1) {
		this.nameStep1.setText(nameStep1);
	}

	public void setDescStep2(String descStep2) {
		this.descStep2.setText(descStep2);
	}

	public void setNameStep2(String nameStep2) {
		this.nameStep2.setText(nameStep2);
	}

	public void setDescStep3(String descStep3) {
		this.descStep3.setText(descStep3);
	}

	public void setNameStep3(String nameStep3) {
		this.nameStep3.setText(nameStep3);
	}

	public void setDescStep4(String descStep4) {
		this.descStep4.setText(descStep4);
	}

	public void setNameStep4(String nameStep4) {
		this.nameStep4.setText(nameStep4);
	}

	public void setDescStep5(String descStep5) {
		this.descStep5.setText(descStep5);
	}

	public void setNameStep5(String nameStep5) {
		this.nameStep5.setText(nameStep5);
	}

	public void setPreparationTime(String preparationTime) {
		this.preparationTime.setText(preparationTime);
	}

	public void setDifficulty(int difficulty) {
		this.difficulty.getValueFactory().setValue(difficulty);
	}

	public void setNumberPeople(int numberPeople) {
		this.numberPeople.getValueFactory().setValue(numberPeople);
	}

	public void setImage(String image) {
		this.image.setText(image);
	}

	public void setCourseCategory(String courseCategory) {
		this.courseCategory.setValue(courseCategory);
	}

	public void setAllRecipeInformation() {

		Recipe newRecipe = facade.findRecipe(idRecipe);

		this.setNameRecipe(newRecipe.getNameRecipe());
		// this.setIngredients();

		ArrayList<CookingStep> cookingSteps = facade.findCookingSteps(idRecipe);

		step1.setIdCookingStep(cookingSteps.get(0).getIdCookingStep());
		step2.setIdCookingStep(cookingSteps.get(1).getIdCookingStep());
		step3.setIdCookingStep(cookingSteps.get(2).getIdCookingStep());
		step4.setIdCookingStep(cookingSteps.get(3).getIdCookingStep());
		step5.setIdCookingStep(cookingSteps.get(4).getIdCookingStep());
				
		this.setNameStep1(cookingSteps.get(0).getName());
		this.setDescStep1(cookingSteps.get(0).getDescription());
		
		this.setNameStep2(cookingSteps.get(1).getName());
		this.setDescStep2(cookingSteps.get(1).getDescription());

		this.setNameStep3(cookingSteps.get(2).getName());
		this.setDescStep3(cookingSteps.get(2).getDescription());

		this.setNameStep4(cookingSteps.get(3).getName());
		this.setDescStep4(cookingSteps.get(3).getDescription());

		this.setNameStep5(cookingSteps.get(4).getName());
		this.setDescStep5(cookingSteps.get(4).getDescription());

		this.setDifficulty(newRecipe.getDifficulty());
		this.setPreparationTime(Integer.toString(newRecipe.getPreparationTime()));
		this.setNumberPeople(newRecipe.getNbPersRecipe());
		this.setImage(newRecipe.getImage());
		this.setCourseCategory(facade.findCourseCategoryName(newRecipe.getIdCourse()));
	}

	/* initialize the spinners with integers */
	private void initSpinner() {
		numberPeople.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
		difficulty.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5));
	}

	// Event Listener on Button[#create].onAction
	@FXML
	public void submitEdits(ActionEvent event) {

		// create the recipe edited 
		int idCourseSelected = this.getIdCourseByCourseNameSelected();
		Recipe recipeEdited = new Recipe(idRecipe, nameRecipe.getText(), Integer.parseInt(preparationTime.getText()),
				(int) numberPeople.getValue(), image.getText(), idCourseSelected, (int) difficulty.getValue());

		// put new cooking steps in a list	
		step1.setName(nameStep1.getText());
		step1.setDescription(descStep1.getText());
		
		step2.setName(nameStep2.getText());
		step2.setDescription(descStep2.getText());
		
		step3.setName(nameStep3.getText());
		step3.setDescription(descStep3.getText());
		
		step4.setName(nameStep4.getText());
		step4.setDescription(descStep4.getText());
		
		step5.setName(nameStep5.getText());
		step5.setDescription(descStep5.getText());

		ArrayList<CookingStep> cookingStepsEdited = new ArrayList<CookingStep>();
		cookingStepsEdited.add(step1);
		cookingStepsEdited.add(step2);
		cookingStepsEdited.add(step3);
		cookingStepsEdited.add(step4);
		cookingStepsEdited.add(step5);
		
		// update recipe and cooking steps in DB
		facade.editRecipe(recipeEdited, cookingStepsEdited);

		// Display confirmation message if recipe is created and added
		this.displayConfirmationEdit(event);
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

	private void displayConfirmationEdit(Event event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Your recipe has been well edited ! ");
		alert.showAndWait();

		// Come back to myRecipes page with the new recipe created
		this.redirectToMyRecipes(event);
	}

	// Event Listener on Button[#cancel].onAction
	@FXML
	public void redirectToMyRecipes(Event event) {
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		initSpinner();
		setCourseCategory();
	}
}
