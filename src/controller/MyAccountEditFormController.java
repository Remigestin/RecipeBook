package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import businessLogic.User;
import facade.UserFacade;
import javafx.event.Event;
import javafx.scene.control.PasswordField;

public class MyAccountEditFormController implements Initializable {
	@FXML
	private Button cancel;
	@FXML
	private PasswordField oldpassword;
	@FXML
	private PasswordField newpassword;
	@FXML
	private PasswordField newpasswordconfirmation;
	@FXML
	private TextField firstname;
	@FXML
	private TextField lastname;
	@FXML
	private Button submit;

	public void setFirstname(String firstname) {
		this.firstname.setText(firstname);
	}

	public void setLastname(String lastname) {
		this.lastname.setText(lastname);
	}

	private UserFacade facade = UserFacade.getInstance();

	// Event Listener on Button[#submit].onAction
	@FXML
	public void submit(Event event) {

		if (this.checkOldPassword() && this.checkNewPasswordMatching()) {

			User user = new User();
			user.setPassword(newpassword.getText());
			user.setFirstname(firstname.getText());
			user.setLastname(lastname.getText());

			facade.editAccount(user);
		}
	}

	// Event Listener on Button[#cancel].onAction
	@FXML
	public void cancel(Event event) {
		Parent root;
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MyAccountPage.fxml"));
			root = loader.load();
			Scene scene = new Scene(root, 1920, 1080);
			Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			newStage.setScene(scene);
			newStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean checkOldPassword() {

		if (!oldpassword.getText().equals(User.getSession().getPassword())) {
			this.displayOldPasswordWrong();
			return false;
		} else {
			return true;
		}
	}

	private boolean checkNewPasswordMatching() {

		if (!newpassword.getText().equals(newpasswordconfirmation.getText())) {
			this.displayNewPasswordsNotMatching();
			return false;
		} else {
			return true;
		}
	}

	private void displayOldPasswordWrong() {

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Wait ! ");
		alert.setHeaderText("Old password is wrong !");
		alert.showAndWait();
	}

	private void displayNewPasswordsNotMatching() {

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Wait ! ");
		alert.setHeaderText("New passwords not matching !");
		alert.showAndWait();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.setFirstname(User.getSession().getFirstname());
		this.setLastname(User.getSession().getLastname());
	}
}
