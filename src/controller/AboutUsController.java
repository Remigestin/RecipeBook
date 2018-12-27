package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.Event;

public class AboutUsController implements ControllerInterface {
	@FXML
	private Button login;
	@FXML
	private Button register;

	// Event Listener on Button[#login].onAction
	// Event Listener on Button[#login1].onAction
	@FXML
	public void redirectToNewPage(ActionEvent event) {

		if (((Button) event.getSource()).getText().equals("Login")) {
			this.switchToNewPage(event, "/views/LoginPage.fxml");
		} else {
			this.switchToNewPage(event, "/views/Register.fxml");
		}
	}

	@Override
	public void setUsername(String username) {
		// TODO Auto-generated method stub

	}

	@Override
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
