package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import businessLogic.Recipe;
import businessLogic.User;
import facade.FavoritesFacade;
import facade.RecipeFacade;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

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
		
	}
	
	/*attributes*/
	private FavoritesFacade facade = FavoritesFacade.getInstance();
	
	/*methods*/
	
	//in order to test
    @FXML
    void addFavoriteRecipe(ActionEvent event) {
    	favorites = facade.addFavoriteRecipe(User.getSession().getId(),idRecipe);

    }

  //in order to test
    @FXML
    void deleteFavoriteRecipe(ActionEvent event) {
    	//to do
    }
	

}



