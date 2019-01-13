package facade;

import java.util.ArrayList;
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

		// Update the user list of created recipes and favorite recipes
		User.getSession().setCreateList(newCreateList);
		User.getSession().setFavoriteList(recipeDAO.loadFavoriteRecipe(User.getSession().getId()));

		return newCreateList;

	}

	public String findCourseCategoryName(int idCourse) {

		return recipeDAO.findCourseCategory(idCourse);

	}

	public HashMap<Integer, String> findAllCourseCategory() {

		return recipeDAO.findAllCourseCategory();

	}

	public void createComment(String text) {

		commentDAO.createComment(text);
	}

	public ArrayList<Commentary> showComment(int idRecipe) {

		return commentDAO.showcomment(idRecipe);
	}

	public ArrayList<Recipe> searchRecipes(String search) {
		return recipeDAO.searchRecipes(search);
	}

	public boolean hasRatedRecipe(int idRecipe) {
		return recipeDAO.findRate(User.getSession().getId(), idRecipe) != -1;
	}

	public int getRate(int idRecipe) {
		return recipeDAO.findRate(User.getSession().getId(), idRecipe);
	}

	public void rateARecipe(int idRecipe, int ratingValue) {
		User session = User.getSession();
		recipeDAO.rateARecipe(idRecipe, session.getId(), ratingValue);

		// todo : refresh session

	}

}
