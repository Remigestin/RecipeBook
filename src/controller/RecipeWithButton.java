package controller;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.Event;

public class RecipeWithButton {

	private int idRecipe;
	private String nameRecipe;
	private int preparationTime;
	private int difficulty;
	private Button editButton;

	public RecipeWithButton(int idRecipe, String nameRecipe, Integer preparationTime, Integer difficulty, String buttonType) {
		
		this.idRecipe = idRecipe;
		this.nameRecipe = nameRecipe;
		this.preparationTime = preparationTime;
		this.difficulty = difficulty;
		
		this.editButton = new Button(buttonType);
		
		
		this.editButton.setOnAction(new EventHandler<ActionEvent>() {
			
				@Override public void handle(ActionEvent event) {
					
					if(buttonType.equals("edit")) {
						
					
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
					
					}else if(buttonType.equals("remove")){
						FavoritesController controller = new FavoritesController();
						controller.deleteFavoriteRecipe(event);
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

	public Button getEditButton() {
		return editButton;
	}

	public void setEditButton(Button editButton) {
		this.editButton = editButton;
	}
}
