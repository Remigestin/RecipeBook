package facade;

import java.util.ArrayList;

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

		AbstractFactory f = MySQLFactory.getInstance();
		AbstractCookingStepDAO cookingStepDAO = f.createCookingStepDAO();

		return cookingStepDAO.loadCookingStepsOfRecipe(idRecipe);
	}

	public Recipe findRecipe(int idRecipe) {

		AbstractFactory f = MySQLFactory.getInstance();
		AbstractRecipeDAO recipeDAO = f.createRecipeDAO();

		return recipeDAO.findRecipe(idRecipe);
	}

	public ArrayList<Recipe> createRecipe(Recipe newRecipe, ArrayList<CookingStep> cookingSteps) {
		
		// Recipe DAO
		AbstractFactory f = MySQLFactory.getInstance();
		AbstractRecipeDAO recipeDAO = f.createRecipeDAO();
		
		// Cooking Step DAO
		AbstractCookingStepDAO cookingStepDAO = f.createCookingStepDAO();
		
		// Add recipe in DB 
		ArrayList<Recipe> newCreateList = recipeDAO.createRecipe(newRecipe);
		
		// Add cooking steps in DB with the new id Recipe
		int idNewRecipe = newCreateList.get(newCreateList.size()-1).getIdRecipe();
		cookingStepDAO.createCookingStep(cookingSteps,idNewRecipe);
		
		// Add new recipe in the user list of created recipes
		User.getSession().setCreateList(newCreateList);
		
		return newCreateList;
		
	}

	public String findCourseCategoryName(int idCourse) {

		AbstractFactory f = MySQLFactory.getInstance();
		AbstractRecipeDAO recipeDAO = f.createRecipeDAO();

		return recipeDAO.findCourseCategory(idCourse);

	}

	public void createComment(String text) {
		AbstractFactory f = MySQLFactory.getInstance();
		AbstractCommentDAO commentDAO = f.createCommentDAO();
		commentDAO.createComment(text);
	}

	public ArrayList<Commentary> showComment(int idRecipe) {
		AbstractFactory f = MySQLFactory.getInstance();
		AbstractCommentDAO commentDAO = f.createCommentDAO();

		return commentDAO.showcomment(idRecipe);
	}
}
