package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import businessLogic.Recipe;
import businessLogic.User;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class FavoritesController implements Initializable {

	@FXML
	private CommonThemeController commonThemeController = new CommonThemeController();
	
    @FXML
    private TextArea text;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//print the list of favorite recipes
		String s ="";
		ArrayList<Recipe> favorites = User.getSession().getFavoriteList();
		
		for(Recipe r : favorites) {
			s += r.getNameRecipe()+"\n";
		}
		text.setText(s);
		
	}
	

}



