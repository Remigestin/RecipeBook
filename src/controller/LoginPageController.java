package controller;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import abstractDAO.AbstractUserDAO;
import businessLogic.User;
import factory.AbstractFactory;
import factory.MySQLFactory;
import javafx.event.ActionEvent;

import javafx.scene.control.PasswordField;

public class LoginPageController {
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private Button login;

	// Event Listener on Button[#login].onAction
	@FXML
	public void login(ActionEvent event) {
		
		AbstractFactory f = new MySQLFactory();
		
		AbstractUserDAO userDAO = f.createUserDAO();
		
		User user = userDAO.login(username.getText(), password.getText());
		
		System.out.println(user.getUsername());
		
		
	}
}
