package controller;

import javafx.event.Event;
import javafx.fxml.FXML;

public class HomePageController implements ControllerInterface {
	
	@FXML
	private CommonThemeController commonThemeController;

	@FXML
	private FooterController footerController;
	
	@Override
	public void setUsername(String username) {
		commonThemeController.setUsername(username);
	}

	@Override
	public void switchToNewPage(Event event, String newPage) {
		
	}
}