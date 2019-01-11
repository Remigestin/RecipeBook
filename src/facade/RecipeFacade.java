package facade;

import java.util.ArrayList;

import abstractDAO.AbstractCookingStepDAO;
import businessLogic.CookingStep;
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
}
