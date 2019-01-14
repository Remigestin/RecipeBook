package controller;

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

import facade.LoginPageFacade;
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

	private LoginPageFacade facade = LoginPageFacade.getInstance();

	// Event Listener on PasswordField[#passwordConfirmation].onKeyPressed
	@FXML
	public void registerWithEnterKey(KeyEvent event) {
		if (!username.getText().isEmpty() && event.getCode() == KeyCode.ENTER) {
			this.register(event);
		}
	}

	// Event Listener on Button[#register].onAction
	@FXML
	public void register(Event event) {

		if (this.checkUsernameAlreadyUsed() && this.checkPasswordMatching()) {

			facade.register(firstname.getText(), lastname.getText(), username.getText(), password.getText());

			this.switchToNewPage(event, "/views/LoginPage.fxml");
		}
	}

	private boolean checkPasswordMatching() {

		if (!password.getText().equals(passwordConfirmation.getText())) {
			displayPasswordsNotMatching();
			return false;
		} else {
			return true;
		}
	}

	private boolean checkUsernameAlreadyUsed() {

		if (facade.findUsername(username.getText())) {
			displayUsernameAlreadyUsed();
			return false;
		} else {
			return true;
		}
	}

	private void displayPasswordsNotMatching() {

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Wait ! ");
		alert.setHeaderText("Passwords not matching !");
		alert.showAndWait();
	}

	private void displayUsernameAlreadyUsed() {

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Wait ! ");
		alert.setHeaderText("Username already used !");
		alert.showAndWait();
	}

	// Event Listener on Hyperlink[#login].onAction
	@FXML
	public void login(Event event) {
		this.switchToNewPage(event, "/views/LoginPage.fxml");
	}

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
