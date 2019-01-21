package abstractDAO;

import java.util.ArrayList;
import businessLogic.CookingStep;

/**
 * 
 * @author Chawaf Alia
 * @version 1.0
 */

public abstract class AbstractCookingStepDAO {

	/**
	 * 
	 * @param idRecipe
	 * @see businessLogic.CookingStep
	 * @return the list of cooking steps of the recipe with the id in parameter
	 */
	public abstract ArrayList<CookingStep> loadCookingStepsOfRecipe(int idRecipe);

	/**
	 * Add the new cooking steps when a recipe is created
	 * @param newSteps list of the cooking steps of the new recipe created
	 * @param idRecipe id of the new recipe created
	 * @see businessLogic.CookingStep
	 */
	public abstract void createCookingStep(ArrayList<CookingStep> newSteps, int idRecipe);

	/**
	 * Edit the cooking steps when a recipe is edited
	 * @param editedSteps list of the edited cooking steps 
	 * @see businessLogic.CookingStep
	 */
	public abstract void editCookingStep(ArrayList<CookingStep> editedSteps);
}