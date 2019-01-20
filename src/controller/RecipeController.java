package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import businessLogic.Commentary;
import businessLogic.CookingStep;
import businessLogic.Recipe;
import businessLogic.User;
import facade.FavoritesFacade;
import facade.RecipeFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import mySQLDAO.MySQLRecipeDAO;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.controlsfx.control.Rating;

public class RecipeController implements Initializable {

	/* FXML elements */
	@FXML
	private Label nameRecipe;
	@FXML
	private Label courseCategory;
	@FXML
	private ImageView image;
	@FXML
	private TextArea ingredients;
	@FXML
	private ListView cookingSteps;
	@FXML
	private Rating rating;
	@FXML
	private Label preparationTime;
	@FXML
	private Label difficulty;
	@FXML
	private Spinner numberPeople;
	@FXML
	private ImageView addToFavorite;
	@FXML
	private Pane frameAddFavoriteButton;
	@FXML
	private ImageView addToCart;

	@FXML
	private Rating ratingAdded;
	@FXML
	private ImageView editRatingAdded;
	@FXML
	private Pane frameEditRatingAdded;
	@FXML
	private ImageView deleteRatingAdded;
	@FXML
	private Pane frameDeleteRatingAdded;

	@FXML
	private TextArea textaddcomment;
	@FXML
	private Button buttonadd;
	@FXML
	private ImageView buttonEditRecipe;
	@FXML
	private Pane frameEditButton;
	@FXML
	private TableView<Commentary> tableView;
	@FXML
	private TableColumn<Commentary, String> ColoneUser;
	@FXML
	private TableColumn<Commentary, String> Date;
	@FXML
	private TableColumn<Commentary, String> Text;
	@FXML
	private TableColumn<RecipeWithButton, String> edit;

	/* attributes */
	private RecipeFacade facade = RecipeFacade.getInstance();
	private FavoritesFacade favoritesfacade = FavoritesFacade.getInstance();

	private int idRecipe;

	public void setIdRecipe(int idRecipe) {
		this.idRecipe = idRecipe;
	}

	/* Getters Setters */
	public void setNameRecipe(String nameRecipe) {
		this.nameRecipe.setText(nameRecipe);
	}

	public void setCourseCategory(String courseCategory) {
		this.courseCategory.setText(courseCategory);
	}

	public void setImage(String urlImage) {
		this.image.setImage(new Image(urlImage));
	}

	public void setRating(float rating) {
		this.rating.setRating(rating);
	}

	public void setPreparationTime(String preparationTime) {
		this.preparationTime.setText(preparationTime + "  min");
	}

	public void setDifficulty(String difficulty) {
		this.difficulty.setText(difficulty + " / 5");
	}

	public void setNumberPeople(int numberPeople) {
		this.numberPeople.getValueFactory().setValue(numberPeople);
	}

	public void setCookingSteps(ArrayList<CookingStep> cookingSteps) {

		int numberStep = 1;

		for (CookingStep c : cookingSteps) {

			if (c.getName() != null) {

				this.cookingSteps.getItems().add(numberStep + ". " + c.getName());
				this.cookingSteps.getItems().add(c.getDescription());

			} else {

				this.cookingSteps.getItems().add(numberStep + ". " + c.getDescription());

			}

			this.cookingSteps.getItems().add(""); // skip a line between each step
			numberStep++;
		}

	}

	public TextArea getTextaddcomment() {
		return textaddcomment;
	}

	public void setTextaddcomment(TextArea textaddcomment) {
		this.textaddcomment = textaddcomment;
	}

	public TableColumn<Commentary, String> getColoneUser() {
		return ColoneUser;
	}

	public void setColoneUser(TableColumn<Commentary, String> coloneUser) {
		ColoneUser = coloneUser;
	}

	public TableColumn<Commentary, String> getDate() {
		return Date;
	}

	public void setDate(TableColumn<Commentary, String> date) {
		Date = date;
	}

	public TableColumn<Commentary, String> getText() {
		return Text;
	}

	public void setText(TableColumn<Commentary, String> text) {
		Text = text;
	}

