package controller;

import javafx.scene.control.Button;

public class RecipeWithButton {

	private int idRecipe;
	private String nameRecipe;
	private int preparationTime;
	private int difficulty;
	private Button editButton;
	
	
	public RecipeWithButton(int idRecipe, String nameRecipe, Integer preparationTime, Integer difficulty) {
		
		this.idRecipe = idRecipe;
		this.nameRecipe = nameRecipe;
		this.preparationTime = preparationTime;
		this.difficulty = difficulty;
		this.editButton = new Button("edit");
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
