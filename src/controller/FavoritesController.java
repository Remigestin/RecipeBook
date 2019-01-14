package controller;

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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;

public class FavoritesController implements Initializable {

	//in order to test
	@FXML
	ArrayList<Recipe> favorites = User.getSession().getFavoriteList();
	
	//in order to test
	@FXML
	int idRecipe = 1;
	
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
	    

    //in order to test
    @FXML
    private Button add;
    
  //in order to test
    @FXML
    private Button delete;

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
	
	/*methods*/
	
	public ObservableList<RecipeWithButton> getRecipes() {

		ObservableList<RecipeWithButton> recipes = FXCollections.observableArrayList();
		
		for (Recipe r : favorites) {

			recipes.add(new RecipeWithButton(r.getIdRecipe(), r.getNameRecipe(), r.getPreparationTime(),
					r.getDifficulty(), "remove"));

		}
		
		

		return recipes;

	}
	
	//in order to test
    @FXML
    void addFavoriteRecipe(ActionEvent event) {
    	favorites = facade.addFavoriteRecipe(User.getSession().getId(),idRecipe);
    	favoritesTab.setItems(getRecipes());
    	favoritesTab.refresh();
    	
    	
    }

  //in order to test
    @FXML
    void deleteFavoriteRecipe(ActionEvent event) {
    	facade.removeFavoriteRecipe(User.getSession().getId(),idRecipe);
    	favoritesTab.setItems(getRecipes());
    	favoritesTab.refresh();
    	
    }
	

}



