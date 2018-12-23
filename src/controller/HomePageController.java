package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class HomePageController {
	@FXML
	private Label username;
	
	public void setUsername(String username) {
		this.username.setText(username);
	}
	
}
