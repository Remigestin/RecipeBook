package controller;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;

public class CommonThemeController implements ControllerInterface {
	@FXML
	private Label username;
	@FXML
	private HBox home;
	@FXML
	private HBox advancedSearch;
	@FXML
	private HBox myRecipes;
	@FXML
	private HBox favorites;
	@FXML
	private HBox randomMenu;
	@FXML
	private HBox shoppingList;

	// Event Listener on HBox[#home].onMousePressed
	// Event Listener on HBox[#advancedSearch].onMousePressed
	// Event Listener on HBox[#myRecipes].onMousePressed
	// Event Listener on HBox[#favorites].onMousePressed
	// Event Listener on HBox[#randomMenu].onMousePressed
	// Event Listener on HBox[#shoppingList].onMousePressed
	@FXML
	public void redirectToNewTab(MouseEvent event) {

		String newPage = null;

		switch (event.getSource().toString()) {

			case "HBox[id=home]":
				newPage = "/views/HomePage.fxml";
				break;
	
			case "HBox[id=advancedSearch]":
				// newPage = "/views/AdvancedSearch.fxml";
				break;
	
			case "HBox[id=myRecipes]":
				// newPage = "/views/MyRecipes.fxml";
				break;
	
			case "HBox[id=favorites]":
				// newPage = "/views/Favorites.fxml";
				break;
	
			case "HBox[id=randomMenu]":
				// newPage = "/views/RandomMenu.fxml";
				break;
	
			case "HBox[id=shoppingList]":
				// newPage = "/views/ShoppingList.fxml";
				break;
		}

		this.switchToNewPage(event, newPage);
	}

	@Override
	public void setUsername(String username) {
		this.username.setText(username);
	}
	
	@Override
	public void switchToNewPage(Event event, String newPage) {
		
		Parent root;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(newPage));

			root = loader.load();

			ControllerInterface newController = loader.getController();
			newController.setUsername(username.getText());

			Scene scene = new Scene(root, 1920, 1080);

			Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

			newStage.setScene(scene);
			newStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}