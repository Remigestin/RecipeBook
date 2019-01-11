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
import javafx.scene.input.MouseEvent;

public class MyRecipesController implements Initializable {

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		recipeName.setCellValueFactory(new PropertyValueFactory<Recipe, String>("nameRecipe"));
		// rating.setCellValueFactory(new PropertyValueFactory<Recipe, String>(""));
		preparationTime.setCellValueFactory(new PropertyValueFactory("preparationTime"));
		difficulty.setCellValueFactory(new PropertyValueFactory("difficulty"));
		listRecipes.setItems(getRecipes());

	}

	public ObservableList<Recipe> getRecipes() {

		ObservableList<Recipe> recipes = FXCollections.observableArrayList();

		recipes.addAll(User.getSession().getCreateList());

		return recipes;

	}

	@FXML
	void test(MouseEvent event) {

		System.out.print(((TableView) event.getSource()).getSelectionModel().selectedIndexProperty().get());

	}

}
