package abstractDAO;

import java.util.ArrayList;

import businessLogic.Recipe;

public abstract class AbstractRecipeDAO {
	
	public abstract ArrayList<Recipe> loadFavoriteRecipe(int idUser);

}
