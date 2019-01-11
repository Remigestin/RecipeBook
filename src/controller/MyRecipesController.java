package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import businessLogic.Recipe;
import businessLogic.User;
import facade.RecipeFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MyRecipesController implements Initializable {

	@FXML
	private TableView<Recipe> listRecipes;

	@FXML
	private TableColumn<Recipe, String> recipeName;

	@FXML
	private TableColumn<Recipe, String> rating;

	@FXML
	private TableColumn<Recipe, Integer> preparationTime;

	@FXML
	private TableColumn<Recipe, Integer> difficulty;

	private RecipeFacade facade = RecipeFacade.getInstance();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		recipeName.setCellValueFactory(new PropertyValueFactory<Recipe, String>("nameRecipe"));
		// rating.setCellValueFactory(new PropertyValueFactory<Recipe, String>(""));
		preparationTime.setCellValueFactory(new PropertyValueFactory("preparationTime"));
		difficulty.setCellValueFactory(new PropertyValueFactory("difficulty"));
		listRecipes.setItems(getRecipes());

	}

	public ObservableList<Recipe> getRecipes() {

		ObservableList<Recipe> recipes = FXCollections.observableArrayList();

		recipes.addAll(User.getSession().getCreateList());

		return recipes;

	}

	@FXML
	void consultRecipe(Event event) {

		System.out.print(((TableView) event.getSource()).getSelectionModel().selectedIndexProperty().get());

		this.switchToNewPage(event, "/views/RecipePage.fxml");
	}

	public int getIdRecipeByRowNumber(int rowNumber) {

		return User.getSession().getCreateList().get(rowNumber).getIdRecipe();

	}

	public void switchToNewPage(Event event, String newPage) {

		Parent root;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(newPage));

			root = loader.load();

			RecipeController controller = loader.getController();

			int rowNumber = ((TableView) event.getSource()).getSelectionModel().selectedIndexProperty().get();
			int idRecipe = this.getIdRecipeByRowNumber(rowNumber);
			controller.consultRecipe(idRecipe);

			Scene scene = new Scene(root, 1920, 1080);

			Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

			newStage.setScene(scene);
			newStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
