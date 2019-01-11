package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class FavoritesController implements Initializable, ControllerInterface{

    @FXML
    private TextArea text;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		text.setText("ok");
		
	}

	@Override
	public void switchToNewPage(Event event, String newPage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUsername(String username) {
		// TODO Auto-generated method stub
		
	}
}



