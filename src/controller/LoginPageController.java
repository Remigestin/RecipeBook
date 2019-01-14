package controller;

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
import facade.LoginPageFacade;
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

	private LoginPageFacade facade = LoginPageFacade.getInstance();

	// Event Listener on TextField[#password].HeyPressed
	@FXML
	void loginWithEnterKey(KeyEvent event) {

		if (!username.getText().isEmpty() && event.getCode() == KeyCode.ENTER) {
			this.login(event);
		}
	}

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

	private void displayError() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error alert");
		alert.setHeaderText("Username and password not matching !");
		alert.showAndWait();
	}

	@FXML
	void register(Event event) {

		this.switchToNewPage(event, "/views/RegisterPage.fxml");

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