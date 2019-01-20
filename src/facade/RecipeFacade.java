package facade;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import abstractDAO.AbstractCommentDAO;
import abstractDAO.AbstractCookingStepDAO;
import abstractDAO.AbstractRecipeDAO;
import abstractDAO.AbstractUserDAO;
import businessLogic.Commentary;
import businessLogic.CookingStep;
import businessLogic.Recipe;
import businessLogic.User;
import factory.AbstractFactory;
import factory.MySQLFactory;
import mySQLDAO.MySQLRecipeDAO;

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

	public ArrayList<CookingStep> findCookingSteps(int idRecipe) {

		return cookingStepDAO.loadCookingStepsOfRecipe(idRecipe);
	}

	public Recipe findRecipe(int idRecipe) {

		return recipeDAO.findRecipe(idRecipe);
	}

	public ArrayList<Recipe> findAllRecipes() {
		
		return recipeDAO.findAllRecipes();
	}

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

	public ArrayList<Recipe> editRecipe(Recipe recipeEdited, ArrayList<CookingStep> cookingStepsEdited) {

		// Update recipe in DB
		ArrayList<Recipe> newCreateList = recipeDAO.editRecipe(recipeEdited);

		// Update cooking steps in DB
		cookingStepDAO.editCookingStep(cookingStepsEdited);

		this.refreshSession();

		return newCreateList;

	}

	public String findCourseCategoryName(int idCourse) {

		return recipeDAO.findCourseCategory(idCourse);

	}

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

	public ArrayList<Recipe> searchRecipes(String search) {
		return recipeDAO.searchRecipes(search);
	}

	// return -1 if there is no rate
	public int getRate(int idRecipe) {
		return recipeDAO.findRate(User.getSession().getId(), idRecipe);
	}

	public void rateARecipe(int idRecipe, double ratingValue) {
		User session = User.getSession();
		recipeDAO.rateARecipe(idRecipe, session.getId(), ratingValue);

		this.refreshSession();

	}

	public void editRating(int idRecipe, double ratingValue) {
		User session = User.getSession();
		recipeDAO.editRating(idRecipe, session.getId(), ratingValue);

		this.refreshSession();

	}

	public void deleteRating(int idRecipe) {
		recipeDAO.deleteRating(idRecipe, User.getSession().getId());
		this.refreshSession();
	}

	private void refreshSession() {

		// Update the user list of created recipes and favorite recipes
		User.getSession().setCreateList(recipeDAO.loadCreateRecipe(User.getSession().getId()));
		User.getSession().setFavoriteList(recipeDAO.loadFavoriteRecipe(User.getSession().getId()));

		// Update user random menu
		User.getSession().setRandomStarter(recipeDAO.findRecipe(User.getSession().getRandomStarter().getIdRecipe()));
		User.getSession().setRandomMain(recipeDAO.findRecipe(User.getSession().getRandomMain().getIdRecipe()));
		User.getSession().setRandomDessert(recipeDAO.findRecipe(User.getSession().getRandomDessert().getIdRecipe()));

	}

	public Recipe findTop1Recipe() {
		return recipeDAO.findTop1Recipe();
	}

}
