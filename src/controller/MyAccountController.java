package controller;

/**
 * This class is the controller of the MyAccount view
 * This view displays all the information of the user logged in
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import businessLogic.User;
import facade.UserFacade;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.PasswordField;

public class MyAccountController implements Initializable {
	@FXML
	private Button delete;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private TextField firstname;
	@FXML
	private TextField lastname;
	@FXML
	private ImageView editButton;

	@FXML
	private CommonThemeController commonThemeController = new CommonThemeController();

	public void setUsername(String username) {
		this.username.setText(username);
	}

	public void setPassword(String password) {
		this.password.setText(password);
	}

	public void setFirstname(String firstname) {
		this.firstname.setText(firstname);
	}

	public void setLastname(String lastname) {
		this.lastname.setText(lastname);
	}

	private UserFacade facade = UserFacade.getInstance();

	/**
	 * Method called when clicking on Delete button Display a popup to ask for a
	 * confirmation
	 * 
	 * @param event
	 * @see #displayDeleteConfirmation(Event)
	 */
	@FXML
	public void delete(ActionEvent event) {
		this.displayDeleteConfirmation(event);
	}

	/**
	 * Method called when clicking on the Pencil button to edit account Redirect to
	 * editing form of the account
	 * 
	 * @param event
	 * @see #switchToNewPage(Event, String)
	 */
	@FXML
	void editAccount(Event event) {

		this.switchToNewPage(event, "/views/MyAccountEditForm.fxml");
	}

	/**
	 * Display a popup to ask for a confirmation before deleting account Delete
	 * account and redirect to LoginPage if clicking on OK, otherwise do nothing if
	 * clicking on Cancel button
	 * 
	 * @param event
	 */
	private void displayDeleteConfirmation(Event event) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Hey wait there ! ");
		alert.setHeaderText("Are you sure you want to delete your account ? This is irreversible !!!");

		Optional<ButtonType> response = alert.showAndWait();
		if (response.get() == ButtonType.OK) {
			facade.deleteAccount(User.getSession().getId());
			this.commonThemeController.logout(event);
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

	/**
	 * Method used to set all the information of the used logged in
	 */
	public void setAllUserInfo() {

		User user = User.getSession();

		this.setUsername(user.getUsername());
		this.setPassword(user.getPassword());
		this.setFirstname(user.getFirstname());
		this.setLastname(user.getLastname());
	}

	/**
	 * {@inheritDoc}
	 * @see #setAllUserInfo()
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		this.setAllUserInfo();
	}
}
