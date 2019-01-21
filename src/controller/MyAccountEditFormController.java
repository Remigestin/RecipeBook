package controller;

/**
 * This class is the controller of the MyAccountEditForm view
 * This view displays allows to edit password (require to write the old one), and/or edit first name and last name
 *
 * @author Chawaf Alia
 * @version 1.0 
 */

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

	/**
	 * Method called when clicking on submit button
	 * Do some password checks and then edit user information 
	 * 
	 * @param event
	 * @see #edit(Event, String)
	 */
	// Event Listener on Button[#submit].onAction
	@FXML
	public void submit(Event event) {

		// if password is not edited, we set the current one, else we check fields and
		// set the new one
		if (newpassword.getText().equals("") && this.checkIfNamesChanged()) {

			this.edit(event, facade.findPassword(User.getSession().getId()));

		} else {

			if (this.checkOldPassword() && this.checkNewPasswordMatching()) {
				this.edit(event, newpassword.getText());
			}
		}
	}

	/**
	 * Method called to edit user information
	 * 
	 * @param event
	 * @param password the password(new or old) to set 
	 * @see #displayConfirmation()
	 * @see #switchToNewPage(Event, String)
	 */
	private void edit(Event event, String password) {

		User user = new User();

		user.setPassword(password);
		user.setId(User.getSession().getId());
		user.setFirstname(firstname.getText());
		user.setLastname(lastname.getText());

		facade.editAccount(user);
		this.displayConfirmation();
		this.switchToNewPage(event, "/views/MyAccountPage.fxml");
	}

	/**
	 * Method called when clicking on Cancel button
	 * Redirect to AccountPage
	 * 
	 * @param event
	 * @see #switchToNewPage(Event, String)
	 */
	@FXML
	public void cancel(Event event) {
		this.switchToNewPage(event, "/views/MyAccountPage.fxml");
	}

	/**
	 * Switch to another page
	 * 
	 * @param event   Event occurred
	 * @param newPage path of the new page to display
	 */
	private void switchToNewPage(Event event, String newPage) {
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
	 * Method called if a new password is input
	 * Check if old password entered is correct
	 * 
	 * @return true if the old password correct, otherwise return false
	 * @see #displayOldPasswordWrong()
	 */
	private boolean checkOldPassword() {

		String currentPwd = facade.findPassword(User.getSession().getId());

		if (oldpassword.getText().equals(currentPwd)) {
			return true;
		} else {
			this.displayOldPasswordWrong();
			return false;
		}
	}

	/**
	 * Method called if a new password is input
	 * Check if new password confirmation entered matches  
	 * 
	 * @return true if both new password input matches, otherwise return false
	 */
	private boolean checkNewPasswordMatching() {

		if (newpassword.getText().equals(newpasswordconfirmation.getText())) {
			return true;
		} else {
			this.displayNewPasswordsNotMatching();
			return false;
		}
	}

	/**
	 * Check if first name and/or last name has been edited
	 * 
	 * @return true if names changed, otherwise return false
	 */
	private boolean checkIfNamesChanged() {

		if (firstname.getText().equals(User.getSession().getFirstname())
				&& lastname.getText().equals(User.getSession().getLastname())) {

			return false;

		} else {
			return true;
		}
	}

	/**
	 * Display an error pop-up to inform that old password entered is wrong
	 */
	private void displayOldPasswordWrong() {

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Wait ! ");
		alert.setHeaderText("Old password is wrong !");
		alert.showAndWait();
	}

	/**
	 * Display an error pop-up to inform that new password confirmation is wrong
	 */
	private void displayNewPasswordsNotMatching() {

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Wait ! ");
		alert.setHeaderText("New passwords not matching !");
		alert.showAndWait();
	}

	/**
	 * Display a confirmation pop-up when editing succeed
	 */
	private void displayConfirmation() {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Information well edited !");
		alert.showAndWait();
	}

	/**
	 * {@inheritDoc}
	 * @see #setFirstname(String)
	 * @see #setLastname(String)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.setFirstname(User.getSession().getFirstname());
		this.setLastname(User.getSession().getLastname());
	}
}
