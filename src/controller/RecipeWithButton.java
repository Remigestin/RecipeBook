package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import mySQLDAO.MySQLRecipeDAO;

import java.io.IOException;

import facade.RecipeFacade;
import javafx.event.ActionEvent;
import javafx.event.Event;

public class RecipeWithButton {

	private int idRecipe;
	private String nameRecipe;
	private int preparationTime;
	private int difficulty;
	private String course;
	private float rate;
	private Button editButton;

	public RecipeWithButton(int idRecipe, String nameRecipe, Integer preparationTime, Integer difficulty, int idCourse,
			String buttonType) {

		this.idRecipe = idRecipe;
		this.nameRecipe = nameRecipe;
		this.preparationTime = preparationTime;
		this.difficulty = difficulty;

		this.editButton = new Button(buttonType);

		RecipeFacade facade = RecipeFacade.getInstance();
		this.course = facade.findCourseCategoryName(idCourse);
		this.rate = MySQLRecipeDAO.findRating(idRecipe);

		this.editButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (buttonType.equals("edit")) {

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

				} else if (buttonType.equals("remove")) {

					Parent root;

					try {
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Favorites.fxml"));

						root = loader.load();

						FavoritesController controller = loader.getController();

						controller.setIdRecipe(idRecipe);
						controller.deleteFavoriteRecipe((Event) event);

						Scene scene = new Scene(root, 1920, 1080);

						Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

						newStage.setScene(scene);
						newStage.show();

					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		});

	}

	public int getIdRecipe() {
		return idRecipe;
	}

	public void setIdRecipe(int idRecipe) {
		this.idRecipe = idRecipe;
	}

	public String getNameRecipe() {
		return nameRecipe;
	}

	public void setNameRecipe(String nameRecipe) {
		this.nameRecipe = nameRecipe;
	}

	public int getPreparationTime() {
		return preparationTime;
	}

	public void setPreparationTime(int preparationTime) {
		this.preparationTime = preparationTime;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public Button getEditButton() {
		return editButton;
	}

	public void setEditButton(Button editButton) {
		this.editButton = editButton;
	}
}
