package controller;

/**
 * This class is the controller of the LoginPage view
 *  
 * @author Chawaf Alia
 * @version 1.0
 */

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

import businessLogic.User;
import facade.UserFacade;
import javafx.event.Event;
import javafx.scene.control.PasswordField;

public class LoginPageController {

	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private Button login;
	@FXML
	private Hyperlink register;

	/**
	 * Facade associated
	 */
	private UserFacade facade = UserFacade.getInstance();

	/**
	 * Method called when pressing a key (from keyboard) in the password field
	 * Check if the key pressed is "Enter" 
	 * Login if yes, otherwise do nothing
	 * 
	 * @param event
	 * @see #login(Event)
	 */
	// Event Listener on TextField[#password].HeyPressed
	@FXML
	void loginWithEnterKey(KeyEvent event) {

		if (!username.getText().isEmpty() && event.getCode() == KeyCode.ENTER) {
			this.login(event);
		}
	}

	/**
	 * Method called when clicking on Login button
	 * Try to login
	 * Redirect to HomePage if login succeed, otherwise display an error
	 * 
	 * @param event
	 * @see #switchToNewPage(Event, String)
	 * @see #displayError()
	 */
	// Event Listener on Button[#login].onAction
	@FXML
	public void login(Event event) {

		User user = facade.login(username.getText(), password.getText());

		if (user == null) {

			password.clear();
			this.displayError();

		} else {
			this.switchToNewPage(event, "/views/HomePage.fxml");
		}
	}

	/**
	 * Display an error when login failed
	 * 
	 */
	private void displayError() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error alert");
		alert.setHeaderText("Username and password not matching !");
		alert.showAndWait();
	}

	/**
	 * Method called when clicking on Register text
	 * Redirect to the RegisterPage
	 * 
	 * @param event
	 * @see #switchToNewPage(Event, String)
	 */
	@FXML
	void register(Event event) {

		this.switchToNewPage(event, "/views/RegisterPage.fxml");
	}

	/**
	 * Switch to another page
	 * 
	 * @param event   Event occurred
	 * @param newPage path of the new page to display
	 */
	public void switchToNewPage(Event event, String newPage) {

		Parent root;

		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource(newPage));

			root = loader.load();

			Scene scene = new Scene(root, 1920, 1080);
			Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			newStage.setScene(scene);
			newStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}