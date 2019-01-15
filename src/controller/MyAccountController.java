package controller;

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
import facade.LoginPageFacade;
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

	private LoginPageFacade facade = LoginPageFacade.getInstance();

	// Event Listener on Button[#delete].onAction
	@FXML
	public void delete(ActionEvent event) {
		this.displayDeleteConfirmation(event);
	}

	@FXML
	void editAccount(Event event) {

		this.switchToNewPage(event, "/views/MyAccountEditForm.fxml");
	}

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

	public void setAllUserInfo() {

		User user = User.getSession();

		this.setUsername(user.getUsername());
		this.setPassword(user.getPassword());
		this.setFirstname(user.getFirstname());
		this.setLastname(user.getLastname());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		this.setAllUserInfo();
	}
}
