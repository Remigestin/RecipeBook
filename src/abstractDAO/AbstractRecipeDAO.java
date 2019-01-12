package abstractDAO;

import java.util.ArrayList;
import java.util.HashMap;

import businessLogic.Recipe;

public abstract class AbstractRecipeDAO {

	public abstract ArrayList<Recipe> loadFavoriteRecipe(int idUser);

	public abstract ArrayList<Recipe> loadCreateRecipe(int idUser);

	public abstract Recipe findRecipe(int idRecipe);
	
	public abstract ArrayList<Recipe> createRecipe(Recipe recipe);
	
	public abstract String findCourseCategory(int idCourse);
	
	public abstract Recipe findRandomRecipe(int idCourse);
	
	public abstract Recipe findRandomRecipe(int idCourse, int idRecipe);
	
	public abstract int findRate(int idUser, int idRecipe);
	
	public abstract HashMap<Integer, String> findAllCourseCategory();

}
