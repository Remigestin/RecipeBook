package controller;

/**
 * This class is the controller of the Footer view
 * 
 * @author Chawaf Alia
 * @version 1.0
 */

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

public class FooterController {

	@FXML
	private Text aboutUs;
	@FXML
	private Text contactUs;

	/**
	 * Method called when clicking on AboutUs or ContactUs text It redirects to the
	 * right page depending on the text clicked
	 * 
	 * @param event
	 * @see #switchToNewPage(Event, String)
	 */
	// Event Listener on Text[#aboutUs].onMousePressed
	// Event Listener on Text[#contactUs].onMousePressed
	@FXML
	public void redirectToNewPage(MouseEvent event) {

		if (((Text) event.getSource()).getText().equals("About us")) {
			switchToNewPage(event, "/views/AboutUs.fxml");
		} else {
			switchToNewPage(event, "/views/ContactUs.fxml");
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
}
