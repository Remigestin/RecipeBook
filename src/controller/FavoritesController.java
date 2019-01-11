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
	private TableView<Recipe> favoritesTab;
	
	@FXML
    private TableColumn<Recipe, String> recipeName;

    @FXML
    private TableColumn<Recipe, String> difficulty;

    @FXML
    private TableColumn<Recipe, String> preparationTime;

    @FXML
    private TableColumn<Recipe, String> course;
    
    @FXML
    private TextArea text;

    //in order to test
    @FXML
    private Button add;
    
  //in order to test
    @FXML
    private Button delete;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//print the list of favorite recipes
		String s ="";
		
		for(Recipe r : favorites) {
			s += r.getNameRecipe()+"\n";
		}
		text.setText(s);
		
		recipeName.setCellValueFactory(new PropertyValueFactory<Recipe, String>("nameRecipe"));
		preparationTime.setCellValueFactory(new PropertyValueFactory("preparationTime"));
		difficulty.setCellValueFactory(new PropertyValueFactory("difficulty"));
		favoritesTab.setItems(getRecipes());
	}
	
	/*attributes*/
	private FavoritesFacade facade = FavoritesFacade.getInstance();
	
	/*methods*/
	
	public ObservableList<Recipe> getRecipes() {

		ObservableList<Recipe> recipes = FXCollections.observableArrayList();

		recipes.addAll(favorites);

		return recipes;

	}
	
	//in order to test
    @FXML
    void addFavoriteRecipe(ActionEvent event) {
    	favorites = facade.addFavoriteRecipe(User.getSession().getId(),idRecipe);
    	//favoritesTab.refresh();
    	
    	
    }

  //in order to test
    @FXML
    void deleteFavoriteRecipe(ActionEvent event) {
    	facade.removeFavoriteRecipe(User.getSession().getId(),idRecipe);
    }
	

}



