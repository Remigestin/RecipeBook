package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import businessLogic.Recipe;
import businessLogic.User;
import facade.FavoritesFacade;
import facade.RecipeFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
/**
 * 
 * @author MISSOUM BENZIANE Ines
 *
 */
public class FavoritesController implements Initializable {


	@FXML
	ArrayList<Recipe> favorites = User.getSession().getFavoriteList();

	@FXML
	int idRecipe;

	@FXML
	private CommonThemeController commonThemeController = new CommonThemeController();

	@FXML
	private TableView<RecipeWithButton> favoritesTab;

	@FXML
	private TableColumn<RecipeWithButton, String> recipeName;

	@FXML
	private TableColumn<RecipeWithButton, String> difficulty;

	@FXML
	private TableColumn<RecipeWithButton, String> rating;

	@FXML
	private TableColumn<RecipeWithButton, String> preparationTime;

	@FXML
	private TableColumn<RecipeWithButton, String> course;

	@FXML
	private TableColumn<RecipeWithButton, String> remove;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		recipeName.setCellValueFactory(new PropertyValueFactory<RecipeWithButton, String>("nameRecipe"));
		preparationTime.setCellValueFactory(new PropertyValueFactory("preparationTime"));
		difficulty.setCellValueFactory(new PropertyValueFactory("difficulty"));
		course.setCellValueFactory(new PropertyValueFactory<RecipeWithButton, String>("course"));
		rating.setCellValueFactory(new PropertyValueFactory("rate"));
		remove.setCellValueFactory(new PropertyValueFactory("editButton"));
		favoritesTab.setItems(getRecipes());
	}

	/*attributes*/

	private FavoritesFacade facade = FavoritesFacade.getInstance();
	private ObservableList<RecipeWithButton> recipes = FXCollections.observableArrayList();


	/*methods*/

	public int getIdRecipe() {
		return idRecipe;
	}

	public void setIdRecipe(int idRecipe) {
		this.idRecipe = idRecipe;
	}

	/**
	 * The TableView needs a ObservableList<RecipeWithButton> to print all the favorites. This methods changes the user's favorites list on an ObservableList<RecipeWithButton>
	 * @return an ObservableList of RecipeWithButton so it can be printed 
	 */
	public ObservableList<RecipeWithButton> getRecipes() {

		recipes.clear();
		for (Recipe r : favorites) {

			recipes.add(new RecipeWithButton(r.getIdRecipe(), r.getNameRecipe(), r.getPreparationTime(),
					r.getDifficulty(),r.getIdCourse(), "remove"));


		}

		return recipes;

	}

	/**
	 * When the user clicks on the button that allows him to remove a recipe from his/her favorites, this method is called.
	 * This method removes the recipe from the favorites list of the user, print the changes on the screen and a confirmation 
	 * @param event
	 */
	@FXML
	void deleteFavoriteRecipe(Event event) {

		RecipeFacade recipe_facade = RecipeFacade.getInstance();
		//removes the recipe from the favorites list of the user
		favorites = facade.removeFavoriteRecipe(User.getSession().getId(),idRecipe);
		//set the list changed to be printed
		favoritesTab.setItems(getRecipes());	
		favoritesTab.refresh();
		Recipe r = recipe_facade.findRecipe(idRecipe);
		//displays a confirmation
		displayDeleteConfirmation(r);


	}
	/**
	 * displays a message to confirm that the recipe is deleted from the favorites
	 * @param r the Recipe deleted to the favorites of the user 
	 */
	private void displayDeleteConfirmation(Recipe r) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("The recipe "+r.getNameRecipe()+" has been removed from your favorites ! ");
		alert.showAndWait();

	}

	/**
	 * This method is called when the user clicks on a recipe in the tab. 
	 * This method loads the view of the recipe, where I can have all the information of the recipe.
	 * @param event
	 */
	@FXML
	void consultRecipe(Event event) {

		Parent root;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RecipePage.fxml"));

			root = loader.load();

			RecipeController controller = loader.getController();

			int rowNumber = ((TableView) event.getSource()).getSelectionModel().selectedIndexProperty().get();
			controller.setIdRecipe(recipes.get(rowNumber).getIdRecipe());
			controller.consultRecipe();

			Scene scene = new Scene(root, 1920, 1080);

			Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

			newStage.setScene(scene);
			newStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}



