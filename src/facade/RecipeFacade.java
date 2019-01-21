package facade;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import abstractDAO.AbstractCommentDAO;
import abstractDAO.AbstractCookingStepDAO;
import abstractDAO.AbstractRecipeDAO;
import businessLogic.Commentary;
import businessLogic.CookingStep;
import businessLogic.Recipe;
import businessLogic.User;
import factory.AbstractFactory;
import factory.MySQLFactory;

/**
 * 
 * @author gestin remi
 * @author Chawaf Alia
 * @author Gardeisen Marine
 * @version 1.0
 */
public class RecipeFacade {

	AbstractFactory f = MySQLFactory.getInstance();
	AbstractCookingStepDAO cookingStepDAO = f.createCookingStepDAO();
	AbstractRecipeDAO recipeDAO = f.createRecipeDAO();
	AbstractCommentDAO commentDAO = f.createCommentDAO();

	/* Private constructor */
	private RecipeFacade() {
	}

	/* Singleton Holder */
	private static class RecipeFacadeHolder {
		private final static RecipeFacade RecipeFacade = new RecipeFacade();
	}

	public static RecipeFacade getInstance() {
		return RecipeFacadeHolder.RecipeFacade;
	}

	/**
	 * Get cooking steps of a recipe
	 * @param idRecipe
	 * @return the list of cooking steps of the recipe with the id in parameter
	 */
	public ArrayList<CookingStep> findCookingSteps(int idRecipe) {

		return cookingStepDAO.loadCookingStepsOfRecipe(idRecipe);
	}

	/**
	 * 
	 * @param idRecipe
	 * @return the recipe given in param.
	 */
	public Recipe findRecipe(int idRecipe) {

		return recipeDAO.findRecipe(idRecipe);
	}

	/**
	 * 
	 * @return all the recipes of the application.
	 */
	public ArrayList<Recipe> findAllRecipes() {
		
		return recipeDAO.findAllRecipes();
	}

	/**
	 * Add a new recipe
	 * 
	 * @param newRecipe
	 * @param cookingSteps
	 * @return the updated list of recipes created (with the new one) of the user
	 *         logged in
	 */
	public ArrayList<Recipe> createRecipe(Recipe newRecipe, ArrayList<CookingStep> cookingSteps) {

		// Add recipe in DB
		ArrayList<Recipe> newCreateList = recipeDAO.createRecipe(newRecipe);

		// Add cooking steps in DB with the new id Recipe
		int idNewRecipe = newCreateList.get(newCreateList.size() - 1).getIdRecipe();
		cookingStepDAO.createCookingStep(cookingSteps, idNewRecipe);

		// Add new recipe in the user list of created recipes
		User.getSession().setCreateList(newCreateList);

		return newCreateList;

	}

	/**
	 * Edit a recipe
	 * 
	 * @param recipeEdited
	 * @param cookingStepsEdited
	 * @return the updated list of recipes created (with the edit one) of the user
	 *         logged in
	 */
	public ArrayList<Recipe> editRecipe(Recipe recipeEdited, ArrayList<CookingStep> cookingStepsEdited) {

		// Update recipe in DB
		ArrayList<Recipe> newCreateList = recipeDAO.editRecipe(recipeEdited);

		// Update cooking steps in DB
		cookingStepDAO.editCookingStep(cookingStepsEdited);

		this.refreshSession();

		return newCreateList;

	}

	/**
	 * 
	 * @param idCourse
	 * @return the name of the course category with the id in parameter
	 */
	public String findCourseCategoryName(int idCourse) {

		return recipeDAO.findCourseCategory(idCourse);

	}

	/**
	 * Get all the course categories
	 * @return a list of all the couples (id of a course category, name of the course category corresponding)
	 */
	public HashMap<Integer, String> findAllCourseCategory() {

		return recipeDAO.findAllCourseCategory();
	}

	/**
	 * 
	 * @param idRecipe id of the recipe
	 * @param idUser   id of the user
	 * @return true if the recipe is a favorite recipe of the user
	 */
	public boolean isFavorite(int idRecipe, int idUser) {

		return recipeDAO.isFavorite(idRecipe, idUser);
	}

	/**
	 * 
	 * @param idRecipe
	 * @return the id of the user who has created the recipe with the id in
	 *         parameter
	 */
	public int findIdUserCreator(int idRecipe) {

		return recipeDAO.findIdUserCreator(idRecipe);
	}

	
	/**
	 * create a commentary for a recipe
	 * @param text
	 * @param cal
	 * @param idRecipe
	 * @param idUser
	 */
	public void createComment(String text, Calendar cal, int idRecipe, int idUser) {

		commentDAO.createComment(text, cal, idRecipe, idUser);
	}

	/**
	 * show all the comments from a recipe
	 * @param idRecipe
	 * @return a list of comments that correspond to the recipe in param
	 */
	public ArrayList<Commentary> showComment(int idRecipe) {

		return commentDAO.showcomment(idRecipe);
	}

	/**
	 * 
	 * @param search
	 * @return the recipes which corresponds to the search.
	 */
	public ArrayList<Recipe> searchRecipes(String search) {
		return recipeDAO.searchRecipes(search);
	}

	/**
	 * 
	 * @param idRecipe
	 * @return the rate of the user in session on the recipe given in param. return -1 if there is no rate.
	 */
	public int getRate(int idRecipe) {
		return recipeDAO.findRate(User.getSession().getId(), idRecipe);
	}

	/**
	 * add a rate between the user in session and the recipe given in param.
	 * @param idRecipe
	 * @param ratingValue
	 */
	public void rateARecipe(int idRecipe, double ratingValue) {
		User session = User.getSession();
		recipeDAO.rateARecipe(idRecipe, session.getId(), ratingValue);

		this.refreshSession();

	}

	/**
	 * edit the rate of the user in session on the recipe given in param.
	 * @param idRecipe
	 * @param ratingValue
	 */
	public void editRating(int idRecipe, double ratingValue) {
		User session = User.getSession();
		recipeDAO.editRating(idRecipe, session.getId(), ratingValue);

		this.refreshSession();

	}

	/**
	 * delete the rate of the user in session on the recipe given in param.
	 * @param idRecipe
	 */
	public void deleteRating(int idRecipe) {
		recipeDAO.deleteRating(idRecipe, User.getSession().getId());
		this.refreshSession();
	}

	/**
	 * refresh all the lists of the user in session.
	 */
	private void refreshSession() {

		// Update the user list of created recipes and favorite recipes
		User.getSession().setCreateList(recipeDAO.loadCreateRecipe(User.getSession().getId()));
		User.getSession().setFavoriteList(recipeDAO.loadFavoriteRecipe(User.getSession().getId()));

		// Update user random menu
		User.getSession().setRandomStarter(recipeDAO.findRecipe(User.getSession().getRandomStarter().getIdRecipe()));
		User.getSession().setRandomMain(recipeDAO.findRecipe(User.getSession().getRandomMain().getIdRecipe()));
		User.getSession().setRandomDessert(recipeDAO.findRecipe(User.getSession().getRandomDessert().getIdRecipe()));

	}

	/**
	 * 
	 * @return the recipe with the higher rating average
	 */
	public Recipe findTop1Recipe() {
		return recipeDAO.findTop1Recipe();
	}
}
