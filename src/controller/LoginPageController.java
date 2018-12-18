package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

import abstractDAO.AbstractUserDAO;
import businessLogic.User;
import factory.AbstractFactory;
import factory.MySQLFactory;
import javafx.event.ActionEvent;

import javafx.scene.control.PasswordField;

public class LoginPageController {

	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private Button login;

	// Event Listener on Button[#login].onAction
	@FXML
	public void login(ActionEvent event) {

		AbstractFactory f = new MySQLFactory();

		AbstractUserDAO userDAO = f.createUserDAO();

		User user = userDAO.login(username.getText(), password.getText());

		if (user == null) {

			username.clear();
			password.clear();
			this.displayError();

		} else {
			Parent root;
			try {
				
				FXMLLoader loader = new
				FXMLLoader(getClass().getResource("/views/HomePage.fxml"));
				//loader.setController(new HomePageController());
				root = loader.load();
				
				//root = FXMLLoader.load(getClass().getResource("/views/HomePage.fxml"));
				Scene scene = new Scene(root, 1920, 1080);

				Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

				newStage.setScene(scene);
				newStage.show();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void displayError() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error alert");
		alert.setHeaderText("Username and password not matching !");
		alert.showAndWait();
	}
}