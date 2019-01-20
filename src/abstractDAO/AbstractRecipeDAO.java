package abstractDAO;

import java.util.ArrayList;
import java.util.HashMap;

import businessLogic.Recipe;
/**
 * 
 * @author MISSOUM BENZIANE Ines
 * @author gestin remi
 *
 */
public abstract class AbstractRecipeDAO {

	/**
	 * 
	 * @param idUser
	 * @return the list of favorite recipe from the user in param.
	 */
	public abstract ArrayList<Recipe> loadFavoriteRecipe(int idUser);

	/**
	 * 
	 * @param idUser
	 * @return the list of recipe created by the user in param.
	 */
	public abstract ArrayList<Recipe> loadCreateRecipe(int idUser);
	
	/**
	 * 
	 * @param search
	 * @return return the recipe which corresponds to the search.
	 */
	public abstract ArrayList<Recipe> searchRecipes(String search);
	
	/**
	 * 
	 * @return all the recipes of the application.
	 */
	public abstract ArrayList<Recipe> findAllRecipes();

	/**
	 * 
	 * @param idRecipe
	 * @return return the recipe which have the id in param.
	 */
	public abstract Recipe findRecipe(int idRecipe);
	
	/**
	 * 
	 * @param idRecipe id of the recipe
	 * @param idUser id of the user
	 * @return true if the recipe is a favorite recipe of the user in the DB, otherwise return false
	 */
	public abstract boolean isFavorite(int idRecipe, int idUser);
	
	public abstract int findIdUserCreator(int idRecipe);
	
	public abstract ArrayList<Recipe> createRecipe(Recipe recipe);
	
	public abstract ArrayList<Recipe> editRecipe(Recipe recipe);
	
	public abstract String findCourseCategory(int idCourse);
	
	/**
	 * 
	 * @param idCourse
	 * @return a random recipe of the category given in param.
	 */
	public abstract Recipe findRandomRecipe(int idCourse);
	
	/**
	 * 
	 * @param idCourse
	 * @param idRecipe
	 * @return a random recipe of the category given in param and which is different from the recipe given in param.
	 */
	public abstract Recipe findRandomRecipe(int idCourse, int idRecipe);
	
	/**
	 * 
	 * @param idUser
	 * @param idRecipe
	 * @return the rate of the user about the recipe given in param. if there is no note, it's return -1.
	 */
	public abstract int findRate(int idUser, int idRecipe);
	
	/**
	 * add the rate of the user on the recipe given in param. 
	 * @param idRecipe
	 * @param idUser
	 * @param ratingValue
	 */
	public abstract void rateARecipe(int idRecipe,int idUser,double ratingValue);
	
	/**
	 * edit the rate of the user on the recipe given in param.
	 * @param idRecipe
	 * @param idUser
	 * @param ratingValue
	 */
	public abstract void editRating(int idRecipe,int idUser, double ratingValue);
	
	/**
	 * delete the rate of the user on the recipe given in param.
	 * @param idRecipe
	 * @param idUser
	 */
	public abstract void deleteRating(int idRecipe,int idUser);
	
	public abstract HashMap<Integer, String> findAllCourseCategory();
	
	public abstract Recipe findTop1Recipe();

}
