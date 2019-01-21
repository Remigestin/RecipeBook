package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.Rating;

import businessLogic.Recipe;
import facade.RecipeFacade;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import mySQLDAO.MySQLRecipeDAO;

/**
 * This class is the controller of the HomePage view This view displays the top1
 * recipe and the list of all the recipes of the application.
 * 
 * @author Chawaf Alia
 * @version 1.0
 */

public class HomePageController implements Initializable {

	/*
	 * elements of the top1 recipe
	 */
	@FXML
	private Rating ratingTop1;
	@FXML
	private Label preparationTimeTop1;
	@FXML
	private Label difficultyTop1;
	@FXML
	private Label nbPeopleTop1;
	@FXML
	private Label nameRecipeTop1;
	@FXML
	private ImageView imageTop1;

	private int idTop1;

	/*
	 * Elements of all the recipes of the app
	 */
	@FXML
	private TableView<RecipeWithButton> listRecipes;
	@FXML
	private TableColumn<RecipeWithButton, String> recipeName;
	@FXML
	private TableColumn<RecipeWithButton, String> courseCategory;
	@FXML
	private TableColumn<RecipeWithButton, String> rating;
	@FXML
	private TableColumn<RecipeWithButton, Integer> preparationTime;
	@FXML
	private TableColumn<RecipeWithButton, Integer> difficulty;

	/**
	 * Facade associated
	 */
	private RecipeFacade facade = RecipeFacade.getInstance();

	/**
	 * List of all the recipes to add in the table
	 */
	private ObservableList<RecipeWithButton> myRecipes = FXCollections.observableArrayList();

	/*
	 * Setters
	 */
	public void setRatingTop1(float rating) {
		this.ratingTop1.setRating(rating);
	}

	public void setPreparationTimeTop1(String preparationTime) {
		this.preparationTimeTop1.setText(preparationTime);
	}

	public void setDifficultyTop1(String difficulty) {
		this.difficultyTop1.setText(difficulty);
	}

	public void setNbPeopleTop1(String nbPeople) {
		this.nbPeopleTop1.setText(nbPeople);
	}

	public void setNameRecipeTop1(String nameRecipe) {
		this.nameRecipeTop1.setText(nameRecipe);
	}

	public void setImageTop1(String image) {
		this.imageTop1.setImage(new Image(image));
	}

	public void setIdTop1(int id) {
		idTop1 = id;
	}

	/**
	 * Method used to set all the information of the top1 recipe
	 */
	private void setTop1() {

		Recipe top1 = facade.findTop1Recipe();

		setDifficultyTop1(Integer.toString(top1.getDifficulty()) + " / 5");
		setNameRecipeTop1(top1.getNameRecipe());
		setNbPeopleTop1(Integer.toString(top1.getNbPersRecipe()));
		setPreparationTimeTop1(Integer.toString(top1.getPreparationTime()) + " min");
		setRatingTop1(MySQLRecipeDAO.findRating(top1.getIdRecipe()));
		setImageTop1("file:../../asset/imageRecette/" + top1.getImage());
	}

	/**
	 * Recover all recipes
	 * 
	 * @return the list of all the recipes of the application to add in the table
	 */
	public ObservableList<RecipeWithButton> getAllRecipes() {

		myRecipes.clear();
		for (Recipe r : facade.findAllRecipes()) {

			myRecipes.add(new RecipeWithButton(r.getIdRecipe(), r.getNameRecipe(), r.getPreparationTime(),
					r.getDifficulty(), r.getIdCourse(), ""));
		}
		return myRecipes;
	}

	/**
	 * Method called when clicking on a recipe of the table. Redirect to a new page
	 * displaying the recipe concerned with all its information
	 * 
	 * @param event
	 * @see RecipeController
	 */
	@FXML
	void consultRecipe(Event event) {

		Parent root;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RecipePage.fxml"));

			root = loader.load();

			RecipeController controller = loader.getController();

			int rowNumber = ((TableView) event.getSource()).getSelectionModel().selectedIndexProperty().get();

			// get the id of the recipe clicked from the row number of table clicked on
			controller.setIdRecipe(myRecipes.get(rowNumber).getIdRecipe());
			controller.consultRecipe();

			Scene scene = new Scene(root, 1920, 1080);

			Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

			newStage.setScene(scene);
			newStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method called when clicking on a the top1 recipe Redirect to a new page
	 * displaying the top1 recipe with all its information
	 * 
	 * @param event
	 * @see RecipeController
	 */
	@FXML
	void consultTop1(Event event) {

		Parent root;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RecipePage.fxml"));

			root = loader.load();

			RecipeController controller = loader.getController();

			controller.setIdRecipe(idTop1);
			controller.consultRecipe();

			Scene scene = new Scene(root, 1920, 1080);

			Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

			newStage.setScene(scene);
			newStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see #setTop1()
	 * @see #getAllRecipes()
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		this.setTop1();

		recipeName.setCellValueFactory(new PropertyValueFactory<RecipeWithButton, String>("nameRecipe"));
		courseCategory.setCellValueFactory(new PropertyValueFactory<RecipeWithButton, String>("course"));
		rating.setCellValueFactory(new PropertyValueFactory("rate"));
		preparationTime.setCellValueFactory(new PropertyValueFactory("preparationTime"));
		difficulty.setCellValueFactory(new PropertyValueFactory("difficulty"));

		listRecipes.setItems(getAllRecipes());
	}
}
