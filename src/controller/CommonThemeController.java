package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import businessLogic.Recipe;
import businessLogic.User;
import facade.RecipeFacade;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class CommonThemeController implements Initializable {

	@FXML
	private TextField quickSearch;
	@FXML
	private Button submitSearch;
	@FXML
	private Label username;
	@FXML
	private ImageView usernameLogo;
	@FXML
	private HBox home;
	@FXML
	private HBox advancedSearch;
	@FXML
	private HBox myRecipes;
	@FXML
	private HBox favorite;
	@FXML
	private HBox randomMenu;
	@FXML
	private HBox shoppingList;
	
	private RecipeFacade facade = RecipeFacade.getInstance();

	// Event Listener on HBox[#home].onMousePressed
	// Event Listener on HBox[#advancedSearch].onMousePressed
	// Event Listener on HBox[#myRecipes].onMousePressed
	// Event Listener on HBox[#favorites].onMousePressed
	// Event Listener on HBox[#randomMenu].onMousePressed
	// Event Listener on HBox[#shoppingList].onMousePressed
	@FXML
	public void redirectToNewTab(MouseEvent event) {

		String newPage = null;

		switch (((HBox) event.getSource()).getId()) {

		case "home":
			newPage = "/views/HomePage.fxml";
			break;

		case "advancedSearch":
			// newPage = "/views/AdvancedSearch.fxml";
			break;

		case "myRecipes":
			newPage = "/views/MyRecipes.fxml";
			break;

		case "favorite":
			newPage = "/views/Favorites.fxml";
			break;

		case "randomMenu":
			newPage = "/views/RandomMenu.fxml";
			break;

		case "shoppingList":
			// newPage = "/views/ShoppingList.fxml";
			break;
		}

		this.switchToNewPage(event, newPage);
	}
	
	@FXML
	public void redirectToMyAccount(Event event) {
		this.switchToNewPage(event, "/views/MyAccountPage.fxml");
	}

	public void setUsername(String username) {

		this.username.setText(username);
	}

	public void switchToNewPage(Event event, String newPage) {

		Parent root;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(newPage));

			root = loader.load();

			if (newPage.equals("/views/HomePage.fxml")) {
				HomePageController controller = loader.getController();
				controller.setIdTop1(facade.findTop1Recipe().getIdRecipe());
			}
			
			Scene scene = new Scene(root, 1920, 1080);

			Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

			newStage.setScene(scene);
			newStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void quickSearchWithKeyEnter(KeyEvent event) {
		if (!username.getText().isEmpty() && event.getCode() == KeyCode.ENTER) {
			this.quickSearch(event);
		}
	}

	@FXML
	void quickSearch(Event event) {

		ArrayList<Recipe> results = facade.searchRecipes(quickSearch.getText());
		
		// switch to quick search page with the results
		Parent root;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/QuickSearchResultsPage.fxml"));

			root = loader.load();
			
			QuickSearchController controller = loader.getController();
			controller.setResults(results);
			controller.setResultsInTableView();
			
			Scene scene = new Scene(root, 1920, 1080);
			Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			newStage.setScene(scene);
			newStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		this.setUsername(User.getSession().getUsername());
	}

	@FXML
	void logout(Event event) {
		User.setSession(null);
		this.switchToNewPage(event, "/views/LoginPage.fxml");

	}
}