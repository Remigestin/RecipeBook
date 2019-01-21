package controller;

/**
 * This class is the controller of the MyRecipes view
 * MyRecipes view displays the list of all the recipes created by the user logged in
 * 
 * @author Chawaf Alia
 * @version 1.0 
 */

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import businessLogic.Recipe;
import businessLogic.User;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MyRecipesController implements Initializable {

	@FXML
	private ImageView createRecipe;

	@FXML
	private TableView<RecipeWithButton> listRecipes;

	@FXML
	private TableColumn<RecipeWithButton, String> recipeName;

	@FXML
	private TableColumn<RecipeWithButton, String> courseCategory;

	@FXML
	private TableColumn<RecipeWithButton, String> rating;

	@FXML
	private TableColumn<RecipeWithButton, Integer> preparationTime;

	@FXML
	private TableColumn<RecipeWithButton, Integer> difficulty;

	@FXML
	private TableColumn<RecipeWithButton, String> edit;

	@FXML
	private ComboBox<String> filter;

	private RecipeFacade facade = RecipeFacade.getInstance();

	/**
	 * List of all the recipes created to add in the table
	 * 
	 * @see RecipeWithButton
	 */
	private ObservableList<RecipeWithButton> myRecipes = FXCollections.observableArrayList();

	/**
	 * Set the choices of the filter (ComboBox) with all the course categories
	 * existing in DB
	 * 
	 * @see ComboBox
	 */
	public void setCourseCategoryFilter() {

		HashMap<Integer, String> courses = facade.findAllCourseCategory();
		ObservableList<String> choices = FXCollections.observableArrayList(courses.values());
		choices.add(0, "all recipes");
		filter.setItems(choices);
	}

	/**
	 * 
	 * @param event
	 */
	@FXML
	void addRecipe(Event event) {

		Parent root;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RecipeCreatingFormPage.fxml"));

			root = loader.load();
			Scene scene = new Scene(root, 1920, 1080);
			Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			newStage.setScene(scene);
			newStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The TableView needs an ObservableList<RecipeWithButton> to print all the
	 * favorites.
	 * 
	 * This method changes the user's created recipes list to an
	 * ObservableList<RecipeWithButton>
	 * 
	 * @return the list of created recipes
	 */
	public ObservableList<RecipeWithButton> getAllRecipes() {

		myRecipes.clear();
		for (Recipe r : User.getSession().getCreateList()) {

			myRecipes.add(new RecipeWithButton(r.getIdRecipe(), r.getNameRecipe(), r.getPreparationTime(),
					r.getDifficulty(), r.getIdCourse(), "edit"));
		}
		return myRecipes;
	}

	/**
	 * The TableView needs a ObservableList<RecipeWithButton> to print all the
	 * favorites. This methods changes the user's recipes list to an
	 * ObservableList<RecipeWithButton> depending on filter selection
	 * 
	 * @param idCourseSelected id of the course selected in the filter
	 * @return the list of recipes created with a course category corresponding to
	 *         the selection in the filter
	 * @see RecipeWithButton
	 */
	public ObservableList<RecipeWithButton> getRecipesByFilterCourseSelected(int idCourseSelected) {

		myRecipes.clear();
		for (Recipe r : User.getSession().getCreateList()) {

			if (r.getIdCourse() == idCourseSelected)

				myRecipes.add(new RecipeWithButton(r.getIdRecipe(), r.getNameRecipe(), r.getPreparationTime(),
						r.getDifficulty(), r.getIdCourse(), "edit"));
		}

		return myRecipes;
	}

	/**
	 * Method called when clicking on a recipe of the table. Redirect to a new page
	 * displaying the recipe concerned with all its information
	 * 
	 * @param event
	 * @see RecipeController
	 */
	@FXML
	void consultRecipe(Event event) {

		Parent root;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RecipePage.fxml"));

			root = loader.load();

			RecipeController controller = loader.getController();

			int rowNumber = ((TableView) event.getSource()).getSelectionModel().selectedIndexProperty().get();
			controller.setIdRecipe(myRecipes.get(rowNumber).getIdRecipe());
			controller.consultRecipe();

			Scene scene = new Scene(root, 1920, 1080);

			Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

			newStage.setScene(scene);
			newStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Display only the recipes with the course category selected in the filter
	 * 
	 * @param event
	 */
	@FXML
	private void filterRecipesByCourseCategory(Event event) {

		if (filter.getValue().equals("all recipes")) {
			listRecipes.setItems(getAllRecipes());

		} else {
			int idCourseSelected = getIdCourseByCourseNameSelected();
			listRecipes.setItems(getRecipesByFilterCourseSelected(idCourseSelected));
		}

		listRecipes.refresh();
	}

	/**
	 * 
	 * @return the id of the course selected in the filter ComboBox
	 * @see ComboBox
	 */
	private int getIdCourseByCourseNameSelected() {

		HashMap<Integer, String> allCourses = facade.findAllCourseCategory();

		int idCourseSelected = 0;

		for (Object id : allCourses.keySet()) {
			if (allCourses.get(id).equals(filter.getValue())) {
				idCourseSelected = (int) id;
			}
		}

		return idCourseSelected;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see #getAllRecipes()
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		recipeName.setCellValueFactory(new PropertyValueFactory<RecipeWithButton, String>("nameRecipe"));
		courseCategory.setCellValueFactory(new PropertyValueFactory<RecipeWithButton, String>("course"));
		rating.setCellValueFactory(new PropertyValueFactory("rate"));
		preparationTime.setCellValueFactory(new PropertyValueFactory("preparationTime"));
		difficulty.setCellValueFactory(new PropertyValueFactory("difficulty"));
		edit.setCellValueFactory(new PropertyValueFactory<RecipeWithButton, String>("editButton"));

		listRecipes.setItems(getAllRecipes());

		setCourseCategoryFilter();
	}
}