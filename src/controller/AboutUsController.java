package controller;

/**
 * This class is the controller of the AboutUs view
 * @author Chawaf Alia
 * @version 1.0
 */

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

	/**
	 * Method called when clicking on Login button or HomePage button Redirect to
	 * the right page depending on the button clicked
	 * 
	 * @param event
	 * @see #switchToNewPage(Event, String)
	 */
	@FXML
	public void redirectToNewPage(ActionEvent event) {

		if (((Button) event.getSource()).getText().equals("Login")) {
			this.switchToNewPage(event, "/views/LoginPage.fxml");
		} else {
			this.switchToNewPage(event, "/views/HomePage.fxml");
		}
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

	/**
	 * Hide the Login button if the user is logged, otherwise hide the HomePage
	 * button
	 */
	private void setButton() {

		if (User.getSession() == null) {
			homePage.setVisible(false);
		} else {
			login.setVisible(false);
		}
	}

	/**
	 * Set the right button when the class is loaded {@inheritDoc}
	 * 
	 * @see #setButton()
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		this.setButton();
	}
}
