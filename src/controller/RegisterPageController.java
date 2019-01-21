package controller;

/**
 * This class is the controller of the RecipeEditingForm view
 * 
 * This view is display all recipe information and allows the user to edit them
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
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

import facade.UserFacade;
import javafx.event.Event;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.control.Hyperlink;

public class RegisterPageController {
	@FXML
	private TextField firstname;
	@FXML
	private TextField lastname;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private PasswordField passwordConfirmation;
	@FXML
	private Button register;
	@FXML
	private Hyperlink login;

	private UserFacade facade = UserFacade.getInstance();


	/**
	 * Method called when pressing a key (from keyboard) in the second password field
	 * Check if the key pressed is "Enter" 
	 * Register if yes, otherwise do nothing
	 * 
	 * @param event
	 * @see #register(Event)
	 */
	@FXML
	public void registerWithEnterKey(KeyEvent event) {
		if (!username.getText().isEmpty() && event.getCode() == KeyCode.ENTER) {
			this.register(event);
		}
	}

	/**
	 * Called when clicking on Register button 
	 * Check in username entered is not already used and if passwords are matching before registering
	 * 
	 * @param event
	 * @see #switchToNewPage(Event, String)
	 */
	@FXML
	public void register(Event event) {

		if (this.checkUsernameAlreadyUsed() && this.checkPasswordMatching()) {

			facade.register(firstname.getText(), lastname.getText(), username.getText(), password.getText());

			this.switchToNewPage(event, "/views/LoginPage.fxml");
		}
	}

	/**
	 * Check password confirmation 
	 * 
	 * @return true if both password entered match, else return false
	 */
	private boolean checkPasswordMatching() {

		if (!password.getText().equals(passwordConfirmation.getText())) {
			displayPasswordsNotMatching();
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Check if username already exists in DB
	 * 
	 * @return true if username is aready user, else false
	 */
	private boolean checkUsernameAlreadyUsed() {

		if (facade.findUsername(username.getText())) {
			displayUsernameAlreadyUsed();
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Display a pop-up error when passwords are not matching
	 */
	private void displayPasswordsNotMatching() {

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Wait ! ");
		alert.setHeaderText("Passwords not matching !");
		alert.showAndWait();
	}

	/**
	 * Display a pop-up error when username is already used
	 */
	private void displayUsernameAlreadyUsed() {

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Wait ! ");
		alert.setHeaderText("Username already used !");
		alert.showAndWait();
	}

	/**
	 * Called when clickin on Login text
	 * Redirect to LoginPage
	 * 
	 * @param event
	 * @see #switchToNewPage(Event, String)
	 */
	@FXML
	public void login(Event event) {
		this.switchToNewPage(event, "/views/LoginPage.fxml");
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
