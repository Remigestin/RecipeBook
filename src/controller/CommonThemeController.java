package controller;

/**
 * This class is the controller of the CommonTheme view
 * CommonThem view represents the menu (on left) with all the tabs, 
 * the logo of the application, the quick search field, username icon and name, logout button 
 * 
 * @author Chawaf Alia
 * @version 1.0 
 */

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

	/**
	 * FXML elements of the CommonTheme view
	 */
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

	/**
	 * Facade associated
	 */
	private RecipeFacade facade = RecipeFacade.getInstance();

	public void setUsername(String username) {
		this.username.setText(username);
	}

	/**
	 * Method called when clicking on a tab of the menu Call switchToNewPage with
	 * the right path of the new Page depending on the tab clicked
	 * 
	 * @see #switchToNewPage(Event, String)
	 * @param event
	 */
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

	/**
	 * Method called when clicking on username icon or username name Redirect to
	 * account page with the user information
	 * 
	 * @param event
	 * @see #switchToNewPage(Event, String)
	 */
	@FXML
	public void redirectToMyAccount(Event event) {
		this.switchToNewPage(event, "/views/MyAccountPage.fxml");
	}

	/**
	 * Method used to switch to another page
	 * 
	 * @param event
	 * @param newPage path of the new page to display
	 */
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

	/**
	 * Method called when clicking on logout button Logout the user and then
	 * redirect to the LoginPage
	 * 
	 * @param event
	 * @see #switchToNewPage(Event, String)
	 */
	@FXML
	void logout(Event event) {
		User.setSession(null);
		this.switchToNewPage(event, "/views/LoginPage.fxml");
	}

	/**
	 * Method called when pressing a key (from keyboard) in the quick search field
	 * Check if the key pressed is "Enter" Do the quick search if yes, otherwise do
	 * nothing
	 * 
	 * @param event
	 * @see #quickSearch
	 */
	@FXML
	void quickSearchWithKeyEnter(KeyEvent event) {
		if (!username.getText().isEmpty() && event.getCode() == KeyCode.ENTER) {
			this.quickSearch(event);
		}
	}

	/**
	 * Method called when clicking of the OK button of the quick search field 
	 * Get the quick search results to set them and then switch to a new page to display them
	 * 
	 * @param event
	 */
	@FXML
	void quickSearch(Event event) {

		ArrayList<Recipe> results = facade.searchRecipes(quickSearch.getText());
		ArrayList<RecipeWithButton> finalResults = new ArrayList<RecipeWithButton>();

		for (Recipe r : results) {

			finalResults.add(new RecipeWithButton(r.getIdRecipe(), r.getNameRecipe(), r.getPreparationTime(),
					r.getDifficulty(), r.getIdCourse(), ""));

		}

		// switch to quick search page with the results
		Parent root;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/QuickSearchResultsPage.fxml"));

			root = loader.load();

			QuickSearchController controller = loader.getController();
			controller.setResults(finalResults);
			controller.setResultsInTableView();

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
	 * @see #setUsername(String)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		this.setUsername(User.getSession().getUsername());
	}
}