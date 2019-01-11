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

public class CommonThemeAdminController {
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
	@FXML
	private HBox admin;

	// Event Listener on HBox[#home].onMousePressed
	// Event Listener on HBox[#advancedSearch].onMousePressed
	// Event Listener on HBox[#myRecipes].onMousePressed
	// Event Listener on HBox[#favorites].onMousePressed
	// Event Listener on HBox[#randomMenu].onMousePressed
	// Event Listener on HBox[#shoppingList].onMousePressed
	// Event Listener on HBox[#admin].onMousePressed
	@FXML
	public void redirectToNewTab(MouseEvent event) {

		String newPage = null;
		
		switch (((HBox)event.getSource()).getId()) {

			case "home":
				newPage = "/views/HomePage.fxml";
				break;
	
			case "advancedSearch":
				// newPage = "/views/AdvancedSearch.fxml";
				break;
	
			case "myRecipes":
				// newPage = "/views/MyRecipes.fxml";
				break;
	
			case "favorites":
				// newPage = "/views/Favorites.fxml";
				break;
	
			case "randomMenu":
				// newPage = "/views/RandomMenu.fxml";
				break;
	
			case "shoppingList":
				// newPage = "/views/ShoppingList.fxml";
				break;
				
			case "admin":
				newPage = "/views/admin/AdminMode.fxml";
				break;
		}

		this.switchToNewPage(event, newPage);
	}
	
	public String getUsername() {
		return this.username.getText();
	}

	public void setUsername(String username) {
		this.username.setText(username);
	}
	
	public void switchToNewPage(Event event, String newPage) {
		
		Parent root;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(newPage));

			root = loader.load();

			//ControllerInterface newController = loader.getController();
			//newController.setUsername(username.getText());

			Scene scene = new Scene(root, 1920, 1080);

			Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

			newStage.setScene(scene);
			newStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}