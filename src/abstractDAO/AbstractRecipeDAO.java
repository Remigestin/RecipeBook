package abstractDAO;

import java.util.ArrayList;
import java.util.HashMap;

import businessLogic.Recipe;

public abstract class AbstractRecipeDAO {

	public abstract ArrayList<Recipe> loadFavoriteRecipe(int idUser);

	public abstract ArrayList<Recipe> loadCreateRecipe(int idUser);
	
	public abstract ArrayList<Recipe> searchRecipes(String search);

	public abstract Recipe findRecipe(int idRecipe);
	
	public abstract boolean isFavorite(int idRecipe, int idUser);
	
	public abstract int findIdUserCreator(int idRecipe);
	
	public abstract ArrayList<Recipe> createRecipe(Recipe recipe);
	
	public abstract ArrayList<Recipe> editRecipe(Recipe recipe);
	
	public abstract String findCourseCategory(int idCourse);
	
	public abstract Recipe findRandomRecipe(int idCourse);
	
	public abstract Recipe findRandomRecipe(int idCourse, int idRecipe);
	
	public abstract int findRate(int idUser, int idRecipe);
	
	public abstract void rateARecipe(int idRecipe,int idUser,double ratingValue);
	
	public abstract void editRating(int idRecipe,int idUser, double ratingValue);
	
	public abstract void deleteRating(int idRecipe,int idUser);
	
	public abstract HashMap<Integer, String> findAllCourseCategory();

}
