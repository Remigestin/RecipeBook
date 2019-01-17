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

	public ObservableList<RecipeWithButton> getRecipes() {

		recipes.clear();
		for (Recipe r : favorites) {

			recipes.add(new RecipeWithButton(r.getIdRecipe(), r.getNameRecipe(), r.getPreparationTime(),
					r.getDifficulty(), "remove"));

		}
		

		return recipes;

	}
	

    @FXML
    void deleteFavoriteRecipe(Event event) {
    	
    	RecipeFacade recipe_facade = RecipeFacade.getInstance();
    	favorites = facade.removeFavoriteRecipe(User.getSession().getId(),idRecipe);
    	favoritesTab.setItems(getRecipes());	
    	favoritesTab.refresh();
    	Recipe r = recipe_facade.findRecipe(idRecipe);
    	displayDeleteConfirmation(r);
    	
    	
    }
    
    private void displayDeleteConfirmation(Recipe r) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("The recipe "+r.getNameRecipe()+" has been removed from your favorites ! ");
		alert.showAndWait();

	}
	
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