	/* initialize the spinner of numberPeople with integers */
	private void initSpinner() {
		numberPeople.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100));
	}

	/* methods */
	public void consultRecipe() {

		/* find and set cooking steps */
		cookingSteps.getItems().clear();
		this.setCookingSteps(facade.findCookingSteps(idRecipe));

		/* find recipe details and set them */
		Recipe recipe = facade.findRecipe(idRecipe);

		this.setNameRecipe(recipe.getNameRecipe());
		this.setNumberPeople(recipe.getNbPersRecipe());
		this.setPreparationTime(Integer.toString(recipe.getPreparationTime()));
		this.setDifficulty(Integer.toString(recipe.getDifficulty()));
		this.setRating(MySQLRecipeDAO.findRating(recipe.getIdRecipe()));
		this.setImage("file:../../asset/imageRecette/"+recipe.getImage());

		if (facade.getRate(idRecipe) == -1) { // if there is no rate from the user
			this.editRatingAdded.setOnMousePressed(e -> {
				this.addRating(null);
			});
			this.editRatingAdded.setImage(new Image("file:../../asset/image/add.png"));

			this.deleteRatingAdded.setVisible(false);
			this.frameDeleteRatingAdded.setVisible(false);
		} else {
			this.ratingAdded.setRating(facade.getRate(idRecipe));
			this.editRatingAdded.setOnMousePressed(e -> {
				this.editeRating(null);
			});
			this.editRatingAdded.setImage(new Image("file:../../asset/image/pencil.png"));
			this.deleteRatingAdded.setVisible(true);
			this.frameDeleteRatingAdded.setVisible(true);
		}

		this.deleteRatingAdded.setOnMousePressed(e -> {
			this.deleteRating(null);
		});
		
		this.setVisibleEditRecipeButton();
		this.setVisibleFavoriteRecipeButton();

		/* find main course and set it */
		this.setCourseCategory(facade.findCourseCategoryName(recipe.getIdCourse()));

		ColoneUser.setCellValueFactory(new PropertyValueFactory<Commentary, String>("idUser"));
		Date.setCellValueFactory(new PropertyValueFactory<Commentary, String>("date"));
		Text.setCellValueFactory(new PropertyValueFactory<Commentary, String>("text"));
		tableView.setItems(getComment());

	}

	private void addRating(Event event) {

		facade.rateARecipe(idRecipe, this.ratingAdded.getRating());
		this.consultRecipe();

	}

	private void editeRating(Event event) {
		facade.editRating(idRecipe, this.ratingAdded.getRating());
		this.consultRecipe();
	}

	private void deleteRating(Event event) {
		facade.deleteRating(idRecipe);
		this.ratingAdded.setRating(0);
		this.consultRecipe();
	}

	// Event Listener on Button[#buttonEdit].onAction
	@FXML
	private void editRecipe(Event event) {

		Parent root;

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RecipeEditingFormPage.fxml"));

			root = loader.load();

			RecipeEditingFormController controller = loader.getController();

			controller.setIdRecipe(idRecipe);
			controller.setAllRecipeInformation();

			Scene scene = new Scene(root, 1920, 1080);

			Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

			newStage.setScene(scene);
			newStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//add the recipe to the fav list
	@FXML
	void addFavoriteRecipe(Event event) {
		
		
		favoritesfacade.addFavoriteRecipe(User.getSession().getId(),idRecipe);
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Your recipe " + nameRecipe.getText() + " has been added to your favorites ! ");
		alert.showAndWait();



	}

	   
	// Event Listener on Button[#buttonadd].onAction
	@FXML
	public void addComment(ActionEvent event) {
		Calendar cal = Calendar.getInstance();
		facade.createComment(textaddcomment.getText(), cal, idRecipe, User.getSession().getId());
		textaddcomment.clear();
		tableView.setItems(getComment());
		tableView.refresh();

	}

	private ObservableList<Commentary> getComment() {

		ObservableList<Commentary> comments = FXCollections.observableArrayList();
		comments.addAll(facade.showComment(idRecipe));
		return comments;

	}
	/*
	public ObservableList<CommentWithButton> getComment() {

	
		for (Commentary c : User.getSession().getCreateList()) {

			comments.add(new CommentWithButton(c.getUser(), c.getText(), "edit"));
		}
		return comments;
		*/
	

	public void editComment(ArrayList<Commentary> listcomment) {

//			for (Commentary c : listcomment) {
		//
//				if (c.getName() != null) {
		//
//					this.cookingSteps.getItems().add(numberStep + ". " + c.getName());
//					this.cookingSteps.getItems().add(c.getDescription());
		//
//				} else {
		//
//					this.cookingSteps.getItems().add(numberStep + ". " + c.getDescription());
		//
//				}
		//
//				this.cookingSteps.getItems().add(""); // skip a line between each step
//				numberStep++;
	}

	/*
	 * public void showComment(Event e){
	 * 
	 * this.setComment(facade.showComment(1)); }
	 */

	public void setVisibleEditRecipeButton() {

		if (User.getSession().getId() != facade.findIdUserCreator(idRecipe)) {

			this.buttonEditRecipe.setVisible(false);
			this.frameEditButton.setVisible(false);
		}
	}
	
	/**
	 * hide the button to add a recipe in the favorites if it's already in it.
	 */
	public void setVisibleFavoriteRecipeButton() {

		if (facade.isFavorite(idRecipe,User.getSession().getId())) {

			this.addToFavorite.setVisible(false);
			this.frameAddFavoriteButton.setVisible(false);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		initSpinner();

	}

}
