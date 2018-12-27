package controller;

import javafx.event.Event;

public interface ControllerInterface {

	public void setUsername(String username);
	
	public void switchToNewPage(Event event, String newPage);
}
