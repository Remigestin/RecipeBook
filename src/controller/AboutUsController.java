package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import businessLogic.User;
import javafx.event.ActionEvent;
import javafx.event.Event;

public class AboutUsController implements Initializable {
	@FXML
	private Button login;
	@FXML
	private Button homePage;

	// Event Listener on Button[#login].onAction
	// Event Listener on Button[#login1].onAction
	@FXML
	public void redirectToNewPage(ActionEvent event) {

		if (((Button) event.getSource()).getText().equals("Login")) {
			this.switchToNewPage(event, "/views/LoginPage.fxml");
		} else {
			this.switchToNewPage(event, "/views/HomePage.fxml");
		}
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
	
	private void setButton() {
		
		if (User.getSession() == null) {
			homePage.setVisible(false);
		} else {
			login.setVisible(false);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		this.setButton();
		
	}

}
