package facade;

import java.util.ArrayList;

import abstractDAO.AbstractCommentDAO;
import abstractDAO.AbstractCookingStepDAO;
import abstractDAO.AbstractRecipeDAO;
import businessLogic.Commentary;
import businessLogic.CookingStep;
import businessLogic.Recipe;
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

	public String findCourseCategoryName(int idCourse) {

		AbstractFactory f = MySQLFactory.getInstance();
		AbstractRecipeDAO recipeDAO = f.createRecipeDAO();

		return recipeDAO.findCourseCategory(idCourse);

	}

	public void createComment(String text){
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
