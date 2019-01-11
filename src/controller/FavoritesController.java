package controller;

import java.net.URL;
import java.util.ResourceBundle;

import businessLogic.User;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class FavoritesController implements Initializable {

	@FXML
	private CommonThemeController commonThemeController = new CommonThemeController();
	
    @FXML
    private TextArea text;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
		text.setText("ok");
		
	}

}



