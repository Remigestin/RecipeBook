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

public class HomePageController implements Initializable {
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
	
	private RecipeFacade facade = RecipeFacade.getInstance();

	private ObservableList<RecipeWithButton> myRecipes = FXCollections.observableArrayList();
	
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

	private void setTop1() {
		
		Recipe top1 = facade.findTop1Recipe();
		
		setDifficultyTop1(Integer.toString(top1.getDifficulty()) + " / 5");
		setNameRecipeTop1(top1.getNameRecipe());
		setNbPeopleTop1(Integer.toString(top1.getNbPersRecipe()));
		setPreparationTimeTop1(Integer.toString(top1.getPreparationTime()) + " min");
		setRatingTop1(MySQLRecipeDAO.findRating(top1.getIdRecipe()));
		setImageTop1("file:../../asset/imageRecette/"+top1.getImage());
	}

	public ObservableList<RecipeWithButton> getAllRecipes() {

		myRecipes.clear();
		for (Recipe r : facade.findAllRecipes()) {

			myRecipes.add(new RecipeWithButton(r.getIdRecipe(), r.getNameRecipe(), r.getPreparationTime(),
					r.getDifficulty(), r.getIdCourse(), ""));
		}
		return myRecipes;
	}
	
	@FXML
	void consultRecipe(Event event) {

		Parent root;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RecipePage.fxml"));

			root = loader.load();

			RecipeController controller = loader.getController();

			int rowNumber = ((TableView) event.getSource()).getSelectionModel().selectedIndexProperty().get();
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
