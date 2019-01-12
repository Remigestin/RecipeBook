package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
	private TableView<Recipe> listRecipes;

	@FXML
	private TableColumn<Recipe, String> recipeName;

	@FXML
	private TableColumn<Recipe, String> rating;

	@FXML
	private TableColumn<Recipe, Integer> preparationTime;

	@FXML
	private TableColumn<Recipe, Integer> difficulty;

	@FXML
	private ComboBox<String> filter;

	private RecipeFacade facade = RecipeFacade.getInstance();

	public void setFilter() {

		HashMap<Integer, String> courses = facade.findAllCourseCategory();
		ObservableList<String> choices = FXCollections.observableArrayList(courses.values());
		choices.add(0, "all recipes");
		filter.setItems(choices);
	}

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

	public ObservableList<Recipe> getAllRecipes() {

		ObservableList<Recipe> recipes = FXCollections.observableArrayList();
		recipes.addAll(User.getSession().getCreateList());
		return recipes;

	}

	public ObservableList<Recipe> getRecipesByFilterCourseSelected(int idCourseSelected) {

		ObservableList<Recipe> recipesFiltered = FXCollections.observableArrayList();

		for (Recipe r : User.getSession().getCreateList()) {

			if (r.getIdCourse() == idCourseSelected)
				recipesFiltered.add(r);
		}

		return recipesFiltered;

	}

	@FXML
	void consultRecipe(Event event) {

		Parent root;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RecipePage.fxml"));

			root = loader.load();

			RecipeController controller = loader.getController();

			int rowNumber = ((TableView) event.getSource()).getSelectionModel().selectedIndexProperty().get();
			int idRecipe = this.getIdRecipeByRowNumber(rowNumber);
			controller.setIdRecipe(idRecipe);
			controller.consultRecipe();

			Scene scene = new Scene(root, 1920, 1080);

			Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

			newStage.setScene(scene);
			newStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getIdRecipeByRowNumber(int rowNumber) {

		return User.getSession().getCreateList().get(rowNumber).getIdRecipe();
	}

	@FXML
	void filterRecipesByCourseCategory(Event event) {

		if (filter.getValue().equals("all recipes")) {
			listRecipes.setItems(getAllRecipes());
			
		} else {
			int idCourseSelected = getIdCourseByCourseNameSelected();
			listRecipes.setItems(getRecipesByFilterCourseSelected(idCourseSelected));
		}
		
		listRecipes.refresh();
	}

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		recipeName.setCellValueFactory(new PropertyValueFactory<Recipe, String>("nameRecipe"));
		// rating.setCellValueFactory(new PropertyValueFactory<Recipe, String>(""));
		preparationTime.setCellValueFactory(new PropertyValueFactory("preparationTime"));
		difficulty.setCellValueFactory(new PropertyValueFactory("difficulty"));
		listRecipes.setItems(getAllRecipes());

		setFilter();
	}
}
