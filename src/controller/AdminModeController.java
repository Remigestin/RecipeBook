package controller;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AdminModeController {
	@FXML
	private CommonThemeAdminController commonThemeAdminController;

	@FXML
	private Label users;
	@FXML
	private Label ingredients;
	@FXML
	private Label recipes;
	@FXML
	private Label diets;

	// Event Listener on Label[#users].onMousePressed
	// Event Listener on Label[#ingredients].onMousePressed
	// Event Listener on Label[#recipes].onMousePressed
	// Event Listener on Label[#diets].onMousePressed
	@FXML
	public void redirectToNewPage(MouseEvent event) {

		String newPage = null;

		switch (((Label) event.getSource()).getText()) {

		case "Users":
			newPage = "/views/admin/UsersAdmin.fxml";
			break;

		case "Recipes":
			newPage = "/views/admin/RecipesAdmin.fxml";
			break;

		case "Diets":
			newPage = "/views/admin/DietsAdmin.fxml";
			break;

		case "Ingredients":
			newPage = "/views/admin/IngredientsAdmin.fxml";
			break;
		}

		this.switchToNewPage(event, newPage);
	}

	public void switchToNewPage(Event event, String newPage) {

		Parent root;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(newPage));

			root = loader.load();

			//ControllerInterface newController = loader.getController();
			//String username = this.commonThemeAdminController.getUsername();
			//newController.setUsername(username);

			Scene scene = new Scene(root, 1920, 1080);

			Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

			newStage.setScene(scene);
			newStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void setUsername(String username) {
		this.commonThemeAdminController.setUsername(username);
	}

}
