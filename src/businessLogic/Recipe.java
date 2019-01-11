package businessLogic;

public class Recipe {

	private int idRecipe;
	private String nameRecipe;
	private int preparationTime;
	private int nbPersRecipe;
	private String image;
	private int idCourse;
	private int difficulty;

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

	public int getNbPersRecipe() {
		return nbPersRecipe;
	}

	public void setNbPersRecipe(int nbPersRecipe) {
		this.nbPersRecipe = nbPersRecipe;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getIdCourse() {
		return idCourse;
	}

	public void setIdCourse(int idCourse) {
		this.idCourse = idCourse;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

}
