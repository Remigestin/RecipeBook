package controller;

/**
 * This class is the controller of the QuickSearchResults view
 * 
 * This view display all the results of a quick search
 * 
 * @author Chawaf Alia
 * @version 1.0 
 */


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.input.MouseEvent;

import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;

public class QuickSearchController implements Initializable {
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
	private Text nbResult;
	
	private ArrayList<RecipeWithButton> results = new ArrayList<RecipeWithButton>();

	public void setNbResult(String nbResult) {
		this.nbResult.setText(nbResult);
	}
	public void setResults(ArrayList<RecipeWithButton> results) {
		this.results = results;
	}

	/**
	 * Set the list of recipes found with the search in the table
	 * 
	 */
	public void setResultsInTableView() {
		listRecipes.setItems(FXCollections.observableArrayList(results));
		setNbResult(Integer.toString(listRecipes.getItems().size()));
	}
	
	/**
	 * Method called when clicking on a recipe of the result table. 
	 * Redirect to a new page displaying the recipe concerned with all its information
	 * 
	 * @param event
	 * @see RecipeController
	 */
	@FXML
	public void consultRecipe(MouseEvent event) {
		Parent root;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RecipePage.fxml"));

			root = loader.load();

			RecipeController controller = loader.getController();

			int rowNumber = ((TableView) event.getSource()).getSelectionModel().selectedIndexProperty().get();
			controller.setIdRecipe(listRecipes.getItems().get(rowNumber).getIdRecipe());
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
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		recipeName.setCellValueFactory(new PropertyValueFactory<RecipeWithButton, String>("nameRecipe"));
		courseCategory.setCellValueFactory(new PropertyValueFactory<RecipeWithButton, String>("course"));
		rating.setCellValueFactory(new PropertyValueFactory("rate"));
		preparationTime.setCellValueFactory(new PropertyValueFactory("preparationTime"));
		difficulty.setCellValueFactory(new PropertyValueFactory("difficulty"));		
	}
}
