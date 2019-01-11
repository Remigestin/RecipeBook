package controller;

import java.net.URL;
import java.util.ResourceBundle;

import businessLogic.Recipe;
import businessLogic.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class MyRecipesController implements Initializable {

	@FXML
	private TableView<Recipe> listRecipes;

	@FXML
	private TableColumn<Recipe, String> recipeName;

	@FXML
	private TableColumn<Recipe, Integer> prepTime;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		recipeName.setCellValueFactory(new PropertyValueFactory<Recipe, String>("teeeeeeees"));
		
		listRecipes.setItems(getRecipes());
		
	}

	public ObservableList<Recipe> getRecipes(){
		
		ObservableList<Recipe> recipes = FXCollections.observableArrayList();
		
		recipes.addAll(User.getSession().getCreateList());
		
		return recipes;
		
	}
	
	
}
