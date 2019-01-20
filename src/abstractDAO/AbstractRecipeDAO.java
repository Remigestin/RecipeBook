package abstractDAO;

import java.util.ArrayList;
import java.util.HashMap;

import businessLogic.Recipe;

/**
 * @author Chawaf Alia
 * @author MISSOUM BENZIANE Ines
 * @author gestin remi
 * @version 1.0
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
	 * @param idUser   id of the user
	 * @return true if the recipe is a favorite recipe of the user in the DB,
	 *         otherwise return false
	 */
	public abstract boolean isFavorite(int idRecipe, int idUser);

	/**
	 * Find the creator of a recipe
	 * 
	 * @param idRecipe
	 * @return the id of the user who has created the recipe with the id in
	 *         parameter
	 */
	public abstract int findIdUserCreator(int idRecipe);

	/**
	 * Add a new recipe in DB
	 * 
	 * @param recipe The new recipe created
	 * @return the updated list of recipes created (with the new one) of the user
	 *         logged in
	 */
	public abstract ArrayList<Recipe> createRecipe(Recipe recipe);

	/**
	 * Edit a recipe
	 * 
	 * @param recipe The recipe to edit with its new information
	 * @return the updated list of recipes created (with the edit one) of the user
	 *         logged in
	 */
	public abstract ArrayList<Recipe> editRecipe(Recipe recipe);

	/**
	 * 
	 * @param idCourse
	 * @return the name of the course category with the id in parameter
	 */
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
	 * @return a random recipe of the category given in param and which is different
	 *         from the recipe given in param.
	 */
	public abstract Recipe findRandomRecipe(int idCourse, int idRecipe);

	/**
	 * 
	 * @param idUser
	 * @param idRecipe
	 * @return the rate of the user about the recipe given in param. if there is no
	 *         note, it's return -1.
	 */
	public abstract int findRate(int idUser, int idRecipe);

	/**
	 * add the rate of the user on the recipe given in param.
	 * 
	 * @param idRecipe
	 * @param idUser
	 * @param ratingValue
	 */
	public abstract void rateARecipe(int idRecipe, int idUser, double ratingValue);

	/**
	 * edit the rate of the user on the recipe given in param.
	 * 
	 * @param idRecipe
	 * @param idUser
	 * @param ratingValue
	 */
	public abstract void editRating(int idRecipe, int idUser, double ratingValue);

	/**
	 * delete the rate of the user on the recipe given in param.
	 * 
	 * @param idRecipe
	 * @param idUser
	 */
	public abstract void deleteRating(int idRecipe, int idUser);

	/**
	 * Get all the course categories
	 * @return a list of all the couples (id of a course category, name of the course category corresponding)
	 */
	public abstract HashMap<Integer, String> findAllCourseCategory();

	/**
	 * Get the top1 recipe
	 * @return the recipe with the higher rating average
	 */
	public abstract Recipe findTop1Recipe();

}
