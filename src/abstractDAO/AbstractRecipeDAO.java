package abstractDAO;

import java.util.ArrayList;

import businessLogic.CookingStep;
import businessLogic.Recipe;

public abstract class AbstractRecipeDAO {
	
	public abstract ArrayList<Recipe> loadFavoriteRecipe(int idUser);
	public abstract ArrayList<Recipe> loadCreateRecipe(int idUser);
	
	public abstract ArrayList<CookingStep> loadCookingStepsRecipe(int idRecipe);

}
