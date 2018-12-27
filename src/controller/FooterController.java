package controller;

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

public class FooterController implements ControllerInterface {

	@FXML
	private Text aboutUs;
	@FXML
	private Text contactUs;

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

	@Override
	public void switchToNewPage(Event event, String newPage) {

		Parent root;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(newPage));
			System.out.println(newPage);
			root = loader.load();
			Scene scene = new Scene(root, 1920, 1080);
			Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			newStage.setScene(scene);
			newStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setUsername(String username) {
	}
}
